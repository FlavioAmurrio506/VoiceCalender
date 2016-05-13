package gbreaker2000.voicecalender;

import android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Date;
import java.util.List;

/**
 * Created by Flavi on 5/13/2016.
 */
public class AlertReceiverNotification extends BroadcastReceiver {

    // Called when a broadcast is made targeting this class
    @Override
    public void onReceive(Context context, Intent intent) {

        Date today = new Date();
        int month = today.getMonth() + 1;
        int day = today.getDate();
        int year = today.getYear() + 1900;
        List<Appointment> dayApt = Appointment.DayAppointments(month + "/" + day + "/" + year);

        StringBuilder sb = new StringBuilder();
        if (dayApt.size() == 0)
        {
            sb.append("No Data");
        }
        else
        {
            for(int i = 0; i<dayApt.size(); i++)
            {
                sb.append(dayApt.get(i).getTittle() + " " + dayApt.get(i).getStartTime() + "\n");
            }
        }


        createNotification(context, "Appointments for Today", sb.toString(), "Alert");

    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        // Define an Intent and an action to perform with it by another application
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        // Builds a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        // Defines the Intent to fire when the notification is clicked
        mBuilder.setContentIntent(notificIntent);

        // Set the default notification option
        // DEFAULT_SOUND : Make sound
        // DEFAULT_VIBRATE : Vibrate
        // DEFAULT_LIGHTS : Use the default light notification
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        // Auto cancels the notification when clicked on in the task bar
        mBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        mNotificationManager.notify(1, mBuilder.build());

    }
}