package com.github.djbamba.gofish.ui.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
private HttpStatus status;
private String message;
private String error;
private String path;
private final LocalDateTime timeStamp = LocalDateTime.now();

public ResponseEntity<ErrorResponse> entity(){
    return ResponseEntity.status(status)
        .headers(HttpHeaders.EMPTY)
        .body(this);
}

}
