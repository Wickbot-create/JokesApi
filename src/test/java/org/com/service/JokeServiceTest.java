package org.com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.com.client.ExternalJokesApiClient;
import org.com.entity.Joke;
import org.com.repo.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class JokeServiceTest {

    @Mock
    private ExternalJokesApiClient jokesApiClient;

    @Mock
    private JokeRepository jokeRepository;

    @InjectMocks
    private JokesService jokesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchJokes() {
        List<Joke> jokeList = List.of(
            new Joke("What did the ocean say to the shore?", "Nothing, it just waved."),
            new Joke( "Why don't scientists trust atoms?", "Because they make up everything!")
        );

        Multi<Joke> jokeMulti = Multi.createFrom().iterable(jokeList);
        when(jokesApiClient.fetchRandomJokes(2)).thenReturn(jokeMulti);
        when(jokeRepository.save(any(Joke.class))).thenReturn(Uni.createFrom().item(jokeList.get(0)));

        List<Joke> result = jokesService.getJokes(2).collect().asList().await().indefinitely();

        assertEquals(jokeList.size(), result.size());
        assertEquals(jokeList.get(0).getQuestion(), result.get(0).getQuestion());
    }
}
