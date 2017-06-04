package com.example.tag.myalarm02;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private FragmentManager fragmentManager;
    TextView textView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = new Intent(this,ActivatedActivity.class);
        final PendingIntent pendingIntent1 = PendingIntent.getActivity(this,0,intent1,0);

        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        fragmentManager = getFragmentManager();     

        textView = (TextView)findViewById(R.id.textView);

        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        textView.setText(hour+":"+minute);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(fragmentManager,"test");
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                }

                Toast.makeText(MainActivity.this,
                        calendar.get(Calendar.HOUR_OF_DAY)+"時"+
                                calendar.get(Calendar.MINUTE)+"分にアラームをセットします。",
                                Toast.LENGTH_LONG).show();

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),pendingIntent1);

            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView.setText(hourOfDay+":"+minute);
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
