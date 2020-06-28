package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PrimeraRevision_insertar extends Activity {
    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet, editNotaFinal, editObservaciones;
    Spinner spinTipoEval, spinTipoGrupo, spinMotCambNota;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5, Texto6;
    Button BtnPlay;
    private int numarch=0;

    RadioGroup radioEstado, radioAsistencia;
    RadioButton radioSi, radioNo, radioPendiente, radioTerminada;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_revision_insertar);
        helper = new ControladorBase(this);
        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editCoddocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNotaFinal = (EditText) findViewById(R.id.editNotaDespues);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        spinMotCambNota = (Spinner) findViewById(R.id.spinMotCambioNota);
        spinTipoGrupo = (Spinner) findViewById(R.id.spinTipoGrupo);
        radioEstado = (RadioGroup) findViewById(R.id.opciones_asistencia);
        radioSi = (RadioButton) findViewById(R.id.radio_Si);
        radioNo = (RadioButton) findViewById(R.id.radio_No);
        radioAsistencia = (RadioGroup) findViewById(R.id.opciones_estado);
        radioPendiente = (RadioButton) findViewById(R.id.radio_Pendiente);
        radioTerminada = (RadioButton) findViewById(R.id.radio_Terminada);

        Texto=(TextView) findViewById(R.id.editCodasignatura);
        Texto1=(TextView) findViewById(R.id.editCodciclo);
        Texto2=(TextView) findViewById(R.id.editNumeval);
        Texto3=(TextView) findViewById(R.id.editCoddocente);
        Texto4=(TextView) findViewById(R.id.editCarnet);
        Texto5=(TextView) findViewById(R.id.editNotaDespues);
        Texto6=(TextView) findViewById(R.id.editObservaciones);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    public void insertarPrimeraRevision(View v){
        String asignatura = editAsignatura.getText().toString();
        String ciclo = editCiclo.getText().toString();
        String numEval = editNumEval.getText().toString();
        String docente = editDocente.getText().toString();
        String carnet = editCarnet.getText().toString();
        String notafinal = editNotaFinal.getText().toString();
        String observaciones = editObservaciones.getText().toString();
        String tipoRevision = "PR";
        String regInsertados;

        PrimeraRevision primRev = new PrimeraRevision();
        primRev.setCodasignatura(asignatura);
        primRev.setCodciclo(ciclo);
        primRev.setCoddocente(docente);
        primRev.setCarnet(carnet);
        primRev.setObservacionesprimerarev(observaciones);
        primRev.setCodtiporevision(tipoRevision);


        if(!editNumEval.getText().toString().isEmpty()){
            primRev.setNumeroeval(Integer.parseInt(numEval));
        }else{
            primRev.setNumeroeval(0);
        }

        if(!editNotaFinal.getText().toString().isEmpty()){
            primRev.setNotadespuesprimerarevision(Float.parseFloat(notafinal));
        }else{
            primRev.setNotadespuesprimerarevision(0.0f);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoEval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            primRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            primRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            primRev.setCodtipoeval(tipoEval);
        }else{
            primRev.setCodtipoeval("");
        }

        if(spinMotCambNota.getSelectedItem().toString().equals("Error de suma")){
            String motivoCambNota = "ERRSUM";
            primRev.setMotivoCambioNota(motivoCambNota);
        }else if(spinMotCambNota.getSelectedItem().toString().equals("Error de elaboracion de preguntas")){
            String motivoCambNota = "ERRELPR";
            primRev.setMotivoCambioNota(motivoCambNota);
        }else if(spinMotCambNota.getSelectedItem().toString().equals("Error de calificacion")){
            String motivoCambNota = "ERRCAL";
            primRev.setMotivoCambioNota(motivoCambNota);
        }else{
            primRev.setMotivoCambioNota("");
        }

        if(spinTipoGrupo.getSelectedItem().toString().equals("GT")){
            String tipoGrupo = "GT";
            primRev.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GD")){
            String tipoGrupo = "GD";
            primRev.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GL")){
            String tipoGrupo = "GL";
            primRev.setCodtipogrupo(tipoGrupo);
        }else{
            primRev.setCodtipogrupo("");
        }

        if(radioSi.isChecked()){
            String asistencia = "SI";
            primRev.setAsistio(asistencia);
        }else if(radioNo.isChecked()){
            String asistencia = "NO";
            primRev.setAsistio(asistencia);
        }

        if(radioPendiente.isChecked()){
            String estado = "Pendiente";
            primRev.setEstadoprimerrevision(estado);
        }else if(radioTerminada.isChecked()){
            String estado = "Terminada";
            primRev.setEstadoprimerrevision(estado);
        }

        helper.abrir();
        regInsertados = helper.insertar(primRev);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        editDocente.setText("");
        editCarnet.setText("");
        editNotaFinal.setText("");
        editObservaciones.setText("");
        spinTipoEval.setSelection(0);
        spinMotCambNota.setSelection(0);
        spinTipoGrupo.setSelection(0);
        radioSi.setChecked(true);
        radioNo.setChecked(false);
        radioTerminada.setChecked(true);
        radioPendiente.setChecked(false);
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
                tts.speak(Texto6.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
