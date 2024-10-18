package org.com.service;

import org.com.entity.Joke;
import org.com.repo.JokeRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JokeDBService {

    @Inject
    JokeRepository jokeRepository;

    public Uni<Joke> saveJokeIfNotPresent(Joke joke) {
        return jokeRepository.save(joke);
    }
}

