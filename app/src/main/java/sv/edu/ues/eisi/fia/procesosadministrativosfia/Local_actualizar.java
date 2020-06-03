package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Local_actualizar extends Activity{
    EditText editCodlocal, editNomlocal, editUbicacionlocal;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_actualizar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
        editUbicacionlocal = (EditText) findViewById(R.id.editUbicacionlocal);
    }

    public void actualizarLocal(View v){
        Local local = new Local();
        local.setCodlocal(editCodlocal.getText().toString());
        local.setNomlocal(editNomlocal.getText().toString());
        local.setUbicacionlocal(editUbicacionlocal.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(local);
        helper.cerrar();

        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
        editUbicacionlocal.setText("");
    }
}
