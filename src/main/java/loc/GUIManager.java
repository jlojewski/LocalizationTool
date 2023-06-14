package loc;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.apache.commons.io.FilenameUtils;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class GUIManager implements ActionListener, PropertyChangeListener, ChangeListener {
    JFrame mainFrame;
    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JPanel buttonPanel3;
    JTextArea log;
    JFileChooser fileChooser;
    JFileChooser settingsChooser;
    JFileChooser fileToGameChooser;
    JButton openButtonGameToTrans1;
    JButton saveButtonGameToTrans1;
    JButton importSettingsButton;
    JButton languageSettingsButton;
    JButton confirmButtonGameToTrans1;
    JScrollPane logScrollPane;
    BorderLayout layout1;
    BorderLayout layout2;
    LanguageMenu languageMenu;
    TitledBorder border1;
    TitledBorder border2;
    TitledBorder border3;
    JButton openButtonTransToGame1;
    JButton openButtonTransToGame2;
    JButton saveButtonTransToGame1;
    JButton confirmButtonTransToGame1;
    JButton openButtonForMerge1;
    JButton openButtonForMerge2;
    JButton confirmButtonForMerge1;
    JButton confirmButtonForMerge2;
    JButton mergeButton;
    JTabbedPane tabPane;

    private static GUIManager GUIManagerInstance;

    private GUIManager() {
        fileChooser = new JFileChooser();


    }

    public static GUIManager getInstance() {
        if (GUIManagerInstance == null) {
            GUIManagerInstance = new GUIManager();
        }

        return GUIManagerInstance;
    }


    public void prepareMainGUI() {
        layout1 = new BorderLayout();
        layout2 = new BorderLayout();
        tabPane = new JTabbedPane();

        log = new JTextArea(8, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        logScrollPane = new JScrollPane(log);

        fileChooser = new JFileChooser();
        settingsChooser = new JFileChooser();
        fileToGameChooser = new JFileChooser();

        openButtonGameToTrans1 = new JButton("Add game JSONs");
        openButtonGameToTrans1.addActionListener(this);

        saveButtonGameToTrans1 = new JButton("Export to translation file");
        saveButtonGameToTrans1.addActionListener(this);

        confirmButtonGameToTrans1 = new JButton("Confirm JSON selection");
        confirmButtonGameToTrans1.addActionListener(this);

        openButtonTransToGame1 = new JButton("Add game JSONs");
        openButtonTransToGame1.addActionListener(this);

        openButtonTransToGame2 = new JButton("Load translation file");
        openButtonTransToGame2.addActionListener(this);

        saveButtonTransToGame1 = new JButton("Export to game");
        saveButtonTransToGame1.addActionListener(this);

        confirmButtonTransToGame1 = new JButton("Confirm JSON selection");
        confirmButtonTransToGame1.addActionListener(this);

        importSettingsButton = new JButton("Import language settings");
        importSettingsButton.addActionListener(this);

        languageSettingsButton = new JButton("Create language settings");
        languageSettingsButton.addActionListener(this);

        openButtonForMerge1 = new JButton("Load base translation files to receive merge");
        openButtonForMerge1.addActionListener(this);

        openButtonForMerge2 = new JButton("Load translation files to be merged");
        openButtonForMerge2.addActionListener(this);

        confirmButtonForMerge1 = new JButton("Confirm selection #1");
        confirmButtonForMerge1.addActionListener(this);

        confirmButtonForMerge2 = new JButton("Confirm selection #2");
        confirmButtonForMerge2.addActionListener(this);

        mergeButton = new JButton("Merge into base file");
        mergeButton.addActionListener(this);

        border1 = new TitledBorder("Game files -> Translation files");
        border1.setTitleJustification(TitledBorder.CENTER);
        border1.setTitlePosition(TitledBorder.TOP);

        border2 = new TitledBorder("Translation files -> Game files");
        border2.setTitleJustification(TitledBorder.CENTER);
        border2.setTitlePosition(TitledBorder.TOP);

        border3 = new TitledBorder("Merge language entries into a single translation file");
        border3.setTitleJustification(TitledBorder.CENTER);
        border3.setTitlePosition(TitledBorder.TOP);

        buttonPanel1 = new JPanel(new GridLayout(1, 0));
        buttonPanel1.add(openButtonGameToTrans1);
        buttonPanel1.add(confirmButtonGameToTrans1);
        buttonPanel1.add(importSettingsButton);
        importSettingsButton.setEnabled(false);
        buttonPanel1.add(languageSettingsButton);
//        languageSettingsButton.setEnabled(false);
        buttonPanel1.add(saveButtonGameToTrans1);
        buttonPanel1.setBorder(border1);

//        layout1.addLayoutComponent(buttonPanel1, BorderLayout.NORTH);
//        layout1.addLayoutComponent(logScrollPane, BorderLayout.PAGE_END);
        languageMenu = new LanguageMenu();

//        buttonPanel2 = new JPanel(new BorderLayout());
        buttonPanel2 = new JPanel(new GridLayout(1, 0));
        buttonPanel2.add(openButtonTransToGame1);
        buttonPanel2.add(confirmButtonTransToGame1);
        buttonPanel2.add(openButtonTransToGame2);
        buttonPanel2.add(saveButtonTransToGame1);
        buttonPanel2.setBorder(border2);
//        layout1.addLayoutComponent(buttonPanel2, BorderLayout.NORTH);
//        layout2.addLayoutComponent(logScrollPane, BorderLayout.PAGE_END);


        buttonPanel3 = new JPanel(new GridLayout(1, 0));
        buttonPanel3.add(openButtonForMerge1);
        buttonPanel3.add(confirmButtonForMerge1);
        buttonPanel3.add(openButtonForMerge2);
        buttonPanel3.add(confirmButtonForMerge2);
        buttonPanel3.add(mergeButton);
        buttonPanel3.setBorder(border3);


        tabPane.addTab("Game -> Translation", buttonPanel1);
        tabPane.addTab("Translation -> Game", buttonPanel2);
        tabPane.addTab("Merge translation files", buttonPanel3);
        tabPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("Tab " + tabPane.getSelectedIndex());
                IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(new ArrayList<>());
                IOManager.getInstance().setExpandableListOfLoadedFiles(new ArrayList<>());
                IOManager.getInstance().setSetOfUniqueLanguages(new LinkedHashSet<>());
                IOManager.getInstance().setLoadedTranslationFileForExport(new ArrayList<>());

            }
        });



    }

    public void showMainGUI() {
        mainFrame = new JFrame("Localization Tool");
//        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);



        mainFrame.add(tabPane);
//        mainFrame.add(logScrollPane);

//        mainFrame.add(buttonPanel1);
//        mainFrame.add(buttonPanel2);
//        mainFrame.add(logScrollPane);
//        mainFrame.setLayout(layout1);


        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public File[] setupGameToTranslationFileChooser() {

        String programPath = System.getProperty("user.dir");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setCurrentDirectory(new File(programPath));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Choose all .json files containing translation files");

        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == fileChooser.APPROVE_OPTION) {
            File[] chosenFiles = fileChooser.getSelectedFiles();

            for (var f : chosenFiles) {
                String fileExt = FilenameUtils.getExtension(f.toString());

                if (!fileExt.equals("json")) {
                    return setupGameToTranslationFileChooser();

                } else {
                    break;
                }
            }
            return chosenFiles;
        } else {
            return null;
        }
    }

    public File setupSettingsChooser() {

        String programPath = System.getProperty("user.dir");
        settingsChooser.setMultiSelectionEnabled(false);
        settingsChooser.setCurrentDirectory(new File(programPath));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        settingsChooser.setFileFilter(filter);
        settingsChooser.setDialogTitle("Choose a .json file containing language settings");

        int returnVal = settingsChooser.showOpenDialog(null);
        if (returnVal == settingsChooser.APPROVE_OPTION) {
            File chosenSettings = settingsChooser.getSelectedFile();
            String fileExt = FilenameUtils.getExtension(chosenSettings.toString());

                if (!fileExt.equals("json")) {
                    return setupSettingsChooser();

                } else {
                    return chosenSettings;
                }

        } else {
            return null;
        }
    }

    public File setupTranslationToGameFileChooser() {

        String programPath = System.getProperty("user.dir");
        fileToGameChooser.setMultiSelectionEnabled(false);
        fileToGameChooser.setCurrentDirectory(new File(programPath));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        fileToGameChooser.setFileFilter(filter);
        fileToGameChooser.setDialogTitle("Choose a previously generated .json translation file");

        int returnVal = fileToGameChooser.showOpenDialog(null);
        if (returnVal == fileToGameChooser.APPROVE_OPTION) {
            File chosenFile = fileToGameChooser.getSelectedFile();
            String fileExt = FilenameUtils.getExtension(chosenFile.toString());

            if (!fileExt.equals("json")) {
                return setupTranslationToGameFileChooser();

            } else {
                return chosenFile;
            }

            ///TU SIĘ CZAI CANCELUJACE ZLO - WYMYSL JAK TO OBSLUZYC
        } else {
            return null;
        }
    }

    public String openLanguageDialogInput() {
        String userInput = (String) JOptionPane.showInputDialog(
                null,
                "Insert your desired name/shorthand for the new language:",
                "Add language",
                JOptionPane.PLAIN_MESSAGE
        );

        return userInput;

    }

    public void enableLanguageButton(boolean trueOrFalse) {
        if (trueOrFalse == true) {
            importSettingsButton.setEnabled(true);
        } else {
            importSettingsButton.setEnabled(false);
        }
    }

    public void openLanguageTable() {
        languageMenu.setVisible(true);

        // og language table code - uncomment when your current solution inevitably doesn't work
//        LanguageCheckboxTable languageBox = new LanguageCheckboxTable();
//        languageBox.setDefaultCloseOperation(HIDE_ON_CLOSE);
//        languageBox.pack();
//        languageBox.setLocation(150, 150);
//        languageBox.setVisible(true);
    }

    public void pickLanguagesFromTable(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 2).toString());

            if (isChecked) {

            } else {

            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openButtonGameToTrans1) {
            var chosenFiles = setupGameToTranslationFileChooser();
            if (chosenFiles == null) {
                return;
            }
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));

            var tempListOfLists = IOManager.getInstance().getExpandableListOfLoadedFiles();
            tempListOfLists.add(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries());
            IOManager.getInstance().setExpandableListOfLoadedFiles(tempListOfLists);


