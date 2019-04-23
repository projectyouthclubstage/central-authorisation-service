package de.youthclubstage.backend.central.authorisation.exception;

public class TokenCreationException extends CustomizedException {

    public TokenCreationException(String error, String message) {
        super(error, message);
    }

}
