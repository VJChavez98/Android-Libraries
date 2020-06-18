package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargaAcademicaConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodciclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4;
    Button BtnPlay;
    private int numarch=0;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_consultar);

        helper= new ControladorBase(this);
        editIdcargaacademica= (EditText) findViewById(R.id.editIdcargaacademica);
        editCodciclo= (EditText) findViewById(R.id.editCodciclo);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText) findViewById(R.id.editIdtipodocenteciclo);

        Texto=(TextView) findViewById(R.id.editIdcargaacademica);
        Texto1=(TextView) findViewById(R.id.editCodciclo);
        Texto2=(TextView) findViewById(R.id.editCoddocente);
        Texto3=(TextView) findViewById(R.id.editCodasignatura);
        Texto4=(TextView) findViewById(R.id.editIdtipodocenteciclo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        mEntradaVoz=findViewById(R.id.editIdcargaacademica);
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
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Código de la Carga Académica ");
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

    public  void consultarCargaacademica(View v){
        helper.abrir();
        CargaAcademica cargaAcademica=
                helper.consultarCargaAcademica(editIdcargaacademica.getText().toString());
        helper.cerrar();
        if(cargaAcademica==null)
            Toast.makeText(this,"Carga Académica con Código "+editIdcargaacademica.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();

        else{
            editIdcargaacademica.setText(cargaAcademica.getIdcargaacademica());
            editCodciclo.setText(cargaAcademica.getCodciclo());
            editCoddocente.setText(cargaAcademica.getCoddocente());
            editCodasignatura.setText(cargaAcademica.getCodasignatura());
            editIdtipodocenteciclo.setText(cargaAcademica.getIdtipodocenteciclo());
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
