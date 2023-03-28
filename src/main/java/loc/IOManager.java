package loc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.io.FilenameUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class IOManager {

    private LinkedHashMap<String, String> mapOfLoadedFiles;
    private List<TranslationEntry> listOfLoadedFilesAsTranslationEntries;
    private PropertyChangeSupport support;
    private LinkedHashSet<String> setOfUniqueLanguages = new LinkedHashSet<>();
    //    private File loadedTranslationFileForExport;
    private List<TranslationEntry> loadedTranslationFileForExport;


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

    public List<TranslationEntry> getLoadedTranslationFileForExport() {
        return loadedTranslationFileForExport;
    }

    public void setLoadedTranslationFileForExport(List<TranslationEntry> loadedTranslationFileForExport) {
        this.loadedTranslationFileForExport = loadedTranslationFileForExport;
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



    public ArrayList<TranslationEntry> loadUnconsolidatedTranslationFiles() {
        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupGameToTranslationFileChooser()));

        ObjectMapper fileImportMapper = new ObjectMapper();
        fileImportMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

        ArrayList<List<TranslationEntry>> listOfLoadedFiles = new ArrayList<List<TranslationEntry>>();
        for (File f : filesToConvert) {


            try {
                Path importedFilePath = Paths.get(f.getAbsolutePath());
                Path importedFileName = importedFilePath.getFileName();
                String convertedPath = importedFilePath.toString();
                String targetJson = readFileAsString(convertedPath);
                listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertGameJsonToList(f, targetJson, fileImportMapper));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ArrayList<TranslationEntry> finalMergedList = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(listOfLoadedFiles);

        return finalMergedList;
    }


    public ArrayList<TranslationEntry> loadConsolidatedTranslationFile(File consolidatedJson) {
        ArrayList<TranslationEntry> result = new ArrayList<TranslationEntry>();
        ObjectMapper fileImportMapper = new ObjectMapper();
        TypeReference<ArrayList<TranslationEntry>> typeRef = new TypeReference<ArrayList<TranslationEntry>>(){};

        try {
            result = fileImportMapper.readValue(consolidatedJson, typeRef);


        } catch (IOException e) {
            e.printStackTrace();
            //come back later and check if the above needs to be replaced with
            //another call GUIManager.getInstance().setupFileChooser();
            //as it seems to be buggy at the moment
        }


        return result;
    }




//latest commented version of the method
//    public ArrayList<TranslationEntry> loadTranslationFiles() {
//        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
//
//        ObjectMapper fileImportMapper = new ObjectMapper();
//
//        ArrayList<List<TranslationEntry>> listOfLoadedFiles = new ArrayList<List<TranslationEntry>>();
//        for (File f : filesToConvert) {
//
//
//            listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertJsonToList(f, fileImportMapper));
//
//        }
//        ArrayList<TranslationEntry> finalMergedList = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(listOfLoadedFiles);
//
//        return finalMergedList;
//    }



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

//    public void saveConsolidatedTranslationFile(List<TranslationEntry> consolidatedArray) {
//        String programPath = (System.getProperty("user.dir"));
//        try {
//            File savedConsolidatedFile = new File(programPath, "consolidated_translation_file.json");
//            mapConsolidatedTranslationFile(consolidatedArray, savedConsolidatedFile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void saveConsolidatedTranslationFile(List<TranslationEntry> consolidatedArray) {
        String programPath = (System.getProperty("user.dir"));
        try {
            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(consolidatedArray, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
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


//  work in progress - messy, unfinished; to be cleaned up and split into separate methods
    public void exportUnconsolidatedTranslationFiles(List<TranslationEntry> listOfEntries) {
        LinkedHashMap<String, String> convertedExportMap;
        ObjectMapper exportMapper = new ObjectMapper();

        try {
            Files.createDirectories(Paths.get("localization"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String programPath = (System.getProperty("user.dir"));
        String basePath = FilenameUtils.concat(programPath, "localization");
        String varPath;
        String outputKey;
        String outputValue;




        for (TranslationEntry t : listOfEntries) {
            try {
                LinkedHashMap<String, String> languages = t.getLanguages();
                for (Map.Entry<String, String> entry : languages.entrySet()) {
                    varPath = FilenameUtils.concat(basePath, entry.getKey());
                    Files.createDirectories(Paths.get(varPath));
                    LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
                    tempMap.put(t.getEntryKey(), entry.getValue());
                    saveUnconsolidatedFileVariant(tempMap, exportMapper, varPath, t.getFilename());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void saveUnconsolidatedFileVariant(LinkedHashMap<String, String> mapVariantToSave, ObjectMapper mapper, String path, String filename) {
        try {
            File savedFileVariant = new File(path, filename);
            mapper.writeValue(savedFileVariant, mapVariantToSave);
        } catch (IOException e) {
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


    public String readFileAsString(String file) throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }



//    public void setListOfLoadedFilesAs(List<TranslationEntry> listOfLoadedFilesAsTranslationEntries) {
//        var oldVal= this.listOfLoadedFilesAsTranslationEntries;
//        this.listOfLoadedFilesAsTranslationEntries = listOfLoadedFilesAsTranslationEntries;
//    }




}



