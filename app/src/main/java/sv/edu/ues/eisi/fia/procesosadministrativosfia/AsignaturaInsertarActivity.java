package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsignaturaInsertarActivity extends Activity {
    ControladorBase helper;
    EditText editCodasignatura, editNombreasignatura, editUnidadesval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_insertar);
        helper= new ControladorBase(this);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editNombreasignatura= (EditText) findViewById(R.id.editNombreasignatura);
        editUnidadesval= (EditText) findViewById(R.id.editUnidadesval);
    }
    public void insertarAsignatura(View v){

        String codasignatura=editCodasignatura.getText().toString();
        String nombreasignatura=editNombreasignatura.getText().toString();
        String unidadesval=editUnidadesval.getText().toString();
        String regInsertados;
        Asignatura asignatura= new Asignatura();
        asignatura.setCodasignatura(codasignatura);
        asignatura.setNomasignatura(nombreasignatura);
        asignatura.setUnidadesval(unidadesval);
        helper.abrir();
        regInsertados=helper.insertar(asignatura);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editCodasignatura.setText("");
        editNombreasignatura.setText("");
        editUnidadesval.setText("");
    }
}
