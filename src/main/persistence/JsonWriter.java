package persistence;

import model.Event;
import model.EventLog;
import model.MyMangaList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes Json representations of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Effects: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Modifies: this
    // Effects: opens writer, throws FileNotFoundException if destination
    // file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Modifies: this
    // Effects: writes JSON representation of MyMangaList to file
    public void write(MyMangaList mml) {
        JSONObject json = mml.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Mangas Saved"));
    }

    // Modifies: this
    // Effects: closes writer
    public void close() {
        writer.close();
    }

    // Modifies: this
    // Effects: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

// Citation: JsonSerializationDemo, JsonWriter.java class