package loc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.LinkedHashSet;

public class TranslationSettingsManager {
    private TranslationSettings currentTranslationSettings;

    private static TranslationSettingsManager TranslationSettingsManagerInstance;


    private TranslationSettingsManager() {

    }

    public static TranslationSettingsManager getInstance() {
        if (TranslationSettingsManagerInstance == null) {
            TranslationSettingsManagerInstance = new TranslationSettingsManager();
        }

        return TranslationSettingsManagerInstance;

    }

    public TranslationSettings getCurrentTranslationSettings() {
        return currentTranslationSettings;
    }

    public void setCurrentTranslationSettings(TranslationSettings currentTranslationSettings) {
        this.currentTranslationSettings = currentTranslationSettings;
    }



//    public void saveTranslationSettings(TranslationSettings settingsToUse) {
//        ObjectMapper translationSettingsMapper = new ObjectMapper();
//        String programPath = (System.getProperty("user.dir"));
//        try {
//            File savedTranslationSettingsFile = new File(programPath, "translation_settings.json");
//            translationSettingsMapper.writeValue(savedTranslationSettingsFile, settingsToUse);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}
