package sample.kingja.calendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kingja.supercalendar.SuperCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SuperCalendar superCalendar = (SuperCalendar) findViewById(R.id.superCalendar);
        superCalendar.setOnCalendarSelectedListener(new SuperCalendar.OnCalendarSelectedListener() {
            @Override
            public void onCalendarSelected(Date date) {
                Toast.makeText(MainActivity.this, new SimpleDateFormat("MM yyyy").format(date), Toast.LENGTH_SHORT)
                        .show();
            }
        });}
}
