package me.kany.project.learning.spring.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import me.kany.project.learning.spring.json.deserializer.FastjsonStringTrimJsonDeserialize;
import me.kany.project.learning.spring.json.serializer.FastjsonStringTrimJsonSerializer;

/**
 * @author Jason.Wang
 */
@Data
public class User extends Base {
    /**
     * 用户名
     */
    @JSONField(serializeUsing = FastjsonStringTrimJsonSerializer.class, deserializeUsing = FastjsonStringTrimJsonDeserialize.class)
    private String userName;

    /**
     * 昵称
     */
    private String nickName;
}
