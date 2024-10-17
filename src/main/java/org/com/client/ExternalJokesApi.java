package org.com.client;



import org.com.entity.JokeResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/random_joke")
@RegisterRestClient(configKey = "jokes-api")
public interface ExternalJokesApi {

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 Uni<JokeResponse> getRandomJoke(); 

}


