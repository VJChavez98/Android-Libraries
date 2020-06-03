package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargaAcademicaInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodciclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_insertar);

        helper= new ControladorBase(this);
        editIdcargaacademica= (EditText) findViewById(R.id.editIdcargaacademica);
        editCodciclo= (EditText) findViewById(R.id.editCodciclo);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText) findViewById(R.id.editIdtipodocenteciclo);

    }

    public void insertarCargaacademica(View v){
        String regInsertados;
        String idcargaacademica=editIdcargaacademica.getText().toString();
        String codciclo=editCodciclo.getText().toString();
        String coddocente=editCoddocente.getText().toString();
        String codasignatura=editCodasignatura.getText().toString();
        String idtipodocenteciclo=editIdtipodocenteciclo.getText().toString();
        CargaAcademica cargaAcademica= new CargaAcademica();
        cargaAcademica.setIdcargaacademica(idcargaacademica);
        cargaAcademica.setCodciclo(codciclo);
        cargaAcademica.setCoddocente(coddocente);
        cargaAcademica.setCodasignatura(codasignatura);
        cargaAcademica.setIdtipodocenteciclo(idtipodocenteciclo);
        helper.abrir();
        regInsertados=helper.insertar(cargaAcademica);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdcargaacademica.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodasignatura.setText("");
        editIdtipodocenteciclo.setText("");
    }
}
