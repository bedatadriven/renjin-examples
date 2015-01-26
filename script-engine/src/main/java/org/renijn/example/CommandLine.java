package org.renijn.example;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CommandLine {

    public static void main(String[] arguments) throws ScriptException {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine renjin = scriptEngineManager.getEngineByName("Renjin");
        if(renjin == null) {
            throw new RuntimeException("Could not create the Renjin ScriptEngine");
        }

        renjin.eval("print(1+1)");
    }
}
