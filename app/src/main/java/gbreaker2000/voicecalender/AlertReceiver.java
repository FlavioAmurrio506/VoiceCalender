package gbreaker2000.voicecalender;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;

public class AlertReceiver extends AppCompatActivity {

    MediaPlayer mPlayer = MediaPlayer.create(this,R.raw.alarmsiren);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_receiver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mPlayer = new MediaPlayer();
        try {
            //mPlayer.setDataSource();
            mPlayer.prepare();
            mPlayer.start();

        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }
    }

}
