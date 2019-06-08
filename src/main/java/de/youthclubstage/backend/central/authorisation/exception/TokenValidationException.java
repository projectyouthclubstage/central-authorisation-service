package de.youthclubstage.backend.central.authorisation.exception;

public class TokenValidationException extends CustomizedException {

    public TokenValidationException(Integer code) {
        super(ExceptionMessages.buildCode(code), ExceptionMessages.getMessage(code));
    }

}