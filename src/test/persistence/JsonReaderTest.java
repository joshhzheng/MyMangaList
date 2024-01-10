package persistence;

import model.Manga;
import model.MyMangaList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNullFile() {
        JsonReader reader = new JsonReader("./data/nullFile.json");
        try {
            MyMangaList mml = reader.read();
            fail("IOException EXPECTED");
        } catch (IOException e) {
            // passes test
        }
    }

    @Test
    void testReaderEmptyMangaList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMyMangaList.json");
        try {
            MyMangaList mml = reader.read();
            assertEquals("My Mangas", mml.getName());
            assertEquals(0, mml.getSize());
        } catch (IOException e) {
            fail("COULDN'T READ FROM FILE");
        }
    }

    @Test
    void testReaderMangaList() {
        JsonReader reader = new JsonReader("./data/testReaderMangaList.json");
        try {
            MyMangaList mml = reader.read();
            assertEquals("My Mangas", mml.getName());
            List<Manga> mangas = mml.viewManga();
            assertEquals(2, mangas.size());
            checkManga("Mushoku Tensei", 9, mangas.get(1));
            checkManga("Solo Levelling", 10, mangas.get(0));
        } catch (IOException e) {
            System.out.println("Couldn't read from file");
        }
    }
}
