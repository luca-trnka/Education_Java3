package org.example.model;

import java.util.List;

public class Game {
    private String title;
    private int releaseYear;
    private List<String> developers;
    private List<String> publishers;
    private List<String> genres;

    public Game(String title, int releaseYear, List<String> developers, List<String> publishers, List<String> genres) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.developers = developers;
        this.publishers = publishers;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<String> developers) {
        this.developers = developers;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
