package me.kany.project.learning.spring.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.kany.project.learning.spring.utils.CustomLongNumberDateConverter;
import me.kany.project.learning.spring.utils.CustomLongStringConverter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Jason.Wang
 * @since 2019-10-25
 */
@Data
@HeadRowHeight(25)
@ContentRowHeight(20)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的Id，使用自定义的标签将数字变成 0000000000001 的格式
     */
    @ColumnWidth(value = 26)
    @ExcelProperty(value = {"用户ID"}, index = 0, converter = CustomLongStringConverter.class)
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 登录名
     */
    @ColumnWidth(value = 18)
    @ExcelProperty(value = {"登录项目", "用户名"}, index = 1)
    private String loginName;

    /**
     * 用户手机
     */
    @ColumnWidth(value = 16)
    @ExcelProperty(value = {"登录项目", "用户手机"}, index = 2)
    private String mobile;

    /**
     * 用户密码
     * 使用ExcelIgnore忽略
     */
    @ExcelIgnore
    private String passWord;

    /**
     * 创建时间
     */
    @ColumnWidth(value = 30)
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    @ExcelProperty(value = {"创建时间"}, index = 3, converter = CustomLongNumberDateConverter.class)
    private Long createTime;

    /**
     * 最后登录时间
     */
    @ColumnWidth(value = 30)
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    @ExcelProperty(value = {"最后登录时间"}, index = 4, converter = CustomLongNumberDateConverter.class)
    private Long lastLoginTime;


}
