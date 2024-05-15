package org.example.model;

import java.util.List;

public class Game {
    private String title;
    private int releaseYear;
    private String developer;
    private String publisher;
    private List<String> genres;

    public Game(String title, int releaseYear, String developer, String publisher, List<String> genres) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.developer = developer;
        this.publisher = publisher;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
