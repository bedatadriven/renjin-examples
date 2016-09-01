package org.renjin.example.fraud;


import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.DoubleVector;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.script.ScriptContext.ENGINE_SCOPE;

public class FraudScorerServlet extends HttpServlet {

  /**
   * Maintain one instance of Renjin for each request thread.
   */
  private static final ThreadLocal<ScriptEngine> ENGINE = new ThreadLocal<ScriptEngine>();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    double score = score(Double.parseDouble(req.getParameter("balance")), Double.parseDouble(req.getParameter("numTrans")), Double.parseDouble(req.getParameter("creditLine")));
    resp.getWriter().write(Double.toString(score));

  }

  double score(double balance, double numTrans, double creditLine) {
    ScriptEngine engine = ENGINE.get();
    if(engine == null) {
      engine = initEngine();
      ENGINE.set(engine);
    }
    double score;
    Bindings bindings = engine.getBindings(ENGINE_SCOPE);
    bindings.put("balance", balance);
    bindings.put("numTrans", numTrans);
    bindings.put("creditLine", creditLine);

    try {
      engine.eval("score <- data.frame(balance=balance,numTrans=numTrans,creditLine=creditLine)");
      engine.eval("x <- predict(fraudModel, score)");

      DoubleVector x = (DoubleVector) bindings.get("x");
      score = x.getElementAsDouble(0);

    } catch (ScriptException e) {
      throw new RuntimeException("Prediction failed");
    }
    return score;
  }

  private ScriptEngine initEngine() {
    try {
      // Do one-time initialization
      ScriptEngine engine; 
      engine = new RenjinScriptEngineFactory().getScriptEngine();
      engine.eval("load('res:fraudModel.rData')");
      return engine;
    } catch (ScriptException e) {
      throw new RuntimeException("Failed to initialize ScriptEngine", e);
    }
  }
}
