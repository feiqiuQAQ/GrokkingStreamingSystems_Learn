package com.streamwork.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class CliFrontendTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testRunWithValidParameters() {
        CliFrontend cli = new CliFrontend();
        String[] args = {"start", "-j", "app.jar", "-c", "Main", "-p", "2"};

        int exitCode = cli.parseAndRun(args);
        assertEquals(0, exitCode);
        assertTrue(outContent.toString().contains("启动任务:"));
    }

    @Test
    public void testMissingAction() {
        CliFrontend cli = new CliFrontend();
        int exitCode = cli.parseAndRun(new String[]{});
        assertEquals(1, exitCode);
        assertTrue(errContent.toString().contains("Please specify an action."));
    }

    @Test
    public void testHelpOption() {
        CliFrontend cli = new CliFrontend();
        String[] args = {"start", "-h"};
        int exitCode = cli.parseAndRun(args);

        assertEquals(0, exitCode);
        assertTrue(outContent.toString().contains("显示帮助信息"));
    }
}
