package me.kany.project.learning.spring.format;

import me.kany.project.learning.spring.annotation.SpacesType;
import me.kany.project.learning.spring.utils.StringUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jason.Wang
 */
public class SpacesFormatter implements Formatter<String> {

    private final SpacesType spacesType;

    public SpacesFormatter(SpacesType spacesType) {
        if (Objects.isNull(spacesType)) {
            throw new NullPointerException();
        }
        this.spacesType = spacesType;
    }

    @Override
    public String parse(String text, Locale locale) throws ParseException {
        return StringUtils.trimSpaces(text, spacesType);
    }

    @Override
    public String print(String object, Locale locale) {
        return object;
    }
}
