package me.kany.project.learning.spring.converter;

import me.kany.project.learning.spring.annotation.SpacesBean;
import me.kany.project.learning.spring.annotation.SpacesField;
import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Jason.Wang
 */
public class CustomerConverterHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {

        //判断返回类型是否是集合类型
        boolean isCollection = Collection.class.isAssignableFrom(object.getClass());
        //判断返回类型是否是数组类型
        boolean isArray = object.getClass().isArray();
        if(isCollection || isArray){
            List<?> list = null;
            if(isArray){
                list = Collections.singletonList(object);
            }
            if(isCollection){
                list = (List<?>)object;
            }
            for (Object item:list) {
                trimObjectSpaces(item.getClass(),item);
            }
        }

        super.writeInternal(object, type, outputMessage);
    }

    /**
     * isCheckClass: 检查类中的字段是否有配置 @SpacesBean 注解<br/>
     *
     * @param clazz
     * @return
     * @author Jason.Wang
     * @date 2022/3/31 13:55
     */
    private boolean isCheckClass(Class<?> clazz) {
        boolean checkResult = false;
        SpacesBean spacesBean = AnnotationUtils.findAnnotation(clazz, SpacesBean.class);
        if (Objects.nonNull(spacesBean)) {
            checkResult = true;
        }
        return checkResult;
    }

    /**
     * isCheckFields: 读取字段是否配置 @SpacesField <br/>
     *
     * @param clazz
     * @return
     * @author Jason.Wang
     * @date 2022/3/31 12:43
     */
    private boolean isCheckFields(Class<?> clazz) {
        boolean checkResult = false;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            SpacesField spacesField = field.getAnnotation(SpacesField.class);
            if (Objects.nonNull(spacesField)) {
                checkResult = true;
                break;
            }
        }
        return checkResult;
    }

    /**
     * supportInternalAndHandleClass: 检查类上是否配置<br/>
     *
     * @param clazz
     * @return
     * @author Jason.Wang
     * @date 2022/3/31 12:41
     */
    private boolean supportInternalAndHandleClass(Class<?> clazz) {
        boolean checkResult = false;
        Class<?> superClass = clazz;

        while (!"java.lang.Object".equals(superClass.getName())) {
            checkResult = isCheckClass(superClass);
            if (checkResult) {
                break;
            }
            superClass = superClass.getSuperclass();
        }
        return checkResult;
    }

    /**
     * supportInternalAndHandleField: 检查类中的字段是否有配置 @SpacesField 注解<br/>
     *
     * @param clazz
     * @return
     * @author Jason.Wang
     * @date 2022/3/31 12:42
     */
    private boolean supportInternalAndHandleField(Class<?> clazz) {
        boolean checkResult = false;
        Class<?> superClass = clazz;
        while (!"java.lang.Object".equals(superClass.getName())) {
            checkResult = isCheckFields(superClass);
            if (checkResult) {
                break;
            }
            superClass = superClass.getSuperclass();
        }
        return checkResult;
    }

    /**
     * trimObjectSpaces: 执行去除指定注解上的空格信息<br/>
     *
     * @param clazz
     * @param object
     * @author Jason.Wang
     * @date 2022/3/31 15:21
     */
    private void trimObjectSpaces(Class<?> clazz, Object object) {
        // 如果当前有Bean上的注解就直接使用Bean的注解
        if (supportInternalAndHandleClass(clazz)) {
            SpacesBean spacesBean = AnnotationUtils.findAnnotation(clazz, SpacesBean.class);
            assert spacesBean != null;
            Class<?> superClass = clazz;
            while (!"java.lang.Object".equals(superClass.getName())) {
                Field[] fields = superClass.getDeclaredFields();
                for (Field field : fields) {
                    trimField(object, field, spacesBean.value());
                }
                superClass = superClass.getSuperclass();
            }
        } else if (supportInternalAndHandleField(clazz)) {
            Class<?> superClass = clazz;
            while (!"java.lang.Object".equals(superClass.getName())) {
                Field[] fields = superClass.getDeclaredFields();
                for (Field field : fields) {
                    SpacesField spacesField = field.getAnnotation(SpacesField.class);
                    if (Objects.nonNull(spacesField)) {
                        trimField(object, field, spacesField.value());
                    }
                }
                superClass = superClass.getSuperclass();
            }
        }
    }

    /**
     * trimField: 处理字段信息<br/>
     *
     * @param object
     * @param field
     * @param spacesType
     * @author Jason.Wang
     * @date 2022/4/8 11:35
     */
    private void trimField(Object object, Field field, SpacesType spacesType) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value instanceof String) {
            try {
                field.set(object, StringUtils.trimSpaces((String)value, spacesType));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
