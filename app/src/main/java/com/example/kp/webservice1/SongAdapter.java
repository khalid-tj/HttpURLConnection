package com.example.kp.webservice1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Techjini on 9/18/2016.
 */
public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Song> songs;
    private Context context;
    private DowloadClickListener dowloadClickListener;

    //
    public SongAdapter(ArrayList<Song> paramSongs, Context paramContext, DowloadClickListener paramDowloadClickListener)  {
        this.songs = paramSongs;
        this.context = paramContext;
        this.dowloadClickListener = paramDowloadClickListener;
    }

    //
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolderSongs(view);
    }

    //
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolderSongs viewHolderSongs = (ViewHolderSongs) holder;
        viewHolderSongs.getSongName().setText(songs.get(position).getSongName());
        viewHolderSongs.getSongArtist().setText(songs.get(position).getSongArtist());
        Picasso.with(context).load(songs.get(position).getSongImage()).into(viewHolderSongs.getSongImage());
        viewHolderSongs.getDownloadSong().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dowloadClickListener.onDownloadClicked(songs.get(position).getSongId(),songs.get(position).getSongDownloadUrl());
            }
        });
    }

    //
    @Override
    public int getItemCount() {
        return songs.size();
    }
}
