package com.pochworld.project.imdb;

public class Movie {

    private String title;

    private String id;

    private String poster;

    private String year;

    private String genre;

    private String plot;

    public Movie(String id, String title, String poster, String year, String genre, String plot) {
        this.setTitle(title);
        this.setId(id);
        this.setPoster(poster);
        this.setYear(year);
        this.setGenre(genre);
        this.setPlot(plot);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
