/*
 * Copyright (C) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.glassware;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.mirror.model.Command;
import com.google.api.services.mirror.model.Contact;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.MenuValue;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles POST requests from index.jsp
 *
 * @author Jenny Murphy - http://google.com/+JennyMurphy
 */
public class MainServlet extends HttpServlet {

  /**
   * Private class to process batch request results.
   * <p/>
   * For more information, see
   * https://code.google.com/p/google-api-java-client/wiki/Batch.
   */
  private final class BatchCallback extends JsonBatchCallback<TimelineItem> {
    private int success = 0;
    private int failure = 0;

    @Override
    public void onSuccess(TimelineItem item, HttpHeaders headers) throws IOException {
      ++success;
    }

    @Override
    public void onFailure(GoogleJsonError error, HttpHeaders headers) throws IOException {
      ++failure;
      LOG.info("Failed to insert item: " + error.getMessage());
    }
  }

  private static final Logger LOG = Logger.getLogger(MainServlet.class.getSimpleName());
  public static final String CONTACT_ID = "com.google.glassware.contact.java-quick-start";
  public static final String CONTACT_NAME = "Java Quick Start";

  private static final String PAGINATED_HTML =
      "<article class='auto-paginate'>"
      + "<h2 class='blue text-large'>Did you know...?</h2>"
      + "<p>Cats are <em class='yellow'>solar-powered.</em> The time they spend napping in "
      + "direct sunlight is necessary to regenerate their internal batteries. Cats that do not "
      + "receive sufficient charge may exhibit the following symptoms: lethargy, "
      + "irritability, and disdainful glares. Cats will reactivate on their own automatically "
      + "after a complete charge cycle; it is recommended that they be left undisturbed during "
      + "this process to maximize your enjoyment of your cat.</p><br/><p>"
      + "For more cat maintenance tips, tap to view the website!</p>"
      + "</article>";

