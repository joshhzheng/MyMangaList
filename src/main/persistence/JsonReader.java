package persistence;

import model.Event;
import model.EventLog;
import model.Manga;
import model.MyMangaList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads MyMangaList from JSON data stored in file
public class JsonReader {
    private String source;

    // Effects: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Effects: reads MyMangaList from file and returns it,
    // throws IOException if an error occurs reading data from file
    public MyMangaList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Mangas Loaded"));
        return parseMyMangaList(jsonObject);
    }

    // Effects: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // Effects: parses MangaList from JSON object and returns it
    private MyMangaList parseMyMangaList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MyMangaList mml = new MyMangaList(name);
        addMangas(mml, jsonObject);
        return mml;
    }

    // Modifies: mml
    // Effects: parses Mangas from JSON object and adds them to MyMangaList
    private void addMangas(MyMangaList mml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mangas");
        for (Object json : jsonArray) {
            JSONObject nextMangas = (JSONObject) json;
            addManga(mml, nextMangas);
        }
    }

    // Modifies: mml
    // Effects: parses Manga from JSON object and adds it to MyMangaList
    private void addManga(MyMangaList mml, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        Manga manga = new Manga(name, rating);
        mml.addManga(manga);
    }
}

// Citation: JsonSerializationDemo, JsonReader.java class