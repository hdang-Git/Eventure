package com.example.hai.eventfinder.Formatting;

import org.junit.Test;

import java.text.Format;

import static org.junit.Assert.*;

/**
 * Created by Hai on 4/20/2017.
 */
public class FormatUtilsTest {
    String datetime = "2017-04-29T22:00:00";

    @Test
    public void retrieveEventBriteDate() throws Exception {
        String actualDate = FormatUtils.retrieveEventBriteDate(datetime);
        String expectedDate = "Apr 29";
        assertEquals(expectedDate,actualDate);
    }

    @Test
    public void retrieveEventBriteTime() throws Exception {
        String actualTime = FormatUtils.retrieveEventBriteTime(datetime);
        String expectedTime = "10:00 PM";
        assertEquals(expectedTime,actualTime);
    }

}