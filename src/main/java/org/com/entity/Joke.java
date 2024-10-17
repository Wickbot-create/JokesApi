package org.com.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Joke extends PanacheEntityBase {

    @Id
    public String id = UUID.randomUUID().toString();
    public String question;
    public String answer;

    public Joke() {}

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Joke(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}

