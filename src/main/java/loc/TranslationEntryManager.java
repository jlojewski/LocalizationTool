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
            GUIManager.getInstance().setupFileChooser();
        }
        return result;


    }

//    public ArrayList<TranslationEntry> convertJson(File file, ObjectMapper mapper) {
//        ArrayList<> result = new ArrayList<TranslationEntry>();
//        try {
//            TranslationEntry trentry = mapper.readValue(file, TranslationEntry.class);
//            result.put(trentry.getEntryKey(), trentry.getEntryValue());
//        } catch (IOException e) {
//            GUIManager.getInstance().setupFileChooser();
//        }
//        return result;

//
//    }

//    public LinkedHashMap<String, String> mergeLoadedEntryFiles(ArrayList<LinkedHashMap> listOfEntryFiles) {
//        LinkedHashMap<String,String> mergedMap = new LinkedHashMap<String,String>();
//         listOfEntryFiles.forEach((entryFile)->Stream.concat(mergedMap.entrySet().stream(),entryFile.entrySet().stream()));
//         return mergedMap;
//    }

    public LinkedHashMap<String, String> mergeLoadedEntryFiles(ArrayList<LinkedHashMap<String,String>> listOfEntryFiles) {
        LinkedHashMap<String,String> mergedMap = new LinkedHashMap<String,String>();
        for (var map:listOfEntryFiles) {
          map.forEach((k,v)->mergedMap.put(k,v));

        };
        return mergedMap;
    }


}
