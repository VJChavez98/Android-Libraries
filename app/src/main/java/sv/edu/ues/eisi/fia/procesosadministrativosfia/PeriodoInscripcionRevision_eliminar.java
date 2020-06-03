package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PeriodoInscripcionRevision_eliminar extends Activity {
    EditText editCoddocente, editCodlocal, editCodasignatura, editCodciclo, editNumeval, editFechadesde, editFechahasta, editFecharev, editHorarev;
    Spinner spinTiporev, spinTipoeval;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodo_inscripcion_revision_eliminar);
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
    }

    public void eliminarPeriodoRevision(View v){
        String regEliminados;
        PeriodoInscripcionRevision perInscRev = new PeriodoInscripcionRevision();

        perInscRev.setCodAsignatura(editCodasignatura.getText().toString());
        perInscRev.setCodCiclo(editCodciclo.getText().toString());

        if(!editNumeval.getText().toString().isEmpty()){
            perInscRev.setNumeroEval(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            perInscRev.setNumeroEval(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            perInscRev.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            perInscRev.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            perInscRev.setCodTipoEval(tipoEval);
        }else{
            perInscRev.setCodTipoEval("");
        }

        if(spinTiporev.getSelectedItem().toString().equals("Primer Revisión")){
            String tipoEval = "PR";
            perInscRev.setTipoRevision(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Segunda Revisión")){
            String tipoEval = "SR";
            perInscRev.setTipoRevision(tipoEval);
        }else{
            perInscRev.setTipoRevision("");
        }

        helper.abrir();
        regEliminados = helper.eliminar(perInscRev);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_LONG).show();
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        editNumeval.setText("");
        spinTipoeval.setSelection(0);
        spinTiporev.setSelection(0);
    }
}
