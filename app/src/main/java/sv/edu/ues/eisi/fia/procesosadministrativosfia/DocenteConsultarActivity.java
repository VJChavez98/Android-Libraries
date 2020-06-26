package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DocenteConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editCoddocente, editNombredocente, editApellidodocente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_consultar);
        helper= new ControladorBase(this);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editNombredocente=(EditText) findViewById(R.id.editNombredocente);
        editApellidodocente=(EditText) findViewById(R.id.editApellidodocente);

    }
    public  void consultarDocente(View v){
        helper.abrir();
        Docente docente=
                helper.consultarDocente(editCoddocente.getText().toString());
        helper.cerrar();
        if(docente==null)
            Toast.makeText(this,"Docente con Código"+editCoddocente.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();

        else{
            editCoddocente.setText(docente.getCoddocente());
            editNombredocente.setText(docente.getNomdocente());
            editApellidodocente.setText(docente.getApellidodocente());
        }
    }
    public void limpiarTexto(View v){

        editCoddocente.setText("");
        editNombredocente.setText("");
        editApellidodocente.setText("");
    }
}
