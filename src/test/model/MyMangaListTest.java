package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyMangaListTest {

    private Manga testManga1;
    private Manga testManga2;
    private Manga testUnaddedManga;
    private MyMangaList testMangaList;

    @BeforeEach
    void runBefore() {
        testManga1 = new Manga("Solo Levelling", 10);
        testManga2 = new Manga("Overlord", 7);
        testUnaddedManga = new Manga("Code Geass", 10);

        testMangaList = new MyMangaList("mmlTest");
        testMangaList.addManga(testManga1);
        testMangaList.addManga(testManga2);
    }

    @Test
    void testConstructor() {
        assertEquals(2, testMangaList.getSize());
    }

    @Test
    void testViewManga() {
        List<Manga> expectedList = new ArrayList<>();
        expectedList.add(testManga1);
        expectedList.add(testManga2);

        assertEquals(expectedList, testMangaList.viewManga());
    }

    @Test
    void testGetManga() {
        assertEquals("Solo Levelling", testManga1.getName());
        assertEquals(10, testManga1.getRating());
    }

    @Test
    void testAddManga() {
        assertEquals(2, testMangaList.getSize());
        testMangaList.addManga(testUnaddedManga);
        assertEquals(3, testMangaList.getSize());
    }

    @Test
    void testRemoveManga() {
        assertEquals(2, testMangaList.getSize());
        assertTrue(testMangaList.removeManga(testManga2.getName()));
        assertEquals(1, testMangaList.getSize());
    }

    @Test
    void testRemoveMangaNotInList() {
        assertEquals(2, testMangaList.getSize());
        assertTrue(testMangaList.removeManga(testManga2.getName()));
        assertFalse(testMangaList.removeManga(testManga2.getName()));

        assertTrue(testMangaList.removeManga(testManga1.getName()));
        assertFalse(testMangaList.removeManga(testManga1.getName()));
    }

    @Test
    void testEditMangaSuccess() {
        boolean success = testMangaList.modifyManga(testManga1.getName(), "Solo Level", 3);
        assertTrue(success);

        assertEquals(3, testManga1.getRating());
        assertEquals("Solo Level", testManga1.getName());
    }

    @Test
    void testEditMangaFail() {
        boolean fail = testMangaList.modifyManga("bob the builder", "among us", 30000);
        assertFalse(fail);
    }

    @Test
    void testGetSize() {
        assertEquals(2, testMangaList.getSize());
    }
}