package loc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

        public class TranslationEntryManager {

            private static loc.TranslationEntryManager TranslationEntryManagerInstance;

            private TranslationEntryManager() {

            }

            public static TranslationEntryManager getInstance() {
                if (TranslationEntryManagerInstance == null) {
            TranslationEntryManagerInstance = new TranslationEntryManager();
        }

        return TranslationEntryManagerInstance;

    }

// original list conversion method back when attempting to first write a custom deserializer; uncomment
// if the current implementation doesn't work
//    public ArrayList<TranslationEntry> convertJsonToList(File file, ObjectMapper mapper) {
//        ArrayList<TranslationEntry> result = new ArrayList<TranslationEntry>();
//        TypeReference<ArrayList<TranslationEntry>> typeRef = new TypeReference<ArrayList<TranslationEntry>>(){};
//        try {
//            result = mapper.readValue(file, typeRef);
//        } catch (IOException e) {
//            e.printStackTrace();
//            //come back later and check if the above needs to be replaced with
//            //another call GUIManager.getInstance().setupFileChooser();
//            //as it seems to be buggy at the moment
//        }
//        Path importedFilePath = Paths.get(file.getAbsolutePath());
//        Path importedFileName = importedFilePath.getFileName();
//        String filename = importedFileName.toString();
//        for (TranslationEntry t : result)
//            {
//
//                t.setFilename(filename);
//
//
//        }
//        return result;
//    }


    public ArrayList<TranslationEntry> convertGameJsonToList(File file, String json, ObjectMapper mapper, InputStreamReader reader) {
        ArrayList<TranslationEntry> result = new ArrayList<TranslationEntry>();
        TypeReference<ArrayList<TranslationEntry>> typeRef = new TypeReference<ArrayList<TranslationEntry>>() {
        };

//        Path importedFilePath = Paths.get(file.getAbsolutePath());
        Path importedFilePath = Paths.get(file.getAbsolutePath());
        Path importedFileName = importedFilePath.getFileName();
        String filename = importedFileName.toString();
        String originalFilepath = FilenameUtils.getFullPath(importedFilePath.toString());
        TranslationEntry tempTranslationEntry;

        JsonNode node;

        try {
            node = mapper.readValue(reader, JsonNode.class);
            Iterator<Map.Entry<String, JsonNode>> userEntries = node.fields();
            while (userEntries.hasNext()) {
                Map.Entry<String, JsonNode> userEntry = userEntries.next();
                tempTranslationEntry = new TranslationEntry(null, null, null, null, null, null);
                tempTranslationEntry.entryID = UUID.randomUUID();
                tempTranslationEntry.entryKey = userEntry.getKey();
                //.asText is currently experimental - need to check after rewrite if it works and all and whether
                //it throws nullpointerexceptions
                tempTranslationEntry.entryValue = userEntry.getValue().asText();
                tempTranslationEntry.filename = filename;
                tempTranslationEntry.originalFilepath = originalFilepath;
                tempTranslationEntry.languages = new LinkedHashMap<>();
                tempTranslationEntry.languages.put("DEFAULT", tempTranslationEntry.getEntryValue());
                result.add(tempTranslationEntry);

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //come back later and check if the above needs to be replaced with
            //another call GUIManager.getInstance().setupFileChooser();
            //as it seems to be buggy at the moment
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

//    public ArrayList<TranslationEntry> convertConsolidatedJsonToList(File file, ObjectMapper mapper) {
//        ArrayList<TranslationEntry> result = new ArrayList<TranslationEntry>();
//        TypeReference<ArrayList<TranslationEntry>> typeRef = new TypeReference<ArrayList<TranslationEntry>>(){};
//
//        try {
//            result = mapper.readValue(file, typeRef);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            //come back later and check if the above needs to be replaced with
//            //another call GUIManager.getInstance().setupFileChooser();
//            //as it seems to be buggy at the moment
//        }
//
//
//        return result;
//    }

//    public LinkedHashMap<String, String> convertJsonToMap(File file, ObjectMapper mapper) {
//        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
//        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>(){};
//        try {
//            result = mapper.readValue(file, typeRef);
//        } catch (IOException e) {
//            e.printStackTrace();
//            //come back later and check if the above needs to be replaced with
//            //another call GUIManager.getInstance().setupFileChooser();
//            //as it seems to be buggy at the moment
//        }
//        return result;
//
//
//    }

//    public ArrayList<String> convertJsonToList(File file, ObjectMapper mapper) {
//        ArrayList<String> result = new LinkedHashMa<String, String>();
//        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>(){};
//        try {
//            result = mapper.readValue(file, typeRef);
//        } catch (IOException e) {
//            e.printStackTrace();
//            //come back later and check if the above needs to be replaced with
//            //another call GUIManager.getInstance().setupFileChooser();
//            //as it seems to be buggy at the moment
//        }
//        return result;
//
//
//    }


//    public LinkedHashMap<String, String> mergeLoadedEntryFiles(ArrayList<LinkedHashMap<String,String>> listOfEntryFiles) {
//        LinkedHashMap<String,String> mergedMap = new LinkedHashMap<String,String>();
//        for (var map:listOfEntryFiles) {
//          map.forEach((k,v)->mergedMap.put(k,v));
//
//        }
//        return mergedMap;
//    }

//    public ArrayList<TranslationEntry> mergeLoadedEntryFilesInArrays(ArrayList<List<TranslationEntry>> listOfEntryFiles) {
//        ArrayList<TranslationEntry> mergedList = new ArrayList<>();
//        for (var list:listOfEntryFiles) {
//            mergedList.addAll(list);
//
//        }
//        return mergedList;
//    }

    public ArrayList<TranslationEntry> mergeLoadedEntryFilesInArrays(ArrayList<List<TranslationEntry>> listOfEntryFiles) {
        ArrayList<TranslationEntry> mergedList = new ArrayList<>();
        for (var list : listOfEntryFiles) {
            mergedList.addAll(list);

        }
        return mergedList;
    }


    public LinkedHashMap<String, String> replaceAllMappingsWithInitialInputValue(String valueToUse, LinkedHashMap<String, String> currentMap) {
        currentMap.replaceAll((k, v) -> v = valueToUse);
        return currentMap;
    }

    //this is the currently used/worked on/developed method to convert the initially loaded maps into array(s) with objects
    public List<TranslationEntry> createTranslationEntriesFromMap(LinkedHashMap<String, String> inputMap, Path filename, Path originalFilepath) {
        List<TranslationEntry> arrayListOfEntriesWithValuesTakenFromMap = inputMap.entrySet().stream().map(entry -> {
            TranslationEntry t = new TranslationEntry(null, null, null, null, null, null);
            t.setEntryID(UUID.randomUUID());
            t.languages = new LinkedHashMap<>();
            t.languages.put("DEFAULT", entry.getValue());
            t.setFilename(filename.toString());
            t.setOriginalFilepath(t.originalFilepath);
            t.setEntryKey(entry.getKey());
            t.setEntryValue(entry.getValue());
            return t;
        }).collect(Collectors.toList());
        return arrayListOfEntriesWithValuesTakenFromMap;
    }


// UNFINISHED; additionally, the current version of this method presumes modifying an existing list; uncomment and rewrite this if it turns out that the list
// should remain unmodified and the end result of adding languages should be added to a copied list instead
//    public List<TranslationEntry> addLanguagesToLoadedEntries(List<TranslationEntry> originalList) {
//        List<TranslationEntry> modifiedList = new List<TranslationEntry>;
//        TranslationEntry tempEntry = null;
//        String tempValue = null;
//        for(int i = 0; i < originalList.size(); i++) {
//            tempEntry = originalList.get(i);
//            tempValue = tempEntry.getLanguages().get("DEFAULT");
//            for(String lang : TranslationSettingsManager.getInstance().getCurrentTranslationSettings().getUserDefinedLanguages()) {
//                tempEntry.languages.put(lang, tempValue);
//            }
//        } return modifiedList;
//    }

    public void addLanguagesToLoadedEntries(List<TranslationEntry> originalList, TranslationSettings languageSettings) {
        TranslationEntry tempEntry = null;
        String tempValue = null;
        for (int i = 0; i < originalList.size(); i++) {
            tempEntry = originalList.get(i);
            tempValue = tempEntry.getLanguages().get("DEFAULT");
            for (String lang : TranslationSettingsManager.getInstance().getCurrentTranslationSettings().getUserDefinedLanguages()) {
                originalList.get(i).languages.put(lang, tempValue);
            }
        }
    }

            public void addFilepathToLoadedEntries(List<TranslationEntry> originalList, Path rootPathForRelativize) {
                TranslationEntry tempEntry = null;
                Path tempPath = null;
                Path relativizedPath = null;
                for (int i = 0; i < originalList.size(); i++) {
                    tempEntry = originalList.get(i);
                    tempPath = Paths.get(tempEntry.getOriginalFilepath());
                    relativizedPath = rootPathForRelativize.relativize(tempPath);
                    originalList.get(i).setOriginalFilepath(relativizedPath.toString());

                }
            }

//    public void checkIfEntryKeysAreUnique(List<TranslationEntry> listToCheck) {
//        for (int i = 0; i < listToCheck.size(); i++) {
//            for (int j = i + 1; j < listToCheck.size(); j++) {
//                if (listToCheck.get(i).getEntryKey() == listToCheck.get(j).getEntryKey()) {
////                    String tempNameString = listToCheck.get(i).getEntryKey();
//                    while (listToCheck.)
//
//
//
//                }
//            }
//        }
//    }

    public ArrayList<String> extractKeys(List<TranslationEntry> listOfEntries) {
        String tempKey = null;
        ArrayList<String> listOfKeys = new ArrayList<>();
        for (TranslationEntry e : listOfEntries) {
            tempKey = e.getEntryKey();
            listOfKeys.add(tempKey);
        }
        return listOfKeys;
    }


//    public void compareKeys(ArrayList<String> listToCompareFrom, ArrayList<String> listToCompareTo) {
//        String tempKeyToCompareTo = null;
//        String tempStreamResult = null;
//        ArrayList<String> listOfMissingKeys = new ArrayList<String>();
//        for (int i = 0; i < listToCompareFrom.size() - 1; i++) {
//            tempKeyToCompareTo = listToCompareFrom.get(i);
//            String finalTempKeyToCompareTo = tempKeyToCompareTo;
//            tempStreamResult = listToCompareTo.stream()
//                    .filter(e -> e.equals(finalTempKeyToCompareTo))
//                    .findFirst()
//                    .orElse(null);
//            if (tempStreamResult == null) {
//                String keyToReport = tempKeyToCompareTo;
//                listOfMissingKeys.add(keyToReport);
//            }
//
//        }
//        for (int i = 0; i < listToCompareTo.size() - 1; i++) {
//            tempKeyToCompareTo = listToCompareTo.get(i);
//            String finalTempKeyToCompareTo = tempKeyToCompareTo;
//            tempStreamResult = listToCompareFrom.stream()
//                    .filter(e -> e.equals(finalTempKeyToCompareTo))
//                    .findFirst()
//                    .orElse(null);
//            if (tempStreamResult == null) {
//                String keyToReport = tempKeyToCompareTo;
//                listOfMissingKeys.add(keyToReport);
//            }
//        }
//        if (!listOfMissingKeys.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for (String s : listOfMissingKeys) {
//                sb.append(s);
//                sb.append("\n");
//            }
//            JOptionPane.showMessageDialog(null, "WARNING! Found missing keys between the file batches:" + "\n" + sb, "Warning", JOptionPane.WARNING_MESSAGE);
//
//
//        }
//    }

    public void compareKeys(List<TranslationEntry> listToCompareFrom, List<TranslationEntry> listToCompareTo) {
        Log.print(Log.COMPARING_KEYS);
        TranslationEntry tempEntryToCompareTo = null;
        TranslationEntry tempStreamResult = null;
        ArrayList<TranslationEntry> listOfEntriesWithMissingKeys = new ArrayList<TranslationEntry>();
//        ArrayList<TranslationEntry> listOfEntriesWithMissingKeysInGameFiles = new ArrayList<TranslationEntry>();
//        ArrayList<TranslationEntry> listOfEntriesWithMissingKeysInTranslationFile = new ArrayList<TranslationEntry>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listToCompareFrom.size() - 1; i++) {
            tempEntryToCompareTo = listToCompareFrom.get(i);
            var finalTempEntryToCompareTo = tempEntryToCompareTo;
            tempStreamResult = listToCompareTo.stream()
                    .filter(e -> e.getEntryKey().equals(finalTempEntryToCompareTo.getEntryKey()))
                    .findFirst()
                    .orElse(null);
            if (tempStreamResult == null) {
//                String keyToReport = tempKeyToCompareTo;
                listOfEntriesWithMissingKeys.add(tempEntryToCompareTo);
                sb.append(tempEntryToCompareTo.getEntryKey());
                sb.append(" (in game files: " + tempEntryToCompareTo.getFilename() + ")");
                sb.append("\n");

            }

        }
        for (int i = 0; i < listToCompareTo.size() - 1; i++) {
            tempEntryToCompareTo = listToCompareTo.get(i);
            var finalTempEntryToCompareTo = tempEntryToCompareTo;
            tempStreamResult = listToCompareFrom.stream()
                    .filter(e -> e.getEntryKey().equals(finalTempEntryToCompareTo.getEntryKey()))
                    .findFirst()
                    .orElse(null);
            if (tempStreamResult == null) {
//                String keyToReport = tempKeyToCompareTo;
                listOfEntriesWithMissingKeys.add(tempEntryToCompareTo);
                sb.append(tempEntryToCompareTo.getEntryKey());
                sb.append(" (in translation file: " + tempEntryToCompareTo.getFilename() + ")");
                sb.append("\n");
            }
        }

//        List<TranslationEntry> listOfEntriesWithMissingKeys = Stream.of(listOfEntriesWithMissingKeysInGameFiles, listOfEntriesWithMissingKeysInTranslationFile)
//                .flatMap(e -> e.stream())
//                .collect(Collectors.toList());


        if (!listOfEntriesWithMissingKeys.isEmpty()) {
//         StringBuilder sb = new StringBuilder();
//            for (TranslationEntry t : listOfEntriesWithMissingKeys) {
//                sb.append(t.getEntryKey());
//                sb.append(" (in: " + t.getFilename() + ")");
//                sb.append("\n");
//            }

//            JOptionPane.showMessageDialog(null, "WARNING! Found missing keys between the file batches:" + "\n" + sb, "Warning", JOptionPane.WARNING_MESSAGE);
            Log.print(listOfEntriesWithMissingKeys.size() + Log.WARNING_KEYS);
            JTextArea textArea = new JTextArea("WARNING! Found missing keys between loaded file batches:" + "\n" + sb);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(false);
            textArea.setWrapStyleWord(true);
            scrollPane.setPreferredSize(new Dimension(750, 750));
            JOptionPane.showMessageDialog(null, scrollPane, "Warning",
                    JOptionPane.WARNING_MESSAGE);

        } else {
            Log.print(Log.COMPARING_KEYS_SUCCESS);
        }
    }


//    public void mergeLanguageValues(List<TranslationEntry> mergeBase, List<TranslationEntry> mergeMergees) {
//        TranslationEntry tempBaseEntry= null;
//        TranslationEntry tempStreamResult = null;
//        LinkedHashMap<String, String> tempContents = null;
//
//        for (int i = 0; i < mergeBase.size() - 1; i++) {
//            tempBaseEntry = mergeBase.get(i);
//            tempContents = tempBaseEntry.getLanguages();
//            var finalTempEntryToCompareTo = tempBaseEntry;
//
//            for (TranslationEntry t : mergeMergees) {
//                for (var m :
//                     ) {
//
//                }
//            }
//
//
//        }
//    }

//    public void mergeLanguageValues(List<TranslationEntry> mergeBase, List<TranslationEntry> mergeMergees) {
//        TranslationEntry tempBaseEntry = null;
//        TranslationEntry tempStreamResult = null;
//        LinkedHashMap<String, String> tempContents = null;
//
//        for (int i = 0; i < mergeBase.size(); i++) {
//            tempBaseEntry = mergeBase.get(i);
//            tempContents = tempBaseEntry.getLanguages();
//            tempContents.remove(0);
//            var finalTempEntryToCompareTo = tempBaseEntry;
//
//            for (TranslationEntry t : mergeMergees) {
//                if (t.getEntryKey().equals(tempBaseEntry.getEntryKey()) && t.getFilename().equals(tempBaseEntry.getEntryKey())) {
//                    for (Map.Entry<String, String> langEntry : t.getLanguages().entrySet()) {
//                        if (!tempContents.containsKey(langEntry.getKey())) {
//                            mergeBase.get(i).getLanguages().put(langEntry.getKey(), langEntry.getValue());
//                        }
//                    }
//                }
//            }
//
//
//        }
//    }

    public void mergeLanguageValues(List<TranslationEntry> mergeBase, List<TranslationEntry> mergeMergees) {
        TranslationEntry tempBaseEntry = null;
        TranslationEntry tempStreamResult = null;
        LinkedHashMap<String, String> tempContents = null;

        for (int i = 0; i <= mergeBase.size() -1; i++) {
            tempBaseEntry = mergeBase.get(i);
            tempContents = tempBaseEntry.getLanguages();
//            tempContents.remove(0);
            var finalTempEntryToCompareTo = tempBaseEntry;

            for (TranslationEntry t : mergeMergees) {
                if (t.getEntryKey().equals(tempBaseEntry.getEntryKey()) && t.getFilename().equals(tempBaseEntry.getFilename())) {
                    for (Map.Entry<String, String> langEntry : t.getLanguages().entrySet()) {
                        if (!tempContents.containsKey(langEntry.getKey())) {
                            mergeBase.get(i).getLanguages().put(langEntry.getKey(), langEntry.getValue());
                        }
                    }
                }
            }


        }
    }

    public ArrayList<TranslationEntry> trimFilepaths(Path userSpecifiedPath, ArrayList<TranslationEntry> listToWorkOn) {
        for (TranslationEntry t : listToWorkOn) {
            var ogPath = Paths.get(t.getOriginalFilepath());
            var trimmedPath = (ogPath.relativize(userSpecifiedPath)).toString();
            t.setOriginalFilepath(trimmedPath);
        } return listToWorkOn;
    }


}
