package me.kany.project.learning.spring.json.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;

import java.lang.reflect.Type;

/**
 * @author Jason.Wang
 */
public class FastjsonStringTrimJsonDeserialize implements ObjectDeserializer {

    /**
     * fastjson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JSON#parseObject(String, Type, Feature[])} method to create objects
     * for any non-trivial field of the returned object.
     *
     * @param parser    context DefaultJSONParser being deserialized
     * @param type      The type of the Object to deserialize to
     * @param fieldName parent object field name
     * @return a deserialized object of the specified type which is a subclass of {@code T}
     */
    @Override
    public String deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String strVal = parser.getLexer().stringVal();
        return StringUtils.trimSpaces(strVal, SpacesType.EXCEPT_LINE_SPACES);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
