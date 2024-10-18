package org.com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.com.client.ExternalJokesApi;
import org.com.entity.Joke;
import org.com.entity.JokeResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@QuarkusTest
public class JokeApiServiceTest {

    @Mock
    ExternalJokesApi jokesApi;

    @InjectMocks
    @Inject
    JokeApiService jokeApiService;

    @Test
    public void testFetchRandomJokes_positive() {
        when(jokesApi.getRandomJoke()).thenReturn(Uni.createFrom().item(new JokeResponseDTO("Why?", "Because!")));

        Multi<Joke> jokes = jokeApiService.fetchRandomJokes(5);

        jokes.subscribe().with(item -> {
            assertNotNull(item);
            assertEquals("Why?", item.question);
            assertEquals("Because!", item.answer);
        });
    }

    @Test
    public void testFetchRandomJokes_negative() {
        when(jokesApi.getRandomJoke()).thenReturn(Uni.createFrom().failure(new RuntimeException("Joke API failed")));

        Multi<Joke> jokes = jokeApiService.fetchRandomJokes(5);

        jokes.subscribe().with(item -> fail("Expected failure"), failure -> assertEquals("Joke API failed", failure.getMessage()));
    }
}
