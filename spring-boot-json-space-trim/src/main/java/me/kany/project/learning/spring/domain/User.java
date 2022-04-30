package me.kany.project.learning.spring.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.kany.project.learning.spring.annotation.SpacesField;
import me.kany.project.learning.spring.json.deserializer.FastjsonStringTrimJsonDeserialize;
import me.kany.project.learning.spring.json.deserializer.JacksonStringTrimJsonDeserialize;
import me.kany.project.learning.spring.json.serializer.FastjsonStringTrimJsonSerializer;
import me.kany.project.learning.spring.json.serializer.JacksonStringTrimJsonSerializer;

/**
 * @author Jason.Wang
 */
@Data
public class User extends Base {
    /**
     * 用户名
     */
    @SpacesField
    @JsonDeserialize(using = JacksonStringTrimJsonDeserialize.class)
    @JsonSerialize(using = JacksonStringTrimJsonSerializer.class)
    @JSONField(serializeUsing = FastjsonStringTrimJsonSerializer.class, deserializeUsing = FastjsonStringTrimJsonDeserialize.class)
    private String userName;

    /**
     * 昵称
     */
    private String nickName;
}
