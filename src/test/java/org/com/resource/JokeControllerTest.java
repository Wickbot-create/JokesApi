package org.com.resource;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.com.entity.Joke;
import org.com.service.JokesService;
import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;

public class JokeControllerTest {

    private final JokesService jokeService = mock(JokesService.class);

    @Test
    void testGetJokes() {
        List<Joke> jokeList = List.of(new Joke("What did the ocean say to the shore?", "Nothing, it just waved."), 
                                      new Joke("Why don't scientists trust atoms?", "Because they make up everything!"));
        Multi<Joke> jokeUni = Multi.createFrom().iterable(jokeList);

        Multi<Joke> jokeMulti = Multi.createFrom().iterable(jokeList);


        equals(jokeList);
    }
}
