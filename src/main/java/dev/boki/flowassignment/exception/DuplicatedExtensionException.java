package dev.boki.flowassignment.exception;

public class DuplicatedExtensionException extends RuntimeException {

    public DuplicatedExtensionException() {
        super("중복된 확장자명입니다");
    }
}