package loc;


import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class GUIManager {
//    JFrame mainFrame;
//    JPanel buttonPanel;
//    JTextArea log;
    JFileChooser fileChooser;
//    JButton openButton;
//    JButton saveButton;
//    JScrollPane logScrollPane;
//    BorderLayout layout;

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

//
//    public void prepareMainGUI() {
//        layout = new BorderLayout();
//
//        log = new JTextArea(5,20);
//        log.setMargin(new Insets(5,5,5,  5));
//        log.setEditable(false);
//        logScrollPane = new JScrollPane(log);
//
//        fileChooser = new JFileChooser();
//
//        openButton = new JButton("Open .json");
//        saveButton = new JButton("Save");
//
//        buttonPanel = new JPanel();
//        buttonPanel.add(openButton);
//        buttonPanel.add(saveButton);
//
//        layout.addLayoutComponent(buttonPanel, BorderLayout.PAGE_START);
//        layout.addLayoutComponent(logScrollPane, BorderLayout.CENTER);
//
//
//    }
//
//    public void showMainGUI() {
//        JFrame mainFrame = new JFrame("Localization Tool");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        mainFrame.add(buttonPanel);
//        mainFrame.add(logScrollPane);
//        mainFrame.setLayout(layout);
//
//        mainFrame.pack();
//        mainFrame.setVisible(true);
//    }



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




}
