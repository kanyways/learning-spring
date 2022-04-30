package me.kany.project.learning.spring.format;

import me.kany.project.learning.spring.annotation.SpacesField;
import me.kany.project.learning.spring.annotation.SpacesType;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.*;

/**
 * @author Jason.Wang
 */
public class CustomerSpacesAnnotationFormatterFactory implements AnnotationFormatterFactory<SpacesField> {

    /**
     * 根据枚举创建类型映射
     */
    private static final Map<SpacesType, SpacesFormatter> SPACES_FORMATTER_MAP;

    /**
     * 静态初始化值
     */
    static {
        SpacesType[] values = SpacesType.values();
        Map<SpacesType, SpacesFormatter> map = new HashMap<>(values.length);
        for (SpacesType spacesType : values) {
            map.put(spacesType, new SpacesFormatter(spacesType));
        }
        SPACES_FORMATTER_MAP = Collections.unmodifiableMap(map);
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>(1, 1);
        fieldTypes.add(String.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(SpacesField annotation, Class<?> fieldType) {
        return SPACES_FORMATTER_MAP.get(annotation.value());
    }

    @Override
    public Parser<?> getParser(SpacesField annotation, Class<?> fieldType) {
        return SPACES_FORMATTER_MAP.get(annotation.value());
    }
}
