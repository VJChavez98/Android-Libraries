package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EncarImpresionesActualizarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEncargado;
    EditText editNombreEncargado;
    EditText editApellidoEncargado;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encar_impresiones_actualizar);

        helper = new ControladorBase(this);
        editIdEncargado= (EditText) findViewById(R.id.editIdEncarImpresiones);
        editNombreEncargado = (EditText) findViewById(R.id.editNombreEncargado);
        editApellidoEncargado = (EditText) findViewById(R.id.editApellidoEncargado);

    }
    public void actualizarEncarImpresiones(View v) {

        editIdEncargado.setError(null);
        editNombreEncargado.setError(null);
        editApellidoEncargado.setError(null);

        String idencargado=editIdEncargado.getText().toString();
        String nombreencargado=editNombreEncargado.getText().toString();
        String apellidoencargado=editApellidoEncargado.getText().toString();

        if(TextUtils.isEmpty(idencargado)){
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(nombreencargado)){
            editNombreEncargado.setError(getString(R.string.error_campo_obligatorio));
            editNombreEncargado.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(apellidoencargado)){
            editApellidoEncargado.setError(getString(R.string.error_campo_obligatorio));
            editApellidoEncargado.requestFocus();
            return;
        }

        EncarImpresiones enc = new EncarImpresiones();
        enc.setIdencargado(editIdEncargado.getText().toString());
        enc.setNombreencargado(editNombreEncargado.getText().toString());
        enc.setApellidoencargado(editApellidoEncargado.getText().toString());

        helper.abrir();
        String estado = helper.actualizarEncarImpresiones(enc);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdEncargado.setText("");
        editNombreEncargado.setText("");
        editApellidoEncargado.setText("");
    }

}
