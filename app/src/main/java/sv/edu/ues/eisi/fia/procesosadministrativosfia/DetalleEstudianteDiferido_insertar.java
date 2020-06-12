package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class DetalleEstudianteDiferido_insertar extends AppCompatActivity {
    EditText editCarnet, editMateria, editNumEval, editFecha;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3;
    Button BtnPlay;
    private int numarch=0;

    Spinner spinTipoEval;
    ControladorBase helper;
    private int nYearIni, nMonthIni, nDayIni;
    private int sYearIni, sMonthIni, sDayIni;
    private int sHour, nHour, sMinute, nMinute;
    static final int DATE_ID1 = 0;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_diferido_insertar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editMateria = findViewById(R.id.editCodasignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editCodasignatura);
        Texto2=(TextView) findViewById(R.id.editNumeval);
        Texto3=(TextView) findViewById(R.id.editFechaInscrip);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);

        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID1);
            }
        });
    }

    public void insertarDetalleEstudiante(View view) {
        DetalleEstudianteDiferido detalle = new DetalleEstudianteDiferido();
        detalle.setCarnet(editCarnet.getText().toString());
        String idDetalleDifRep = editMateria.getText().toString() + spinTipoEval.getSelectedItem().toString() + editNumEval.getText().toString() + "Diferido";
        detalle.setIdDetalleDifeRep(idDetalleDifRep);
        detalle.setIdDetalleEstudianteDif(editCarnet.getText().toString() + idDetalleDifRep);
        detalle.setFechaInscripcionDiferido(editFecha.getText().toString());
        //detalle.setAsignatura(editMateria.getText().toString());
        //detalle.setTipoEval(spinTipoEval.getSelectedItem().toString());
        //if(!editNumEval.getText().toString().isEmpty()) {
         //   detalle.setNumEval(Integer.parseInt(editNumEval.getText().toString()));
        //}else {detalle.setNumEval(0);}
        helper.abrir();
        String resultado = helper.insertar(detalle);
        helper.cerrar();
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editMateria.setText("");
        spinTipoEval.setSelection(0);
        editNumEval.setText("");
        editFecha.setText("");
    }

    private void colocar_fecha() {
        if (String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1) {
            editFecha.setText(nYearIni + "-0" + nMonthIni + "-0" + nDayIni);
        } else if (String.valueOf(nMonthIni).length() == 1) {
            editFecha.setText(nYearIni + "-0" + nMonthIni + "-" + nDayIni);
        } else if (String.valueOf(nDayIni).length() == 1) {
            editFecha.setText(nYearIni + "-" + nMonthIni + "-0" + nDayIni);
        } else {
            editFecha.setText(nYearIni + "-" + nMonthIni + "-" + nDayIni);
        }
    }
    private DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nYearIni = year;
            nMonthIni = month;
            nDayIni = dayOfMonth;
            colocar_fecha();        }
    };
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID1:
                return new DatePickerDialog(this, fecha, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    TextToSpeech.OnInitListener OnInit= new TextToSpeech.OnInitListener(){
        @Override
        public void onInit(int status){
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else{
                Toast.makeText(getApplicationContext(), "TTS No Disponible", Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener(){
        @SuppressLint("SdCardPath")
        public void onClick(View v){
            if (v.getId()==R.id.btnText2SpeechPlay){
                tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
