package gbreaker2000.voicecalender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        FileIO read = new FileIO();

        List<Appointment> looker= new ArrayList<>();
        looker.clear();
        looker.addAll(read.FileInput());


        int indexOfItem = 0;
        for (int i = 0; i<looker.size(); i++)
        {
            if(looker.get(i).getMilliTime()>System.currentTimeMillis())
            {
                indexOfItem = i;
                break;
            }
        }

        Toast.makeText(arg0, "" + looker.get(indexOfItem-1).getTittle() + "", Toast.LENGTH_LONG).show();

        MediaPlayer alarmSound = new MediaPlayer();

        try {
            alarmSound.setDataSource(getAlarmPath());
            alarmSound.prepare();
            alarmSound.start();

        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }
//        while(1==1)
//        {
//            if (!alarmSound.isPlaying())
//            {
//                alarmSound.release();
//                break;
//            }
//        }
    }

    private static String getAlarmPath()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/alarmsiren.mp3");
    }

    public String getCurrentAlarm()
    {
        int indexOfNext = 0;
        for (int i=0; i < MainActivity.appdata.size(); i++)
        {
            if (MainActivity.appdata.get(i).getMilliTime() > System.currentTimeMillis())
            {
                indexOfNext = i;
            }
        }
        if (indexOfNext<1)
        {
            return "Alarm Time!!!";
        }
        else
        {
            return MainActivity.appdata.get(indexOfNext-1).getTittle();
        }
    }
    public int getCurrentAlarmIndex()
    {
        int indexOfNext = 0;
        for (int i=0; i < MainActivity.appdata.size(); i++)
        {
            if (MainActivity.appdata.get(i).getMilliTime() > System.currentTimeMillis())
            {
                indexOfNext = i;
            }
        }
        return indexOfNext;
    }


}