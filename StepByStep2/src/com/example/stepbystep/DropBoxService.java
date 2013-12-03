package com.example.stepbystep;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxPath.InvalidPathException;
import com.example.stepbystep.dropboxdata.APIResponse;
import com.example.stepbystep.dropboxdata.Task;

public class DropBoxService{
	
	static DbxAccountManager _acctMgr;
	DbxFileSystem dbxFs;
	
	public static void setAcctMgr(Context context){
		_acctMgr = DbxAccountManager.getInstance(context, "j1bohgxwhlirlq6", "kamif5qmk6m7und");
	}
	
	public static void updateDropBox(String json){
		DbxFile testFile = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());
			testFile = dbxFs.open(new DbxPath("glass1.txt"));
			if (!json.contentEquals("")){
				testFile.writeString(json);
			}
			Log.d("Dropbox Test", "File contents: " + json);
		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			testFile.close();
		}
	}
	
	public static void linkAccount(Context context, int request){
		if (!_acctMgr.hasLinkedAccount()){
			_acctMgr.startLink((Activity) context, request);
		} 
	}
	
	public static boolean hasLinkedAccount(){
		return _acctMgr.hasLinkedAccount();
	}
	
	public static void writeToDropBox(APIResponse data){
		
		ObjectWriter writer = new ObjectMapper().writer();

		String json = "";
		try {
			json = writer.writeValueAsString(data);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		updateDropBox(json);
	}
	
	static String uploadPhoto(Bitmap imageBitmap) {
		DbxFile image = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());

			int stepIndex = (Integer) Register.register.get("stepIndex") + 1;
			Task currentTask = (Task) Register.register.get("currentTask");
			String taskTitle = currentTask.getTitle();

			String imgURL = "Images/" + taskTitle + "/" + "Step" + stepIndex + ".jpg";
			if (dbxFs.exists(new DbxPath(imgURL))){
				image = dbxFs.open(new DbxPath(imgURL));
			}

			Bitmap bitmap = imageBitmap;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			byte[] bitmapdata = bos.toByteArray();

			FileOutputStream output = image.getWriteStream();

			output.write(bitmapdata);
			
			return imgURL;

		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			image.close();
		}
		return null;
	}
	
	static Bitmap downloadImage(String url) {
		
		DbxFile image = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());

			image = dbxFs.open(new DbxPath(url));

			FileInputStream in = image.getReadStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			byte[] bitMapA= new byte[buf.available()];
			buf.read(bitMapA);
			Bitmap bM = BitmapFactory.decodeByteArray(bitMapA, 0, bitMapA.length);
			if (in != null) {
				in.close();
			}
			if (buf != null) {
				buf.close();
			}
			return bM;
		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			image.close();
		}
		return null;
	}
	
}