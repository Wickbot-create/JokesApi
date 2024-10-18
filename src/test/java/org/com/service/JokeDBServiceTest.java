package org.com.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.com.entity.Joke;
import org.com.repo.JokeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@QuarkusTest
public class JokeDBServiceTest {

    @Mock
    JokeRepository jokeRepository;

    @InjectMocks
    @Inject
    JokeDBService jokeDBService;

    @Test
    public void testSaveJokeIfNotPresent_positive() {
        Joke joke = new Joke("Why did the chicken cross the road?", "To get to the other side.");
        when(jokeRepository.save(joke)).thenReturn(Uni.createFrom().item(joke));

        Uni<Joke> result = jokeDBService.saveJokeIfNotPresent(joke);

        result.subscribe().with(savedJoke -> {
            assertNotNull(savedJoke);
            assertEquals("Why did the chicken cross the road?", savedJoke.question);
        });
    }

    @Test
    public void testSaveJokeIfNotPresent_alreadyExists() {
        Joke joke = new Joke("Why did the chicken cross the road?", "To get to the other side.");
        when(jokeRepository.save(joke)).thenReturn(Uni.createFrom().nullItem());

        Uni<Joke> result = jokeDBService.saveJokeIfNotPresent(joke);

        result.subscribe().with(jokeResult -> assertNull(jokeResult));
    }
}
