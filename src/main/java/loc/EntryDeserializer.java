package loc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.util.UUID;

public class EntryDeserializer extends StdDeserializer<TranslationEntry> {

    public EntryDeserializer() {
        this(null);
    }

    public EntryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TranslationEntry deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String originalKey = node.get(0).asText();
        String value = node.get(1).asText();
//        String originalKey = node.textValue();
//        String value = node.textValue();

        var i = new TranslationEntry(UUID.randomUUID(),originalKey, value,null,null );

        return i;
    }

}
