package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargaAcademicaConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdcargaacademica, editCodciclo, editCoddocente, editCodasignatura, editIdtipodocenteciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_academica_consultar);

        helper= new ControladorBase(this);
        editIdcargaacademica= (EditText) findViewById(R.id.editIdcargaacademica);
        editCodciclo= (EditText) findViewById(R.id.editCodciclo);
        editCoddocente= (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editIdtipodocenteciclo=(EditText) findViewById(R.id.editIdtipodocenteciclo);

    }

    public  void consultarCargaacademica(View v){
        helper.abrir();
        CargaAcademica cargaAcademica=
                helper.consultarCargaAcademica(editIdcargaacademica.getText().toString());
        helper.cerrar();
        if(cargaAcademica==null)
            Toast.makeText(this,"Carga Académica con Código "+editIdcargaacademica.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();

        else{
            editIdcargaacademica.setText(cargaAcademica.getIdcargaacademica());
            editCodciclo.setText(cargaAcademica.getCodciclo());
            editCoddocente.setText(cargaAcademica.getCoddocente());
            editCodasignatura.setText(cargaAcademica.getCodasignatura());
            editIdtipodocenteciclo.setText(cargaAcademica.getIdtipodocenteciclo());
        }
    }
    public void limpiar(View v){
        editIdcargaacademica.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodasignatura.setText("");
        editIdtipodocenteciclo.setText("");
    }
}
