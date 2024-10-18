package org.com.service;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.com.entity.Joke;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JokesServiceTest {

    @Mock
    JokeApiService jokeApiService;

    @Mock
    JokeDBService jokeDBService;

    @InjectMocks
    @Inject
    JokesService jokesService;

    @Test
    public void testGetJokes_validCount() {
        Joke joke1 = new Joke("Why did the chicken cross the road?", "To get to the other side.");
        Joke joke2 = new Joke("What’s orange and sounds like a parrot?", "A carrot.");
        
        when(jokeApiService.fetchRandomJokes(2)).thenReturn(Multi.createFrom().items(joke1, joke2));
        
        when(jokeDBService.saveJokeIfNotPresent(joke1)).thenReturn(Uni.createFrom().item(joke1));
        when(jokeDBService.saveJokeIfNotPresent(joke2)).thenReturn(Uni.createFrom().item(joke2));

        Multi<Joke> result = jokesService.getJokes(2);

        result.subscribe().with(
            joke -> assertNotNull(joke),
            failure -> fail("Should not fail for valid count")
        );

        verify(jokeApiService, times(1)).fetchRandomJokes(2);
        verify(jokeDBService, times(1)).saveJokeIfNotPresent(joke1);
        verify(jokeDBService, times(1)).saveJokeIfNotPresent(joke2);
    }

    @Test
    public void testGetJokes_invalidCount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jokesService.getJokes(0);
        });

        assertEquals("Count must be greater than 0.", exception.getMessage());

        verifyNoInteractions(jokeApiService);
        verifyNoInteractions(jokeDBService);
    }

    @Test
    public void testGetJokes_databaseFailure() {
        Joke joke = new Joke("Why did the chicken cross the road?", "To get to the other side.");
        
        when(jokeApiService.fetchRandomJokes(1)).thenReturn(Multi.createFrom().item(joke));
        
        when(jokeDBService.saveJokeIfNotPresent(joke)).thenReturn(Uni.createFrom().failure(new RuntimeException("DB Error")));

        Multi<Joke> result = jokesService.getJokes(1);

        result.subscribe().with(
            jokeResult -> fail("Expected a database failure"),
            failure -> assertEquals("DB Error", failure.getMessage())
        );

        verify(jokeApiService, times(1)).fetchRandomJokes(1);
        verify(jokeDBService, times(1)).saveJokeIfNotPresent(joke);
    }

    @Test
    public void testGetJokes_jokeApiFailure() {
       
        when(jokeApiService.fetchRandomJokes(1)).thenReturn(Multi.createFrom().failure(new RuntimeException("API Error")));

        Multi<Joke> result = jokesService.getJokes(1);

        result.subscribe().with(
            joke -> fail("Expected API failure"),
            failure -> assertEquals("API Error", failure.getMessage())
        );

        verify(jokeApiService, times(1)).fetchRandomJokes(1);
        verifyNoInteractions(jokeDBService);
    }
}
