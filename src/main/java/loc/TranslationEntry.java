package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class TranslationEntry {

    String entryKey;
    LinkedHashMap<String, String> entryValue;


    @JsonCreator
    public TranslationEntry(@JsonProperty("entryKey") String entryKey, @JsonProperty("entryValue") LinkedHashMap<String, String> entryValue) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }


}
