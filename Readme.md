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

This is an app designed for glass.
Download the source code on glass with a launcher installed and run the app. Currently the default tasks and glass names are added
for testing the app.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////