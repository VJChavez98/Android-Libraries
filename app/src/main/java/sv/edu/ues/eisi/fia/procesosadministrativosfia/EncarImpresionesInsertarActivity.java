package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class EncarImpresionesInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEncargado;
    EditText editNombreEncargado;
    EditText editApellidoEncargado;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encar_impresiones_insertar);
        helper = new ControladorBase(this);
        editIdEncargado = (EditText) findViewById(R.id.editIdEncarImpresiones);
        editNombreEncargado = (EditText) findViewById(R.id.editNombreEncargado);
        editApellidoEncargado = (EditText) findViewById(R.id.editApellidoEncargado);

        Texto=(TextView) findViewById(R.id.editIdEncarImpresiones);
        Texto1=(TextView) findViewById(R.id.editNombreEncargado);
        Texto2=(TextView) findViewById(R.id.editApellidoEncargado);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);


    }
    public void insertarEncarImpresiones(View v) {
        editIdEncargado.setError(null);
        editNombreEncargado.setError(null);
        editApellidoEncargado.setError(null);


        String idencargado=editIdEncargado.getText().toString();
        String nombreencargado=editNombreEncargado.getText().toString();
        String apellidoencargado=editApellidoEncargado.getText().toString();
        String regInsertados;

        if(TextUtils.isEmpty(idencargado)){
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(nombreencargado)){
            editNombreEncargado.setError(getString(R.string.error_campo_obligatorio));
            editNombreEncargado.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(apellidoencargado)){
            editApellidoEncargado.setError(getString(R.string.error_campo_obligatorio));
            editApellidoEncargado.requestFocus();
            return;
        }

        EncarImpresiones enc=new EncarImpresiones();
        enc.setIdencargado(idencargado);
        enc.setNombreencargado(nombreencargado);
        enc.setApellidoencargado(apellidoencargado);

        helper.abrir();
        regInsertados=helper.insertarEncarImpresiones(enc);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdEncargado.setText("");
        editNombreEncargado.setText("");
        editApellidoEncargado.setText("");
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
