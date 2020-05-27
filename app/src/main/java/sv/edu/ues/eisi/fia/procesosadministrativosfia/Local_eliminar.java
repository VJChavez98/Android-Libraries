package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Local_eliminar extends Activity {
    EditText editCodlocal, editNomlocal;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_eliminar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
    }

    public void eliminarLocal(View v){
        String regEliminados;
        Local local = new Local();
        local.setCodlocal(editCodlocal.getText().toString());
        local.setNomlocal(editNomlocal.getText().toString());
        helper.abrir();
        regEliminados = helper.eliminar(local);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
    }
}
