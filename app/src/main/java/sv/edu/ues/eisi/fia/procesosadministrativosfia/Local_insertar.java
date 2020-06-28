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

public class Local_insertar extends Activity {
    EditText editCodlocal, editNomlocal, editUbicacionLocal;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_insertar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
        editUbicacionLocal = (EditText) findViewById(R.id.editUbicacionlocal);

        Texto=(TextView) findViewById(R.id.editCodlocal);
        Texto1=(TextView) findViewById(R.id.editNomlocal);
        Texto2=(TextView) findViewById(R.id.editUbicacionlocal);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void insertarLocal(View v){
        String codlocal = editCodlocal.getText().toString();
        String nomlocal = editNomlocal.getText().toString();
        String ubiclocal = editUbicacionLocal.getText().toString();
        String regInsertados;

        Local local = new Local();
        local.setCodlocal(codlocal);
        local.setNomlocal(nomlocal);
        local.setUbicacionlocal(ubiclocal);

        helper.abrir();
        regInsertados = helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
        editUbicacionLocal.setText("");
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
