
# Renjin Eclipse Example

This is an example of using Renjin in an Eclipse dynamic web project, without 
using a build tools such as Maven, Gradle or even Ant.

Please note this is *not* recommended. As soon as you begin using non-trivial
R packages, it will become increasingly difficult to manage dependencies
(and the dependencies of _those_ dependencies) through a point-and-click interface. 

## Project Set up

### Download Apache Tomcat

Download and unpack Apache Tomcat 7.0 from the 
[Tomcat Website](https://tomcat.apache.org/download-70.cgi) into separate directory
on your local machine, for example, in /home/alex/tomcat-7.0

### Create an new project

This example project was created in Eclipse Mars by choosing new "Dynamic Web Project"
from the file menu. 

Near the "Target Runtime" field, choose "New Runtime", then Apache 7.0, and then provide
the path to the directory where you unpacked Tomcat above.

### Download and copy Renjin dependencies

Without a dependency management tool, the easiest way to add Renjin to your project is 
to [download](http://www.renjin.org/downloads.html) the standalone .jar from the Renjin 
website.

Copy this jar into the `WebContent/WEB-INF/lib` folder.

For this example, we're also including the `rjson` package. You can download pre-built
CRAN and bioconductor packages from the
[BeDataDriven Nexus Repository](https://nexus.bedatadriven.com/content/groups/public/org/renjin/cran/), but you'll also have to check the `pom.xml` file for additional dependencies. rjson, 
for example, depends on the Google GSON library, which also needs to be copied into
`WebContent/WEB-INF/lib`

## Handling Requests with Renjin

A Java Servlet container is multi-threaded: multiple incoming requests can be handled 
at the same time, but by separate threads. To ensure that requests are handled correctly, 
you can use a global [ThreadLocal](https://docs.oracle.com/javase/7/docs/api/java/lang/ThreadLocal.html) instance to ensure that each thread has its own
`RenjinScriptEngine` instance for handling requests.

See [RenjinServlet](src/org/renjin/example/RenjinServlet.java) for a super-simple example of evaluating an R expression, serializing
the result to JSON, and sending the JSON to the client.

Querying `http://localhost:8080/dynamic-web-example/RenjinServlet?sd=10` then returns:

```
{
  "(Intercept)":9.82194831624089,
   "x":1.0201585366465116
}
```




 