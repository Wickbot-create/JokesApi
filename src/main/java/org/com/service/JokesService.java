package org.com.service;

import io.smallrye.mutiny.Multi;
import org.com.entity.Joke;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JokesService {

    @Inject
    JokeApiService jokeApiService;

    @Inject
    JokeDBService jokeDBService;

    public Multi<Joke> getJokes(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0.");
        }

        return jokeApiService.fetchRandomJokes(count)
                .onItem().transformToUniAndMerge(joke -> jokeDBService.saveJokeIfNotPresent(joke));
    }
}
