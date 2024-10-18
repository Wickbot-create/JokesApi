package org.com.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JokeAPIExceptionTest {

    @Test
    public void testJokeAPIExceptionWithMessage() {
        String errorMessage = "API error occurred";
        JokeAPIException exception = new JokeAPIException(errorMessage);

        assertNotNull(exception); 
        assertEquals(errorMessage, exception.getMessage()); 
        assertNull(exception.getCause()); 
    }

    @Test
    public void testJokeAPIExceptionWithMessageAndCause() {
        String errorMessage = "API error occurred";
        Throwable cause = new RuntimeException("Underlying cause");

        JokeAPIException exception = new JokeAPIException(errorMessage, cause);

        assertNotNull(exception); 
        assertEquals(errorMessage, exception.getMessage()); 
        assertNotNull(exception.getCause()); 
        assertEquals(cause, exception.getCause()); 
        assertEquals("Underlying cause", exception.getCause().getMessage()); 
    }

    @Test
    public void testJokeAPIExceptionCauseOnly() {
        Throwable cause = new RuntimeException("Only cause");
        JokeAPIException exception = new JokeAPIException(cause.getMessage(), cause);

        assertNotNull(exception); 
        assertEquals("Only cause", exception.getMessage()); 
        assertEquals(cause, exception.getCause()); 
    }
}
