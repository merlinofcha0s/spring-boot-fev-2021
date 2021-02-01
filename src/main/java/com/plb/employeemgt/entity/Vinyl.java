package com.plb.employeemgt.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vinyl")
public class Vinyl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vinylSequenceGenerator")
    @SequenceGenerator(name = "vinylSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "songName", nullable = false)
    private String songName;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @ManyToOne
    private User user;

    public Vinyl() {
    }

    public Vinyl(String songName, LocalDate releaseDate, User user) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Objects.equals(songName, vinyl.songName) && Objects.equals(releaseDate, vinyl.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songName, releaseDate);
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
