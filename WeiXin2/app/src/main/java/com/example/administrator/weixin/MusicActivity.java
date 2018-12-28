package com.example.administrator.weixin;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MusicActivity extends AppCompatActivity {
    private Button start_stop;
    private boolean start;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                start_stop.setBackground(getResources().getDrawable(R.drawable.start));
            }
        });

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!start){
                    start_stop.setBackground(getResources().getDrawable(R.drawable.stop));
                    mPlayer.start();
                }else{
                    start_stop.setBackground(getResources().getDrawable(R.drawable.start));
                    mPlayer.pause();
                }
                start = !start;
            }
        });

    }

    private void init(){
        this.start_stop = (Button)findViewById(R.id.start_stop);
        this.start = false;
        this.mPlayer = MediaPlayer.create(this,R.raw.huluwa);
    }
}
