package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PeriodoInscripcionRevision_consultar extends Activity {
    EditText editCoddocente, editCodlocal, editCodasignatura, editCodciclo, editNumeval, editFechadesde, editFechahasta, editFecharev, editHorarev;
    Spinner spinTiporev, spinTipoeval;
    ControladorBase helper;

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
    }

    public void consultarPeriodoRevision(View v){
        String tipoEval = spinTipoeval.getSelectedItem().toString();
        String tipoRev = spinTiporev.getSelectedItem().toString();

        if(tipoEval == "Examen Parcial"){
            tipoEval = "EP";
        }else if(tipoEval == "Examen Discusion"){
            tipoEval = "ED";
        }else{
            tipoEval = "EL";
        }

        if(tipoRev == "Primera Revision"){
            tipoRev = "PR";
        }else{
            tipoRev = "SR";
        }

        helper.abrir();
        PeriodoInscripcionRevision perInscRev = helper.consultarPeriodoInscripcion(tipoRev, editCodasignatura.getText().toString(), tipoEval, editCodciclo.getText().toString(), Integer.parseInt(editNumeval.getText().toString()));
        helper.cerrar();
        if(perInscRev == null){
            Toast.makeText(this, "Periodo de Inscripcion no registrado", Toast.LENGTH_LONG).show();
        }else{
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
    }
}
