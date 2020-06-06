package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EncarImpresionesInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEncargado;
    EditText editNombreEncargado;
    EditText editApellidoEncargado;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encar_impresiones_insertar);
        helper = new ControladorBase(this);
        editIdEncargado = (EditText) findViewById(R.id.editIdEncarImpresiones);
        editNombreEncargado = (EditText) findViewById(R.id.editNombreEncargado);
        editApellidoEncargado = (EditText) findViewById(R.id.editApellidoEncargado);

    }
    public void insertarEncarImpresiones(View v) {
        editIdEncargado.setError(null);
        editNombreEncargado.setError(null);
        editApellidoEncargado.setError(null);


        String idencargado=editIdEncargado.getText().toString();
        String nombreencargado=editNombreEncargado.getText().toString();
        String apellidoencargado=editApellidoEncargado.getText().toString();
        String regInsertados;

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

        EncarImpresiones enc=new EncarImpresiones();
        enc.setIdencargado(idencargado);
        enc.setNombreencargado(nombreencargado);
        enc.setApellidoencargado(apellidoencargado);

        helper.abrir();
        regInsertados=helper.insertarEncarImpresiones(enc);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdEncargado.setText("");
        editNombreEncargado.setText("");
        editApellidoEncargado.setText("");
    }
}
