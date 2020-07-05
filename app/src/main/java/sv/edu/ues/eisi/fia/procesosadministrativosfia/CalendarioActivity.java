package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;

    private CalendarView mCalendarView;
    ControladorBase helper;

    private SQLiteDatabase db;

    //Lista de Eventos del Objeto Event Day
    private List<EventDay> mEventDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        helper= new ControladorBase(this);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        helper.abrir();
        final ArrayList<String> solicitudes = helper.conInsertarCalendario();
        helper.cerrar();

        for(int indice = 0;indice<solicitudes.size();indice++)
        {
            System.out.println(solicitudes.get(indice));

            String objFecha=solicitudes.get(indice);
            String evento=solicitudes.get(indice).substring(29);

            Integer mes=null;
            Integer dia;

            //Recuperar Año
            String aCadena = objFecha;
            String aSubCadena = aCadena.substring(24,28);

            Integer anio= Integer.valueOf(aSubCadena);

            //Recuperar Numero de Mes
            String mCadena = objFecha;
            String mSubCadena = mCadena.substring(4,7);

            mes=recupNumMes(mSubCadena);



            //Recuperar Dia
            String dCadena = objFecha;
            String dSubCadena = dCadena.substring(8,10);

            dia= Integer.valueOf(dSubCadena);

            Calendar calendar = Calendar.getInstance();
            calendar.set(anio,mes, dia);
            MyEventDay myEventDay;

            myEventDay = new MyEventDay(calendar, R.drawable.ic_event, evento);
            mEventDays.add(myEventDay);
            //Agregar Evento al Calendario
            mCalendarView.setEvents(mEventDays);
        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020,4, 26);
//        MyEventDay myEventDay;
//
//        myEventDay = new MyEventDay(calendar, R.drawable.ic_event, "I am Event");
//        mEventDays.add(myEventDay);
//        //Agregar Evento al Calendario
//        mCalendarView.setEvents(mEventDays);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                previewNote(eventDay);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            try {
                mCalendarView.setDate(myEventDay.getCalendar());
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }
            mEventDays.add(myEventDay);
            //Agregar Evento al Calendario
            mCalendarView.setEvents(mEventDays);
        }
    }

    private void addNote() {
        Intent intent = new Intent(this, sv.edu.ues.eisi.fia.procesosadministrativosfia.CalendarioAddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }

    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(this, sv.edu.ues.eisi.fia.procesosadministrativosfia.CalendarioNotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (sv.edu.ues.eisi.fia.procesosadministrativosfia.MyEventDay) eventDay);
        }
        startActivity(intent);
    }

    public Integer recupNumMes(String mSubCadena){
        Integer mes=null;
        switch (mSubCadena) {
            case "Jan":
                mes = 0;
                break;
            case "Feb":
                mes = 1;
                break;
            case "Mar":
                mes = 2;
                break;
            case "Apr":
                mes = 3;
                break;
            case "May":
                mes = 4;
                break;
            case "Jun":
                mes = 5;
                break;
            case "Jul":
                mes = 6;
                break;
            case "Aug":
                mes = 7;
                break;
            case "Sep":
                mes = 8;
                break;
            case "Oct":
                mes = 9;
                break;
            case "Nov":
                mes = 10;
                break;
            case "Dec":
                mes = 11;
                break;
        }return mes;
    }
}