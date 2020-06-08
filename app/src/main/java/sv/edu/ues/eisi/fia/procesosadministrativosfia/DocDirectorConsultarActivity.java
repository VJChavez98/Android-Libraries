package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DocDirectorConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdDocenteDirector;
    EditText editIdEscuela;
    EditText editNombreDirector;
    EditText editApellidoDirector;
    EditText editCorreoDirector;
    EditText editTelefono;
    LinearLayout linear;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_director_consultar);
        helper = new ControladorBase(this);
        editIdDocenteDirector = (EditText) findViewById(R.id.editIdDocenteDirector);
        editIdEscuela = (EditText) findViewById(R.id.editIdEscuela);
        editNombreDirector = (EditText) findViewById(R.id.editNombreDirector);
        editApellidoDirector = (EditText) findViewById(R.id.editApellidoDirector);
        editCorreoDirector = (EditText) findViewById(R.id.editCorreoDirector);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        linear = findViewById(R.id.detalle);
    }
    public void consultarDocDirector(View v) {

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

        
        helper.abrir();
        DocDirector docDirector = helper.consultarDocDirector(editIdDocenteDirector.getText().toString(), editIdEscuela.getText().toString());

        helper.cerrar();
        if(docDirector == null)
            Toast.makeText(this, "Docente no registrado", Toast.LENGTH_LONG).show();
        else{
            linear.setVisibility(View.VISIBLE);
            editNombreDirector.setText(String.valueOf(docDirector.getNombredirector()));
            editApellidoDirector.setText(String.valueOf(docDirector.getApellidodirector()));
            editCorreoDirector.setText(String.valueOf(docDirector.getCorreodirector()));
            editTelefono.setText(String.valueOf(docDirector.getTelefono()));
        }
    }

    public void limpiarTexto(View v){
        editIdDocenteDirector.setText("");
        editIdEscuela.setText("");
        editNombreDirector.setText("");
        editApellidoDirector.setText("");
        editCorreoDirector.setText("");
        editTelefono.setText("");
        linear.setVisibility(View.GONE);
    }
}
