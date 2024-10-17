package org.com.client;


import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

import org.com.entity.Joke;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ExternalJokesApiClient {

 // Inject the RestClient to interact with the external jokes API
 @RestClient
 ExternalJokesApi externalJokesApi;

 /**
  * Fetch a batch of jokes in a reactive manner.
  *
  * @param count The number of jokes to fetch.
  * @return A Multi stream containing Joke objects.
  */
 public Multi<Joke> fetchRandomJokes(int count) {
     // Fetch jokes in batches using Multi
     return Multi.createBy().repeating()
             .uni(externalJokesApi::getRandomJoke) // Call the external API for each joke
             .atMost(count) // Limit to the number of jokes requested
             .map(jokeResponse -> new Joke(jokeResponse.setup, jokeResponse.punchline)); // Map response to Joke domain object
 }
}
