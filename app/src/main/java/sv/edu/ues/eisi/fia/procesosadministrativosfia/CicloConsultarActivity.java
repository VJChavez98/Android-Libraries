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

public class CicloConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editCodciclo;
    EditText editFechadesde;
    EditText editFechahasta;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_consultar);
        helper = new ControladorBase(this);

        editCodciclo=(EditText) findViewById(R.id.editCodciclo);
        editFechadesde=(EditText) findViewById(R.id.editFechadesde);
        editFechahasta=(EditText) findViewById(R.id.editFechahasta);

        Texto=(TextView) findViewById(R.id.editCodciclo);
        Texto1=(TextView) findViewById(R.id.editFechadesde);
        Texto2=(TextView) findViewById(R.id.editFechahasta);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

    }
    public void consultarCiclo(View v){
        helper.abrir();
        Ciclo ciclo=
                helper.consultarCiclo(editCodciclo.getText().toString());
        helper.cerrar();
        if(ciclo==null)
        Toast.makeText(this,"Ciclo con Código"+editCodciclo.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();
        else{
            editCodciclo.setText(ciclo.getCodciclo());
            editFechadesde.setText(ciclo.getFechadesde());
            editFechahasta.setText(ciclo.getFechahasta());


        }

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
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }

    public void limpiarTexto(View v){
        editCodciclo.setText("");
        editFechadesde.setText("");
        editFechahasta.setText("");
    }
}
