package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolImpresionEliminarActivity extends Activity {

    EditText editIdSolicitudImpresion;
    EditText editIdDocente;
    EditText editIdDocenteDirector;
    ControladorBase controlhelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_impresion_eliminar);
        controlhelper=new ControladorBase(this);
        editIdSolicitudImpresion=(EditText)findViewById(R.id.editIdSolicitudImpresion);
        editIdDocente=(EditText)findViewById(R.id.editIdDocente);
        editIdDocenteDirector=(EditText)findViewById(R.id.editIdDocenteDirector);
    }
    public void eliminarSolImpresion(View v){

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

        String regEliminadas;
        SolImpresion sol=new SolImpresion();
        sol.setIdsolicitudimpresion(editIdSolicitudImpresion.getText().toString());
        sol.setIddocente(editIdDocente.getText().toString());
        sol.setIddocentedirector(editIdDocenteDirector.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminarSolImpresion(sol);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){
        editIdSolicitudImpresion.setText("");
        editIdDocente.setText("");
        editIdDocenteDirector.setText("");
    }
}
