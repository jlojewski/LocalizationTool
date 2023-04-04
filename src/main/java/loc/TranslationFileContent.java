package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class TranslationFileContent {

    private String checksumKey;
    private List<TranslationEntry> contentList;

    public String getChecksumKey() {
        return checksumKey;
    }

    public void setChecksumKey(String checksumKey) {
        this.checksumKey = checksumKey;
    }

    public List<TranslationEntry> getContentList() {
        return contentList;
    }

    public void setContentList(List<TranslationEntry> contentList) {
        this.contentList = contentList;
    }

    @JsonCreator
    public TranslationFileContent(@JsonProperty("checksumKey") String checksumKey, @JsonProperty("contentList") List<TranslationEntry> contentList) {
        this.checksumKey = checksumKey;
        this.contentList = contentList;
    }



}
