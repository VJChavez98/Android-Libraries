package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DocenteEliminarActivity extends Activity {

    ControladorBase helper;
    EditText editCoddocente, editNombredocente, editApellidodocente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_eliminar);
        helper= new ControladorBase(this);
        editCoddocente=(EditText)findViewById(R.id.editCoddocente);
        editNombredocente=(EditText)findViewById(R.id.editNombredocente);
        editApellidodocente=(EditText)findViewById(R.id.editApellidodocente);

    }
    public void eliminarDocente(View v){
        String regEliminadas;
        Docente docente=new Docente();
        docente.setCoddocente(editCoddocente.getText().toString());
        docente.setNomdocente(editNombredocente.getText().toString());
        docente.setApellidodocente(editApellidodocente.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(docente);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){
        editCoddocente.setText("");
        editNombredocente.setText("");
        editApellidodocente.setText("");

    }
}
