package org.com.resource;


import org.com.entity.Joke;
import org.com.service.JokesService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/jokes")
public class JokeController {

	@Inject
	JokesService jokesService;

    @GET
    public Multi<Joke> getJokes(@QueryParam("count") int count) {
        return jokesService.getJokes(count);
    }
}

