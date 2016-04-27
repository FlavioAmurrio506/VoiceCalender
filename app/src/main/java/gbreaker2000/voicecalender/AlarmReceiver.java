package gbreaker2000.voicecalender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context arg0, Intent arg1) {

               Intent intent = new Intent(arg0, AlarmReminderScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(intent);
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