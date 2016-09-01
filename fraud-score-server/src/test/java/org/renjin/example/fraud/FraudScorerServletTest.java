package org.renjin.example.fraud;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alex on 1-9-16.
 */
public class FraudScorerServletTest {

  @Test
  public void testScore() {
    
    FraudScorerServlet servlet = new FraudScorerServlet();
    double score = servlet.score(60000, 40, 30);
    
    System.out.println(score);
  }
  
}