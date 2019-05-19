package de.youthclubstage.backend.central.authorisation.exception;

public class ExternalAuthenticationFailedException extends CustomizedException {

    public ExternalAuthenticationFailedException(String error, String message) {
        super(error, message);
    }

}
