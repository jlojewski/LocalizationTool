package loc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class IOManager {

    private LinkedHashMap<String, String> mapOfLoadedFiles;
    private List<TranslationEntry> listOfLoadedFilesAsTranslationEntries;
    private PropertyChangeSupport support;
    private LinkedHashSet<String> setOfUniqueLanguages = new LinkedHashSet<>();


    public LinkedHashMap<String, String> getMapOfLoadedFiles() {
        return mapOfLoadedFiles;
    }

    public void setMapOfLoadedFiles(LinkedHashMap<String, String> mapOfLoadedFiles) {
        var oldVal= this.mapOfLoadedFiles;
        this.mapOfLoadedFiles = mapOfLoadedFiles;
        support.firePropertyChange("mapOfLoadedFiles", oldVal, mapOfLoadedFiles);
    }

    public List<TranslationEntry> getListOfLoadedFilesAsTranslationEntries() {
        return listOfLoadedFilesAsTranslationEntries;
    }

    public void setListOfLoadedFilesAsTranslationEntries(List<TranslationEntry> listOfLoadedFilesAsTranslationEntries) {
        var oldVal= this.listOfLoadedFilesAsTranslationEntries;
        this.listOfLoadedFilesAsTranslationEntries = listOfLoadedFilesAsTranslationEntries;
        support.firePropertyChange("listOfLoadedFilesAsTranslationEntries", oldVal, listOfLoadedFilesAsTranslationEntries);
    }

    public LinkedHashSet<String> getSetOfUniqueLanguages() {
        return setOfUniqueLanguages;
    }

    public void setSetOfUniqueLanguages(LinkedHashSet<String> setOfUniqueLanguages) {
        this.setOfUniqueLanguages = setOfUniqueLanguages;
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


    public ArrayList<TranslationEntry> loadTranslationFiles() {
        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
//        LinkedHashMap loadedFilesMap = new LinkedHashMap<String, String>();
        ObjectMapper fileImportMapper = new ObjectMapper();
//        ArrayList<LinkedHashMap<String, String>> listOfLoadedFiles = new ArrayList<LinkedHashMap<String, String>>();
        ArrayList<List<TranslationEntry>> listOfLoadedFiles = new ArrayList<List<TranslationEntry>>();
        for (File f : filesToConvert) {

            Path importedFilePath = Paths.get(f.getAbsolutePath());
            Path importedFileName = importedFilePath.getFileName();
//            listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertJsonToMap(f, fileImportMapper));
            LinkedHashMap<String, String> tempMap = TranslationEntryManager.getInstance().convertJsonToMap(f, fileImportMapper);
            listOfLoadedFiles.add(TranslationEntryManager.getInstance().createTranslationEntriesFromMap(tempMap, importedFileName));

        }
        ArrayList<TranslationEntry> finalMergedList = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(listOfLoadedFiles);

        return finalMergedList;
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

//    public LinkedHashMap<String, String> loadTranslationFiles() {
//        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
////        LinkedHashMap loadedFilesMap = new LinkedHashMap<String, String>();
//        ObjectMapper fileImportMapper = new ObjectMapper();
//        ArrayList<LinkedHashMap<String, String>> listOfLoadedFiles = new ArrayList<LinkedHashMap<String, String>>();
//        for (File f : filesToConvert) {
//
//            listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertJsonToMap(f, fileImportMapper));
//        }
//        LinkedHashMap<String, String> finalMergedMap = TranslationEntryManager.getInstance().mergeLoadedEntryFiles(listOfLoadedFiles);
//
//        return finalMergedMap;
//    }


//    public void saveConsolidatedTranslationFile(LinkedHashMap consolidatedMap) {
//        String programPath = (System.getProperty("user.dir"));
//        try {
//            File savedConsolidatedFile = new File(programPath, "consolidated_translation_file.json");
//            mapConsolidatedTranslationFile(consolidatedMap, savedConsolidatedFile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void saveConsolidatedTranslationFile(List<TranslationEntry> consolidatedArray) {
        String programPath = (System.getProperty("user.dir"));
        try {
            File savedConsolidatedFile = new File(programPath, "consolidated_translation_file.json");
            mapConsolidatedTranslationFile(consolidatedArray, savedConsolidatedFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public void mapConsolidatedTranslationFile(LinkedHashMap consolidatedMapToMap, File targetFile) {
//        ObjectMapper fileSaveMapper = new ObjectMapper();
//        try {
//            fileSaveMapper.writeValue(targetFile, consolidatedMapToMap.entrySet());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void mapConsolidatedTranslationFile(List<TranslationEntry> consolidatedArrayToMap, File targetFile) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        try {
            jsonObjectMapper.writeValue(targetFile, consolidatedArrayToMap);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


    public void saveTranslationSettings(TranslationSettings settingsToUse) {
        ObjectMapper translationSettingsMapper = new ObjectMapper();
        String programPath = (System.getProperty("user.dir"));
        try {
            File savedTranslationSettingsFile = new File(programPath, "translation_settings.json");
            translationSettingsMapper.writeValue(savedTranslationSettingsFile, settingsToUse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TranslationSettings loadTranslationSettings(File settingsToImport) {
            TranslationSettings settings = null;
            try {
                ObjectMapper settingsImportMapper = new ObjectMapper();
                settings = settingsImportMapper.readValue(settingsToImport , TranslationSettings.class);
            } catch (IOException e) {
                GUIManager.getInstance().setupSettingsChooser();
            }
            return settings;
    }


//        consolidatedMapToMap.entrySet().forEach(entry -> {
//            try {
//                fileSaveMapper.writeValue(targetFile, entry);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });



}



