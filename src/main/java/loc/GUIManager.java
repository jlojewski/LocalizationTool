package loc;


import javax.swing.*;
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
    JPanel buttonPanel;
    JTextArea log;
    JFileChooser fileChooser;
    JButton openButton;
    JButton saveButton;
    JButton importSettingsButton;
    JButton languageSettingsButton;
    JScrollPane logScrollPane;
    BorderLayout layout;
    LanguageMenu languageMenu;

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

        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        logScrollPane = new JScrollPane(log);

        fileChooser = new JFileChooser();

        openButton = new JButton("Open .json");
        openButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        importSettingsButton = new JButton("Import language settings");
        importSettingsButton.addActionListener(this);

        languageSettingsButton = new JButton("Create language settings");
        languageSettingsButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(importSettingsButton);
        buttonPanel.add(languageSettingsButton);
        languageSettingsButton.setEnabled(false);
        buttonPanel.add(saveButton);

        layout.addLayoutComponent(buttonPanel, BorderLayout.PAGE_START);
        layout.addLayoutComponent(logScrollPane, BorderLayout.CENTER);
        languageMenu = new LanguageMenu();


    }

    public void showMainGUI() {
        JFrame mainFrame = new JFrame("Localization Tool");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainFrame.add(buttonPanel);
        mainFrame.add(logScrollPane);
        mainFrame.setLayout(layout);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public File[] setupFileChooser() {

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
                    return setupFileChooser();

                } else {
                    break;
                }
            }
            return chosenFiles;
        } else {
            System.exit(0);
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

        if (e.getSource() == openButton) {
            IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(IOManager.getInstance().loadTranslationFiles());
//                log.append("Opening: " + file.getName() + "." + newline);
//                log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == saveButton) {
            IOManager.getInstance().saveConsolidatedTranslationFile(IOManager.getInstance().getListOfLoadedFilesAsTranslationEntries());
//            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == importSettingsButton) {


        } else if (e.getSource() == languageSettingsButton) {
//            openLanguageDialogInput();
            openLanguageTable();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //enableLanguageButton(IOManager.getInstance().checkIfTranslationFilesHaveBeenLoaded());
        enableLanguageButton(evt.getNewValue() != null);
    }
}
