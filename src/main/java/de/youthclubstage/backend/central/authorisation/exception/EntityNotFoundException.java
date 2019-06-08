package de.youthclubstage.backend.central.authorisation.exception;

public class EntityNotFoundException extends CustomizedException {

    public EntityNotFoundException(Integer code) {
        super(ExceptionMessages.buildCode(code), ExceptionMessages.getMessage(code));
    }

}