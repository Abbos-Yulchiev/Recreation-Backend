package com.epam.recreation_module.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private Long id;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause, Long id) {
        super(message, cause);
        this.id = id;
    }
}
