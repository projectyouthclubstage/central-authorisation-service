package de.youthclubstage.backend.central.authorisation.exception;

public class ExternalAuthenticationFailedException extends CustomizedException {

    public ExternalAuthenticationFailedException(Integer code) {
        super(ExceptionMessages.buildCode(code), ExceptionMessages.getMessage(code));
    }

}
