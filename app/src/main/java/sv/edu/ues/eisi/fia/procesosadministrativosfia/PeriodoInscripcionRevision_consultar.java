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

public class PeriodoInscripcionRevision_consultar extends Activity {
    EditText editCoddocente, editCodlocal, editCodasignatura, editCodciclo, editNumeval, editFechadesde, editFechahasta, editFecharev, editHorarev;
    Spinner spinTiporev, spinTipoeval;
    ControladorBase helper;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodo_inscripcion_revision_consultar);
        helper = new ControladorBase(this);

        editCoddocente = (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        editFechadesde = (EditText) findViewById(R.id.editFechaDesdeRev);
        editFechahasta = (EditText) findViewById(R.id.editFechaHastaRev);
        editFecharev = (EditText) findViewById(R.id.editFechaRev);
        editHorarev = (EditText) findViewById(R.id.editHoraRevision);
        spinTiporev = (Spinner) findViewById(R.id.spinTipoRev);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);

        mEntradaVoz=findViewById(R.id.editCoddocente);
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
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Código del Docente ");
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

    public void consultarPeriodoRevision(View v){
        PeriodoInscripcionRevision perIns = new PeriodoInscripcionRevision();

        if(!editNumeval.getText().toString().isEmpty()){
            perIns.setNumeroEval(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            perIns.setNumeroEval(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            perIns.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            perIns.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            perIns.setCodTipoEval(tipoEval);
        }else{
            perIns.setCodTipoEval("");
        }

        if(spinTiporev.getSelectedItem().toString().equals("Primer Revisión")){
            String tipoEval = "PR";
            perIns.setTipoRevision(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Segunda Revisión")){
            String tipoEval = "SR";
            perIns.setTipoRevision(tipoEval);
        }else{
            perIns.setTipoRevision("");
        }

        helper.abrir();
        PeriodoInscripcionRevision perInscRev = helper.consultarPeriodoInscripcion(perIns.getTipoRevision(), editCodasignatura.getText().toString(), perIns.getCodTipoEval(), editCodciclo.getText().toString(), perIns.getNumeroEval());
        helper.cerrar();
        if(perInscRev == null){
            Toast.makeText(this, "Periodo de Inscripcion no registrado", Toast.LENGTH_LONG).show();
        }else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editCoddocente.setText(perInscRev.getCodDocente());
            editCodlocal.setText(perInscRev.getCodLocal());
            editFechadesde.setText(perInscRev.getFechaDesde());
            editFechahasta.setText(perInscRev.getFechaHasta());
            editFecharev.setText(perInscRev.getFechaRevision());
            editHorarev.setText(perInscRev.getHoraRevision());
        }
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodlocal.setText("");
        editNumeval.setText("");
        spinTipoeval.setSelection(0);
        spinTiporev.setSelection(0);
        editFechadesde.setText("");
        editFechahasta.setText("");
        editFecharev.setText("");
        editHorarev.setText("");
        findViewById(R.id.detalle).setVisibility(View.GONE);
    }
}
