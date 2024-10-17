package org.com.entity;

public class JokeDTO {
    public String id;
    public String setup;
    public String punchline;

    // Default constructor
    public JokeDTO() {}

    public JokeDTO(String id, String setup, String punchline) {
        this.id = id;
        this.setup = setup;
        this.punchline = punchline;
    }
}



