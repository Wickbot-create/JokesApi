package org.com.entity;

public class JokeDTO {
    public String id;
    public String question;
    public String answer;

    public JokeDTO() {}

    public JokeDTO(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public static JokeDTO fromEntity(Joke joke) {
        return new JokeDTO(joke.id, joke.question, joke.answer);
    }
}
