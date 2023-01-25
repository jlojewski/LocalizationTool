package loc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class IOManager {

    private static IOManager IOManagerInstance;

    private IOManager() {


    }

    public static IOManager getInstance() {
        if (IOManagerInstance == null) {
            IOManagerInstance = new IOManager();
        }

        return IOManagerInstance;
    }



    public LinkedHashMap<String, String> loadTranslationFiles() {
            ArrayList<File> filesToConvert = new ArrayList(Arrays.asList(GUIManager.getInstance().setupFileChooser()));
            LinkedHashMap loadedFilesMap = new LinkedHashMap<String, String>();
            ObjectMapper fileImportMapper = new ObjectMapper();
            for (File f : filesToConvert) {
                try {
                    TranslationEntry tren = fileImportMapper.readValue(f, TranslationEntry.class);
                    loadedFilesMap.put(tren.entryKey, tren.entryValue);
                } catch (IOException e) {
                    GUIManager.getInstance().setupFileChooser();
                }
            }
            return loadedFilesMap;

        }






}
