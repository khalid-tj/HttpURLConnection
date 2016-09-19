package com.example.kp.webservice1;

/**
 * Created by Techjini on 9/18/2016.
 */
public class Song {
    private String songId,songName,songImage,songArtist,songDownloadUrl;


    //
    public String getSongId() {
        return songId;
    }


    //
    public void setSongId(String songId) {
        this.songId = songId;
    }


    //
    public String getSongName() {
        return songName;
    }


    //
    public void setSongName(String songName) {
        this.songName = songName;
    }


    //
    public String getSongDownloadUrl() {
        return songDownloadUrl;
    }


    //
    public void setSongDownloadUrl(String songDownloadUrl) {
        this.songDownloadUrl = songDownloadUrl;
    }


    //
    public String getSongArtist() {
        return songArtist;
    }


    //
    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }


    //
    public String getSongImage() {
        return songImage;
    }


    //
    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }
}
