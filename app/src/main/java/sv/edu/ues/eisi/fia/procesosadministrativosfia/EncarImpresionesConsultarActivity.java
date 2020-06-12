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


public class EncarImpresionesConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEncargado;
    EditText editNombreEncargado;
    EditText editApellidoEncargado;
    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_encar_impresiones_consultar);
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
    public void consultarEncImpresiones(View v) {

        editIdEncargado.setError(null);
        String idencargado=editIdEncargado.getText().toString();
        if(TextUtils.isEmpty(idencargado)){
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        helper.abrir();
        EncarImpresiones enc = helper.consultarEncarImpresiones(editIdEncargado.getText().toString());
        helper.cerrar();
        if(enc == null)
            Toast.makeText(this, "Encargado con id " + editIdEncargado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editNombreEncargado.setText(enc.getNombreencargado());
            editApellidoEncargado.setText(enc.getApellidoencargado());


        }
    }
    public void limpiarTexto(View v){
        editIdEncargado.setText("");
        editNombreEncargado.setText("");
        editApellidoEncargado.setText("");
        findViewById(R.id.detalle).setVisibility(View.GONE);
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
