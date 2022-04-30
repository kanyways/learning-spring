package me.kany.project.learning.spring.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;

import java.io.IOException;

/**
 * @author Jason.Wang
 */
public class JacksonStringTrimJsonSerializer extends JsonSerializer<String> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(StringUtils.trimSpaces(value, SpacesType.EXCEPT_LINE_SPACES));
    }
}
