package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class EstadoImpresionInsertarActivity extends AppCompatActivity {

    ControladorBase helper;
    EditText editIdEstadoImpresion;
    EditText editIdSolicitudImpresion;
    EditText editIdEncargado;
    EditText editIdMotivoImpresion;
    EditText editRealizado;
    EditText editObservaciones;
    Spinner spinner3;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2,Texto3, Texto4, Texto5;
    Button BtnPlay;
    private int numarch=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_impresion_insertar);
        helper = new ControladorBase(this);

        editIdEstadoImpresion = (EditText) findViewById(R.id.editIdEstadoImpresion);
        editIdSolicitudImpresion = (EditText) findViewById(R.id.editIdSolicitudImpresion);
        editIdEncargado = (EditText) findViewById(R.id.editIdEncargado);
        editIdMotivoImpresion = (EditText) findViewById(R.id.editIdMotivoImpresion);
        editRealizado = (EditText) findViewById(R.id.editRealizado);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        cargaSpinner();

        Texto=(TextView) findViewById(R.id.editIdEstadoImpresion);
        Texto1=(TextView) findViewById(R.id.editIdSolicitudImpresion);
        Texto2=(TextView) findViewById(R.id.editIdEncargado);
        Texto3=(TextView) findViewById(R.id.editIdMotivoImpresion);
        Texto4=(TextView) findViewById(R.id.editRealizado);
        Texto5=(TextView) findViewById(R.id.editObservaciones);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
    }

    private void cargaSpinner(){
        ArrayList<MiModelo> lista =new ArrayList<MiModelo>();
        lista.add(new MiModelo(1, "true"));
        lista.add(new MiModelo(1, "false"));

        ArrayAdapter<MiModelo> adapter = new ArrayAdapter<MiModelo>(this,
                R.layout.support_simple_spinner_dropdown_item, lista);
        spinner3.setAdapter(adapter);
    }

    public void insertarEstadoImpresion(View v) {

        editIdEstadoImpresion.setError(null);
        editIdSolicitudImpresion.setError(null);
        editIdEncargado.setError(null);
        editIdMotivoImpresion.setError(null);

        MiModelo miModelo=(MiModelo) spinner3.getSelectedItem();



        String idestadoimpresion = editIdEstadoImpresion.getText().toString();
        String idsolicitudimpresion = editIdSolicitudImpresion.getText().toString();
        String idencargado = editIdEncargado.getText().toString();
        String idmotivoimpresion = editIdMotivoImpresion.getText().toString();
        String observaciones = editObservaciones.getText().toString();
        String regInsertados;

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


        Boolean realizadoN =Boolean.valueOf(miModelo.toString());


        EstadoImpresion est = new EstadoImpresion();

        est.setIdestadoimpresion(idestadoimpresion);
        est.setIdsolicitudimpresion(idsolicitudimpresion);
        est.setIdencargado(idencargado);
        est.setIdmotivoimpresion(idmotivoimpresion);
        est.setRealizado(realizadoN);
        est.setObservaciones(observaciones);

        helper.abrir();
        regInsertados = helper.insertarEstadoImpresion(est);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdEstadoImpresion.setText("");
        editIdSolicitudImpresion.setText("");
        editIdEncargado.setText("");
        editIdMotivoImpresion.setText("");
        editObservaciones.setText("");
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