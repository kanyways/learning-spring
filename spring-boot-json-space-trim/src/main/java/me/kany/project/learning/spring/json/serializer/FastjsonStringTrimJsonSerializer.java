package me.kany.project.learning.spring.json.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Jason.Wang
 */
public class FastjsonStringTrimJsonSerializer implements ObjectSerializer {
    /**
     * fastjson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     *
     * @param serializer
     * @param object     src the object that needs to be converted to Json.
     * @param fieldName  parent object field name
     * @param fieldType  parent object field type
     * @param features   parent object field serializer features
     * @throws IOException
     */
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
        throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            serializer.writeNull();
            return;
        }

        String strVal = object.toString();
        serializer.write(StringUtils.trimSpaces(strVal, SpacesType.EXCEPT_LINE_SPACES));
    }
}
