package org.com.service;


import org.com.client.ExternalJokesApiClient;
import org.com.entity.Joke;
import org.com.repo.JokeRepository;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JokesService {

    @Inject
    ExternalJokesApiClient jokesApiClient;

    @Inject
    JokeRepository jokeRepository;

 public Multi<Joke> getJokes(int count) {
     return jokesApiClient.fetchRandomJokes(count)
         .onItem().transformToUniAndMerge(joke -> jokeRepository.save(joke));
 }
}




