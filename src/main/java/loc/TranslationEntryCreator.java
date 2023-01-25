package loc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class TranslationEntryCreator {

        //temporary class for testing purposes - delete when the target input files finally materialize

        public static void main(String[] args) {


            TranslationEntry translationSample = new TranslationEntry("blebleblekey", "blebleblevalue");

            ObjectMapper jsonSettingsMapper = new ObjectMapper();
            String programPath = (System.getProperty("user.dir")) + "\\translation_sample.json";

            try {
                jsonSettingsMapper.writeValue(new FileWriter(programPath), translationSample);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
