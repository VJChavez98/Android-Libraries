package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolImpresionConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdSolicitudImpresion;
    EditText editIdDocente;
    EditText editIdDocenteDirector;
    EditText editCantidadExamenes;
    EditText editHojasEmpaque;
    EditText editEstadoAprobacion;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_impresion_consultar);
        helper = new ControladorBase(this);
        editIdSolicitudImpresion = (EditText) findViewById(R.id.editIdSolicitudImpresion);
        editIdDocente = (EditText) findViewById(R.id.editIdDocente);
        editIdDocenteDirector = (EditText) findViewById(R.id.editIdDocenteDirector);
        editCantidadExamenes = (EditText) findViewById(R.id.editCantidadExamenes);
        editHojasEmpaque = (EditText) findViewById(R.id.editHojasEmpaque);
        editEstadoAprobacion = (EditText) findViewById(R.id.editEstadoAprobacion);
    }
    public void consultarSolImpresion(View v) {

        editIdSolicitudImpresion.setError(null);
        editIdDocente.setError(null);
        editIdDocenteDirector.setError(null);

        String idsolicitudimpresion = editIdSolicitudImpresion.getText().toString();
        String iddocente = editIdDocente.getText().toString();
        String iddocentedirector = editIdDocenteDirector.getText().toString();

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

        helper.abrir();
        SolImpresion solImpresion = helper.consultarSolImpresion(editIdSolicitudImpresion.getText().toString(), editIdDocente.getText().toString(), editIdDocenteDirector.getText().toString());

        helper.cerrar();
        if(solImpresion == null)
            Toast.makeText(this, "Solicitud no registrada", Toast.LENGTH_LONG).show();
        else{
            editCantidadExamenes.setText(String.valueOf(solImpresion.getCantidadexamenes()));
            editHojasEmpaque.setText(String.valueOf(solImpresion.getHojasempaque()));
            editEstadoAprobacion.setText(String.valueOf(solImpresion.getEstadoaprobacion()));
        }
    }

    public void limpiarTexto(View v){
        editIdSolicitudImpresion.setText("");
        editIdDocente.setText("");
        editIdDocenteDirector.setText("");
        editCantidadExamenes.setText("");
        editHojasEmpaque.setText("");
        editEstadoAprobacion.setText("");
    }
}
