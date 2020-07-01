package sv.edu.ues.eisi.fia.procesosadministrativosfia;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class DocenteActualizarActivity extends Activity {

    ControladorBase helper;
    EditText editCoddocente, editNombredocente, editApellidodocente;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_actualizar);
        helper= new ControladorBase(this);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editNombredocente= (EditText) findViewById(R.id.editNombredocente);
        editApellidodocente= (EditText) findViewById(R.id.editApellidodocente);

        Texto=(TextView) findViewById(R.id.editCoddocente);
        Texto1=(TextView) findViewById(R.id.editNombredocente);
        Texto2=(TextView) findViewById(R.id.editApellidodocente);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

    }
    public void actualizarDocente(View v) {
        Docente docente = new Docente();

        docente.setCoddocente(editCoddocente.getText().toString());
        docente.setNomdocente(editNombredocente.getText().toString());
        docente.setApellidodocente(editApellidodocente.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(docente);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {

        editCoddocente.setText("");
        editNombredocente.setText("");
        editApellidodocente.setText("");
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
}
