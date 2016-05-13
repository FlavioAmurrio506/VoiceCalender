package gbreaker2000.voicecalender;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //CalendarView calendar;
    EditText tittle;
    //String date;
    DatePicker date_picker;
    private MediaRecorder recorder = new MediaRecorder();
    public String PATH_NAME = "";
    FloatingActionButton rec_float_button;
    FloatingActionButton today_button;
    FloatingActionButton add_float_Button;
    boolean recStatus = false;
    NotificationManager notificationManager;
    int notifID = 33;
    boolean isNotificActive = false;
    FileIO read = new FileIO();
    public static List<Appointment> appdata = new ArrayList<>();
    final static int RQS_1 = 1;
    public static AlarmManager alarmManager;
    TextView upcoming_events;
    String maybe = "";
//    TextView calendar_date_display;
//    String monthTextView;--------------

    MediaPlayer mPlayer = new MediaPlayer();
    TextView listViewTittle = null;
    public static HashSet<Date> Mainevents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //upcoming_events = (TextView)findViewById(R.id.upcoming_events);
        rec_float_button = (FloatingActionButton)findViewById(R.id.rec_float_button);
        today_button = (FloatingActionButton)findViewById(R.id.today_button);
        add_float_Button = (FloatingActionButton)findViewById(R.id.add_float_button);
         rec_float_button.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
        today_button.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
        add_float_Button.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
        listViewTittle = (TextView)findViewById(R.id.listViewTittle);
//--------------------        calendar_date_display = (TextView) findViewById(R.id.calendar_date_display);



        try
        {
            setNextAlarm();
        }
        catch (Exception e)
        {

        }

        StringBuilder sb = new StringBuilder();
        //sb.append("Upcoming Events \n");


        appdata.clear();

        appdata.addAll(read.FileInput());
        //Toast.makeText(this,("File Read" + appdata.size() + ""),Toast.LENGTH_SHORT).show();
        Date actualTime = new Date(System.currentTimeMillis());
        //Toast.makeText(this,"Current Time" + actualTime.toString(),Toast.LENGTH_SHORT).show();

        int indexOfNext = 0;
        int maxOfLines = 5;
        for (int i=0; i < appdata.size(); i++)
        {
            if (appdata.get(i).getMilliTime() > System.currentTimeMillis())
            {
                indexOfNext = i;
                break;
            }
        }

        for (int i = indexOfNext; i < appdata.size(); i++)
        {
            sb.append(appdata.get(i).getTittle() + " - " + appdata.get(i).getCurDate().toString().substring(0,10) + "\n");
            maxOfLines--;
            if (maxOfLines == 0)
            {
                break;
            }
        }

//        upcoming_events.setText(sb.toString());


        final HashSet<Date> events = new HashSet<>();
        events.add(new Date());
//        Mainevents = new HashSet<Date>();
//        Mainevents.addAll(events);

        for(int i = 0; i<appdata.size(); i++)
        {
            events.add(appdata.get(i).getCurDate());
        }


        final CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(FileIO.HashInput());
        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();

                String tempMaybe = df.format(date);

                maybe = dateExtractor(tempMaybe);

//                Toast.makeText(MainActivity.this, dateExtractor(tempMaybe), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(MainActivity.this, MakeAppointment.class);
//                intent.putExtra("MAYBE_MAIN",maybe);
//                intent.putExtra("PATH_NAME_MAIN",PATH_NAME);
//                startActivity(intent);
                ArrayList<Appointment> selDayApt = new ArrayList<>();
                selDayApt.addAll(Appointment.DayAppointments(maybe));

                try {
                    ListViewModifier(selDayApt);
                }
                catch (Exception e)
                {

                }



            }
        });

//       -------------------------------------- monthTextView = calendar_date_display.getText().toString();

//         + "   " + year  df.format(date)month + "/" + day + "/" + year
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //date_picker = (Calendar)findViewById(R.id.calendar);


        //addAppoint_button = (Button)findViewById(R.id.addAppoint_button);
        //tittle = (EditText)findViewById(R.id.tittle);


//        calendar = (CalendarView) findViewById(R.id.calendar);
//        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
//                //date = dayOfMonth + "/" + month + "/" + year;
//
//
//            }
//        });


        //--------New Stuff----
        try {
            ListViewModifier((ArrayList<Appointment>) Appointment.UpComingAppointments());
        }
        catch (Exception e)
        {

        }
//        ListViewModifier((ArrayList<Appointment>) appdata);

//        FileIO read = new FileIO();
//        final ArrayList<Appointment> aptdata = new ArrayList<>();
//        aptdata.clear();
//        aptdata.addAll(read.FileInput());
//
//        String[] aptArray = toArray(aptdata);
//
//        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.mytextview,aptArray);
//
//        ListView smallTheListView = (ListView) findViewById(R.id.smallTheListView);
//
//        smallTheListView.setAdapter(theAdapter);
//
//        smallTheListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                StopPlayer();
//
//                playAudio(aptdata.get(i).getFileName());
//            }
//        });
//        calendar_date_display.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String a = calendar_date_display.getText().toString();
//                if (!a.equals(monthTextView))
//                {
//                    cv.updateCalendar();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }



    public void ListViewModifier(final ArrayList<Appointment> tApt) throws Exception
    {
        listViewTittle.setText(maybe);
        String[] aptArray = toArray(tApt);
        final int otherPostion;

        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.mytextview,aptArray);

        final ListView smallTheListView = (ListView) findViewById(R.id.smallTheListView);

        smallTheListView.setAdapter(theAdapter);
