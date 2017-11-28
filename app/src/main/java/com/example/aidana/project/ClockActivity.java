package com.example.aidana.project;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ClockActivity extends ActionBarActivity {

    DatePicker datePicker;
    TimePicker timePicker;

    RadioButton optAlarm, optNotification, optRingtone;
    RadioButton optRingTonePicker;
    Button btnStart;
    TextView info;

    Ringtone ringTone;

    Uri uriAlarm, uriNotification, uriRingtone;

    final static int RQS_RINGTONEPICKER = 1;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        datePicker = (DatePicker)findViewById(R.id.datepicker);
        timePicker = (TimePicker)findViewById(R.id.timepicker);

        optAlarm = (RadioButton)findViewById(R.id.optAlarm);
        optNotification = (RadioButton)findViewById(R.id.optNotification);
        optRingtone = (RadioButton)findViewById(R.id.optRingtone);
        optRingTonePicker = (RadioButton)findViewById(R.id.optPicker);
        btnStart = (Button)findViewById(R.id.start);
        info = (TextView)findViewById(R.id.info);

        uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        optAlarm.setText("uriAlarm: " + uriAlarm);
//        optNotification.setText("uriNotification: " + uriNotification);
//        optRingtone.setText("uriRingtone: " + uriRingtone);

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (optAlarm.isChecked()) {
                    setAlarm(uriAlarm);
                } else if (optNotification.isChecked()) {
                    setAlarm(uriNotification);
                } else if (optRingtone.isChecked()) {
                    setAlarm(uriRingtone);
                } else if (optRingTonePicker.isChecked()) {
                    startRingTonePicker();
                }

            }
        });

    }

    private void startRingTonePicker(){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, RQS_RINGTONEPICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RQS_RINGTONEPICKER && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            setAlarm(uri);
        }
    }

    private void setAlarm(Uri passuri){

        Calendar cal = Calendar.getInstance();
        cal.set(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(),
                00);

        String passString = passuri.toString();
        info.setText("\n\n***\n"
                + "Alarm is set" + cal.getTime() + "\n"
                + "***\n"
                + "Uri: " + passString);

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("KEY_TONE_URL", passString);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(),
                RQS_1,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}