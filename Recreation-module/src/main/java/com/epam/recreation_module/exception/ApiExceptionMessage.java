package com.epam.recreation_module.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiExceptionMessage {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime time;
}
