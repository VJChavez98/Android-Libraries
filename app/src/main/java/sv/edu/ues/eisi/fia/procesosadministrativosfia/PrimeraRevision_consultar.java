package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class PrimeraRevision_consultar extends Activity {
    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet, editNotaFinal, editObservaciones, editEstado, editAsistencia, editMotCambio;
    Spinner spinTipoEval, spinTipoGrupo;
    ControladorBase helper;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_revision_consultar);
        helper = new ControladorBase(this);

        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editNomdocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNotaFinal = (EditText) findViewById(R.id.editNotaFinal);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        spinTipoGrupo = (Spinner) findViewById(R.id.spinTipoGrupo);
        editEstado = (EditText) findViewById(R.id.editEstado);
        editAsistencia = (EditText) findViewById(R.id.editAsitencia);
        editMotCambio = (EditText) findViewById(R.id.editMotivoCambio);

        mEntradaVoz=findViewById(R.id.editCodasignatura);
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
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Código de la asignatura");
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

    public void consultarPrimeraRevision(View v){
        PrimeraRevision primRev = new PrimeraRevision();
        if(!editNumEval.getText().toString().isEmpty()){
            primRev.setNumeroeval(Integer.parseInt(editNumEval.getText().toString()));
        }else{
            primRev.setNumeroeval(0);
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

        helper.abrir();
        PrimeraRevision primer = helper.consultarPrimerRevision(editAsignatura.getText().toString(), editCiclo.getText().toString(), primRev.getNumeroeval(), editCarnet.getText().toString(), primRev.getCodtipoeval(), primRev.getCodtipogrupo());
        helper.cerrar();
        if(primer == null){
            Toast.makeText(this, "Revision no Registrada", Toast.LENGTH_LONG).show();
        }else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editEstado.setText(primer.getEstadoprimerrevision());
            editAsistencia.setText(primer.getAsistio());
            editNotaFinal.setText(String.valueOf(primer.getNotadespuesprimerarevision()));
            editMotCambio.setText(primer.getMotivoCambioNota());
            editObservaciones.setText(primer.getObservacionesprimerarev());

            helper.abrir();
            Docente doc = helper.consultarNomDocente(primer.getCoddocente());
            MotivoCambioNota motCamb = helper.consultarMotCambNota(primer.getMotivoCambioNota());
            helper.cerrar();
            if(doc == null){
                editDocente.setText("");
            }else{
                editDocente.setText(doc.getNomdocente()+" "+doc.getApellidodocente());
            }
            if(motCamb == null){
                editMotCambio.setText("");
            }else{
                editMotCambio.setText(motCamb.getMotivo());
            }
        }
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        spinTipoEval.setSelection(0);
        spinTipoGrupo.setSelection(0);
        editCarnet.setText("");
        editEstado.setText("");
        editDocente.setText("");
        editAsistencia.setText("");
        editNotaFinal.setText("");
        editMotCambio.setText("");
        editObservaciones.setText("");
    }
}
