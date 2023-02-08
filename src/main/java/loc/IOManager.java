package loc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class IOManager {

    private LinkedHashMap<String, String> loadedFiles;
    private PropertyChangeSupport support;

    public LinkedHashMap<String, String> getLoadedFiles() {
        return loadedFiles;
    }

    public void setLoadedFiles(LinkedHashMap<String, String> loadedFiles) {
        var oldVal= this.loadedFiles;
        this.loadedFiles = loadedFiles;
        support.firePropertyChange("loadedFiles", oldVal, loadedFiles);
    }

    private static IOManager IOManagerInstance;

    private IOManager() {
        support = new PropertyChangeSupport(this);
        addPropertyChangeListener(GUIManager.getInstance());
    }

    public static IOManager getInstance() {
        if (IOManagerInstance == null) {
            IOManagerInstance = new IOManager();
        }

        return IOManagerInstance;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


//    public LinkedHashMap<String, String> loadTranslationFiles() {
//        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
////        LinkedHashMap loadedFilesMap = new LinkedHashMap<String, String>();
//        ObjectMapper fileImportMapper = new ObjectMapper();
//        ArrayList<LinkedHashMap<String, String>> listOfLoadedFiles = new ArrayList<LinkedHashMap<String, String>>();
//        for (File f : filesToConvert) {
////            try {
////                TranslationEntry tren = fileImportMapper.readValue(f, TranslationEntry.class);
////                loadedFilesMap.put(tren.entryKey, tren.entryValue);
////            } catch (IOException e) {
////                GUIManager.getInstance().setupFileChooser();
////            }
////        }
////        return loadedFilesMap;
//            listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertJsonToMap(f, fileImportMapper));
//        }
//        LinkedHashMap<String, String> finalMergedMap = TranslationEntryManager.getInstance().mergeLoadedEntryFiles(listOfLoadedFiles);
//
//        return finalMergedMap;
//    }

    public LinkedHashMap<String, String> loadTranslationFiles() {
        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
//        LinkedHashMap loadedFilesMap = new LinkedHashMap<String, String>();
        ObjectMapper fileImportMapper = new ObjectMapper();
        ArrayList<LinkedHashMap<String, String>> listOfLoadedFiles = new ArrayList<LinkedHashMap<String, String>>();
        for (File f : filesToConvert) {

            listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertJsonToMap(f, fileImportMapper));
        }
        LinkedHashMap<String, String> finalMergedMap = TranslationEntryManager.getInstance().mergeLoadedEntryFiles(listOfLoadedFiles);

        return finalMergedMap;
    }


    public void saveConsolidatedTranslationFile(LinkedHashMap consolidatedMap) {
        String programPath = (System.getProperty("user.dir"));
        try {
            File savedConsolidatedFile = new File(programPath, "consolidated_translation_file.json");
            mapConsolidatedTranslationFile(consolidatedMap, savedConsolidatedFile);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void mapConsolidatedTranslationFile(LinkedHashMap consolidatedMapToMap, File targetFile) {
        ObjectMapper fileSaveMapper = new ObjectMapper();
        try {
            fileSaveMapper.writeValue(targetFile, consolidatedMapToMap.entrySet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;

//        consolidatedMapToMap.entrySet().forEach(entry -> {
//            try {
//                fileSaveMapper.writeValue(targetFile, entry);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });



}



