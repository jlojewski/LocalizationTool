package loc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.ByteArrayDataOutput;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;



public class IOManager {

    private LinkedHashMap<String, String> mapOfLoadedFiles;
    private List<TranslationEntry> listOfLoadedFilesAsTranslationEntries;
    private PropertyChangeSupport support;
    private LinkedHashSet<String> setOfUniqueLanguages = new LinkedHashSet<>();
    private ArrayList<TranslationEntry> loadedTranslationFileForExport;
    private String translationKeyChecksum;
    private ArrayList<List<TranslationEntry>> expandableListOfLoadedFiles;
    private ArrayList<List<TranslationEntry>> expandableListOfLoadedFilesForMerge1;
    private ArrayList<List<TranslationEntry>> expandableListOfLoadedFilesForMerge2;
    private ArrayList<String> listOfExtractedKeys;
    private List<TranslationEntry> listOfLoadedFilesAsTranslationEntriesForMerge1;
    private List<TranslationEntry> listOfLoadedFilesAsTranslationEntriesForMerge2;
    private int totalCountOfOpenedFiles;

    public List<TranslationEntry> getListOfLoadedFilesAsTranslationEntriesForMerge1() {
        return listOfLoadedFilesAsTranslationEntriesForMerge1;
    }

    public void setListOfLoadedFilesAsTranslationEntriesForMerge1(List<TranslationEntry> listOfLoadedFilesAsTranslationEntriesForMerge1) {
        this.listOfLoadedFilesAsTranslationEntriesForMerge1 = listOfLoadedFilesAsTranslationEntriesForMerge1;
    }

    public List<TranslationEntry> getListOfLoadedFilesAsTranslationEntriesForMerge2() {
        return listOfLoadedFilesAsTranslationEntriesForMerge2;
    }

    public void setListOfLoadedFilesAsTranslationEntriesForMerge2(List<TranslationEntry> listOfLoadedFilesAsTranslationEntriesForMerge2) {
        this.listOfLoadedFilesAsTranslationEntriesForMerge2 = listOfLoadedFilesAsTranslationEntriesForMerge2;
    }

    public ArrayList<List<TranslationEntry>> getExpandableListOfLoadedFilesForMerge1() {
        return expandableListOfLoadedFilesForMerge1;
    }

    public void setExpandableListOfLoadedFilesForMerge1(ArrayList<List<TranslationEntry>> expandableListOfLoadedFilesForMerge1) {
        this.expandableListOfLoadedFilesForMerge1 = expandableListOfLoadedFilesForMerge1;
    }

    public ArrayList<List<TranslationEntry>> getExpandableListOfLoadedFilesForMerge2() {
        return expandableListOfLoadedFilesForMerge2;
    }

    public void setExpandableListOfLoadedFilesForMerge2(ArrayList<List<TranslationEntry>> expandableListOfLoadedFilesForMerge2) {
        this.expandableListOfLoadedFilesForMerge2 = expandableListOfLoadedFilesForMerge2;
    }


    public LinkedHashMap<String, String> getMapOfLoadedFiles() {
        return mapOfLoadedFiles;
    }

    public void setMapOfLoadedFiles(LinkedHashMap<String, String> mapOfLoadedFiles) {
        var oldVal = this.mapOfLoadedFiles;
        this.mapOfLoadedFiles = mapOfLoadedFiles;
        support.firePropertyChange("mapOfLoadedFiles", oldVal, mapOfLoadedFiles);
    }

    public List<TranslationEntry> getListOfLoadedFilesAsTranslationEntries() {
        return listOfLoadedFilesAsTranslationEntries;
    }

    public void setListOfLoadedFilesAsTranslationEntries(List<TranslationEntry> listOfLoadedFilesAsTranslationEntries) {
        var oldVal = this.listOfLoadedFilesAsTranslationEntries;
        this.listOfLoadedFilesAsTranslationEntries = listOfLoadedFilesAsTranslationEntries;
        support.firePropertyChange("listOfLoadedFilesAsTranslationEntries", oldVal, listOfLoadedFilesAsTranslationEntries);
    }

    public ArrayList<List<TranslationEntry>> getExpandableListOfLoadedFiles() {
        return expandableListOfLoadedFiles;
    }

    public void setExpandableListOfLoadedFiles(ArrayList<List<TranslationEntry>> expandableListOfLoadedFiles) {
        this.expandableListOfLoadedFiles = expandableListOfLoadedFiles;
    }


