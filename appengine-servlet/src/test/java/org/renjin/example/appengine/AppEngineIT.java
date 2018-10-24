package org.renjin.example.appengine;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Verify that the Servlet works
 */
public class AppEngineIT {

    private Client client;
    private WebTarget root;

    @Before
    public void setUp() {
        this.client = ClientBuilder.newBuilder().build();
        this.root = client.target(System.getProperty("test.root.uri", "http://localhost:8991"));
    }

    @Test
    public void testServlet() throws IOException {
        String response = root.request().get(String.class);
        assertThat(response, equalTo("Hello World\n"));
    }

    /**
     * Make sure the R.home() path is accessible
     */
    @Test
    public void testHomePath() throws IOException {
        String response = root.path("home").request().get(String.class);
        assertThat(response, equalTo("jar:file:///WEB-INF/lib/renjin-core-0.9.2700.jar!/org/renjin\n"));
    }

    /**
     * Make sure we can load cran packages that rely on access to package files
     */
    @Test
    public void testHttr() throws IOException {
        String response = root.path("home").request().get(String.class);
        assertThat(response, equalTo("jar:file:///WEB-INF/lib/renjin-core-0.9.2700.jar!/org/renjin\n"));
    }
}
