package org.com.repo;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.com.entity.Joke;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JokeRepositoryTest {

    @Inject
    JokeRepository jokeRepository;

    @Test
    public void testSave_newJoke() {
        Joke joke = new Joke("Why did the chicken cross the road?", "To get to the other side.");

        Uni<Joke> resultUni = jokeRepository.save(joke);

        resultUni.subscribe().with(
            result -> {
                assertNotNull(result); 
                assertEquals(joke.question, result.question); 
                assertEquals(joke.answer, result.answer); 
                assertNotNull(result.id); 
            },
            failure -> fail("Save operation should succeed.")
        );
    }

    @Test
    public void testSave_existingJoke() {
        Joke joke1 = new Joke("Why did the chicken cross the road?", "To get to the other side.");
        Joke joke2 = new Joke("Why did the chicken cross the road?", "Because it was bored."); // Same question but different answer

        Uni<Joke> saveFirstUni = jokeRepository.save(joke1);

        Uni<Joke> saveSecondUni = saveFirstUni.onItem().transformToUni(j -> jokeRepository.save(joke2));

        saveSecondUni.subscribe().with(
            result -> {
                assertNotNull(result); 
                assertEquals(joke1.question, result.question); 
                assertEquals(joke1.answer, result.answer); 
                assertEquals(joke1.id, result.id); 
            },
            failure -> fail("Save operation should succeed.")
        );
    }

    @Test
    public void testFindById_jokeExists() {
        Joke joke = new Joke("Why don't skeletons fight each other?", "Because they don’t have the guts.");

        Uni<Joke> saveUni = jokeRepository.save(joke);

        saveUni.subscribe().with(savedJoke -> {
            jokeRepository.findById(savedJoke.id).subscribe().with(
                result -> {
                    assertNotNull(result); 
                    assertEquals(savedJoke.id, result.id); 
                    assertEquals(savedJoke.question, result.question); 
                    assertEquals(savedJoke.answer, result.answer); 
                },
                failure -> fail("Joke should be found by ID.")
            );
        });
    }

    @Test
    public void testFindById_jokeDoesNotExist() {
        Uni<Joke> resultUni = jokeRepository.findById("non-existing-id");

        resultUni.subscribe().with(
            result -> assertNull(result), 
            failure -> fail("Find operation should return null when the joke doesn't exist.")
        );
    }
}
