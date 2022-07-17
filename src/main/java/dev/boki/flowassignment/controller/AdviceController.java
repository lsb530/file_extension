package dev.boki.flowassignment.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import dev.boki.flowassignment.data.ErrorResponse;
import dev.boki.flowassignment.exception.BadRequestException;
import dev.boki.flowassignment.exception.DuplicatedExtensionException;
import dev.boki.flowassignment.exception.NotFoundExtensionException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DuplicatedExtensionException.class)
    public ResponseEntity<ErrorResponse> badRequestException(DuplicatedExtensionException e,
        HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, BAD_REQUEST, request);
        return ResponseEntity.badRequest().body(ErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e,
        HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, BAD_REQUEST, request);
        return ResponseEntity.internalServerError().body(ErrorResponse.from("허용되지 않은 확장자명입니다"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e,
        HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, BAD_REQUEST, request);
        return ResponseEntity.badRequest().body(ErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler(NotFoundExtensionException.class)
    public ResponseEntity<ErrorResponse> NotFoundExtensionException(NotFoundExtensionException e,
        HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, NOT_FOUND, request);
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindException e, HandlerMethod method,
        HttpServletRequest request) {
        exactErrorLog(e, method, UNPROCESSABLE_ENTITY, request);
        return ResponseEntity.unprocessableEntity().body(ErrorResponse.from(e.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintException(ConstraintViolationException e,
        HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, BAD_REQUEST, request);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMessage;
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(violation.getMessage()).append(" "));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }
        return ResponseEntity.badRequest().body(ErrorResponse.from(errorMessage));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e, HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, INTERNAL_SERVER_ERROR, request);
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMessageConversionException.class})
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(RuntimeException e, HandlerMethod method, HttpServletRequest request) {
        exactErrorLog(e, method, INTERNAL_SERVER_ERROR, request);
        return ResponseEntity.internalServerError().body(ErrorResponse.from("요청한 값은 고정 확장자에 존재하지 않습니다"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e, HandlerMethod method,
        HttpServletRequest request) {
        exactErrorLog(e, method, INTERNAL_SERVER_ERROR, request);
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e.getMessage()));
    }

    private void exactErrorLog(Exception e, HandlerMethod handlerMethod, HttpStatus httpStatus,
        HttpServletRequest request) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";
        final String RED_UNDERLINED = "\033[4;31m";
        String errorDate = ANSI_YELLOW + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
            Calendar.getInstance().getTime()) + ANSI_RESET;
        String requestURI = ANSI_CYAN + request.getRequestURI() + ANSI_RESET;
        String exceptionName = ANSI_PURPLE + e.getClass().getSimpleName() + ANSI_RESET;
        String status = ANSI_RED + httpStatus.toString() + ANSI_RESET;
        String controllerName =
            ANSI_GREEN + handlerMethod.getMethod().getDeclaringClass().getSimpleName() + ANSI_RESET;
        String methodName = ANSI_BLUE + handlerMethod.getMethod().getName() + ANSI_RESET;
        String message = ANSI_RED + e.getMessage() + ANSI_RESET;
        String lineNumber = RED_UNDERLINED + e.getStackTrace()[0].getLineNumber() + ANSI_RESET;
        log.error(
            "\n[Time: {} | Class: {} | Method: {} | LineNumber: {} | Path: {} | Exception: {} | Status: {} | Message: {}]\n",
            errorDate, controllerName, methodName, lineNumber, requestURI, exceptionName, status,
            message);
    }
}