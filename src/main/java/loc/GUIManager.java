package loc;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.LinkedHashMap;
import org.apache.commons.io.FilenameUtils;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class GUIManager implements ActionListener, PropertyChangeListener {
    JFrame mainFrame;
    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JTextArea log;
    JFileChooser fileChooser;
    JFileChooser settingsChooser;
    JFileChooser fileToGameChooser;
    JButton openButton1;
    JButton saveButton1;
    JButton importSettingsButton;
    JButton languageSettingsButton;
    JScrollPane logScrollPane;
    BorderLayout layout;
    LanguageMenu languageMenu;
    TitledBorder border1;
    TitledBorder border2;
    JButton openButton2;
    JButton saveButton2;

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
        layout = new BorderLayout();

        log = new JTextArea(8, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        logScrollPane = new JScrollPane(log);

        fileChooser = new JFileChooser();
        settingsChooser = new JFileChooser();
        fileToGameChooser = new JFileChooser();

        openButton1 = new JButton("Open .json");
        openButton1.addActionListener(this);

        saveButton1 = new JButton("Save");
        saveButton1.addActionListener(this);

        openButton2 = new JButton("Open .json");
        openButton2.addActionListener(this);

        saveButton2 = new JButton("Export to game");
        saveButton2.addActionListener(this);

        importSettingsButton = new JButton("Import language settings");
        importSettingsButton.addActionListener(this);

        languageSettingsButton = new JButton("Create language settings");
        languageSettingsButton.addActionListener(this);

        border1 = new TitledBorder("Game files -> Translation files");
        border1.setTitleJustification(TitledBorder.CENTER);
        border1.setTitlePosition(TitledBorder.TOP);

        border2 = new TitledBorder("Translation files -> Game files");
        border2.setTitleJustification(TitledBorder.CENTER);
        border2.setTitlePosition(TitledBorder.TOP);

        buttonPanel1 = new JPanel();
        buttonPanel1.add(openButton1);
        buttonPanel1.add(importSettingsButton);
        buttonPanel1.add(languageSettingsButton);
        languageSettingsButton.setEnabled(false);
        buttonPanel1.add(saveButton1);
        buttonPanel1.setBorder(border1);

        layout.addLayoutComponent(buttonPanel1, BorderLayout.PAGE_START);
        languageMenu = new LanguageMenu();

        buttonPanel2 = new JPanel();
        buttonPanel2.add(openButton2);
        buttonPanel2.add(saveButton2);

        buttonPanel2.setBorder(border2);
        layout.addLayoutComponent(buttonPanel2, BorderLayout.CENTER);
        layout.addLayoutComponent(logScrollPane, BorderLayout.PAGE_END);



    }

    public void showMainGUI() {
        JFrame mainFrame = new JFrame("Localization Tool");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainFrame.add(buttonPanel1);
        mainFrame.add(buttonPanel2);
        mainFrame.add(logScrollPane);
        mainFrame.setLayout(layout);

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
            languageSettingsButton.setEnabled(true);
        } else {
            languageSettingsButton.setEnabled(false);
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

        if (e.getSource() == openButton1) {
            var chosenFiles = setupGameToTranslationFileChooser();
            if (chosenFiles == null) {
                return;
            }
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadUnconsolidatedTranslationFiles(chosenFiles));
//                log.append("Opening: " + file.getName() + "." + newline);
//                log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == saveButton1) {
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

        } else if (e.getSource() == openButton2) {
            var chosenFile = setupTranslationToGameFileChooser();
            if (chosenFile == null) {
                return;
            }
            IOManager.getInstance().setLoadedTranslationFileForExport(IOManager.getInstance().loadConsolidatedTranslationFile(chosenFile));

        } else if (e.getSource() == saveButton2) {
            IOManager.getInstance().exportUnconsolidatedTranslationFiles(IOManager.getInstance().getLoadedTranslationFileForExport());
        }
    }


    //ustal czemu i kiedy w konsoli bialym tekstem pojawia "Exception while removing references" na zamknieciu programu


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //enableLanguageButton(IOManager.getInstance().checkIfTranslationFilesHaveBeenLoaded());
        enableLanguageButton(evt.getNewValue() != null);
    }
}
