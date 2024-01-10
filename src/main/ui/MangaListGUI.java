package ui;

import model.Event;
import model.EventLog;
import model.Manga;
import model.MyMangaList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// MyMangaList App with a GUI, buttons to add/remove/edit/save/load manga with
// graphical components
public class MangaListGUI extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/MyMangaList.json";
    private MyMangaList mangaList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private MangaDisplayUI mangaDisplayUI;

    private JPanel buttonPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem menuItem;

    // Effects: creates a new MangaListGUI
    public MangaListGUI() {
        mangaList = new MyMangaList("My Mangas");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        setTitle("MyMangaList");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();

        addButtonPanel();
        addMenu();
        addMangaDisplayPanel();

        setVisible(true);
    }

    // Effects: adds MangaDisplayUI with mangaList which displays the mangas in the mangaList so far
    private void addMangaDisplayPanel() {
        mangaDisplayUI = new MangaDisplayUI(mangaList);
        add(mangaDisplayUI, BorderLayout.CENTER);
    }

    // Effects: adds buttons representing the functions of the MangaListGUI Application
    private void addButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        buttonPanel.add(new JButton(new AddMangaAction()));
        buttonPanel.add(new JButton(new RemoveMangaAction()));
        buttonPanel.add(new JButton(new EditMangaAction()));
        buttonPanel.add(new JButton(new SaveMangaListAction()));
        buttonPanel.add(new JButton(new LoadMangaListAction()));
        buttonPanel.add(new JButton((new QuitAction())));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Effects: creates JMenuBar while also implementing function shortcut commands
    private void addMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("MyMangaList Application");
        fileMenu.setMnemonic('F');
        addMenuItem(fileMenu, new SaveMangaListAction(), KeyStroke.getKeyStroke("control S"));
        addMenuItem(fileMenu, new LoadMangaListAction(), KeyStroke.getKeyStroke("control L"));
        addMenuItem(fileMenu, new RemoveMangaAction(), KeyStroke.getKeyStroke("control R"));
        addMenuItem(fileMenu, new AddMangaAction(), KeyStroke.getKeyStroke("control A"));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // Effects: intakes commands and processes it to do a given action
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // Effects: centres the list of manga on screen
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // Effects: quits the application and prints out eventlog
    private class QuitAction extends AbstractAction {
        QuitAction() {
            super("Quit App");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            EventLog el = EventLog.getInstance(); // TODO

            for (Event event : el) {
                System.out.println(event.getDescription());
            }

            System.exit(0);
        }
    }

    // Modifies: mangaList
    // Effects: adds a manga to mangaList
    private class AddMangaAction extends AbstractAction {
        AddMangaAction() {
            super("Add Manga");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String mangaName = JOptionPane.showInputDialog(MangaListGUI.this,
                    "Enter title of manga:");
            if (mangaName != null) {
                int mangaRating = Integer.parseInt(JOptionPane.showInputDialog(MangaListGUI.this,
                        "Enter your rating:"));
                while (mangaRating > 10 || mangaRating < 1) {
                    mangaRating = Integer.parseInt(JOptionPane.showInputDialog(MangaListGUI.this,
                            "Invalid Rating. Please enter a rating between 1 - 10:"));
                }
                mangaList.addManga(new Manga(mangaName, mangaRating));
                ImageIcon icon = new ImageIcon("./data/geto.png");
                String message = mangaName + " has been added in your MyMangaList.";
                JLabel label = new JLabel(message, icon, JLabel.CENTER);

                JOptionPane.showMessageDialog(MangaListGUI.this, label);
                mangaDisplayUI.refreshDisplay(mangaList);
            }
        }
    }

    // Modifies: mangaList
    // Effects: removes given manga from mangaList
    private class RemoveMangaAction extends AbstractAction {
        RemoveMangaAction() {
            super("Remove Manga");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String mangaName = JOptionPane.showInputDialog(MangaListGUI.this,
                    "Enter title of manga:");
            if (mangaName != null && !mangaList.viewManga().contains(mangaName)) {
                mangaList.removeManga(mangaName);
            }

            ImageIcon icon = new ImageIcon("./data/red.png");
            String message = mangaName + " has been removed in your MyMangaList.";
            JLabel label = new JLabel(message, icon, JLabel.CENTER);

            JOptionPane.showMessageDialog(MangaListGUI.this, label);
            mangaDisplayUI.refreshDisplay(mangaList);
        }
    }

    // Modifies: mangaList
    // Effects: edits a Manga in the MyMangaList
    private class EditMangaAction extends AbstractAction {
        EditMangaAction() {
            super("Edit Manga");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String mangaName = JOptionPane.showInputDialog(MangaListGUI.this,
                    "Enter title of manga to edit:");

            if (mangaName != null && !mangaName.isEmpty()) {
                String newMangaName = JOptionPane.showInputDialog(MangaListGUI.this,
                        "Enter edited title of manga: ");

                if (newMangaName != null && !newMangaName.isEmpty()) {
                    int newMangaRating = Integer.parseInt(JOptionPane.showInputDialog(MangaListGUI.this,
                            "Enter edited rating:"));
                    while (newMangaRating > 10 || newMangaRating < 1) {
                        newMangaRating = Integer.parseInt(JOptionPane.showInputDialog(MangaListGUI.this,
                                "Invalid Rating. Please enter a rating between 1 - 10:"));
                    }

                    if (mangaList.modifyManga(mangaName, newMangaName, newMangaRating)) {
                        ImageIcon icon = new ImageIcon("./data/felix.jpg");
                        JLabel label = new JLabel(mangaName + " has been edited.", icon, JLabel.CENTER);

                        JOptionPane.showMessageDialog(MangaListGUI.this, label);
                        mangaDisplayUI.refreshDisplay(mangaList);
                    }
                }
            }
        }
    }

    // Effects: save mangaList to the JSON_STORE
    private class SaveMangaListAction extends AbstractAction {
        SaveMangaListAction() {
            super("Save Manga List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(mangaList);
                jsonWriter.close();

                ImageIcon icon1 = new ImageIcon("./data/astolfo.png");
                String message = mangaList.getName() + " saved to " + JSON_STORE;
                JLabel label = new JLabel(message, icon1, JLabel.CENTER);

                JOptionPane.showMessageDialog(MangaListGUI.this, label);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(MangaListGUI.this,
                        "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Modifies: mangaList
    // Effects: loads pre-existing save file from MangaListGUI Application
    private class LoadMangaListAction extends AbstractAction {

        LoadMangaListAction() {
            super("Load Manga List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                mangaList = jsonReader.read();

                ImageIcon icon = new ImageIcon("./data/beserk.jpg");
                String message = "Loaded " + mangaList.getName() + " from " + JSON_STORE;
                JLabel label = new JLabel(message, icon, JLabel.CENTER);

                JOptionPane.showMessageDialog(MangaListGUI.this, label);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(MangaListGUI.this,
                        "Unable to load " + mangaList.getName() + " from " + JSON_STORE);
            }

            mangaDisplayUI.refreshDisplay(mangaList);
        }
    }

    // Effects: runs the program
    public static void main(String[] args) {
        new MangaListGUI();
    }
}

// Citation: AlarmSystemUI