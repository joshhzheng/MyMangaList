package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// MyManaList class a list of Mangas that can add/remove/edit Mangas within itself
public class MyMangaList implements Writable {
    private String name;
    private List<Manga> mangaList;

    // Effects: creates an empty manga list with a name
    public MyMangaList(String name) {
        this.name = name;
        mangaList = new ArrayList<>();
    }

    // Effects: returns name
    public String getName() {
        return name;
    }

    // Requires: mangaList != null
    // Effects: returns a list of Manga
    public List<Manga> viewManga() {
        return mangaList;
    }

    // Modifies: mangaList
    // Effects: adds manga to mangaList
    public void addManga(Manga manga) {
        mangaList.add(manga);
        EventLog.getInstance().logEvent(new Event("Manga added: " + name));
    }

    // Requires: manga is already contained in mangaList
    // Modifies: this, mangaList
    // Effects: delete manga from mangaList
    public boolean removeManga(String mangaName) {
        Iterator<Manga> iterator = mangaList.iterator();
        Manga mangaToRemove = null;

        while (iterator.hasNext()) {
            Manga manga = iterator.next();
            if (manga.getName().equalsIgnoreCase(mangaName)) {
                mangaToRemove = manga;
                iterator.remove();
                break;
            }
        }

        if (mangaToRemove != null) {
            mangaList.remove(mangaToRemove);
            EventLog.getInstance().logEvent(new Event("Manga removed: " + name));
            return true;
        } else {
            return false;
        }
    }

    // Requires: manga is already contained in mangaList
    // Modifies: manga
    // Effects: changes either the String value or Integer value
    public boolean modifyManga(String mangaName, String newMangaName, int newMangaRating) {
        Iterator<Manga> iterator = mangaList.iterator();
        Manga mangaToEdit = null;

        while (iterator.hasNext()) {
            Manga manga = iterator.next();
            if (manga.getName().equalsIgnoreCase(mangaName)) {
                mangaToEdit = manga;
                break;
            }
        }

        if (mangaToEdit != null) {
            mangaToEdit.setName(newMangaName);
            mangaToEdit.setRating(newMangaRating);
            EventLog.getInstance().logEvent(new Event("Manga edited: " + newMangaName));
            return true;
        } else {
            return false;
        }
    }

    // Effects: returns the number of mangas inside mangaList
    public int getSize() {
        return mangaList.size();
    }

    // Effects: returns json object of mangaName "name" and mangaRating "10"
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("mangas", mangasToJson());
        return json;
    }

    // Effects: returns things in this MyMangaList as a JSON array
    private JSONArray mangasToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Manga m : mangaList) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}


