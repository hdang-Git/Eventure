package com.example.hai.eventfinder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hai on 3/22/2017.
 */
public class EventTest {
    private Event builder;

    @Before
    public void setUp() throws Exception {
         this.builder = new Event.Builder("Party")
                 .setEventCoordinates("39", "78")
                 .setDate("12/12/12")
                 .setEventLocation("Philadelphia")
                 .setImageUrl("http://image.com/1234")
                 .setEventPrice(10)
                 .setEventDescription("This is a cool event")
                 .setEventStartTime("5:00")
                 .setEventEndTime("8:00")
                 .build();
    }

    @Test
    public void testGetEventName() throws Exception {
        assertEquals("Party", this.builder.getEventName());
    }

    @Test
    public void testGetEventDate() throws Exception {
        assertEquals("12/12/12", this.builder.getEventDate());
    }

    @Test
    public void testGetEventStartTime() throws Exception {
        assertEquals("5:00", this.builder.getEventStartTime());
    }

    @Test
    public void testGetEventEndTime() throws Exception {
        assertEquals("8:00", this.builder.getEventEndTime());
    }

    @Test
    public void testGetEventLatitude() throws Exception {
        assertEquals("39", this.builder.getEventLatitude());
    }

    @Test
    public void testGetEventLongitude() throws Exception {
        assertEquals("78", this.builder.getEventLongitude());
    }

    @Test
    public void testGetEventLocation() throws Exception {
        assertEquals("Philadelphia", this.builder.getEventLocation());
    }

    @Test
    public void testGetEventDescription() throws Exception {
        assertEquals("This is a cool event", this.builder.getEventDescription());
    }

    @Test
    public void testGetEventPrice() throws Exception {
        assertEquals("10", this.builder.getEventPrice());
    }

    @Test
    public void testGetEventImageURL() throws Exception {
        assertEquals("http://image.com/1234", this.builder.getEventImageURL());
    }

    @Test
    public void testToString() {
        String test = "Party 12/12/12 5:00 8:00 Philadelphia 39 78 " +
                "This is a cool event 10 http://image.com/1234";
        assertEquals(test, this.builder.toString());
    }
}