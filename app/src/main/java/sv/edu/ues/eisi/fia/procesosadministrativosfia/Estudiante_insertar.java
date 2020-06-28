package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Estudiante_insertar extends AppCompatActivity {
    EditText editCarnet, editNombre, editApellido, editCarrera;
    ControladorBase helper;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2,Texto3;
    Button BtnPlay;
    private int numarch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_insertar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editNombre = findViewById(R.id.editNombreEstudiante);
        editApellido = findViewById(R.id.editApellidoEstudiante);
        editCarrera = findViewById(R.id.editCarreraEstudiante);

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editNombreEstudiante);
        Texto2=(TextView) findViewById(R.id.editApellidoEstudiante);
        Texto3=(TextView) findViewById(R.id.editCarreraEstudiante);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editCarrera.setText("");
    }

    public void insertarEstudiante(View view) {
        if (!(editCarnet.getText().toString().isEmpty() && editNombre.getText().toString().isEmpty() && editApellido.getText().toString().isEmpty() && editCarrera.getText().toString().isEmpty())){
            if (!editCarnet.getText().toString().isEmpty()) {
                if (!editNombre.getText().toString().isEmpty()) {
                    if (!editApellido.getText().toString().isEmpty()) {
                        if (!editCarrera.getText().toString().isEmpty()) {
                            Estudiante estudiante = new Estudiante();
                            estudiante.setCarnet(editCarnet.getText().toString());
                            estudiante.setNombre(editNombre.getText().toString());
                            estudiante.setApellido(editApellido.getText().toString());
                            estudiante.setCarrera(editCarrera.getText().toString());
                            helper.abrir();
                            String resultado = helper.insertar(estudiante);
                            helper.cerrar();
                            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Carrera",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Apellidos",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Nombres",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Carnet",Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getApplicationContext(), "Campos vacios",Toast.LENGTH_SHORT).show();
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
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
