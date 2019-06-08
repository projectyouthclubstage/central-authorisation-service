package de.youthclubstage.backend.central.authorisation.exception;

public class TokenValidationException extends CustomizedException {

    public TokenValidationException(String error, String message) {
        super(error, message);
    }

}
