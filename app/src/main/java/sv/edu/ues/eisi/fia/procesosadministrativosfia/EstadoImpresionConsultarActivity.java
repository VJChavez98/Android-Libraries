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

public class EstadoImpresionConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEstadoImpresion;
    EditText editIdSolicitudImpresion;
    EditText editIdEncargado;
    EditText editIdMotivoImpresion;
    EditText editRealizado;
    EditText editObservaciones;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2,Texto3, Texto4, Texto5;
    Button BtnPlay;
    private int numarch=0;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_impresion_consultar);
        helper = new ControladorBase(this);

        editIdEstadoImpresion = (EditText) findViewById(R.id.editIdEstadoImpresion);
        editIdSolicitudImpresion = (EditText) findViewById(R.id.editIdSolicitudImpresion);
        editIdEncargado = (EditText) findViewById(R.id.editIdEncargado);
        editIdMotivoImpresion = (EditText) findViewById(R.id.editIdMotivoImpresion);
        editRealizado = (EditText) findViewById(R.id.editRealizado);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);

        Texto=(TextView) findViewById(R.id.editIdEstadoImpresion);
        Texto1=(TextView) findViewById(R.id.editIdSolicitudImpresion);
        Texto2=(TextView) findViewById(R.id.editIdEncargado);
        Texto3=(TextView) findViewById(R.id.editIdMotivoImpresion);
        Texto4=(TextView) findViewById(R.id.editRealizado);
        Texto5=(TextView) findViewById(R.id.editObservaciones);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        mEntradaVoz=findViewById(R.id.editIdEstadoImpresion);
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
    public void consultarEstadoImpresion(View v) {

        editIdEstadoImpresion.setError(null);
        editIdSolicitudImpresion.setError(null);
        editIdEncargado.setError(null);
        editIdMotivoImpresion.setError(null);

        String idestadoimpresion = editIdEstadoImpresion.getText().toString();
        String idsolicitudimpresion = editIdSolicitudImpresion.getText().toString();
        String idencargado = editIdEncargado.getText().toString();
        String idmotivoimpresion = editIdMotivoImpresion.getText().toString();

        if (TextUtils.isEmpty(idestadoimpresion)) {
            editIdEstadoImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdEstadoImpresion.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idsolicitudimpresion)) {
            editIdSolicitudImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdSolicitudImpresion.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idencargado)) {
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idmotivoimpresion)) {
            editIdMotivoImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdMotivoImpresion.requestFocus();
            return;
        }

        helper.abrir();
        EstadoImpresion estadoImpresion = helper.consultarEstadoImpresion(editIdEstadoImpresion.getText().toString(), editIdSolicitudImpresion.getText().toString(), editIdEncargado.getText().toString(), editIdMotivoImpresion.getText().toString());

        helper.cerrar();

        if(estadoImpresion == null)
            Toast.makeText(this, "Estado de Impresion no registrado", Toast.LENGTH_LONG).show();
        else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editRealizado.setText(String.valueOf(estadoImpresion.getRealizado()));
            editObservaciones.setText(String.valueOf(estadoImpresion.getObservaciones()));
        }
    }

    public void limpiarTexto(View v){
        editIdEstadoImpresion.setText("");
        editIdSolicitudImpresion.setText("");
        editIdEncargado.setText("");
        editIdMotivoImpresion.setText("");
        editRealizado.setText("");
        editObservaciones.setText("");
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
                tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto4.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto5.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
