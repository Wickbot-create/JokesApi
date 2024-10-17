package org.com.repo;

import org.com.entity.Joke;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JokeRepository {

    public Uni<Joke> save(Joke joke) {
        return joke.persist().map(j -> (Joke) j);
    }

    public Uni<Joke> findById(String id) {
        return Joke.findById(id);
    }
}

