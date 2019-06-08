package de.youthclubstage.backend.central.authorisation.exception;

import java.util.HashMap;
import java.util.Map;

class ExceptionMessages {

    private static final String PREFIX = "CAS#";
    private static final String DEFAULT_MESSAGE = "An error has occurred!";
    private static final Map<Integer, String> codeToMessage;

    static {
        codeToMessage = new HashMap<>();

        // 2XXX - authentication errors
        codeToMessage.put(2000, "Google-authentication failed!");
        codeToMessage.put(2001, "Facebook-authentication failed!");
        codeToMessage.put(2010, "Token invalid!");
        codeToMessage.put(2011, "Token expired!");

        // 3XXX - internal errors
        codeToMessage.put(3000, "Token creation failed!");

    }

    static String getMessage(Integer code) {
        return codeToMessage.getOrDefault(code, DEFAULT_MESSAGE);
    }

    static String buildCode(Integer code) {
        return PREFIX + code.toString();
    }

}
