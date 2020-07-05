package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;

public class CalendarioAddNoteActivity extends AppCompatActivity {

    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_add_note);

        final CalendarView datePicker = (CalendarView) findViewById(R.id.datePicker);
        Button btnGuardar = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);

        helper= new ControladorBase(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regInsertados;
                Intent returnIntent = new Intent();

                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.ic_event, noteEditText.getText().toString());

                CalendarioN calendarioN= new CalendarioN();
                calendarioN.setObjetoFecha(String.valueOf(datePicker.getSelectedDate().getTime()));
                calendarioN.setEvento(noteEditText.getText().toString());


                helper.abrir();
                regInsertados=helper.insertarCalendario(calendarioN);
                helper.cerrar();
                Toast.makeText(CalendarioAddNoteActivity.this, regInsertados, Toast.LENGTH_SHORT).show();

                returnIntent.putExtra(CalendarioActivity.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}