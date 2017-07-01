package com.harpreetdev.demoapp.model;

import android.graphics.Bitmap;

/**
 * Created by Harpreet on 7/1/17.
 */

public class ListItem {

    String trackName;
    String artistName;
    String genre;
    String price;
    String duration;


    String artWorkURl;

    public ListItem(String trackName, String artistName, String genre, String price, String duration ,String artWorkURL) {
        this.trackName  = trackName;
        this.artistName = artistName;
        this.genre      = genre;
        this.price      = price;
        this.duration   = duration;
        this.artWorkURl = artWorkURL;
       }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getArtWorkURl() {
        return artWorkURl;
    }

    public void setArtWorkURl(String artWorkURl) {
        this.artWorkURl = artWorkURl;
    }





}
