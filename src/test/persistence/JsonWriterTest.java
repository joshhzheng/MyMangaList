package persistence;

import model.Manga;
import model.MyMangaList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MyMangaList mml = new MyMangaList("My Mangas");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName:json");
            writer.open();
            fail("IOException WAS EXPECTED");
        } catch (IOException e) {
            // pass the test
        }
    }

    @Test
    void testWriterEmptyMangaList() {
        try {
            MyMangaList mml = new MyMangaList("My Mangas");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMangaList.json");
            writer.open();
            writer.write(mml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMangaList.json");
            mml = reader.read();
            assertEquals("My Mangas", mml.getName());
            assertEquals(0, mml.getSize());
        } catch (IOException e) {
            fail("EXPECTS IOException");
        }
    }

    @Test
    void testWriterMangaList() {
        try {
            MyMangaList mml = new MyMangaList("My Mangas");
            mml.addManga(new Manga("Solo Levelling", 10));
            mml.addManga(new Manga("Mushoku Tensei", 9));
            JsonWriter writer = new JsonWriter("./data/testWriterMangaList.json");
            writer.open();
            writer.write(mml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterMangaList.json");
            mml = reader.read();
            assertEquals("My Mangas", mml.getName());
            List<Manga> mangas = mml.viewManga();
            assertEquals(2, mangas.size());
            checkManga("Solo Levelling", 10, mangas.get(0));
            checkManga("Mushoku Tensei", 9, mangas.get(1));

        } catch (IOException e) {
            fail("EXCEPTION NOT EXPECTED");
        }
    }
}
