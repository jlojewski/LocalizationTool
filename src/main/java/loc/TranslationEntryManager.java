package loc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
            GUIManager.getInstance().setupFileChooser();
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



}
