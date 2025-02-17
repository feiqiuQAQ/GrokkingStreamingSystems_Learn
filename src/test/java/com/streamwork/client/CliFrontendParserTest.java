package com.streamwork.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CliFrontendParserTest {

    @Test
    public void testParseValidOptions() throws ParseException {
        String[] args = {"-j", "app.jar", "-c", "MainClass", "-p", "3"};
        CommandLine cmd = CliFrontendParser.parse(CliFrontendParser.getRunCommandOptions(), args);

        assertEquals("app.jar", cmd.getOptionValue("j"));
        assertEquals("MainClass", cmd.getOptionValue("c"));
        assertEquals("3", cmd.getOptionValue("p"));
    }

    @Test(expected = ParseException.class)
    public void testMissingRequiredOption() throws ParseException {
        String[] args = {"-j", "app.jar", "-c", "MainClass"}; // 缺少 -p
        CliFrontendParser.parse(CliFrontendParser.getRunCommandOptions(), args);
    }
}
