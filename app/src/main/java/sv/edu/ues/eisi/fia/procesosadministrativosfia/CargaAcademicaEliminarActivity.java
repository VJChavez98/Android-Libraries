package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargaAcademicaEliminarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodciclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4;
    Button BtnPlay;
    private int numarch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_eliminar);
        helper= new ControladorBase(this);
        editIdcargaacademica=(EditText)findViewById(R.id.editIdcargaacademica);
        editCodciclo=(EditText)findViewById(R.id.editCodciclo);
        editCoddocente=(EditText)findViewById(R.id.editCoddocente);
        editCodasignatura=(EditText)findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText)findViewById(R.id.editIdtipodocenteciclo);

        Texto=(TextView) findViewById(R.id.editIdcargaacademica);
        Texto1=(TextView) findViewById(R.id.editCodciclo);
        Texto2=(TextView) findViewById(R.id.editCoddocente);
        Texto3=(TextView) findViewById(R.id.editCodasignatura);
        Texto4=(TextView) findViewById(R.id.editIdtipodocenteciclo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

    }

    public void eliminarCargaacademica(View v){
        String regEliminadas;
        CargaAcademica cargaAcademica=new CargaAcademica();
        cargaAcademica.setIdcargaacademica(editIdcargaacademica.getText().toString());
        cargaAcademica.setCodciclo(editCodciclo.getText().toString());
        cargaAcademica.setCoddocente(editCoddocente.getText().toString());
        cargaAcademica.setCodasignatura(editCodasignatura.getText().toString());
        cargaAcademica.setIdtipodocenteciclo(editIdtipodocenteciclo.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(cargaAcademica);
        helper.cerrar();
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
                tts.speak(Texto1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto4.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
    public void limpiar(View v){
        editIdcargaacademica.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodasignatura.setText("");
        editIdtipodocenteciclo.setText("");
    }

}
