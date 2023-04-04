package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

import java.util.LinkedHashMap;

public class TranslationEntry implements Comparable<TranslationEntry> {


    public UUID entryID;
    public String entryKey;
    @JsonIgnore
    public String entryValue;
    public String filename;
    public LinkedHashMap<String, String> languages;
    @JsonIgnore
    private Long keyChecksum;



    public UUID getEntryID() {
        return entryID;
    }

    public void setEntryID(UUID entryID) {
        this.entryID = entryID;
    }

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public LinkedHashMap<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(LinkedHashMap<String, String> languages) {
        this.languages = languages;
    }

    @JsonIgnore
    public Long getKeyChecksum() {
        return keyChecksum;
    }

    public void setKeyChecksum(Long keyChecksum) {
        this.keyChecksum = keyChecksum;
    }

    @JsonCreator
    public TranslationEntry(@JsonProperty("entryID") UUID entryID, @JsonProperty("entryKey") String entryKey, @JsonProperty("entryValue") String entryValue, @JsonProperty("filename") String filename, @JsonProperty("languages") LinkedHashMap<String, String> languages) {
        this.entryID = entryID;
        this.entryKey = entryKey;
        this.entryValue = entryValue;
        this.filename = filename;
        this.languages = languages;
    }

    @Override
    public int compareTo(TranslationEntry o) {

        return this.entryID.compareTo(o.getEntryID());
    }

/// UWAGA OD GRZEGORZA: ZAMIAST PISAC CUSTOMOWEGO DESERIALIZERA, CO JEST BARDZO TRUDNE I NIE NA TERAZ, ŻEBY OBSLUZYC DUPLIKATY PRZERÓB TRANSLATION ENTRY (?) ZGODNIE Z https://stackoverflow.com/questions/62353185/convert-json-with-duplicated-keys-with-jackson
}
