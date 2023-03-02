package loc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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


    public LinkedHashMap<String, String> convertJsonToMap(File file, ObjectMapper mapper) {
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>(){};
        try {
            result = mapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            //come back later and check if the above needs to be replaced with
            //another call GUIManager.getInstance().setupFileChooser();
            //as it seems to be buggy at the moment
        }
        return result;


    }


    public LinkedHashMap<String, String> mergeLoadedEntryFiles(ArrayList<LinkedHashMap<String,String>> listOfEntryFiles) {
        LinkedHashMap<String,String> mergedMap = new LinkedHashMap<String,String>();
        for (var map:listOfEntryFiles) {
          map.forEach((k,v)->mergedMap.put(k,v));

        }
        return mergedMap;
    }

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

//currently used/worked on/developed method to convert the initially loaded maps into array(s) with objects
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



}
