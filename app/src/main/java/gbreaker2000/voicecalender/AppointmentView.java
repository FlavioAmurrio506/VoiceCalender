package gbreaker2000.voicecalender;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AppointmentView extends AppCompatActivity {


    Button app_but1, app_but2, app_but3, app_but4, app_but5, app_but6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view);
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

        app_but1 = (Button) findViewById(R.id.app_but1);
        app_but2 = (Button) findViewById(R.id.app_but2);
        app_but3 = (Button) findViewById(R.id.app_but3);
        app_but4 = (Button) findViewById(R.id.app_but4);
        app_but5 = (Button) findViewById(R.id.app_but5);
        app_but6 = (Button) findViewById(R.id.app_but6);
        int indexOfNext = 0;
        for (int i=0; i < MainActivity.appdata.size(); i++)
        {
            if (MainActivity.appdata.get(i).getMilliTime() > System.currentTimeMillis())
            {
                indexOfNext = i;
                break;
            }
        }

        try {
            app_but1.setText(MainActivity.appdata.get(indexOfNext + 0).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 0).getCurDate().toString());
            app_but2.setText(MainActivity.appdata.get(indexOfNext + 1).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 1).getCurDate().toString());
            app_but3.setText(MainActivity.appdata.get(indexOfNext + 2).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 2).getCurDate().toString());
            app_but4.setText(MainActivity.appdata.get(indexOfNext + 3).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 3).getCurDate().toString());
            app_but5.setText(MainActivity.appdata.get(indexOfNext + 4).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 4).getCurDate().toString());
            app_but6.setText(MainActivity.appdata.get(indexOfNext + 5).getTittle() + "\n" + MainActivity.appdata.get(indexOfNext + 5).getCurDate().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
