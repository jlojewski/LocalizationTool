package loc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TranslationEntryManager {

    private static TranslationEntryManager TranslationEntryManagerInstance;

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
        TypeReference<ArrayList<TranslationEntry>> typeRef = new TypeReference<ArrayList<TranslationEntry>>(){};

        Path importedFilePath = Paths.get(file.getAbsolutePath());
        Path importedFileName = importedFilePath.getFileName();
        String filename = importedFileName.toString();
        TranslationEntry tempTranslationEntry;

        JsonNode node;

        try {
            node = mapper.readValue(reader, JsonNode.class);
            Iterator<Map.Entry<String, JsonNode>> userEntries = node.fields();
            while(userEntries.hasNext()) {
                Map.Entry<String, JsonNode> userEntry = userEntries.next();
                tempTranslationEntry = new TranslationEntry(null, null, null, null, null);
                tempTranslationEntry.entryID = UUID.randomUUID();
                tempTranslationEntry.entryKey = userEntry.getKey();
                //.asText is currently experimental - need to check after rewrite if it works and all and whether
                //it throws nullpointerexceptions
                tempTranslationEntry.entryValue = userEntry.getValue().asText();
                tempTranslationEntry.filename = filename;
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

    public ArrayList<TranslationEntry> mergeLoadedEntryFilesInArrays(ArrayList<List<TranslationEntry>> listOfEntryFiles) {
        ArrayList<TranslationEntry> mergedList = new ArrayList<>();
        for (var list:listOfEntryFiles) {
            mergedList.addAll(list);

        }
        return mergedList;
    }


    public LinkedHashMap<String, String> replaceAllMappingsWithInitialInputValue(String valueToUse, LinkedHashMap<String, String> currentMap) {
        currentMap.replaceAll((k, v) -> v = valueToUse);
        return currentMap;
    }

//this is the currently used/worked on/developed method to convert the initially loaded maps into array(s) with objects
    public List<TranslationEntry> createTranslationEntriesFromMap(LinkedHashMap<String, String> inputMap, Path filename) {
        List<TranslationEntry> arrayListOfEntriesWithValuesTakenFromMap = inputMap.entrySet().stream().map(entry -> {
            TranslationEntry t = new TranslationEntry(null, null, null, null, null);
            t.setEntryID(UUID.randomUUID());
            t.languages = new LinkedHashMap<>();
            t.languages.put("DEFAULT", entry.getValue());
            t.setFilename(filename.toString());
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
        for(int i = 0; i < originalList.size(); i++) {
            tempEntry = originalList.get(i);
            tempValue = tempEntry.getLanguages().get("DEFAULT");
            for(String lang : TranslationSettingsManager.getInstance().getCurrentTranslationSettings().getUserDefinedLanguages()) {
                originalList.get(i).languages.put(lang, tempValue);
            }
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



}
