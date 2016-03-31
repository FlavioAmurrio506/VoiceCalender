package gbreaker2000.voicecalender;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MakeAppointment extends AppCompatActivity {

    public String PATH_NAME = "";

    //String message = getIntent().getStringExtra(MainActivity.date);
    EditText tittle, appointment_start, appointment_end, reminder, location, file_location;
    Switch all_day;
    Button start_button, stop_button, play_button, cancel_button, save_button;

    //Recorder Variables
    private MediaRecorder recorder = null;
    MediaPlayer mPlayer = null;
    //private int curFormat = 0;
    //private String fileExt[] = {".mp4", ".3gpp"};
    //private int opFormats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tittle = (EditText)findViewById(R.id.tittle);
        appointment_start = (EditText)findViewById(R.id.appointment_start);
        appointment_end = (EditText)findViewById(R.id.appointment_end);
        reminder = (EditText)findViewById(R.id.reminder);
        location = (EditText)findViewById(R.id.location);
        all_day = (Switch)findViewById(R.id.all_day);
        start_button = (Button)findViewById(R.id.start_button);
        stop_button = (Button)findViewById(R.id.stop_button);
        play_button = (Button)findViewById(R.id.play_button);
        save_button = (Button)findViewById(R.id.save_button);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        file_location = (EditText)findViewById(R.id.file_location);
        //appointment_start.setText(MainActivity.getDate());
    }

    public void cancelAppoint(View view) {
        Toast.makeText(getApplicationContext(),PATH_NAME, Toast.LENGTH_LONG).show();
        tittle.setText("Cancel Button");

    }

    public void saveAppoint(View view) {
        Toast.makeText(getApplicationContext(),"Save Button", Toast.LENGTH_LONG).show();
    }

    public void startRecording(View view) {
        //Toast.makeText(getApplicationContext(),"Recording", Toast.LENGTH_LONG).show();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        PATH_NAME = getFilePath();
        recorder.setOutputFile(PATH_NAME);
        try{
            recorder.prepare();
            recorder.start();   // Recording is now started
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        /*
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(getFilePath());
        try
        {
            recorder.prepare();
            recorder.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //Toast.makeText(getApplicationContext(), "Start Button", Toast.LENGTH_LONG).show();
    }

    public void stopRecording(View view) {

        if (recorder != null) {
            recorder.stop();
            recorder.reset();   // You can reuse the object by going back to setAudioSource() step
            // recorder.release(); // Now the object cannot be reused


        /*
        if(recorder!=null)
        {
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
        Toast.makeText(getApplicationContext(),"Stop Button", Toast.LENGTH_LONG).show();
        */
        }
        file_location.setText(PATH_NAME);
    }

    public void startPlay(View view) {
        //Toast.makeText(getApplicationContext(),"Play Button", Toast.LENGTH_LONG).show();

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(PATH_NAME);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }
    }

    //Recorder Helper Methods
    private static String getFilePath()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
    }

    public void stopPlay(View view) {
        mPlayer.release();
        mPlayer = null;
    }
}
