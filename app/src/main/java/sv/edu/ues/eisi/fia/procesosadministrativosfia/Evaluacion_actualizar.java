package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class Evaluacion_actualizar extends Activity {
    ControladorBase helper;
    EditText editCodasignatura, editCodcilo, editNumeval, editFechaeval;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3;
    Button BtnPlay;
    private int numarch=0;

    Spinner spinTipoeval;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_actualizar);
        helper = new ControladorBase(this);
        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodcilo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        editFechaeval = (EditText) findViewById(R.id.editFechaeval);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);

        Texto=(TextView) findViewById(R.id.editCodasignatura);
        Texto1=(TextView) findViewById(R.id.editCodciclo);
        Texto2=(TextView) findViewById(R.id.editNumeval);
        Texto3=(TextView) findViewById(R.id.editFechaeval);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        editFechaeval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
    }

    private void colocar_fecha() {
        if(String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1){
            editFechaeval.setText( nYearIni + "-0" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else if(String.valueOf(nMonthIni).length() == 1){
            editFechaeval.setText( nYearIni + "-0" + (nMonthIni + 1) + "-" +  nDayIni );
        }else if(String.valueOf(nDayIni).length() == 1){
            editFechaeval.setText( nYearIni + "-" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else{
            editFechaeval.setText( nYearIni + "-" + (nMonthIni + 1) + "-" +  nDayIni );
        }
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    nYearIni = year;
                    nMonthIni = monthOfYear;
                    nDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void actualizarEvaluacion(View v){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setCodAsignatura(editCodasignatura.getText().toString());
        evaluacion.setCodCiclo(editCodcilo.getText().toString());
        evaluacion.setFechaEvaluacion(editFechaeval.getText().toString());

        if(!editNumeval.getText().toString().isEmpty()){
            evaluacion.setNumeroEvaluacion(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            evaluacion.setNumeroEvaluacion(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            evaluacion.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            evaluacion.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            evaluacion.setCodTipoEval(tipoEval);
        }else{
            evaluacion.setCodTipoEval("");
        }

        helper.abrir();
        String estado = helper.actualizar(evaluacion);
        helper.cerrar();

        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodcilo.setText("");
        spinTipoeval.setSelection(0);
        editFechaeval.setText("");
        editNumeval.setText("");
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
