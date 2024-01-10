package persistence;

import model.Manga;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkManga(String name, int rating, Manga manga) {
        assertEquals(name, manga.getName());
        assertEquals(rating, manga.getRating());
    }
}
