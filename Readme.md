# This is my readme file.

The first Mirror Api app code is based on the quickstart jack project and can be run by using the following tools and commands


1. Apache Maven to run the code
2. Ngrok to expose the localhost
Make sure to set the environment variables for both and add the JAVA_HOME environment variable.


The commands used to run the code are:

FILE_LOCATION
mvn war:war
mvn jetty:run

Once the Jetty server is started.
ngrok 8080 

Open localhost:4040 and authorize the http: url(NOTE- THE HTTP URL has to be added the product details at code.google.com\apis\console)
