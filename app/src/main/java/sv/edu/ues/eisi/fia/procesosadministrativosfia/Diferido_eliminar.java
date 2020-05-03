package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Diferido_eliminar extends AppCompatActivity {
    EditText editCarnet, editCodEval;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_eliminar);
        helper = new ControladorBase(this);
        editCarnet=(EditText) findViewById(R.id.editCarnet);
        editCodEval=(EditText) findViewById(R.id.editCodEva);
    }

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editCodEval.setText("");
    }

    public void eliminarSolicitudDiferido(View view) {
        String regAfectados;
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
        solicitudDiferido.setIdSolicitud(editCarnet.getText().toString()+editCodEval.getText().toString());
        helper.abrir();
        regAfectados=helper.eliminar(solicitudDiferido);
        helper.cerrar();
        Toast.makeText(this,regAfectados,Toast.LENGTH_SHORT).show();

    }
}
