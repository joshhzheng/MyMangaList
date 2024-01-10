package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MangaTest {

    private Manga m1;
    private Manga m2;

    @BeforeEach
    void before() {
        m1 = new Manga("Solo Levelling", 10);
        m2 = new Manga("Re:Zero", 9);
    }

    @Test
    void testConstructor() {
        assertEquals("Solo Levelling", m1.getName());
        assertEquals("Re:Zero", m2.getName());

        assertEquals(10, m1.getRating());
        assertEquals(9, m2.getRating());
    }

    @Test
    void testGetName() {
        assertEquals("Solo Levelling", m1.getName());
        assertEquals("Re:Zero" ,m2.getName());
    }

    @Test
    void testGetRating() {
        assertEquals(10, m1.getRating());
        assertEquals(9, m2.getRating());
    }

    @Test
    void testSetName() {
        m1.setName("hi");
        assertEquals("hi", m1.getName());

        m2.setName("hello");
        assertEquals("hello", m2.getName());
    }

    @Test
    void testSetRating() {
        m1.setRating(4);
        assertEquals(4, m1.getRating());

        m2.setRating(5);
        assertEquals(5, m2.getRating());
    }

}
