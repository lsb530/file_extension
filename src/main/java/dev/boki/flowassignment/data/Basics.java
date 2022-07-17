package dev.boki.flowassignment.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.boki.flowassignment.exception.BadRequestException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import lombok.Getter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@Getter
public enum Basics {

    BAT, CMD, COM, CPL, EXE, CSR, JS;

    @JsonCreator
    public static Basics from(String val) {
        for (Basics basic : Basics.values()) {
            if (basic.name().equals(val) || basic.name().equals(val.toUpperCase())) {
                return basic;
            }
        }
        throw new HttpMessageConversionException("");
    }

    public static Set<String> getEnums() {
        Set<String> values = new HashSet<>();
        for (Basics b : Basics.values()) {
            values.add(b.name());
        }
        return values;
    }
}