    public LinkedHashSet<String> getSetOfUniqueLanguages() {
        return setOfUniqueLanguages;
    }

    public void setSetOfUniqueLanguages(LinkedHashSet<String> setOfUniqueLanguages) {
        this.setOfUniqueLanguages = setOfUniqueLanguages;
    }

    public ArrayList<TranslationEntry> getLoadedTranslationFileForExport() {
        return loadedTranslationFileForExport;
    }

    public void setLoadedTranslationFileForExport(ArrayList<TranslationEntry> loadedTranslationFileForExport) {
        this.loadedTranslationFileForExport = loadedTranslationFileForExport;
    }

    public String getTranslationKeyChecksum() {
        return translationKeyChecksum;
    }

    public void setTranslationKeyChecksum(String translationKeyChecksum) {
        this.translationKeyChecksum = translationKeyChecksum;
    }

    public ArrayList<String> getListOfExtractedKeys() {
        return listOfExtractedKeys;
    }

    public void setListOfExtractedKeys(ArrayList<String> listOfExtractedKeys) {
        this.listOfExtractedKeys = listOfExtractedKeys;
    }

    public int getTotalCountOfOpenedFiles() {
        return totalCountOfOpenedFiles;
    }

    public void setTotalCountOfOpenedFiles(int totalCountOfOpenedFiles) {
        this.totalCountOfOpenedFiles = totalCountOfOpenedFiles;
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


    public ArrayList<TranslationEntry> loadUnconsolidatedTranslationFiles(File[] filesToUse) {
        ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(filesToUse));

        ObjectMapper fileImportMapper = new ObjectMapper();
//        fileImportMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

        ArrayList<List<TranslationEntry>> listOfLoadedFiles = new ArrayList<List<TranslationEntry>>();
        int loadedCount = 0;
        InputStream inputStream;
        for (File f : filesToConvert) {


            try {
                String defaultEncoding = "UTF-8";
                inputStream = new FileInputStream(f);
                try {
                    BOMInputStream bOMInputStream = new BOMInputStream(inputStream);
                    ByteOrderMark bom = bOMInputStream.getBOM();
                    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();
                    InputStreamReader reader = new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName);

                    Path importedFilePath = Paths.get(f.getAbsolutePath());
                    Path importedFileName = importedFilePath.getFileName();
                    String convertedPath = importedFilePath.toString();
                    String targetJson = readFileAsString(convertedPath);
                    listOfLoadedFiles.add(TranslationEntryManager.getInstance().convertGameJsonToList(f, targetJson, fileImportMapper, reader));
                    loadedCount++;
                    totalCountOfOpenedFiles++;
                } finally {
                    inputStream.close();
                }
                Log.print(Log.OPENING + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
                Log.print(Log.ERROR_OPENING + f.getName());
            }

        }
        ArrayList<TranslationEntry> finalMergedList = TranslationEntryManager.getInstance().mergeLoadedEntryFilesInArrays(listOfLoadedFiles);
//        setTranslationKeyChecksum(getChecksumFromKeysInTranslationFile(finalMergedList));
        Log.print(loadedCount + Log.OPENING_SUCCESS);


        return finalMergedList;
    }


    public ArrayList<TranslationEntry> loadConsolidatedTranslationFile(File consolidatedJson) {
//        String generatedChecksum = null;
        ArrayList<TranslationEntry> result = new ArrayList<TranslationEntry>();
        ObjectMapper fileImportMapper = new ObjectMapper();
        TypeReference<ArrayList<TranslationEntry>> typeRefFinal = new TypeReference<ArrayList<TranslationEntry>>() {
        };

        String programPath = (System.getProperty("user.dir"));
//        String checksumTrackerName = "checksum_tracker.txt";

//        try {
//            FileUtils.touch(new File(checksumTrackerName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        String combinedPath = FilenameUtils.concat(programPath, checksumTrackerName);
//        Path externalChecksumsFilePath = Paths.get(combinedPath);
//        Path externalChecksumsFileName = externalChecksumsFilePath.getFileName();

        try {
            result = fileImportMapper.readValue(consolidatedJson, typeRefFinal);
//            for (TranslationEntry t : result) {
//                System.out.println(t.getEntryKey());
//            }
//            generatedChecksum = getChecksumFromKeysInTranslationFile(result);
//            setTranslationKeyChecksum(generatedChecksum);
//            setTranslationKeyChecksum(getChecksumFromKeysInTranslationFile(result));
//            System.out.println(getTranslationKeyChecksum());
//            System.out.println(generatedChecksum);
//            compareLoadedChecksumWithExternalFile(getTranslationKeyChecksum(), externalChecksumsFileName);
//            saveTestListOfKeys2(result);
//            saveConsolidatedTranslationFile2(result);
            Log.print(Log.OPENING + consolidatedJson.getName());


        } catch (IOException e) {
            e.printStackTrace();
            Log.print(Log.ERROR_OPENING + consolidatedJson.getName());
            //come back later and check if the above needs to be replaced with
            //another call GUIManager.getInstance().setupFileChooser();
            //as it seems to be buggy at the moment
        }


        return result;
    }


