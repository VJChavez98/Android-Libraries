package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DocenteInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editCoddocente, editNombredocente, editApellidodocente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_insertar);
        helper= new ControladorBase(this);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editNombredocente= (EditText) findViewById(R.id.editNombredocente);
        editApellidodocente= (EditText) findViewById(R.id.editApellidodocente);

    }

    public void insertarDocente(View v){

        String coddocente=editCoddocente.getText().toString();
        String nombredocente=editNombredocente.getText().toString();
        String apellidodocente=editApellidodocente.getText().toString();
        String regInsertados;
        Docente docente= new Docente();
        docente.setCoddocente(coddocente);
        docente.setNomdocente(nombredocente);
        docente.setApellidodocente(apellidodocente);
        helper.abrir();
        regInsertados=helper.insertar(docente);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editCoddocente.setText("");
        editNombredocente.setText("");
        editApellidodocente.setText("");
    }
}
