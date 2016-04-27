package gbreaker2000.voicecalender;

import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmReminderScreen extends AppCompatActivity {

    public static MediaPlayer alarmSound = new MediaPlayer();
    TextView alarm_info = null;
    FloatingActionButton ars_play_buttton = null;
    Drawable playDrawable = null;
    Drawable stopDrawable = null;
    List<Appointment> looker= new ArrayList<>();
    int indexOfItem = -1;
    FileIO fileio = new FileIO();
    final static int RQS_1 = 1;
    public static AlarmManager alarmManager;

    @Override
    public void onBackPressed() {
        stopReceiver();
        alarmSound.release();
        setNextAlarm();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_reminder_screen);

        Appointment found = alarmRinger();

        alarm_info = (TextView)findViewById(R.id.alarm_info);
        ars_play_buttton = (FloatingActionButton)findViewById(R.id.ars_play_buttton);
        StringBuilder sb = new StringBuilder();
        playDrawable = getResources().getDrawable(R.drawable.alpha_play);
        stopDrawable = getResources().getDrawable(R.drawable.alpha_stop);
        alarmSound = MediaPlayer.create(this, R.raw.morning);

        FileIO read = new FileIO();

        looker= new ArrayList<>();
        looker.clear();
        looker.addAll(read.FileInput());




        for (int i = 0; i<looker.size(); i++)
        {
            looker.get(i).setMilliSec();
            if(looker.get(i).getMilliTime()>System.currentTimeMillis())
            {
                indexOfItem = i;
                break;
            }
        }
        indexOfItem--;
        if(indexOfItem<0)
        {
            indexOfItem = 0;
        }

//        if(found.equals(looker.get(indexOfItem)))
//        {
//            looker.set(indexOfItem, found);
//        }

        try {

            sb.append("Tittle: " + looker.get(indexOfItem).getTittle() + "\n");
            sb.append("Date: " + looker.get(indexOfItem).getStartDate() + "\n");
            sb.append("Time: " + looker.get(indexOfItem).getStartTime() + "\n");
            sb.append("Location: " + looker.get(indexOfItem).getLocation() + "\n");
            sb.append("Notes: " + looker.get(indexOfItem).getNotes() + "\n");
            alarm_info.setText(sb.toString());
//            Toast.makeText(this, "" + looker.get(indexOfItem - 1).getTittle() + "", Toast.LENGTH_LONG).show();
        }


        catch (Exception e)
        {

        }

        alarmSound.start();
        //alarmSound = MediaPlayer.create(arg0,R.raw.morning);

//        if(!looker.get(indexOfItem).isAllDay()) {
//
//            try {
//                alarmSound.setDataSource(getAlarmPath());
//                alarmSound.prepare();
//                alarmSound.start();
//
//
//            } catch (IOException e) {
//                //Log.e(LOG_TAG, "prepare() failed");
//            }
            looker.get(indexOfItem).setAllDay(true);
            read.FileOutput(looker);

//        }
//        while(1==1)
//        {
//            if (!alarmSound.isPlaying())
//            {
//                alarmSound.release();
//                break;
//            }
//        }


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
//        v.vibrate(500);

//        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};

// The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
        vibrator.vibrate(1000);
//        long startAlarmTime = System.currentTimeMillis();
//        long endAlarmTime = startAlarmTime + (30 * 1000);
//
//        while(System.currentTimeMillis()<endAlarmTime)
//        {
//            if(alarmSound.isPlaying())
//            {
//
//            }
//            else {
//                vibrator.cancel();
//            }
//        }

        if(looker.get(indexOfItem).getFileName().equals(""))
        {
            ars_play_buttton.setVisibility(View.GONE);
        }
        setNextAlarm();

    }
    public static void stopReceiver()
    {
        try
        {
            if (alarmSound.isPlaying()) {
                alarmSound.stop();
//            mPlayer.release();
                alarmSound.reset();
            }
        }
        catch (Exception e)
        {

        }
//        alarmSound.release();
//        alarmSound = new MediaPlayer();
            }

    public void stopReceiver(View view) {
        stopReceiver();
        setNextAlarm();
//        Intent intent = new Intent(AlarmReminderScreen.this,MainActivity.class);
//        startActivity(intent);
    }

    public static String getAlarmPath()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/morning.mp3");
    }

    public void receiver_play_button(View view) {
        if(!alarmSound.isPlaying()) {
            alarmSound = new MediaPlayer();
            try {
                alarmSound.setDataSource(looker.get(indexOfItem).getFileName());
                alarmSound.prepare();
                alarmSound.start();

            } catch (IOException e) {
                //Log.e(LOG_TAG, "prepare() failed");
            }
//            ars_play_buttton.setImageDrawable(stopDrawable);
//            playStatus = true;
        }
        else
        {
            alarmSound.reset();
//            alarmSound.release();
//            alarmSound = null;
//            playStatus = false;
//            ars_play_buttton.setImageDrawable(playDrawable);
        }
//        while (true)
//        {
//            if(!alarmSound.isPlaying()){
//                ars_play_buttton.setImageDrawable(playDrawable);
//                break;
//            }
//        }
    }

    public void gotoMain(View view) {
        stopReceiver();
        try {
            alarmSound.release();
        }
        catch (Exception e)
        {

        }

        setNextAlarm();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private Appointment alarmRinger()
    {
        Date today = new Date(System.currentTimeMillis());
        long secToday = today.getTime();
//        int toDay = today.getDate();
//        int toYear = today.getYear();
//        int toMon = today.getMonth();
//        int toHR = today.getHours();
//        int toMin = today.getMinutes();
//        int day,year,month,hour,min;
        long milliMin = 2*60*1000;
        Date secondpass = new Date();
        long secondLong = 0;
        Date test = new Date();
        ArrayList<Appointment> search = new ArrayList<>();
        search.addAll(FileIO.FileInput());
        Appointment found = new Appointment();
        ArrayList<Appointment> possible = new ArrayList<>();
        for (int i = 0; i<search.size(); i++)
        {
            found = search.get(i);
            test = found.dateObjGet();
            if ((test.getTime()-(32*60*1000))>today.getTime() && (test.getTime()+(2*60*1000))>today.getTime())
            {
                possible.add(found);
            }
        }
        if (possible.size()<1)
        {
            return search.get(0);
        }
        if (possible.size() == 1)
        {
            return possible.get(0);
        }
        else
        {
            for (int i = 0; i< possible.size(); i++)
            {
                secondpass = possible.get(i).dateObjGet();
                secondLong = secondpass.getTime();


                if ((secondLong - milliMin) < secToday && (secondLong + milliMin) > secToday)
                {
                    return possible.get(i);
                }
                if ((secondLong - milliMin) < (secToday + (5*60*1000)) && (secondLong + milliMin) > (secToday + (5*60*1000)))
                {
                    return possible.get(i);
                }
                else if ((secondLong - milliMin) < (secToday + (10*60*1000)) && (secondLong + milliMin) > (secToday + (10*60*1000)))
                {
                    return possible.get(i);
                }
                else if ((secondLong - milliMin) < (secToday + (15*60*1000)) && (secondLong + milliMin) > (secToday + (15*60*1000)))
                {
                    return possible.get(i);
                }

                else if ((secondLong - milliMin) < (secToday + (30*60*1000)) && (secondLong + milliMin) > (secToday + (30*60*1000)))
                {
                    return possible.get(i);
                }
                else
                {
                    return search.get(0);
                }

            }
        }
        return search.get(0);

    }
    public void setNextAlarm() {
//        long currentTime = System.currentTimeMillis();
//        if (targetCal < currentTime && targetCal > 0) {
//            //do Nothing
//            return;
//        } else {
        long targetCal = 6;
        List<Long> alarms = new ArrayList<Long>();
        alarms.clear();
        alarms.addAll(FileIO.AlarmSaveIn());
        alarms.add(targetCal);
        FileIO.AlarmSaveOut(alarms);
        alarms.clear();
        alarms.addAll(FileIO.AlarmSaveIn());


//            info.setText("\n\n***\n"
//                    + "Alarm is set@ " + targetCal.getTime() + "\n"
//                    + "***\n");  getBaseContext()
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarms.get(0), pendingIntent);
    }
    }
