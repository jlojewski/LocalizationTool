package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class TranslationChecksum {

    private String keyChecksum;

    public String getKeyChecksum() {
        return keyChecksum;
    }

    public void setKeyChecksum(String keyChecksum) {
        this.keyChecksum = keyChecksum;
    }


}
