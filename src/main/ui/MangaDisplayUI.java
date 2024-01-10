package ui;

import model.MyMangaList;
import model.Manga;

import java.util.List;
import javax.swing.*;
import java.awt.*;

// Represents the display of current mangas in the MangaList on the centre
// of the screen
public class MangaDisplayUI extends JPanel {
    private MyMangaList mangaList;
    private JLabel displayArea;

    // Represents a new MangaDisplayUI with a mangaList inside of it
    public MangaDisplayUI(MyMangaList mangaList) {
        this.mangaList = mangaList;

        setLayout(new BorderLayout());
        displayArea = new JLabel();
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        refreshDisplay(mangaList);
    }

    // Effects: Refreshes the display and displays the entire list of Mangas,
    // should do this after every action performed
    public void refreshDisplay(MyMangaList mangaList) {
        List<Manga> mml = mangaList.viewManga();

        StringBuilder displayText = new StringBuilder();
        for (Manga manga : mml) {
            displayText.append(manga.getName()).append(" - Rating: ").append(manga.getRating()).append(", ");
        }
        displayArea.setText(displayText.toString());
        validate();
        repaint();
    }
}