  /**
   * Do stuff when buttons on index.jsp are clicked
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

    String userId = AuthUtil.getUserId(req);
    Credential credential = AuthUtil.newAuthorizationCodeFlow().loadCredential(userId);
    String message = "";

    if (req.getParameter("operation").equals("insertSubscription")) {

      // subscribe (only works deployed to production)
      try {
        MirrorClient.insertSubscription(credential, WebUtil.buildUrl(req, "/notify"), userId,
            req.getParameter("collection"));
        message = "Application is now subscribed to updates.";
      } catch (GoogleJsonResponseException e) {
        LOG.warning("Could not subscribe " + WebUtil.buildUrl(req, "/notify") + " because "
            + e.getDetails().toPrettyString());
        message = "Failed to subscribe. Check your log for details";
      }

    } else if (req.getParameter("operation").equals("deleteSubscription")) {

      // subscribe (only works deployed to production)
      MirrorClient.deleteSubscription(credential, req.getParameter("subscriptionId"));

      message = "Application has been unsubscribed.";

    } else if (req.getParameter("operation").equals("insertItem")) {
      LOG.fine("Inserting Timeline Item");
      TimelineItem timelineItem = new TimelineItem();

      if (req.getParameter("message") != null) {
        timelineItem.setText(req.getParameter("message"));
      }

      // Triggers an audible tone when the timeline item is received
      timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));

      if (req.getParameter("imageUrl") != null) {
        // Attach an image, if we have one
        URL url = new URL(req.getParameter("imageUrl"));
        String contentType = req.getParameter("contentType");
        MirrorClient.insertTimelineItem(credential, timelineItem, contentType, url.openStream());
      } else {
        MirrorClient.insertTimelineItem(credential, timelineItem);
      }

      message = "A timeline item has been inserted.";

    } else if (req.getParameter("operation").equals("insertPaginatedItem")) {
      LOG.fine("Inserting Timeline Item");
      TimelineItem timelineItem = new TimelineItem();
      timelineItem.setHtml(PAGINATED_HTML);

      List<MenuItem> menuItemList = new ArrayList<MenuItem>();
      menuItemList.add(new MenuItem().setAction("OPEN_URI").setPayload(
          "https://www.google.com/search?q=cat+maintenance+tips"));
      timelineItem.setMenuItems(menuItemList);

      // Triggers an audible tone when the timeline item is received
      timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));

      MirrorClient.insertTimelineItem(credential, timelineItem);

      message = "A timeline item has been inserted.";

    } else if (req.getParameter("operation").equals("insertItemWithAction")) {
      LOG.fine("Inserting Timeline Item");
      TimelineItem timelineItem = new TimelineItem();
      timelineItem.setText("Tell me what you had for lunch :)");

      List<MenuItem> menuItemList = new ArrayList<MenuItem>();
      // Built in actions
      menuItemList.add(new MenuItem().setAction("REPLY"));
      menuItemList.add(new MenuItem().setAction("READ_ALOUD"));

      // And custom actions
      List<MenuValue> menuValues = new ArrayList<MenuValue>();
      menuValues.add(new MenuValue().setIconUrl(WebUtil.buildUrl(req, "/static/images/drill.png"))
          .setDisplayName("Drill In"));
      menuItemList.add(new MenuItem().setValues(menuValues).setId("drill").setAction("CUSTOM"));

      timelineItem.setMenuItems(menuItemList);
      timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));

      MirrorClient.insertTimelineItem(credential, timelineItem);

      message = "A timeline item with actions has been inserted.";

    } else if (req.getParameter("operation").equals("insertContact")) {
      if (req.getParameter("iconUrl") == null || req.getParameter("name") == null) {
        message = "Must specify iconUrl and name to insert contact";
      } else {
        // Insert a contact
        LOG.fine("Inserting contact Item");
        Contact contact = new Contact();
        contact.setId(req.getParameter("id"));
        contact.setDisplayName(req.getParameter("name"));
        contact.setImageUrls(Lists.newArrayList(req.getParameter("iconUrl")));
        contact.setAcceptCommands(Lists.newArrayList(new Command().setType("TAKE_A_NOTE")));
        MirrorClient.insertContact(credential, contact);

        message = "Inserted contact: " + req.getParameter("name");
      }

    } else if (req.getParameter("operation").equals("deleteContact")) {

      // Insert a contact
      LOG.fine("Deleting contact Item");
      MirrorClient.deleteContact(credential, req.getParameter("id"));

      message = "Contact has been deleted.";

    } else if (req.getParameter("operation").equals("insertItemAllUsers")) {
      if (req.getServerName().contains("glass-java-starter-demo.appspot.com")) {
        message = "This function is disabled on the demo instance.";
      }

      // Insert a contact
      List<String> users = AuthUtil.getAllUserIds();
      LOG.info("found " + users.size() + " users");
      if (users.size() > 10) {
        // We wouldn't want you to run out of quota on your first day!
        message =
            "Total user count is " + users.size() + ". Aborting broadcast " + "to save your quota.";
      } else {
        TimelineItem allUsersItem = new TimelineItem();
        allUsersItem.setText("Hello Everyone!");

        BatchRequest batch = MirrorClient.getMirror(null).batch();
        BatchCallback callback = new BatchCallback();

        // TODO: add a picture of a cat
        for (String user : users) {
          Credential userCredential = AuthUtil.getCredential(user);
          MirrorClient.getMirror(userCredential).timeline().insert(allUsersItem)
              .queue(batch, callback);
        }

        batch.execute();
        message =
            "Successfully sent cards to " + callback.success + " users (" + callback.failure
                + " failed).";
      }


    } else if (req.getParameter("operation").equals("deleteTimelineItem")) {

      // Delete a timeline item
      LOG.fine("Deleting Timeline Item");
      MirrorClient.deleteTimelineItem(credential, req.getParameter("itemId"));

      message = "Timeline Item has been deleted.";

    } else if (req.getParameter("operation").equals("startMakeMeSmile")){
    	// START MAKE ME SMILE
    
    	//Insert a welcome Message
    	
    	final String WELCOME_HTML =
    			"<article class='photo'>"
    	    			+	"<img src=" + WebUtil.buildUrl(req, "/static/images/smileCover.jpg") +" width='100%' height='100%'>"
    	    			+	"<div class='photo-overlay'/>"
    	    			+	"<section>"
    	    			+	"<p class='text-auto-size'> </p>"
    	    			+	"</section>"
    	    			+	"</article>" ;

   	
    	TimelineItem welcomeItem = new TimelineItem();
    	welcomeItem.setHtml(WELCOME_HTML);
    	welcomeItem.setBundleId("Main_Bundle");
    	welcomeItem.setIsBundleCover(true);
        welcomeItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));

    	TimelineItem insertedItem = MirrorClient.insertTimelineItem(credential, welcomeItem);
    	LOG.info("Welcome message inserted" + insertedItem.getId() + " for user "
    	        + userId);
    	
    	//Insert timeline cards
    	
    	final String CARD_ONE =
    			"<article>"
    			+	"<img src=" + WebUtil.buildUrl(req, "/static/images/bgrAll.png") +" >"
    			+ 	"<figure>"
    			+		"<img src=" + WebUtil.buildUrl(req, "/static/images/Img.png") +" >"
    			+ 	"</figure>"
    			+ 	"<section>"
    			+		"<h1 class='red text-x-small'>"
    			+			"<img src="+ WebUtil.buildUrl(req, "/static/images/Mirriam1.png") +" >"
    			+				"<strong>Miriam says..</strong>"
    			+		"</h1>"
    			+		"<hr>"
    			+		"<p class='red text-x-small'>"
    			+  			"<strong>You'll be fine as long as you don't forget your boots!</strong>"
    			+		"</p>"
    			+ 	"</section>"
    			+ "</article>";
    	
    	
    	
    	
    	TimelineItem Item1 = new TimelineItem();
    	Item1.setHtml(CARD_ONE);
    	Item1.setBundleId("Main_Bundle");
    	List<MenuItem> menuItemList1 = new ArrayList<MenuItem>();
        // Built in actions
        menuItemList1.add(new MenuItem().setAction("SHARE"));
        menuItemList1.add(new MenuItem().setAction("DELETE"));
        // And custom actions
        List<MenuValue> menuValues1 = new ArrayList<MenuValue>();
        menuValues1.add(new MenuValue().setIconUrl(WebUtil.buildUrl(req, "/static/images/smileBack.jpg"))
            .setDisplayName("Smile Back"));
        menuItemList1.add(new MenuItem().setValues(menuValues1).setId("smileBack").setAction("CUSTOM"));
        Item1.setMenuItems(menuItemList1);        
    	Item1.setNotification(new NotificationConfig().setLevel("DEFAULT"));
    	TimelineItem insertedItem1 = MirrorClient.insertTimelineItem(credential, Item1);
    	LOG.info("Welcome message inserted" + insertedItem1.getId() + " for user "
    	        + userId);
    	
    	final String CARD_TWO =
    			"<article>"
    	    	+	"<img src=" + WebUtil.buildUrl(req, "/static/images/bgrAll.png") +" >"
    	    	+	"<figure>"
    			+		"<img src=" + WebUtil.buildUrl(req, "/static/images/Img3.png") +" >"
    			+ 	"</figure>"
    			+ 	"<section>"
    			+		"<h1 class='red text-x-small'>"
    			+			"<img src="+ WebUtil.buildUrl(req, "/static/images/Molly1.png") +" >"
    			+				"<strong>Molly says..</strong>"
    			+		"</h1>"
    			+		"<hr>"
    		    +		"<p class='red text-x-small'>"
    		    +  			"<strong>I left these on the kitchen counter</strong>"
    		    +		"</p>"
    			+ 	"</section>"
    			+ "</article>";
    	
    	TimelineItem Item2 = new TimelineItem();
    	Item2.setHtml(CARD_TWO);
    	Item2.setBundleId("Main_Bundle");
    	List<MenuItem> menuItemList2 = new ArrayList<MenuItem>();
    	// Built in actions
        menuItemList2.add(new MenuItem().setAction("SHARE"));
        menuItemList2.add(new MenuItem().setAction("DELETE"));
        // And custom actions
        List<MenuValue> menuValues2 = new ArrayList<MenuValue>();
        menuValues2.add(new MenuValue().setIconUrl(WebUtil.buildUrl(req, "/static/images/smileBack.jpg"))
            .setDisplayName("Smile Back"));
        menuItemList2.add(new MenuItem().setValues(menuValues2).setId("smileBack").setAction("CUSTOM"));
        
        Item2.setMenuItems(menuItemList2);   
    	Item2.setNotification(new NotificationConfig().setLevel("DEFAULT"));
    	TimelineItem insertedItem2 = MirrorClient.insertTimelineItem(credential, Item2);
    	LOG.info("Welcome message inserted" + insertedItem2.getId() + " for user "
    	        + userId);
   
    	

    	final String CARD_THREE =
    			"<article>"
    			+	"<img src=" + WebUtil.buildUrl(req, "/static/images/bgrAll.png") +" >"
    			+ 	"<figure>"
    			+		"<img src=" + WebUtil.buildUrl(req, "/static/images/Img2.png") +" >"
    			+ 	"</figure>"
    			+ 	"<section>"
    			+		"<h1 class='red text-x-small'>"
    			+			"<img src="+ WebUtil.buildUrl(req, "/static/images/Mirriam1.png") +" >"
    			+				"<strong>Miriam says..</strong>"
    			+		"</h1>"
    			+		"<hr>"
    		    +		"<p class='red text-x-small'>"
    		    +  			"<strong>Hello There!!!<strong>"
    		    +		"</p>"
    			+ 	"</section>"
    			+ "</article>";
    	
    	TimelineItem Item3 = new TimelineItem();
    	Item3.setHtml(CARD_THREE);
    	Item3.setBundleId("Main_Bundle");
    	List<MenuItem> menuItemList3 = new ArrayList<MenuItem>();
    	// Built in actions
        menuItemList3.add(new MenuItem().setAction("SHARE"));
        menuItemList3.add(new MenuItem().setAction("DELETE"));
        // And custom actions
        List<MenuValue> menuValues3 = new ArrayList<MenuValue>();
        menuValues3.add(new MenuValue().setIconUrl(WebUtil.buildUrl(req, "/static/images/smileBack.jpg"))
            .setDisplayName("Smile Back"));
        menuItemList3.add(new MenuItem().setValues(menuValues3).setId("smileBack").setAction("CUSTOM"));
        
        Item3.setMenuItems(menuItemList3); 
    	Item3.setNotification(new NotificationConfig().setLevel("DEFAULT"));
    	TimelineItem insertedItem3 = MirrorClient.insertTimelineItem(credential, Item3);
    	LOG.info("Welcome message inserted" + insertedItem3.getId() + " for user "
    	        + userId);
   

    	final String CARD_FOUR =
    			"<article>"
    			+	"<img src=" + WebUtil.buildUrl(req, "/static/images/bgrAll.png") +" >"
    			+ 	"<figure>"
    			+		"<img src=" + WebUtil.buildUrl(req, "/static/images/Img4.png") +" >"
    			+ 	"</figure>"
    			+ 	"<section>"
    			+		"<h1 class='red text-x-small'>"
    			+			"<img src="+ WebUtil.buildUrl(req, "/static/images/Pattie1.png") +" >"
    			+				"<strong>Pattie says..</strong>"
    			+		"</h1>"
    			+		"<hr>"
    		    +		"<p class='red text-x-small'>"
    		    +  			"<strong>Its going to be OK!</strong>"
    		    +		"</p>"
    			+ 	"</section>"
    			+ "</article>";
    	
    	
    	TimelineItem Item4 = new TimelineItem();
    	Item4.setHtml(CARD_FOUR);
    	Item4.setBundleId("Main_Bundle");
    	List<MenuItem> menuItemList4 = new ArrayList<MenuItem>();
    	// Built in actions
        menuItemList4.add(new MenuItem().setAction("SHARE"));
        menuItemList4.add(new MenuItem().setAction("DELETE"));
        // And custom actions
        List<MenuValue> menuValues4 = new ArrayList<MenuValue>();
        menuValues4.add(new MenuValue().setIconUrl(WebUtil.buildUrl(req, "/static/images/smileBack.jpg"))
            .setDisplayName("Smile Back"));
        menuItemList4.add(new MenuItem().setValues(menuValues4).setId("smileBack").setAction("CUSTOM"));
        
        Item4.setMenuItems(menuItemList4); 
    	Item4.setNotification(new NotificationConfig().setLevel("DEFAULT"));
    	TimelineItem insertedItem4 = MirrorClient.insertTimelineItem(credential, Item4);
    	LOG.info("Welcome message inserted" + insertedItem4.getId() + " for user "
    	        + userId);
   
    	// Insert a contact
        LOG.fine("Inserting contact Item");
        Contact contact = new Contact();
        contact.setId("MAKE_ME_SMILE");
        contact.setDisplayName("MAKE ME SMILE");
        contact.setImageUrls(Lists.newArrayList(req.getParameter("iconUrl")));
        contact.setAcceptCommands(Lists.newArrayList(new Command().setType("POST_AN_UPDATE")));
        contact.setSpeakableName("MAKE ME SMILE");
        MirrorClient.insertContact(credential, contact);

        message = "Inserted contact: " + req.getParameter("MAKE_ME_SMILE");
    
    } else {
      String operation = req.getParameter("operation");
      LOG.warning("Unknown operation specified " + operation);
      message = "I don't know how to do that";
    }
    WebUtil.setFlash(req, message);
    res.sendRedirect(WebUtil.buildUrl(req, "/"));
  }
}
