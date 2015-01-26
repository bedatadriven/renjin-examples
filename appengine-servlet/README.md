
# Example Google AppEngine WebApp using Renjin and the Servlet API

The Java part of this web app simply defers to the R script WEB-INF/app.R

Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 6+ in order to run.

To build, run

    mvn package

You can run integration tests against a locally running AppEngine development server by running:

    mvn verify

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine


Based on the AppEngine Java example Copyright (C) 2010-2012 Google Inc.
