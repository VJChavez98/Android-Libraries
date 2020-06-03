package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Local_consultar extends Activity {
    EditText editCodlocal, editNomlocal, editUbicacionlocal;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_consultar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
        editUbicacionlocal = (EditText) findViewById(R.id.editUbicacionlocal);
    }

    public void consultarLocal(View v){
        helper.abrir();
        Local local = helper.consultarLocal(editCodlocal.getText().toString());
        helper.cerrar();
        if(local == null){
            Toast.makeText(this, "Local no registrado", Toast.LENGTH_SHORT).show();
        }else{
            editNomlocal.setText(local.getNomlocal());
            editUbicacionlocal.setText(local.getUbicacionlocal());
        }
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
        editUbicacionlocal.setText("");
    }
}