    public void saveConsolidatedTranslationFile(List<TranslationEntry> consolidatedArray, String filename) {
        String programPath = (System.getProperty("user.dir"));
        Log.print(Log.SAVING);
        var filenameToUse = filename + ".json";
        try {

            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(consolidatedArray, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
//            File savedConsolidatedFile = new File(programPath, "consolidated_translation_file.json");
            File savedConsolidatedFile = new File(programPath, filenameToUse);
            var absolutePath = savedConsolidatedFile.getAbsolutePath();
//            File storedChecksumFile = new File(programPath, "checksum_tracker.txt");
            for (var t : consolidatedArray) {
//                System.out.println(t.getEntryKey());

            }
//            saveTestListOfKeys1(consolidatedArray);
            mapConsolidatedTranslationFile(consolidatedArray, savedConsolidatedFile);
//            storeChecksumInFile(storedChecksumFile);

            Log.print(Log.SAVING_TO_SUCCESS + absolutePath);

        } catch (Exception e) {
            e.printStackTrace();
//            Log.print(Log.ERROR_SAVING_TO + programPath + "\\" + "consolidated_translation.file.json");
            Log.print(Log.ERROR_SAVING_TO + programPath + "\\" + filenameToUse);
        }

    }

    public void saveMergedTranslationFile(List<TranslationEntry> firstConfirmedArray, List<TranslationEntry> secondConfirmedArray) {
        String programPath = (System.getProperty("user.dir"));
        TranslationEntryManager.getInstance().mergeLanguageValues(firstConfirmedArray, secondConfirmedArray);

        Log.print(Log.MERGING);

        try {
//            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(fir, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
            File savedMergedFile = new File(programPath, "merged_translation_file.json");

            mapConsolidatedTranslationFile(firstConfirmedArray, savedMergedFile);

        } catch (Exception e) {
            e.printStackTrace();
            Log.print(Log.ERROR_MERGING);

        }

    }

//    public void saveConsolidatedTranslationFile2(List<TranslationEntry> consolidatedArray) {
//        String programPath = (System.getProperty("user.dir"));
//        try {
////            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(consolidatedArray, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
//            File savedConsolidatedFile = new File(programPath, "test_fi.json");
//            File storedChecksumFile = new File(programPath, "checksum_tracker.txt");
//            for (var t : consolidatedArray) {
////                System.out.println(t.getEntryKey());
//
//            }
//            saveTestListOfKeys1(consolidatedArray);
//            mapConsolidatedTranslationFile(consolidatedArray, savedConsolidatedFile);
//            storeChecksumInFile(storedChecksumFile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    ///zmień na zapisywanie w pliku .txt
    public void storeChecksumInFile(File fileToUse) {
//        ObjectMapper checksumObjectMapper = new ObjectMapper();
//        TranslationChecksum checksum = new TranslationChecksum(getTranslationKeyChecksum());
//        try {
//            checksumObjectMapper.writeValue(fileToUse, checksum);
////        TranslationChecksum checksum = new TranslationChecksum(getTranslationKeyChecksum());
//        } catch (IOException e) {
//           e.printStackTrace();
//        winnerWriter.println(");
//                winnerWriter.close();
//        }

        try (FileWriter fw = new FileWriter(fileToUse, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter winnerWriter = new PrintWriter(bw)) {

            String checksum = getTranslationKeyChecksum();
            winnerWriter.println(checksum);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void mapConsolidatedTranslationFile(List<TranslationEntry> consolidatedArrayToMap, File targetFile) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
//        TranslationChecksum checksum = new TranslationChecksum(getTranslationKeyChecksum());
//        TranslationFileContent content = new TranslationFileContent(checksum, consolidatedArrayToMap);
//        List<Object> listWithChecksum = new ArrayList<Object>();
//        TranslationChecksum checksum = new TranslationChecksum(getTranslationKeyChecksum());
//        listWithChecksum.add(checksum);
//        listWithChecksum.addAll(consolidatedArrayToMap);
        try {
//            jsonObjectMapper.writeValue(targetFile, listWithChecksum);
            jsonObjectMapper.writeValue(targetFile, consolidatedArrayToMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //  work in progress - messy, unfinished; to be cleaned up and split into separate methods
    public void exportUnconsolidatedTranslationFiles(ArrayList<TranslationEntry> listOfEntries) {
        LinkedHashMap<String, String> convertedExportMap;
        ObjectMapper exportMapper = new ObjectMapper();

        Log.print(Log.EXPORTING);

        try {
            Files.createDirectories(Paths.get("localization"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String programPath = (System.getProperty("user.dir"));
        String basePath = FilenameUtils.concat(programPath, "localization");
        Path relPath = Paths.get(basePath);
        String varPath = null;
        String filenameToUse;
        String langPath;
        String outputKey;
        String outputValue;

        LinkedHashMap<String, String> mapOfLanguagesToBeUsed = listOfEntries.get(0).getLanguages();
        List<String> languageList = new ArrayList<>(mapOfLanguagesToBeUsed.keySet());


        try {
            Map<String, List<TranslationEntry>> translationEntriesPerFilename =
                    listOfEntries.stream().collect(Collectors.groupingBy(TranslationEntry::getFilename));
            for (Map.Entry<String, List<TranslationEntry>> z : translationEntriesPerFilename.entrySet()) {
                filenameToUse = z.getKey();
                for (String s : languageList) {
//                    varPath = FilenameUtils.concat(basePath, s);
//                    Files.createDirectories(Paths.get(varPath));
                    LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
                    for (TranslationEntry t : z.getValue()) {
                        tempMap.put(t.getEntryKey(), t.getLanguages().get(s));
                        varPath = FilenameUtils.concat(basePath, t.getOriginalFilepath());
                        Files.createDirectories(Paths.get(varPath));


                    }
                    saveUnconsolidatedFileVariant(tempMap, exportMapper, varPath, filenameToUse);

                }
            }
            Log.print(Log.EXPORTING_SUCCESS_TO + basePath);

        } catch (Exception e) {
            e.printStackTrace();
            Log.print(Log.ERROR_EXPORTING_TO + basePath);
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
            var completePath = savedTranslationSettingsFile.getAbsolutePath();
            translationSettingsMapper.writeValue(savedTranslationSettingsFile, settingsToUse);
            Log.print(Log.SETTINGS_CREATED + "(" + completePath + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TranslationSettings loadTranslationSettings(File settingsToImport) {
        TranslationSettings settings = null;
        try {
            ObjectMapper settingsImportMapper = new ObjectMapper();
            settings = settingsImportMapper.readValue(settingsToImport, TranslationSettings.class);
            Log.print(Log.SETTINGS_IMPORTED + settingsToImport.getAbsolutePath());
        } catch (IOException e) {
            GUIManager.getInstance().setupSettingsChooser();
        }
        return settings;
    }


    public String readFileAsString(String file) {

        byte[] bytes = null;

        try {

            bytes = Files.readAllBytes(Paths.get(file));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bytes);
    }


    public String getChecksumFromKeysInTranslationFile(List<TranslationEntry> list) {
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<TranslationEntry> listCopy = new ArrayList<TranslationEntry>(list);
//        Collections.sort(list);
//        !!!WAZNE: SORTOWANIE ZOSTALO WYKOMENTOWANE, BO POWODUJE LOSOWA ZMIANE CHECKSUMY!!!


        for (TranslationEntry t : listCopy) {
            String extractedKey = t.getEntryKey();
            keys.add(extractedKey);
//            System.out.println(extractedKey);
        }


        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (ObjectOutputStream obj = new ObjectOutputStream(output)) {
//            obj.writeObject(keys);
            obj.writeUnshared(keys);

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = output.toByteArray();


        String keysChecksum = DigestUtils.md5Hex(bytes);

        return keysChecksum;
    }

    public void compareLoadedChecksumWithExternalFile(String checksumToCompare, Path filenameToUse) {
        String programPath = (System.getProperty("user.dir"));
//        Path filePath = Paths.get(fileToBeComparedTo.getAbsolutePath());
//        Path filename = filePath.getFileName();
        ArrayList<String> externalChecksumList = loadChecksumsFromTrackerFile(filenameToUse);


        if (!externalChecksumList.isEmpty()) {
            if (externalChecksumList.contains(checksumToCompare)) {
                System.out.println("ZNALEZIONO TEN SAM CHECKSUM " + checksumToCompare);

            } else {
                System.out.println("NIE ZNALEZIONO CHECKSUMY");
                JOptionPane.showMessageDialog(null, "No matching checksums detected! Please make sure that translation keys are not modified between the operations.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            System.out.println("NIE ZNALEZIONO CHECKSUMY");
            JOptionPane.showMessageDialog(null, "No matching checksums detected! Please make sure that translation keys are not modified between the operations.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public ArrayList<String> loadChecksumsFromTrackerFile(Path filenameToUse) {
        ArrayList<String> listOfChecksums = new ArrayList<String>();
        try {
            BufferedReader importReader = new BufferedReader(new FileReader(filenameToUse.toString()));
            listOfChecksums = new ArrayList<>();
            String checksumLine = importReader.readLine();
            while (checksumLine != null) {
                listOfChecksums.add(checksumLine);
                checksumLine = importReader.readLine();
            }
            importReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfChecksums;
    }

    /// worek wszystkich checksum + sprawdzanie najnowszej + ewentualnie skąd podchodzą
    /// w razie czego zapytaj ponownie


    public void saveTestListOfKeys1(List<TranslationEntry> keyArray) {
        String programPath = (System.getProperty("user.dir"));
        File savedTestFile1 = new File(programPath, "test_file1.txt");
        try (FileWriter fw = new FileWriter(savedTestFile1, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter keyWriter = new PrintWriter(bw)) {
//            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(consolidatedArray, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
//            File storedChecksumFile = new File(programPath, "checksum_tracker.txt");
            for (var t : keyArray) {


                String lineToWrite = t.getEntryKey();
                keyWriter.println(lineToWrite);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveTestListOfKeys2(List<TranslationEntry> keyArray) {
        String programPath = (System.getProperty("user.dir"));
        File savedTestFile2 = new File(programPath, "test_file2.txt");
        try (FileWriter fw = new FileWriter(savedTestFile2, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter keyWriter = new PrintWriter(bw)) {
//            TranslationEntryManager.getInstance().addLanguagesToLoadedEntries(consolidatedArray, TranslationSettingsManager.getInstance().getCurrentTranslationSettings());
//            File storedChecksumFile = new File(programPath, "checksum_tracker.txt");
            for (var t : keyArray) {


                String lineToWrite = t.getEntryKey();
                keyWriter.println(lineToWrite);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int countAllFilesLoaded(List<List<TranslationEntry>> listWithNestedFiles) {
        int totalCount = 0;
        for (var e : listWithNestedFiles) {
            totalCount++;
        }
        return totalCount;
    }


    public static void purgeAllLoadedFiles() {
        IOManager.getInstance().setListOfLoadedFilesAsTranslationEntries(new ArrayList<>());
        IOManager.getInstance().setExpandableListOfLoadedFiles(new ArrayList<>());
        IOManager.getInstance().setSetOfUniqueLanguages(new LinkedHashSet<>());
        IOManager.getInstance().setLoadedTranslationFileForExport(new ArrayList<>());
        IOManager.getInstance().setListOfLoadedFilesAsTranslationEntriesForMerge1(new ArrayList<>());
        IOManager.getInstance().setListOfLoadedFilesAsTranslationEntriesForMerge2(new ArrayList<>());
        //
        IOManager.getInstance().setExpandableListOfLoadedFilesForMerge1(new ArrayList<>());
        IOManager.getInstance().setExpandableListOfLoadedFilesForMerge2(new ArrayList<>());


    }


    public String inputSaveFilename() {
        String filename = (String) JOptionPane.showInputDialog(null, "Save under what name?", "Filename required", JOptionPane.PLAIN_MESSAGE, null, null, "consolidated_translation_file");
        if (filename != null) {
            if (!filename.isEmpty()) {
                return filename;

            } else {
                return inputSaveFilename();
            }
        } else {
            return null;
        }

    }

}


