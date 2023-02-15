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

        };
        return mergedMap;
    }


    public LinkedHashMap<String, String> replaceAllMappingsWithInitialInputValue(String valueToUse, LinkedHashMap<String, String> currentMap) {
        currentMap.replaceAll((k, v) -> v = valueToUse);
        return currentMap;
    }

//currently used/worked on/developed method to convert the initially loaded maps into array(s) with objects
    public List<TranslationEntry> createTranslationEntries(LinkedHashMap<String, String> inputMap) {
        List<TranslationEntry> arrayListOfEntriesWithValuesTakenFromMap = inputMap.entrySet().stream().map(entry -> {
            TranslationEntry t = new TranslationEntry(null, null, null, null);
            t.languages = new LinkedHashMap<>();
            t.languages.put("DEFAULT", entry.getValue());
            Path  = get
            t.filename =
            t.setEntryKey(entry.getKey());
            t.setEntryValue(entry.getValue());
            return t;
        }).collect(Collectors.toList());
        return arrayListOfEntriesWithValuesTakenFromMap;
    }



}
