package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class CargaAcademicaActualizarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodCiclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_actualizar);
        helper= new ControladorBase(this);
        editIdcargaacademica= (EditText) findViewById(R.id.editIdcargaacademica);
        editCodCiclo= (EditText) findViewById(R.id.editCodciclo);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText) findViewById(R.id.editIdtipodocenteciclo);


    }
    public void actualizarCargaacademica(View v) {
        CargaAcademica cargaAcademica = new CargaAcademica();
        cargaAcademica.setIdcargaacademica(editIdcargaacademica.getText().toString());
        cargaAcademica.setCodciclo(editCodCiclo.getText().toString());
        cargaAcademica.setCoddocente(editCoddocente.getText().toString());
        cargaAcademica.setCodasignatura(editCodasignatura.getText().toString());
        cargaAcademica.setIdtipodocenteciclo(editIdtipodocenteciclo.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(cargaAcademica);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdcargaacademica.setText("");
        editCodCiclo.setText("");
        editCoddocente.setText("");
        editCodasignatura.setText("");
        editIdtipodocenteciclo.setText("");
    }

}
