package me.kany.project.learning.spring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jason.Wang
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户的Id
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户手机
     */
    private String mobile;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;


}
