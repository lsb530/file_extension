package dev.boki.flowassignment.exception;

public class NotFoundExtensionException extends RuntimeException {

    public NotFoundExtensionException() {
        super("존재하지 않는 확장자입니다");
    }
}