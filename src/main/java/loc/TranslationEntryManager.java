package loc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TranslationEntryManager {

    LinkedHashMap<String, String> languageMapTemplate;

    private static TranslationEntryManager TranslationEntryManagerInstance;

    private TranslationEntryManager() {
        languageMapTemplate = new LinkedHashMap<>();

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

        };
        return mergedMap;
    }

    public void declareNewLanguage() {
        String newLanguage = GUIManager.getInstance().openLanguageDialogInput();
        languageMapTemplate.put(newLanguage, null);

        }

    //currently unused implementation which mapped one type of map to another;
    // do not revisit unless dire circumstances occur
     public LinkedHashMap<String, LinkedHashMap<String, String>> convertToNewMapFormat(LinkedHashMap<String, String> startingMap, LinkedHashMap<String, String> languageMap) {
         String tempKey = null;
         String tempVal = null;
         String tempKeyForLanguage = null;
         LinkedHashMap<String, String> valueMap = languageMap;
         LinkedHashMap<String, LinkedHashMap<String, String>> convertedMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
         for (Map.Entry<String, String> entry : startingMap.entrySet()) {
             tempKey = entry.getKey();
             tempVal = entry.getValue();

             convertedMap.put(tempKey, replaceAllMappingsWithInitialInputValue(tempVal, valueMap));

         }
         return convertedMap;
     }




    public LinkedHashMap<String, String> replaceAllMappingsWithInitialInputValue(String valueToUse, LinkedHashMap<String, String> currentMap) {
        currentMap.replaceAll((k, v) -> v = valueToUse);
        return currentMap;
    }

//currently used/worked on/developed method to convert the initially loaded maps into array(s) with objects
    public List<TranslationEntry> createTranslationEntries(LinkedHashMap<String, String> inputMap) {
        List<TranslationEntry> arrayListOfEntriesWithValuesTakenFromMap = inputMap.entrySet().stream().map(entry -> {
            TranslationEntry t = new TranslationEntry(null, null);
            t.setEntryKey(entry.getKey());
            t.setEntryValue(entry.getValue());
            return t;
        }).collect(Collectors.toList());
        return arrayListOfEntriesWithValuesTakenFromMap;
    }



}
