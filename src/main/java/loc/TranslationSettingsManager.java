package loc;

public class TranslationSettingsManager {

    private static TranslationSettingsManager TranslationSettingsManagerInstance;

    private TranslationSettingsManager() {

    }

    public static TranslationSettingsManager getInstance() {
        if (TranslationSettingsManagerInstance == null) {
            TranslationSettingsManagerInstance = new TranslationSettingsManager();
        }

        return TranslationSettingsManagerInstance;

    }

    public String declareNewLanguage() {
        String newLanguage = GUIManager.getInstance().openLanguageDialogInput();

        return newLanguage;
    }



}
