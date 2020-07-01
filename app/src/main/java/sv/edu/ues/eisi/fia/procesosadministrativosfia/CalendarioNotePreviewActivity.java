package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.EventDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarioNotePreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_note_preview);

        Intent intent = getIntent();

        TextView note = (TextView) findViewById(R.id.note);

        if (intent != null) {
            Object event = intent.getParcelableExtra(CalendarioActivity.EVENT);

            if(event instanceof MyEventDay){
                MyEventDay myEventDay = (MyEventDay)event;

                getSupportActionBar().setTitle(getFormattedDate(myEventDay.getCalendar().getTime()));
                note.setText(myEventDay.getNote());

                return;
            }

            if(event instanceof EventDay){
                EventDay eventDay = (EventDay)event;
                getSupportActionBar().setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}