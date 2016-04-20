package gbreaker2000.voicecalender;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MakeAppointment extends AppCompatActivity {

    public String PATH_NAME = "";

    //String message = getIntent().getStringExtra(MainActivity.date);
    EditText tittle, appointment_start, appointment_end, reminder, location, file_location;
    Switch all_day;
    Button start_button, stop_button, play_button, cancel_button, save_button;

    //Recorder Variables
    private MediaRecorder recorder = new MediaRecorder();
    MediaPlayer mPlayer = new MediaPlayer();
    boolean recStatus = false;
    FloatingActionButton rec_float_button_make;
    FloatingActionButton play_float_button_make;
    FloatingActionButton stop_image;
    FloatingActionButton stop_rec_image;
    boolean playStatus = false;
    Drawable stopDrawable = null;
    Drawable playDrawable = null;
    Drawable recDrawable = null;
    Drawable stopRecDrawable = null;
    Appointment[] appointment = new Appointment[100];
    public static int appointmentIndex = 0;
    //private int curFormat = 0;
    //private String fileExt[] = {".mp4", ".3gpp"};
    //private int opFormats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent activityThatCalled = getIntent();
        String previousActivity = activityThatCalled.getExtras().getString("PATH_NAME_MAIN");
        file_location = (EditText)findViewById(R.id.file_location);
        file_location.setText(previousActivity);
        PATH_NAME = previousActivity;


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tittle = (EditText)findViewById(R.id.tittle);
        appointment_start = (EditText)findViewById(R.id.appointment_start);
        appointment_end = (EditText)findViewById(R.id.appointment_end);
        reminder = (EditText)findViewById(R.id.reminder);
        location = (EditText)findViewById(R.id.location);
        all_day = (Switch)findViewById(R.id.all_day);
//        start_button = (Button)findViewById(R.id.start_button);
//        stop_button = (Button)findViewById(R.id.stop_button);
//        play_button = (Button)findViewById(R.id.play_button);
        save_button = (Button)findViewById(R.id.save_button);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        rec_float_button_make = (FloatingActionButton)findViewById(R.id.rec_float_button_make);
        play_float_button_make = (FloatingActionButton)findViewById(R.id.play_float_button_make);
        stop_image = (FloatingActionButton)findViewById(R.id.stop_image);
        stopDrawable = stop_image.getDrawable();
        playDrawable = play_float_button_make.getDrawable();
        recDrawable = rec_float_button_make.getDrawable();
        stop_rec_image = (FloatingActionButton)findViewById(R.id.stop_rec_image);
        stopRecDrawable = stop_rec_image.getDrawable();
        rec_float_button_make.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
        play_float_button_make.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));



        //appointment_start.setText(MainActivity.getDate());
    }

    public void cancelAppoint(View view) {
        finish();
//        Intent cancel = new Intent(this, MakeAppointment.class);
//        startActivity(cancel);
//        Toast.makeText(getApplicationContext(),PATH_NAME, Toast.LENGTH_LONG).show();
//        tittle.setText("Cancel Button");

    }

    public void saveAppoint(View view) {
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;
        Intent alertIntent = new Intent(this, AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                PendingIntent.getBroadcast(this, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));



        appointment[appointmentIndex] = new Appointment();
        appointment[appointmentIndex].setTittle(tittle.getText().toString());
        appointment[appointmentIndex].setStartDate(appointment_start.getText().toString());
        appointment[appointmentIndex].setStartTime(appointment_start.getText().toString());
        appointment[appointmentIndex].setEndDate(appointment_end.getText().toString());
        appointment[appointmentIndex].setEndTime(appointment_end.getText().toString());
        appointment[appointmentIndex].setAllDay(all_day.isChecked());
        appointment[appointmentIndex].setFileName(PATH_NAME);
        appointment[appointmentIndex].setLocation(location.getText().toString());
        Intent save = new Intent(this,MainActivity.class);
        this.appointmentIndex++;
        startActivity(save);
        Toast.makeText(getApplicationContext(),"Save Button " + appointmentIndex, Toast.LENGTH_LONG).show();
    }

    public void startRecording(View view) {
        //Toast.makeText(getApplicationContext(),"Recording", Toast.LENGTH_LONG).show();

        if (!recStatus) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            PATH_NAME = getFilePath();
            recorder.setOutputFile(PATH_NAME);
            try {

                recorder.prepare();
                recorder.start();   // Recording is now started


//              Toast.makeText(this,"Recording",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            recStatus = true;
            rec_float_button_make.setBackgroundTintList(ColorStateList.valueOf(0xffff0000));
//            rec_float_button_make.setImageDrawable(stopRecDrawable);
//            start_button.setText("Stop Rec");
        }
        else
        {
            recorder.stop();
            recorder.reset();
//            start_button.setText("Rec");
            recStatus = false;
            rec_float_button_make.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
//            rec_float_button_make.setImageDrawable(recDrawable);
                    file_location.setText(PATH_NAME);
//            Toast.makeText(this,"Recording Stopped",Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,PATH_NAME,Toast.LENGTH_SHORT).show();
        }


//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        PATH_NAME = getFilePath();
//        recorder.setOutputFile(PATH_NAME);
//        try{
//            recorder.prepare();
//            recorder.start();   // Recording is now started
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }

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

//        mPlayer = new MediaPlayer();

        if(!playStatus) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(PATH_NAME);
                mPlayer.prepare();
                mPlayer.start();

            } catch (IOException e) {
                //Log.e(LOG_TAG, "prepare() failed");
            }
            play_float_button_make.setImageDrawable(stopDrawable);
            playStatus = true;
        }
        else
        {
            mPlayer.release();
            mPlayer = null;
            playStatus = false;
            play_float_button_make.setImageDrawable(playDrawable);
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
