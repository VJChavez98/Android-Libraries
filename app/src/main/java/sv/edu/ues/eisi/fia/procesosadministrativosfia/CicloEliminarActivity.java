package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;

public class CicloEliminarActivity extends Activity {

    EditText editCodciclo;
    ControladorBase controlhelper;

    TextToSpeech tts;
    TextView Texto;
    Button BtnPlay;
    private int numarch=0;

    private int dYearIni, dMonthIni, dDayIni, sdYearIni, sdMonthIni, sdDayIni;
    private int hYearIni, hMonthIni, hDayIni, shYearIni, shMonthIni, shDayIni;
    static final int DATE_ID = 0;
    static final int DATE_ID2 = 1;
    Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_eliminar);
        controlhelper = new ControladorBase(this);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);

        Texto = (TextView) findViewById(R.id.editCodciclo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this, OnInit);
        BtnPlay.setOnClickListener(onClick);

        sdMonthIni = c.get(Calendar.MONTH);
        sdDayIni = c.get(Calendar.DAY_OF_MONTH);
        sdYearIni = c.get(Calendar.YEAR);

        shMonthIni = c.get(Calendar.MONTH);
        shDayIni = c.get(Calendar.DAY_OF_MONTH);
        shYearIni = c.get(Calendar.YEAR);

    }
    public void eliminarCiclo(View v){
        String regEliminadas;
        Ciclo ciclo= new Ciclo();
        ciclo.setCodciclo(editCodciclo.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(ciclo);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();


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
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
    public void limpiarTexto(View v){
        editCodciclo.setText("");
    }

}
