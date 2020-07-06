package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EstadoImpresionEliminarActivity extends Activity {

    EditText editIdEstadoImpresion;
    EditText editIdSolicitudImpresion;
    EditText editIdEncargado;
    EditText editIdMotivoImpresion;
    ControladorBase controlhelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_impresion_eliminar);

        controlhelper=new ControladorBase(this);

        editIdEstadoImpresion=(EditText)findViewById(R.id.editIdEstadoImpresion);
        editIdSolicitudImpresion=(EditText)findViewById(R.id.editIdSolicitudImpresion);
        editIdEncargado=(EditText)findViewById(R.id.editIdEncargado);
        editIdMotivoImpresion=(EditText)findViewById(R.id.editIdMotivoImpresion);
    }
    public void eliminarEstadoImpresion(View v){

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


        String regEliminadas;
        EstadoImpresion est=new EstadoImpresion();

        est.setIdestadoimpresion(editIdEstadoImpresion.getText().toString());
        est.setIdsolicitudimpresion(editIdSolicitudImpresion.getText().toString());
        est.setIdencargado(editIdEncargado.getText().toString());
        est.setIdmotivoimpresion(editIdMotivoImpresion.getText().toString());

        controlhelper.abrir();
        regEliminadas=controlhelper.eliminarEstadoImpresion(est);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v){
        editIdEstadoImpresion.setText("");
        editIdSolicitudImpresion.setText("");
        editIdEncargado.setText("");
        editIdMotivoImpresion.setText("");
    }
}
