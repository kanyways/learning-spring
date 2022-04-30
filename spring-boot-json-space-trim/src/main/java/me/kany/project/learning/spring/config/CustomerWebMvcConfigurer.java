package me.kany.project.learning.spring.config;

import me.kany.project.learning.spring.format.CustomerSpacesAnnotationFormatterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jason.Wang
 */
@Configuration
public class CustomerWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * Add {@link Converter Converters} and {@link Formatter Formatters} in addition to the ones
     * registered by default.
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new CustomerSpacesAnnotationFormatterFactory());
        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * Extend or modify the list of converters after it has been, either
     * {@link #configureMessageConverters(List) configured} or initialized with
     * a default list.
     * <p>Note that the order of converter registration is important. Especially
     * in cases where clients accept {@link MediaType#ALL}
     * the converters configured earlier will be preferred.
     *
     * @param converters the list of configured converters to be extended
     * @since 4.1.3
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(0, new CustomerConverterHttpMessageConverter());
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}
