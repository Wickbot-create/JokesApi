package org.com.service;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import org.com.client.ExternalJokesApi;
import org.com.entity.Joke;
import org.com.entity.JokeResponseDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class JokeApiService {

    @RestClient
    ExternalJokesApi externalJokesApi;

    public Multi<Joke> fetchRandomJokes(int count) {
        return Multi.createBy().repeating()
                .uni(externalJokesApi::getRandomJoke)
                .atMost(count)
                .map(this::mapToJoke);
    }

    private Joke mapToJoke(JokeResponseDTO response) {
        return new Joke(response.setup, response.punchline);
    }
}
