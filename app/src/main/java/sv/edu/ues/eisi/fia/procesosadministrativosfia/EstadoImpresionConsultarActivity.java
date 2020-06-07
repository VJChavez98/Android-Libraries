package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EstadoImpresionConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdEstadoImpresion;
    EditText editIdSolicitudImpresion;
    EditText editIdEncargado;
    EditText editIdMotivoImpresion;
    EditText editRealizado;
    EditText editObservaciones;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_impresion_consultar);
        helper = new ControladorBase(this);

        editIdEstadoImpresion = (EditText) findViewById(R.id.editIdEstadoImpresion);
        editIdSolicitudImpresion = (EditText) findViewById(R.id.editIdSolicitudImpresion);
        editIdEncargado = (EditText) findViewById(R.id.editIdEncargado);
        editIdMotivoImpresion = (EditText) findViewById(R.id.editIdMotivoImpresion);
        editRealizado = (EditText) findViewById(R.id.editRealizado);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
    }
    public void consultarEstadoImpresion(View v) {

        editIdEstadoImpresion.setError(null);
        editIdSolicitudImpresion.setError(null);
        editIdEncargado.setError(null);
        editIdMotivoImpresion.setError(null);

        String idestadoimpresion = editIdEstadoImpresion.getText().toString();
        String idsolicitudimpresion = editIdSolicitudImpresion.getText().toString();
        String idencargado = editIdEncargado.getText().toString();
        String idmotivoimpresion = editIdMotivoImpresion.getText().toString();

        if (TextUtils.isEmpty(idestadoimpresion)) {
            editIdEstadoImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdEstadoImpresion.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idsolicitudimpresion)) {
            editIdSolicitudImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdSolicitudImpresion.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idencargado)) {
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idmotivoimpresion)) {
            editIdMotivoImpresion.setError(getString(R.string.error_campo_obligatorio));
            editIdMotivoImpresion.requestFocus();
            return;
        }

        helper.abrir();
        EstadoImpresion estadoImpresion = helper.consultarEstadoImpresion(editIdEstadoImpresion.getText().toString(), editIdSolicitudImpresion.getText().toString(), editIdEncargado.getText().toString(), editIdMotivoImpresion.getText().toString());

        helper.cerrar();

        if(estadoImpresion == null)
            Toast.makeText(this, "Estado de Impresion no registrado", Toast.LENGTH_LONG).show();
        else{
            editRealizado.setText(String.valueOf(estadoImpresion.getRealizado()));
            editObservaciones.setText(String.valueOf(estadoImpresion.getObservaciones()));
        }
    }

    public void limpiarTexto(View v){
        editIdEstadoImpresion.setText("");
        editIdSolicitudImpresion.setText("");
        editIdEncargado.setText("");
        editIdMotivoImpresion.setText("");
        editRealizado.setText("");
        editObservaciones.setText("");
    }
}
