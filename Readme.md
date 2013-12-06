# This is my readme file.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

MIRROR API PROJECT

The first Mirror Api app code is based on the quickstart java project and can be run by using the following tools and commands

1. INSTALL Apache Maven on eclipse to run the code
2. INSTALL Ngrok to expose the localhost

Make sure to set the environment variables for both and add the JAVA_HOME environment variable.

The commands used to run the code are:

FILE_LOCATION
mvn war:war
mvn jetty:run

Once the Jetty server is started.
ngrok 8080 

Open localhost:4040 and authorize the http: url
(NOTE- THE HTTP URL has to be added the product details as an accepted link at code.google.com\apis\console)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

OVX VIDEO CHAT EXAMPLE

This is an example video chat app that uses OpenClove Video Exchange Api for starting a one-one video chat.

Once you have downloaded the source code. ADD the project to eclipse.

NOTE - For accessing this api, you need an api key which you can get when you register your app at 
http://developer.openclove.com/member 

Once you have registered and followed the instructions of setting up the api key from 
http://developer.openclove.com/docs/Quick_Start__Android_SDK

You can run the app on any android device. Remember that you have to use 2 android devices and install the source code on each one.
For ease you use because of NO KEYBOARD on glass , the group id is hardcoded as "abc". This can be initialized by clicking on set up 
group id on the main screen and clicking set.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

STEP BY STEP

Step by step is a Glass App designed to provide a framework for young adults with ASDs to develop the skills they need 
to complete activities independently..

The source code of Step by step contains the code for the full prototype of the app which includes an app to be used by the 
caretaker on the phone and a native android glass app to be run by the user.

Step by step contains two packages in the source code that contains the respective apps. 
Step by step uses the Key/value pair api provided by CCS, Northeastern University for handing id pairing between the glass and the 
phone app.
Also the glass application uses OpenClove video exchange api for an in app video Chat.

Download the source code while adjusting the targets according to the sdks installed on your machine and with all the jar files listed.

To directly test the app just install the stepByStep.apk file on to your glass and your mobile Phone.
The Phone app uses the dropBox server to link to the glass. Currently logining to a dropbox account on glass is an issue and hopefully
by resolved in the updates scheduled.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

STEP BY STEP - 2

The Step By step 2 is just the phone app for the purpose of possibily having two different apps, One consumer and one user app.
The phone app uses the dropBox server to add the scripts. Since currently it is not possible to login on dropbox on glass without
a bluetooth keyboard, the source code for all project uses hardcoded tasks and scripts.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Step By step Murphy

Step By Step Murphy is a project designed for the demo of the glass app incase the CCS server that handles the login fails. In this
project stepByStep assigns you a random id and uses that id throughout the project so you could use it incase you see an "Error -IO-Exception"
message on the home screen of Step By step.

To install this on  glass please download the StepByStepMurphy.apk and make sure your internet is correctly set up for initializing the takss and the
video calls.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Step By step Murphy - 2

This is another project just for the demo incase the internet is not working. Since step by step relies heavily on internet this 
project provides a way to demo the app when there is no internet connectivity.
This project uses harcoded ids and scripts for demoing the look and feel of the glass app.

Download and install the apk file for this project to demo this app.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

HELP: If you need any more infomration or help please send us an email.

