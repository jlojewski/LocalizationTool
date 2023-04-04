package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class TranslationChecksum {

    private Long keyChecksum;

    public Long getKeyChecksum() {
        return keyChecksum;
    }

    public void setKeyChecksum(Long keyChecksum) {
        this.keyChecksum = keyChecksum;
    }

    @JsonCreator
    public TranslationChecksum(@JsonProperty("keyChecksum") Long keyChecksum) {
        this.keyChecksum = keyChecksum;

    }

}
