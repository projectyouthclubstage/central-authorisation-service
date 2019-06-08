package de.youthclubstage.backend.central.authorisation.exception;

public class TokenCreationException extends CustomizedException {

    public TokenCreationException(Integer code) {
        super(ExceptionMessages.buildCode(code), ExceptionMessages.getMessage(code));
    }

}