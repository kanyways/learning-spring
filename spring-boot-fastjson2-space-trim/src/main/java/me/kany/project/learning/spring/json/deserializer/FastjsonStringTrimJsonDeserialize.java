package me.kany.project.learning.spring.json.deserializer;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;

import java.lang.reflect.Type;

/**
 * @author Jason.Wang
 */
public class FastjsonStringTrimJsonDeserialize implements ObjectReader<String> {
    /**
     * @param jsonReader
     * @param fieldType
     * @param fieldName
     * @param features
     * @return {@link T}
     * @throws JSONException If a suitable ObjectReader is not found
     */
    @Override
    public String readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        return StringUtils.trimSpaces(jsonReader.readString(), SpacesType.EXCEPT_LINE_SPACES);
    }
}
