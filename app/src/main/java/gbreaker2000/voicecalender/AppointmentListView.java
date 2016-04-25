package gbreaker2000.voicecalender;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AppointmentListView extends AppCompatActivity {

    MediaPlayer mPlayer = new MediaPlayer();
//    Drawable stopDrawable = getResources().getDrawable( R.drawable.alpha_stop );
//    Drawable playDrawable = getResources().getDrawable( R.drawable.alpha_play );
//    FloatingActionButton stop_image;
//    FloatingActionButton play_float_button_make;
//    FloatingActionButton temp_float_button;
//    Drawable playDrawable = null;
//    Drawable stopDrawable = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list_view);
//        temp_float_button = (FloatingActionButton)findViewById(R.id.temp_float_button);
//        stop_image = (FloatingActionButton)findViewById(R.id.stop_image);
//        play_float_button_make = (FloatingActionButton)findViewById(R.id.play_float_button_make);
//        alp_stop = (FloatingActionButton)findViewById(R.id.alp_stop);
//
//        stopDrawable = stop_image.getDrawable();
//        playDrawable = play_float_button_make.getDrawable();


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
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FileIO read = new FileIO();
        final ArrayList<Appointment> aptdata = new ArrayList<>();
        aptdata.clear();
        aptdata.addAll(read.FileInput());

        String[] aptArray = toArray(aptdata);

        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.mytextview,aptArray);

        ListView theListView = (ListView) findViewById(R.id.theListView);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                StopPlayer();

                playAudio(aptdata.get(i).getFileName());
                //Toast.makeText(AppointmentListView.this, aptdata.get(i).getCurDate().toString().substring(0,16), Toast.LENGTH_SHORT).show();

//                while(mPlayer.isPlaying())
//                {
//
//                }
//                mPlayer.release();
//                mPlayer = null;
                //alp_stop.setImageDrawable(playDrawable);

                //String tvShowPicked = "You selected " + String.valueOf(adapterView.getItemAtPosition(i));

                //Toast.makeText(AppointmentListView.this, tvShowPicked, Toast.LENGTH_SHORT).show();
            }
        });



            }

    private static String[] toArray(ArrayList<Appointment> tempList)
    {
        String[] bar = new String[tempList.size()];
        for (int i = 0; i<tempList.size() ; i++)
        {
            if (tempList.get(i).getFileName().equals(""))
            {
                bar[i] = tempList.get(i).getTittle() + tempList.get(i).shortStringDate();
            }
            else
            {
                bar[i] = tempList.get(i).getTittle() + tempList.get(i).shortStringDate() + " (REC)" ;
            }

        }
        return bar;
    }

    private void playAudio(String filePath)
    {

        if (filePath.equals(""))
        {
            Toast.makeText(this,"No Recording",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                mPlayer.setDataSource(filePath);
                mPlayer.prepare();
                mPlayer.start();
                Toast.makeText(this,"Playing Recording",Toast.LENGTH_SHORT).show();
//                temp_float_button.setImageDrawable(stopDrawable);

            } catch (IOException e) {
                //Log.e(LOG_TAG, "prepare() failed");
            }
        }
    }

    public void StopPlayer() {

        mPlayer.release();
        mPlayer = new MediaPlayer();

//        temp_float_button.setImageDrawable(playDrawable);


    }

    public void StopPlayerNow(View view) {
        StopPlayer();
        Toast.makeText(this,"Playback Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mPlayer.isPlaying())
        {
            StopPlayer();
            Toast.makeText(this,"Playback Stopped", Toast.LENGTH_SHORT).show();
        }
        else
        {
            super.onBackPressed();
        }
    }
}