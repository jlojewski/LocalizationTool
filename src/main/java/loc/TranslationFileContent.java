package loc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class TranslationFileContent {

    private TranslationChecksum checksum;
    private List<TranslationEntry> contentList;

    public TranslationChecksum getChecksum() {
        return checksum;
    }

    public void setChecksum(TranslationChecksum checksum) {
        this.checksum = checksum;
    }

    public List<TranslationEntry> getContentList() {
        return contentList;
    }

    public void setContentList(List<TranslationEntry> contentList) {
        this.contentList = contentList;
    }

    @JsonCreator
    public TranslationFileContent(@JsonProperty("checksum") TranslationChecksum checksum, @JsonProperty("contentList") List<TranslationEntry> contentList) {
        this.checksum = checksum;
        this.contentList = contentList;
    }



}
