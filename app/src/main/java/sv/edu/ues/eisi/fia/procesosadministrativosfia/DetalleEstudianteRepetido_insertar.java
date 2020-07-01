package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleEstudianteRepetido_insertar extends AppCompatActivity {

    EditText editCarnet, editNumEval, editFecha, editAsignatura;
    Spinner spinTipoEval;
    ControladorBase helper;


    TextToSpeech tts;
    Button BtnPlay;
    private int numarch=0;
    TextView Texto, Texto1, Texto2,Texto3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_repetido_insertar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editAsignatura = findViewById(R.id.editCodasignatura);

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editNumeval);
        Texto2=(TextView) findViewById(R.id.editFechaInscrip);
        Texto3=(TextView) findViewById(R.id.editCodasignatura);

        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void insertarDetalle(View view) {
        DetalleEstudianteRepetido detalle = new DetalleEstudianteRepetido();
        detalle.setCarnet(editCarnet.getText().toString());
        detalle.setFechaInscripcion(editFecha.getText().toString());
        String detalleId = editAsignatura.getText().toString()+spinTipoEval.getSelectedItem().toString()+editNumEval.getText().toString()+"Repetido";
        detalle.setIdDetalleDifRep(detalleId);
        detalle.setIdDetalleEstuRep(editCarnet.getText().toString()+detalleId);
        helper.abrir();
        //String resultado = helper.insertar(detalle);
        helper.cerrar();
        //Toast.makeText(this,resultado,Toast.LENGTH_SHORT).show();
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
    public void limpiarTexto(View view){
        editCarnet.setText("");
        editAsignatura.setText("");
        editNumEval.setText("");
        editFecha.setText("");
        spinTipoEval.setSelection(0);
    }
}
