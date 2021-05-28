package com.github.djbamba.gofish.ui.exception;

import java.util.NoSuchElementException;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {
  private final Function<WebRequest, String> getPath =
      webReq -> webReq.getDescription(false).substring(4);

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<?> handleNoSuchElement(RuntimeException ex, WebRequest request) {

    return ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND)
        .message(ex.getMessage())
        .path(getPath.apply(request))
        .build()
        .entity();
  }
}
