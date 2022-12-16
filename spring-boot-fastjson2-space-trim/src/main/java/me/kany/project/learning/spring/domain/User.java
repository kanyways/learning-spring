package me.kany.project.learning.spring.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import me.kany.project.learning.spring.json.deserializer.FastjsonStringTrimJsonDeserialize;
import me.kany.project.learning.spring.json.serializer.FastjsonDateToTimestampJsonSerializer;
import me.kany.project.learning.spring.json.serializer.FastjsonStringTrimJsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @JSONField(serializeUsing = FastjsonDateToTimestampJsonSerializer.class)
    private Date createTime;

    @JSONField(serializeUsing = FastjsonDateToTimestampJsonSerializer.class)
    private java.sql.Date updateTime;

    @JSONField(serializeUsing = FastjsonDateToTimestampJsonSerializer.class)
    private LocalDate modifyTime;

    @JSONField(serializeUsing = FastjsonDateToTimestampJsonSerializer.class)
    private LocalDateTime showTime;
}