//        Toast.makeText(this."hello",Toast.LENGTH_LONG).show();

        smallTheListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try
                {
                    if(mPlayer.isPlaying())
                    {
                        StopPlayer();
                    }
                    else{
                        playAudio(tApt.get(i).getFileName());
                    }
                }
                catch (Exception e)
                {
//                    Toast.makeText(MainActivity.this,"Slow Down, Old device detected",Toast.LENGTH_SHORT).show();
//                    Intent crash = new Intent(MainActivity.this, MainActivity.class);
//                    startActivity(crash);
                }
            }
        });

        smallTheListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//            Toast.makeText(MainActivity.this,tApt.get(position).getTittle(),Toast.LENGTH_LONG).show();
            DialogFragment myFragment = MyDialogFragment.newInstance(tApt.get(position).getMilliTime());

            myFragment.show(getSupportFragmentManager(), "hello");

//            smallTheListView.getItemAtPosition(position) + "");
            return true;
        }

    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addAppointmentMethod(View view) {
        try {
//            recorder.reset();
            recorder.release();
            Intent intent = new Intent(this, MakeAppointment.class);
            intent.putExtra("MAYBE_MAIN",maybe);
            intent.putExtra("PATH_NAME_MAIN",PATH_NAME);
            startActivity(intent);
        }
        catch (Exception e)
        {

        }


    }

    public void quickRec(View view) {
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
                recStatus = true;
                Toast.makeText(this,"Recording",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rec_float_button.setBackgroundTintList(ColorStateList.valueOf(0xffff0000));
        }
        else
        {
            try
            {
                recorder.stop();
                recorder.reset();
            }
            catch (Exception e)
            {

            }
            rec_float_button.setBackgroundTintList(ColorStateList.valueOf(0xff0000ff));
            recStatus = false;
            recorder.release();
            Toast.makeText(this,"Recording Stopped",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,PATH_NAME,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MakeAppointment.class);
            intent.putExtra("PATH_NAME_MAIN", PATH_NAME);
            intent.putExtra("MAYBE_MAIN",maybe);
            startActivity(intent);
        }
        
//        Intent intent = new Intent(this, AudioRecordTest.class);
//        startActivity(intent);
    }


//    public void recScreenMethod(View view) {
//        Intent intent = new Intent(this, AudioRecordTest.class);
//        startActivity(intent);
//    }

//    public void addAppointmentMethod(View view) {
//        Intent intent = new Intent(this, MakeAppointment.class);
//        startActivity(intent);
//    }
//
//    public void toastDP(View view) {
//        Toast.makeText(this, (date_picker.getMonth() + 1) + "/" + date_picker.getDayOfMonth() + "/" + date_picker.getYear(),Toast.LENGTH_SHORT).show();
//    }
    private static String getFilePath()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
    }

    public void showCurrentDate(View view) {

        mPlayer.release();
        recorder.release();


        Intent today = new Intent(this,AppointmentListView.class);
        startActivity(today);
        //Toast.makeText(this,"Current Date",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
//        System.exit(0);
    }

    public static String dateExtractor(String datExc)
    {
        datExc = datExc.replace(", ", " ");
        String[] spl = datExc.split(" ");
        int year = Integer.parseInt(spl[2]);
        int day = Integer.parseInt(spl[1]);
        int month = 0;
        spl[0] = spl[0].toLowerCase();

        if (spl[0].equals("jan"))
        {
            month = 1;
        }
        else if (spl[0].equals("feb"))
        {
            month = 2;
        }
        else if (spl[0].equals("mar"))
        {
            month = 3;
        }
        else if (spl[0].equals("apr"))
        {
            month = 4;
        }
        else if (spl[0].equals("may"))
        {
            month = 5;
        }
        else if (spl[0].equals("jun"))
        {
            month = 6;
        }
        else if (spl[0].equals("jul"))
        {
            month = 7;
        }
        else if (spl[0].equals("aug"))
        {
            month = 8;
        }
        else if (spl[0].equals("sep"))
        {
            month = 9;
        }
        else if (spl[0].equals("oct"))
        {
            month = 10;
        }
        else if (spl[0].equals("nov"))
        {
            month = 11;
        }
        else if (spl[0].equals("dec"))
        {
            month = 12;
        }
        else
        {
            //Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
        }

        return month + "/" + day + "/" + year;
    }


    // -------New Stuff-----
    private static String[] toArray(ArrayList<Appointment> tempList) throws Exception
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

        if (mPlayer.isPlaying())
        {
//            mPlayer.stop();
            mPlayer.release();
//            mPlayer.reset();
            mPlayer = new MediaPlayer();
        }



//        temp_float_button.setImageDrawable(playDrawable);


    }

    public void StopPlayerNow(View view) {
        StopPlayer();
        Toast.makeText(this,"Playback Stopped", Toast.LENGTH_SHORT).show();
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
        Date tempdate = new Date();

        tempdate.setHours(9);
        tempdate.setMinutes(03);
        tempdate.setSeconds(34);
        for(int i = 0; i<7; i++)
        {
            tempdate.setDate(tempdate.getDay()+i);
            alarms.add(tempdate.getTime());
        }
        FileIO.AlarmSaveOut(alarms);
        alarms.clear();
        alarms.addAll(FileIO.AlarmSaveIn());


//            info.setText("\n\n***\n"
//                    + "Alarm is set@ " + targetCal.getTime() + "\n"
//                    + "***\n");  getBaseContext()

        if(alarms.get(0) != tempdate.getTime()) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarms.get(0), pendingIntent);
        }
        else
        {
            Intent intent = new Intent(this, AlertReceiverNotification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarms.get(0), pendingIntent);
        }
    }


}
