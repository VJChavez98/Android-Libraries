package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsignaturaConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editCodasignatura, editNombreasignatura, editUnidadesval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_consultar);
        helper= new ControladorBase(this);
        editCodasignatura= (EditText) findViewById(R.id.editCodasignatura);
        editNombreasignatura=(EditText) findViewById(R.id.editNombreasignatura);
        editUnidadesval=(EditText) findViewById(R.id.editUnidadesval);
    }
    public  void consultarAsignatura(View v){
        helper.abrir();
        Asignatura asignatura=
                helper.consultarAsignatura(editCodasignatura.getText().toString());
        helper.cerrar();
        if(asignatura==null)
            Toast.makeText(this,"Asignatura con Código"+editCodasignatura.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();

        else{

            editCodasignatura.setText(asignatura.getCodasignatura());
            editNombreasignatura.setText(asignatura.getNomasignatura());
            editUnidadesval.setText(asignatura.getUnidadesval());
        }
    }
    public void limpiarTexto(View v) {
        editCodasignatura.setText("");
        editNombreasignatura.setText("");
        editUnidadesval.setText("");
    }
}
