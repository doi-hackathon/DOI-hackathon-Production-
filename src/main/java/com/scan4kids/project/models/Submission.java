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
    private String fileName;

    @Column
    @Lob
    private byte[] file;

    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    private List<Score> scores;

    //Need to look into many to many relationship with judges

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="submission_judges",
            joinColumns={@JoinColumn(name="submission_id")},
            inverseJoinColumns={@JoinColumn(name="judge_id")}
    )
    private List<User> judges;

    public Submission() {
    }

    public Submission(long id, String fileName, byte[] file, User owner, List<Score> scores, List<User> judges) {
        this.id = id;
        this.fileName = fileName;
        this.file = file;
        this.owner = owner;
        this.scores = scores;
        this.judges = judges;
    }

    public Submission(String fileName, byte[] file, User owner, List<Score> scores, List<User> judges) {
        this.fileName = fileName;
        this.file = file;
        this.owner = owner;
        this.scores = scores;
        this.judges = judges;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
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

    public List<User> getJudges() {
        return judges;
    }

    public void setJudges(List<User> judges) {
        this.judges = judges;
    }
}
