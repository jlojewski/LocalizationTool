package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TranslationSettings {

    public LinkedHashSet<String> userDefinedLanguages;

    public LinkedHashSet<String> getUserDefinedLanguages() {
        return userDefinedLanguages;
    }

    public void setUserDefinedLanguages(LinkedHashSet<String> userDefinedLanguages) {
        this.userDefinedLanguages = userDefinedLanguages;
    }

    @JsonCreator
    public TranslationSettings(@JsonProperty("userDefinedLanguages") LinkedHashSet<String> userDefinedLanguages) {
        this.userDefinedLanguages = userDefinedLanguages;
    }


}
