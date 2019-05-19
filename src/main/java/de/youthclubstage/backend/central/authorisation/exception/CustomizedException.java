package de.youthclubstage.backend.central.authorisation.exception;

import lombok.Getter;

@Getter
public abstract class CustomizedException extends RuntimeException {

    private final String error;
    private final String message;

    public CustomizedException(String error, String message) {
        this.error = error;
        this.message = message;
    }


}
