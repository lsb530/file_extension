package dev.boki.flowassignment.converter;

import dev.boki.flowassignment.data.Basics;
import org.springframework.core.convert.converter.Converter;

public class UpperConverter implements Converter<String, Basics> {

    @Override
    public Basics convert(String source) {
        return Basics.valueOf(source.toUpperCase());
    }
}