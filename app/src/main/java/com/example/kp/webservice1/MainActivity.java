package com.example.kp.webservice1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity implements Handler.Callback, DowloadClickListener, View.OnClickListener {
    private NetworkRequest networkRequest;
    private final int ERROR_HTTP_CLIENT_TIMEOUT = 1;
    private final int ERROR_IO_EXCEPTION = 2;
    private final int ERROR_UNKNOWN = 3;
    private final int SUCCESS = 4;
    private Handler handler;
    private ProgressDialog progressDialog;
    private ArrayList<Song> songs;
    private RecyclerView mRvSongs;
    private RecyclerView.LayoutManager mLayoutManager;
    private SongAdapter songAdapter;
    private NetworkStatus networkStatus;
    private ImageView mIvRefresh;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inItWidgets();
        inItObjects();
        if(networkStatus.isNetworkAvailable()) {
            startServerCall();
        } else {
            Toast.makeText(this,"There is no Internet available, please check",Toast.LENGTH_LONG).show();
        }
    }

    //
    private void inItObjects() {
        networkRequest = new NetworkRequest();
        handler = new Handler(this);
        songs = new ArrayList<>();
        networkStatus = new NetworkStatus(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("While fetching data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    //
    private void inItWidgets() {
        mLayoutManager = new LinearLayoutManager(this);
        mRvSongs = (RecyclerView) findViewById(R.id.xRvSongs);
        mRvSongs.setLayoutManager(mLayoutManager);
        mRvSongs.setHasFixedSize(true);
        mIvRefresh = (ImageView)findViewById(R.id.xIvRefresh);
        mIvRefresh.setOnClickListener(this);
    }


    //
    private void startServerCall() {
        progressDialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = networkRequest.callServer();
                switch (response){
                    case NetworkRequest.ERROR_HTTP_CLIENT_TIMEOUT: {
                        handler.sendEmptyMessage(ERROR_HTTP_CLIENT_TIMEOUT);
                        break;
                    }
                    case NetworkRequest.ERROR_IO_EXCEPTION: {
                        handler.sendEmptyMessage(ERROR_IO_EXCEPTION);
                        break;
                    }
                    case NetworkRequest.ERROR_UNKNOWN: {
                        handler.sendEmptyMessage(ERROR_UNKNOWN);
                        break;
                    }
                    default: {
                        //Log.e("--",""+response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            songs.removeAll(songs);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Song song = new Song();
                                song.setSongId(jsonArray.getJSONObject(i).getString("id"));
                                song.setSongName(jsonArray.getJSONObject(i).getString("name"));
                                song.setSongArtist(jsonArray.getJSONObject(i).getString("artist"));
                                song.setSongImage(jsonArray.getJSONObject(i).getString("image"));
                                song.setSongDownloadUrl(jsonArray.getJSONObject(i).getString("downloadUrl"));
                                songs.add(song);
                            }
                            handler.sendEmptyMessage(SUCCESS);
                        } catch (Exception e) {
                            handler.sendEmptyMessage(ERROR_UNKNOWN);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    //
    @Override
    public boolean handleMessage(Message message) {
        progressDialog.dismiss();
        switch (message.what) {
            case ERROR_HTTP_CLIENT_TIMEOUT: {
                Toast.makeText(this,"Timeout Error occurred, Please try again",Toast.LENGTH_LONG).show();
                break;
            }
            case ERROR_IO_EXCEPTION: {
                Toast.makeText(this,"IOException Error occurred, Please try again",Toast.LENGTH_LONG).show();
                break;
            }
            case ERROR_UNKNOWN: {
                Toast.makeText(this,"Server Unknown Error occurred, Please try again",Toast.LENGTH_LONG).show();
                break;
            }
            default: {
                if(null == songAdapter) {
                    songAdapter = new SongAdapter(songs, this, this);
                    mRvSongs.setAdapter(songAdapter);
                    break;
                } else {
                    songAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
    }

    @Override
    public void onDownloadClicked(String songId, String downloadUrl) {
        System.out.println("Song Id : "+songId+" : Download URL : "+downloadUrl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xIvRefresh: {
                if(networkStatus.isNetworkAvailable()) {
                    startServerCall();
                } else {
                    Toast.makeText(this,"There is no Internet available, please check",Toast.LENGTH_LONG).show();
                }
              break;
            }
            default: {
                break;
            }
        }
    }
}
