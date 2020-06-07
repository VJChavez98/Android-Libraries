package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EncarImpresionesConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEncargado;
    EditText editNombreEncargado;
    EditText editApellidoEncargado;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_encar_impresiones_consultar);
        helper = new ControladorBase(this);

        editIdEncargado = (EditText) findViewById(R.id.editIdEncarImpresiones);
        editNombreEncargado = (EditText) findViewById(R.id.editNombreEncargado);
        editApellidoEncargado = (EditText) findViewById(R.id.editApellidoEncargado);

    }
    public void consultarEncImpresiones(View v) {

        editIdEncargado.setError(null);
        String idencargado=editIdEncargado.getText().toString();
        if(TextUtils.isEmpty(idencargado)){
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        helper.abrir();
        EncarImpresiones enc = helper.consultarEncarImpresiones(editIdEncargado.getText().toString());
        helper.cerrar();
        if(enc == null)
            Toast.makeText(this, "Encargado con id " + editIdEncargado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombreEncargado.setText(enc.getNombreencargado());
            editApellidoEncargado.setText(enc.getApellidoencargado());


        }
    }
    public void limpiarTexto(View v){
        editIdEncargado.setText("");
        editNombreEncargado.setText("");
        editApellidoEncargado.setText("");
    }

}
