package org.com.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Joke extends PanacheEntityBase {

    @Id
    public String id;
    public String question;
    public String answer;

    public Joke() {
        this.id = UUID.randomUUID().toString();
    }

    public Joke(String question, String answer) {
        this();
        this.question = question;
        this.answer = answer;
    }
}
