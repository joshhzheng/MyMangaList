package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {

    private Event a;
    private Event b;
    private Event e;
    private Date d;
    private String string;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        b = new Event("Sensor open at door");
        a = new Event("No");
        string = "No";

        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testEquals() {
        assertFalse(a.equals(string));
        assertEquals(b, e);
        assertFalse(e.equals(a));
        assertFalse(b.equals("Sensor open at door"));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testHashCode() {
        assertEquals(b.hashCode(), e.hashCode());
        assertNotEquals(e.hashCode(), a.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }
}
