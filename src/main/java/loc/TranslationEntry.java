package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TranslationEntry {

    public String entryKey;

    public String getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public String entryValue;

    @JsonCreator
    public TranslationEntry(@JsonProperty("key") String entryKey, @JsonProperty("value") String entryValue) {
        this.entryKey = entryKey;
        this.entryValue = entryValue;
    }

}
