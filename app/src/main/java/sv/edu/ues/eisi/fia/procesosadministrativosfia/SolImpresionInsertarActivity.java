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

public class SolImpresionInsertarActivity extends AppCompatActivity {

    ControladorBase helper;
    EditText editIdSolicitudImpresion;
    EditText editIdDocente;
    EditText editIdDocenteDirector;
    EditText editCantidadExamenes;
    EditText editHojasEmpaque;
    EditText editEstadoAprobacion;
    Spinner spinner;
    Spinner spinner2;
    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5;
    Button BtnPlay;
    private int numarch=0;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_impresion_insertar);
        helper = new ControladorBase(this);
        editIdSolicitudImpresion = (EditText) findViewById(R.id.editIdSolicitudImpresion);
        editIdDocente = (EditText) findViewById(R.id.editIdDocente);
        editIdDocenteDirector = (EditText) findViewById(R.id.editIdDocenteDirector);
        editCantidadExamenes = (EditText) findViewById(R.id.editCantidadExamenes);
        editHojasEmpaque = (EditText) findViewById(R.id.editHojasEmpaque);
        editEstadoAprobacion = (EditText) findViewById(R.id.editEstadoAprobacion);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        cargaSpinner();

        Texto=(TextView) findViewById(R.id.editIdSolicitudImpresion);
        Texto1=(TextView) findViewById(R.id.editIdDocente);
        Texto2=(TextView) findViewById(R.id.editIdDocenteDirector);
        Texto3=(TextView) findViewById(R.id.editCantidadExamenes);
        Texto4=(TextView) findViewById(R.id.editHojasEmpaque);
        Texto5=(TextView) findViewById(R.id.editEstadoAprobacion);
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
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

    }

    public void insertarSolicitudImpresion(View v) {

        editIdSolicitudImpresion.setError(null);
        editIdDocente.setError(null);
        editIdDocenteDirector.setError(null);
        editCantidadExamenes.setError(null);
        MiModelo miModelo=(MiModelo) spinner.getSelectedItem();
        MiModelo miModelo1=(MiModelo) spinner2.getSelectedItem();

        String idsolicitudimpresion = editIdSolicitudImpresion.getText().toString();
        String iddocente = editIdDocente.getText().toString();
        String iddocentedirector = editIdDocenteDirector.getText().toString();
        String cantidadexamenes = editCantidadExamenes.getText().toString();
        String regInsertados;

        if(TextUtils.isEmpty(idsolicitudimpresion)){
            editIdSolicitudImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdSolicitudImpresion.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(iddocente)){
            editIdDocente.setError(getString(R.string.error_campo_obligatorio));
            editIdDocente.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(iddocentedirector)){
            editIdDocenteDirector.setError(getString(R.string.error_campo_obligatorio));
            editIdDocenteDirector.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(cantidadexamenes)){
            editCantidadExamenes.setError(getString(R.string.error_campo_obligatorio));
            editCantidadExamenes.requestFocus();
            return;
        }

        Boolean hojasempaqueN =Boolean.valueOf(miModelo.toString());
        Integer cantidadexamenesN = Integer.valueOf(editCantidadExamenes.getText().toString());
        Boolean estadoaprobacionN = Boolean.valueOf(miModelo1.toString());

        if(cantidadexamenesN<1 || cantidadexamenesN>9999){

            editCantidadExamenes.setError(getString(R.string.error_valor_entre));
            editCantidadExamenes.requestFocus();
            return;
        }


        SolImpresion sol = new SolImpresion();
        sol.setIdsolicitudimpresion(idsolicitudimpresion);
        sol.setIddocente(iddocente);
        sol.setIddocentedirector(iddocentedirector);
        sol.setCantidadexamenes(cantidadexamenesN);
        sol.setHojasempaque(hojasempaqueN);
        sol.setEstadoaprobacion(estadoaprobacionN);

        helper.abrir();
        regInsertados = helper.insertar(sol);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdSolicitudImpresion.setText("");
        editIdDocenteDirector.setText("");
        editIdDocente.setText("");
        editCantidadExamenes.setText("");
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
                if(!areEmpty()) {
                    tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    tts.speak(Texto1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    tts.speak(Texto2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                }else Toast.makeText(v.getContext(), "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
    public boolean areEmpty(){
        return (editIdSolicitudImpresion.getText().toString().isEmpty()
                ||editIdDocenteDirector.getText().toString().isEmpty()
                ||editIdDocente.getText().toString().isEmpty()
                ||editCantidadExamenes.getText().toString().isEmpty()
        );
    }
}
