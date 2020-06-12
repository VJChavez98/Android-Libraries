package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Evaluacion_consultar extends Activity {
    EditText editCodasignatura, editCodciclo, editNumeval, editFechaeval, editNomasignatura;
    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4;
    Button BtnPlay;
    private int numarch=0;
    Spinner spinTipoeval;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_consultar);
        helper = new ControladorBase(this);

        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        editFechaeval = (EditText) findViewById(R.id.editFechaeval);
        editNomasignatura = (EditText) findViewById(R.id.editNomasignatura);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);
        Texto=(TextView) findViewById(R.id.editCodasignatura);
        Texto1=(TextView) findViewById(R.id.editCodciclo);
        Texto2=(TextView) findViewById(R.id.editNumeval);
        Texto3=(TextView) findViewById(R.id.editFechaeval);
        Texto4=(TextView) findViewById(R.id.editNomasignatura);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void consultarEvaluacion(View v){
        Evaluacion eval = new Evaluacion();

        if(!editNumeval.getText().toString().isEmpty()){
            eval.setNumeroEvaluacion(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            eval.setNumeroEvaluacion(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            eval.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            eval.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            eval.setCodTipoEval(tipoEval);
        }else{
            eval.setCodTipoEval("");
        }

        helper.abrir();
        Evaluacion evaluacion = helper.consultarEvaluacion(editCodasignatura.getText().toString(), editCodciclo.getText().toString(), eval.getCodTipoEval(), eval.getNumeroEvaluacion());
        Asignatura asignatura = helper.consultarNomAsignatura(editCodasignatura.getText().toString());
        helper.cerrar();
        if(evaluacion == null){
            Toast.makeText(this, "Evaluacion no registrada", Toast.LENGTH_LONG).show();
        }else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editFechaeval.setText(evaluacion.getFechaEvaluacion());
            editNomasignatura.setText(asignatura.getNomasignatura());
        }
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        spinTipoeval.setSelection(0);
        editNumeval.setText("");
        editFechaeval.setText("");
        editNomasignatura.setText("");
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
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
