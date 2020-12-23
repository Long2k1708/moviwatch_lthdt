package com.princefrog2k.lthdt.moviwatch.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmItem implements Serializable {
    ArrayList<String> category, country;
    long minutesLength, releaseTime, viewCount;
    String description, trailerUrl, movieUrl, posterUrl, title, actor, director;

    public FilmItem() {
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public ArrayList<String> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<String> country) {
        this.country = country;
    }

    public long getMinutesLength() {
        return minutesLength;
    }

    public void setMinutesLength(long minutesLength) {
        this.minutesLength = minutesLength;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @NonNull
    @Override
    public String toString() {
        return category.toString() + country.toString() + minutesLength + releaseTime + viewCount + description + trailerUrl + movieUrl + posterUrl + title + actor + director;
    }
}
