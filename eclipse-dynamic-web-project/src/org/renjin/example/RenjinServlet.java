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
 * Servlet implementation class MyServlet
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
		ScriptEngine engine = getScriptEngine();
		StringVector result;
		try {
			result = (StringVector)engine.eval("rjson::toJSON(1:15)");
		} catch (ScriptException e) {
			throw new ServletException(e);
		}
		
		response.setContentType("application/json");
		response.getOutputStream().print(result.getElementAsString(0));
	}

}
