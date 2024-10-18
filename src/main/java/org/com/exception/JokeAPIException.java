package org.com.exception;

public class JokeAPIException extends RuntimeException {
    public JokeAPIException(String message) {
        super(message);
    }

    public JokeAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
