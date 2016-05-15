package gbreaker2000.voicecalender;

import android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

/**
 * Created by Flavi on 5/13/2016.
 */

// This class handles the notifications
public class AlertReceiverNotification extends BroadcastReceiver {

    // Called when a broadcast is made targeting this class
    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Intent intent = new Intent(arg0, NotiActivator.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(intent);
    }

//        displayNotification(context);

//        Toast.makeText(context,"Receive",Toast.LENGTH_LONG).show();
//        Date today = new Date();
//        int month = today.getMonth() + 1;
//        int day = today.getDate();
//        int year = today.getYear() + 1900;
//        List<Appointment> dayApt = Appointment.DayAppointments(month + "/" + day + "/" + year);
//
//        StringBuilder sb = new StringBuilder();
//        if (dayApt.size() == 0)
//        {
//            sb.append("No Data");
//        }
//        else
//        {
//            for(int i = 0; i<dayApt.size(); i++)
//            {
//                sb.append(dayApt.get(i).getTittle() + " " + dayApt.get(i).getStartTime() + "\n");
//            }
//        }


//        createNotification(context, "Appointments for Today", "test" , "Alert");



    public void displayNotification(Context context)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.alpha_mic);
        builder.setContentTitle("My Notification");
        builder.setContentText("This is my Notification...");
        Intent intent = new Intent(null, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0, builder.build());
    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        // Define an Intent and an action to perform with it by another application
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        // Builds a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.alpha_play)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        // Defines the Intent to fire when the notification is clicked
        mBuilder.setContentIntent(notificIntent);

        // Set the default notification option
        // DEFAULT_SOUND : Make sound
        // DEFAULT_VIBRATE : Vibrate
        // DEFAULT_LIGHTS : Use the default light notification
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

        // Auto cancels the notification when clicked on in the task bar
        mBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        mNotificationManager.notify(1, mBuilder.build());

    }
}