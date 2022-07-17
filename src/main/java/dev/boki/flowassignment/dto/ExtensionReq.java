package dev.boki.flowassignment.dto;

import dev.boki.flowassignment.data.Basics;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ExtensionReq {
    
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SingleBasicReq {
        private Basics extension;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MultipleBasicReq {
        private List<Basics> extensions;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SingleCustomReq {
        private String extension;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MultipleCustomReq {
        private List<String> extensions;
    }

}