//                log.append("Opening: " + file.getName() + "." + newline);
//                log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == confirmButtonGameToTrans1) {
            var listToBeUsed = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(IOManager.getInstance().getExpandableListOfLoadedFiles());
            Collections.sort(listToBeUsed);
            IOManager.getInstance().setListOfExtractedKeys(TranslationEntryManager.getInstance().extractKeys(listToBeUsed));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(listToBeUsed);
//            TranslationEntryManager.getInstance().extractKeys(TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(IOManager.getInstance().getExpandableListOfLoadedFiles()));

        } else if (e.getSource() == saveButtonGameToTrans1) {
//            IOManager.getInstance().saveConsolidatedTranslationFile(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries());
            IOManager.getInstance().saveConsolidatedTranslationFile(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries());
//            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == importSettingsButton) {
            var chosenFile = setupSettingsChooser();
            if (chosenFile == null) {
                return;
            }
            TranslationSettingsManager.getInstance().setCurrentTranslationSettings(IOManager.getInstance().loadTranslationSettings(chosenFile));


        } else if (e.getSource() == languageSettingsButton) {
//            openLanguageDialogInput();
            openLanguageTable();

        } else if (e.getSource() == openButtonTransToGame1) {
            var chosenFiles = setupGameToTranslationFileChooser();
            if (chosenFiles == null) {
                return;
            }
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));

            var tempListOfLists = IOManager.getInstance().getExpandableListOfLoadedFiles();
            tempListOfLists.add(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries());
            IOManager.getInstance().setExpandableListOfLoadedFiles(tempListOfLists);


        } else if (e.getSource() == confirmButtonTransToGame1) {
            var listToBeUsed = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(IOManager.getInstance().getExpandableListOfLoadedFiles());
            Collections.sort(listToBeUsed);
            IOManager.getInstance().setListOfExtractedKeys(TranslationEntryManager.getInstance().extractKeys(listToBeUsed));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(listToBeUsed);

        } else if (e.getSource() == openButtonTransToGame2) {
            var chosenFile = setupTranslationToGameFileChooser();
            if (chosenFile == null) {
                return;
            }
            IOManager.getInstance().setLoadedTranslationFileForExport(IOManager.getInstance().loadConsolidatedTranslationFile(chosenFile));
            var keysFromTranslationFile = TranslationEntryManager.getInstance().extractKeys(IOManager.getInstance().getLoadedTranslationFileForExport());
//            TranslationEntryManager.getInstance().compareKeys(IOManager.getInstance().getListOfExtractedKeys(), keysFromTranslationFile);
            TranslationEntryManager.getInstance().compareKeys(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries(), IOManager.getInstance().getLoadedTranslationFileForExport());

        } else if (e.getSource() == saveButtonTransToGame1) {
            IOManager.getInstance().exportUnconsolidatedTranslationFiles(IOManager.getInstance().getLoadedTranslationFileForExport());


        } else if (e.getSource() == openButtonForMerge1) {
            var chosenFiles = setupGameToTranslationFileChooser();
            if (chosenFiles == null) {
                return;
            }
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntriesForMerge1(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));

            var tempListOfLists = IOManager.getInstance().getExpandableListOfLoadedFilesForMerge1();
            tempListOfLists.add(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntriesForMerge1());
            IOManager.getInstance().setExpandableListOfLoadedFilesForMerge1(tempListOfLists);


        } else if (e.getSource() == confirmButtonForMerge1) {
            var listToBeUsed = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(IOManager.getInstance().getExpandableListOfLoadedFilesForMerge1());
            Collections.sort(listToBeUsed);
            IOManager.getInstance().setListOfExtractedKeys(TranslationEntryManager.getInstance().extractKeys(listToBeUsed));
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(listToBeUsed);
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntriesForMerge1(listToBeUsed);


        } else if (e.getSource() == openButtonForMerge2) {
            var chosenFiles = setupGameToTranslationFileChooser();
            if (chosenFiles == null) {
                return;
            }
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));

            var tempListOfLists = IOManager.getInstance().getExpandableListOfLoadedFilesForMerge2();
            tempListOfLists.add(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntriesForMerge2());
            IOManager.getInstance().setExpandableListOfLoadedFilesForMerge2(tempListOfLists);

        } else if (e.getSource() == confirmButtonForMerge2) {
            var listToBeUsed = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(IOManager.getInstance().getExpandableListOfLoadedFilesForMerge2());
            Collections.sort(listToBeUsed);
            IOManager.getInstance().setListOfExtractedKeys(TranslationEntryManager.getInstance().extractKeys(listToBeUsed));
//            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(listToBeUsed);
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntriesForMerge2(listToBeUsed);




        }



    }


    //ustal czemu i kiedy w konsoli bialym tekstem pojawia "Exception while removing references" na zamknieciu programu


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //enableLanguageButton(IOManager.getInstance().checkIfTranslationFilesHaveBeenLoaded());
        enableLanguageButton(evt.getNewValue() != null);
    }


    // na razie brzydko zrobione actionperformed, wiec zanim nie zrobisz kosmetyki - zrob propertychange ifami tak jak w actionperformed, jak wyzej



    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
