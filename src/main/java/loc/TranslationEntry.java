package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class TranslationEntry {

    public String entryKey;
    @JsonIgnore
    public String entryValue;
    public String filename;
    public LinkedHashMap<String, String> languages;


    public String getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

    @JsonIgnore
    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public LinkedHashMap<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(LinkedHashMap<String, String> languages) {
        this.languages = languages;
    }

    @JsonCreator
    public TranslationEntry(@JsonProperty("entryKey") String entryKey, @JsonProperty("entryValue") String entryValue, @JsonProperty("filename") String filename, @JsonProperty("languages") LinkedHashMap<String, String> languages) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
        this.filename = filename;
        this.languages = languages;
    }


}
