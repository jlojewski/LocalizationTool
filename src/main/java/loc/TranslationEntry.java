package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class TranslationEntry {

    String entryKey;
    String entryValue;
//    LinkedHashMap<String, String> entryValue;


    public String getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

//    public LinkedHashMap<String, String> getEntryValue() {
//        return entryValue;
//    }

//    public void setEntryValue(LinkedHashMap<String, String> entryValue) {
//        this.entryValue = entryValue;
//    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

//    @JsonCreator
//    public TranslationEntry(@JsonProperty("entryKey") String entryKey, @JsonProperty("entryValue") LinkedHashMap<String, String> entryValue) {
//        this.entryKey = entryKey;
//        this.entryValue = entryValue;
//    }

    @JsonCreator
    public TranslationEntry(@JsonProperty("entryKey") String entryKey, @JsonProperty("entryValue") String entryValue) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }


}
