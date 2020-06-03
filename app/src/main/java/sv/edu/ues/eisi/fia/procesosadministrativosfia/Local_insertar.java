package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Local_insertar extends Activity {
    EditText editCodlocal, editNomlocal, editUbicacionLocal;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_insertar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
        editUbicacionLocal = (EditText) findViewById(R.id.editUbicacionlocal);
    }

    public void insertarLocal(View v){
        String codlocal = editCodlocal.getText().toString();
        String nomlocal = editNomlocal.getText().toString();
        String ubiclocal = editUbicacionLocal.getText().toString();
        String regInsertados;

        Local local = new Local();
        local.setCodlocal(codlocal);
        local.setNomlocal(nomlocal);
        local.setUbicacionlocal(ubiclocal);

        helper.abrir();
        regInsertados = helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
        editUbicacionLocal.setText("");
    }
}
