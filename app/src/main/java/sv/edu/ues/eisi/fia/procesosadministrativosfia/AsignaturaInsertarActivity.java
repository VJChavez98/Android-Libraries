package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsignaturaInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editCodasignatura, editNombreasignatura, editUnidadesval;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_insertar);
        helper= new ControladorBase(this);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editNombreasignatura= (EditText) findViewById(R.id.editNombreasignatura);
        editUnidadesval= (EditText) findViewById(R.id.editUnidadesval);

        Texto=(TextView) findViewById(R.id.editCodasignatura);
        Texto1=(TextView) findViewById(R.id.editNombreasignatura);
        Texto2=(TextView) findViewById(R.id.editUnidadesval);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }
    public void insertarAsignatura(View v){

        String codasignatura=editCodasignatura.getText().toString();
        String nombreasignatura=editNombreasignatura.getText().toString();
        String unidadesval=editUnidadesval.getText().toString();
        String regInsertados;

        if(TextUtils.isEmpty(codasignatura)){
            editCodasignatura.setError(getString(R.string.error_campo_obligatorio));
            editCodasignatura.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(nombreasignatura)){
            editNombreasignatura.setError(getString(R.string.error_campo_obligatorio));
            editNombreasignatura.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(unidadesval)){
            editUnidadesval.setError(getString(R.string.error_campo_obligatorio));
            editUnidadesval.requestFocus();
            return;
        }

        Asignatura asignatura= new Asignatura();
        asignatura.setCodasignatura(codasignatura);
        asignatura.setNomasignatura(nombreasignatura);
        asignatura.setUnidadesval(unidadesval);
        helper.abrir();
        regInsertados=helper.insertar(asignatura);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
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

    public void limpiarTexto(View v) {
        editCodasignatura.setText("");
        editNombreasignatura.setText("");
        editUnidadesval.setText("");
    }
}
