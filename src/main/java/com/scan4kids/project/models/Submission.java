package com.scan4kids.project.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String submission_file;

    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    private List<Score> scores;


    public Submission() {
    }

    public Submission(long id, String submission_file, User owner, List<Score> scores) {
        this.id = id;
        this.submission_file = submission_file;
        this.owner = owner;
        this.scores = scores;
    }

    public Submission(String submission_file, User owner, List<Score> scores) {
        this.submission_file = submission_file;
        this.owner = owner;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubmission_file() {
        return submission_file;
    }

    public void setSubmission_file(String submission_file) {
        this.submission_file = submission_file;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
