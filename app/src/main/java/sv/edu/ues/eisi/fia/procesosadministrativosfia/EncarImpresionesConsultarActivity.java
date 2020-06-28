package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

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

        mEntradaVoz=findViewById(R.id.editIdEncarImpresiones);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });


    }

    private void iniciarEntradaVoz(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el ID ");
        try {
            startActivityForResult(i, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEntradaVoz.setText(result.get(0));
                }
                break;
            }
        }
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
