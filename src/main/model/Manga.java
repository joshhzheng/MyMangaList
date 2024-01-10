package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a Manga with a String Name and an Integer Rating
public class Manga implements Writable {
    private String mangaName;
    private int mangaRating;

    // Constructor for a Manga
    // Modifies: this
    // Effects: Constructor for a Manga with String Name and Int Rating
    public Manga(String mangaName, int mangaRating) {
        this.mangaName = mangaName;
        this.mangaRating = mangaRating;
    }

    // Effects: returns manga's name
    public String getName() {
        return mangaName;
    }

    // Effects: returns manga's rating score
    public int getRating() {
        return mangaRating;
    }

    // Requires:
    // Modifies: this
    // Effects: modifies the current manga's name
    public void setName(String mangaName) {
        this.mangaName = mangaName;
    }

    // Modifies: this
    // Effects: modifies the current manga's name
    public void setRating(int mangaRating) {
        this.mangaRating = mangaRating;
    }

    // Effects: returns a new json object with "name" and "10"
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", mangaName);
        json.put("rating", mangaRating);
        return json;
    }
}
