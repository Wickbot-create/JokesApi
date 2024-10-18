package org.com.resource;

import org.com.entity.JokeDTO;
import org.com.service.JokesService;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/jokes")
public class JokeController {

    @Inject
    JokesService jokesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<JokeDTO> getJokes(@QueryParam("count") int count) {
        if (count < 10) {
            throw new BadRequestException("The count must be 10 or greater.");
        }

        return jokesService.getJokes(count)
                .onItem().transform(JokeDTO::fromEntity);
    }
}
