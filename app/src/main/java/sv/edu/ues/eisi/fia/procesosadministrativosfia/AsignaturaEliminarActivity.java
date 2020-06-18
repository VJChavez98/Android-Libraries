package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsignaturaEliminarActivity extends Activity {

    ControladorBase helper;
    EditText editCodasignatura, editNombreasignatura, editUnidadesval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_eliminar);
        helper= new ControladorBase(this);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editNombreasignatura= (EditText) findViewById(R.id.editNombreasignatura);
        editUnidadesval= (EditText) findViewById(R.id.editUnidadesval);

    }
    public void eliminarAsignatura(View v){
        String regEliminadas;
        Asignatura asignatura = new Asignatura();
        asignatura.setCodasignatura(editCodasignatura.getText().toString());
        asignatura.setNomasignatura(editNombreasignatura.getText().toString());
        asignatura.setUnidadesval(editUnidadesval.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(asignatura);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editCodasignatura.setText("");
        editNombreasignatura.setText("");
        editUnidadesval.setText("");
    }
}
