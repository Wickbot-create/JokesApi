package org.com.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.com.entity.JokeResponseDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;

@QuarkusTest
public class ExternalJokesApiTest {

    @RestClient
    ExternalJokesApi externalJokesApi;

    @Test
    public void testGetRandomJoke_successfulResponse() {
        JokeResponseDTO mockJokeResponse = new JokeResponseDTO("Why did the chicken cross the road?", "To get to the other side.");
        
        when(externalJokesApi.getRandomJoke()).thenReturn(Uni.createFrom().item(mockJokeResponse));

        Uni<JokeResponseDTO> resultUni = externalJokesApi.getRandomJoke();

        resultUni.subscribe().with(
            result -> {
                assertNotNull(result); 
                assertEquals(mockJokeResponse.setup, result.setup); 
                assertEquals(mockJokeResponse.punchline, result.punchline); 
            },
            failure -> fail("Should not fail for a successful response.")
        );
    }

    @Test
    public void testGetRandomJoke_nullResponse() {
        when(externalJokesApi.getRandomJoke()).thenReturn(Uni.createFrom().nullItem());

        Uni<JokeResponseDTO> resultUni = externalJokesApi.getRandomJoke();

        resultUni.subscribe().with(
            result -> assertNull(result), 
            failure -> fail("Should not fail for a null response.")
        );
    }

    @Test
    public void testGetRandomJoke_failureResponse() {
        Throwable mockFailure = new RuntimeException("External API error");
        
        when(externalJokesApi.getRandomJoke()).thenReturn(Uni.createFrom().failure(mockFailure));

        Uni<JokeResponseDTO> resultUni = externalJokesApi.getRandomJoke();

        resultUni.subscribe().with(
            result -> fail("Expected failure, but got success."),
            failure -> {
                assertNotNull(failure); 
                assertEquals("External API error", failure.getMessage()); 
            }
        );
    }
}
