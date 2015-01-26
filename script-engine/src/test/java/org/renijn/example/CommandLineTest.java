package org.renijn.example;

import org.junit.Test;

import javax.script.ScriptException;

public class CommandLineTest {

    @Test
    public void runsWithoutException() throws ScriptException {
        CommandLine.main(new String[0]);
    }
}