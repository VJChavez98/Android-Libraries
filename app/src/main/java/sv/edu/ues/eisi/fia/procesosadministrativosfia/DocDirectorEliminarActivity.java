package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DocDirectorEliminarActivity extends Activity {

    EditText editIdDocenteDirector;
    EditText editIdEscuela;
    ControladorBase controlhelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_director_eliminar);
        controlhelper=new ControladorBase(this);
        editIdDocenteDirector=(EditText)findViewById(R.id.editIdDocenteDirector);
        editIdEscuela=(EditText)findViewById(R.id.editIdEscuela);

    }
    public void eliminarDocDirector(View v){

        editIdDocenteDirector.setError(null);
        editIdEscuela.setError(null);

        String iddocentedirector = editIdDocenteDirector.getText().toString();
        String idescuela = editIdEscuela.getText().toString();

        if (TextUtils.isEmpty(iddocentedirector)) {
            editIdDocenteDirector.setError(getString(R.string.error_campo_obligatorio));
            editIdDocenteDirector.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idescuela)) {
            editIdEscuela.setError(getString(R.string.error_campo_obligatorio));
            editIdEscuela.requestFocus();
            return;
        }


        String regEliminadas;
        DocDirector dir=new DocDirector();
        dir.setIddocentedirector(editIdDocenteDirector.getText().toString());
        dir.setIdescuela(editIdEscuela.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminarDocDirector(dir);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){
        editIdDocenteDirector.setText("");
        editIdEscuela.setText("");

    }
}
