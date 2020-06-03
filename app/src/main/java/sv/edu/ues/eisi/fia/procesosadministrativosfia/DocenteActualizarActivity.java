package sv.edu.ues.eisi.fia.procesosadministrativosfia;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DocenteActualizarActivity extends Activity {

    ControladorBase helper;
    EditText editCoddocente, editNombredocente, editApellidodocente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_actualizar);
        helper= new ControladorBase(this);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editNombredocente= (EditText) findViewById(R.id.editNombredocente);
        editApellidodocente= (EditText) findViewById(R.id.editApellidodocente);

    }
    public void actualizarDocente(View v) {
        Docente docente = new Docente();

        docente.setCoddocente(editCoddocente.getText().toString());
        docente.setNomdocente(editNombredocente.getText().toString());
        docente.setApellidodocente(editApellidodocente.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(docente);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {

        editCoddocente.setText("");
        editNombredocente.setText("");
        editApellidodocente.setText("");
    }
}
