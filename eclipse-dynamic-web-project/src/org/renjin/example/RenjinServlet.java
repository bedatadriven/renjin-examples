package org.renjin.example;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.renjin.cran.rjson.JsonWritingVisitor;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.StringVector;

/**
 * Servlet which 
 */
public class RenjinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/**
	 * This allows us to maintain one independent RenjinScriptEngine
	 * per thread, allowing your server to handle concurrent requests.
	 */
	private static final ThreadLocal<ScriptEngine> ENGINE = new ThreadLocal<>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenjinServlet() {
        super();
    }
    
    private ScriptEngine getScriptEngine() {
    	ScriptEngine engine = ENGINE.get();
    	if(engine == null) {
    		// Create a new ScriptEngine for this thread if one does not exist.
    		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
    		engine = factory.getScriptEngine();
    		ENGINE.set(engine);
    	}
    	return engine;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Obtain the script engine for this thread
		ScriptEngine engine = getScriptEngine();
		
		// Read the ?sd parameter as the standard deviation 
		// and assign it to the variable "sd" in the R session
		String sd = request.getParameter("sd");
		if(sd == null) {
			engine.put("sd", 1.0);
		} else {
			engine.put("sd", Double.parseDouble(sd));
		}
		
		StringVector result;
		try {
			result = (StringVector)engine.eval(
					"df <- data.frame(x=1:10, y=(1:10)+rnorm(sd, n=10));" +
					"x <- lm(y ~ x, df);" +
					"rjson::toJSON(x$coefficients)");
		} catch (ScriptException e) {
			throw new ServletException(e);
		}
		
		response.setContentType("application/json");
		response.getOutputStream().print(result.getElementAsString(0));
	}

}
