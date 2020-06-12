package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class Repetido_Insertar extends AppCompatActivity {
    EditText carnet, materia, numEval;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;
    Spinner spinTipoEval;
    ControladorBase helper;
    Calendar c;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetido__insertar);
        helper = new ControladorBase(this);
        carnet = findViewById(R.id.editCarnet);
        materia = findViewById(R.id.editAsignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        numEval = findViewById(R.id.editNumeval);
        c = Calendar.getInstance();
        sMonthIni = c.get(Calendar.MONTH)+1;
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editAsignatura);
        Texto2=(TextView) findViewById(R.id.editNumeval);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void limpiarTexto(View view){

    }
    public void insertarRepetido(View view) {
        int numeroEval = 0;
        String resultado;
        DetalleDiferidoRepetido detalleDiferidoRepetido;
        if (!numEval.getText().toString().isEmpty()) {
            numeroEval = Integer.parseInt(numEval.getText().toString());
        }
        helper.abrir();
        detalleDiferidoRepetido = helper.consultarDetalleDifRep(materia.getText().toString(),spinTipoEval.getSelectedItem().toString(),numeroEval,"Repetido");
        helper.cerrar();
        if (detalleDiferidoRepetido != null){
            DetalleEstudianteRepetido detalle = new DetalleEstudianteRepetido();
            detalle.setCarnet(carnet.getText().toString());
            detalle.setIdDetalleDifRep(detalleDiferidoRepetido.getIdDetalle());
            detalle.setFechaInscripcion(colocar_fecha());
            helper.abrir();
            resultado = helper.insertar(detalle);
            helper.cerrar();
        }else {
            resultado = "No existe repetido para la evaluacion o materia";
        }

        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }
    private String colocar_fecha() {
        String fecha = "";
        if (String.valueOf(sMonthIni).length() == 1 && String.valueOf(sDayIni).length() == 1){
            fecha = nYearIni + "-0" + sMonthIni + "-0" + sDayIni;
        }else if (String.valueOf(sMonthIni).length() == 1){
            fecha = sYearIni + "-0" + sMonthIni + "-" + sDayIni ;
        }else if (String.valueOf(sDayIni).length() == 1) {
            fecha = sYearIni + "-" + sMonthIni + "-0" + sDayIni;
        }else {
            fecha = sYearIni + "-" + sMonthIni + "-" + sDayIni ;
        }
        return fecha;
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
