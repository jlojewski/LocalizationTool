package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TranslationEntry {

    public String entryKey;
    public String entryValue;

    @JsonCreator
    public TranslationEntry(@JsonProperty("key") String entryKey, @JsonProperty("value") String entryValue) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }

}
