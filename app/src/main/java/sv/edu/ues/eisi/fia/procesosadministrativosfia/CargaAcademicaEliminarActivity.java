package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargaAcademicaEliminarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodciclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_eliminar);
        helper= new ControladorBase(this);
        editIdcargaacademica=(EditText)findViewById(R.id.editIdcargaacademica);
        editCodciclo=(EditText)findViewById(R.id.editCodciclo);
        editCoddocente=(EditText)findViewById(R.id.editCoddocente);
        editCodasignatura=(EditText)findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText)findViewById(R.id.editIdtipodocenteciclo);

    }

    public void eliminarCargaacademica(View v){
        String regEliminadas;
        CargaAcademica cargaAcademica=new CargaAcademica();
        cargaAcademica.setIdcargaacademica(editIdcargaacademica.getText().toString());
        cargaAcademica.setCodciclo(editCodciclo.getText().toString());
        cargaAcademica.setCoddocente(editCoddocente.getText().toString());
        cargaAcademica.setCodasignatura(editCodasignatura.getText().toString());
        cargaAcademica.setIdtipodocenteciclo(editIdtipodocenteciclo.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(cargaAcademica);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){
        editIdcargaacademica.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodasignatura.setText("");
        editIdtipodocenteciclo.setText("");
    }

}
