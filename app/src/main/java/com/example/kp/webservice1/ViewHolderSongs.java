package com.example.kp.webservice1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Techjini on 9/18/2016.
 */
public class ViewHolderSongs extends RecyclerView.ViewHolder{
    private ImageView songImage, downloadSong;
    private TextView songName, songArtist;

    public ViewHolderSongs(View view) {
        super(view);
        songImage = (ImageView)view.findViewById(R.id.xIvSongImage);
        downloadSong = (ImageView)view.findViewById(R.id.xIvDownload);
        songName = (TextView)view.findViewById(R.id.xTvSongName);
        songArtist = (TextView)view.findViewById(R.id.xTvSongArtist);
    }

    public ImageView getSongImage() {
        return songImage;
    }

    public void setSongImage(ImageView songImage) {
        this.songImage = songImage;
    }

    public ImageView getDownloadSong() {
        return downloadSong;
    }

    public void setDownloadSong(ImageView downloadSong) {
        this.downloadSong = downloadSong;
    }

    public TextView getSongName() {
        return songName;
    }

    public void setSongName(TextView songName) {
        this.songName = songName;
    }

    public TextView getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(TextView songArtist) {
        this.songArtist = songArtist;
    }


}
