package org.com.entity;

public class JokeResponseDTO {
    public String setup;
    public String punchline;

    public JokeResponseDTO() {}

    public JokeResponseDTO(String setup, String punchline) {
        this.setup = setup;
        this.punchline = punchline;
    }
}
