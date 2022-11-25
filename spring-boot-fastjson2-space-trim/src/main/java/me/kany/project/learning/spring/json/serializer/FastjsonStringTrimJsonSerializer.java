package me.kany.project.learning.spring.json.serializer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;

import java.lang.reflect.Type;

/**
 * @author Jason.Wang
 */
public class FastjsonStringTrimJsonSerializer implements ObjectWriter<String> {
    /**
     * @param jsonWriter
     * @param object
     * @param fieldName
     * @param fieldType
     * @param features
     */
    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if (null == object) {
            jsonWriter.writeNull();
            return;
        }
        String strVal = object.toString();
        jsonWriter.writeString(StringUtils.trimSpaces(strVal, SpacesType.EXCEPT_LINE_SPACES));

    }
}
