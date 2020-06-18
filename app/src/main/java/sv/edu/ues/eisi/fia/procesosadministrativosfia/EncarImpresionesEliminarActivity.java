package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EncarImpresionesEliminarActivity extends Activity {

    EditText editIdEncargado;
    ControladorBase controlhelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encar_impresiones_eliminar);
        controlhelper=new ControladorBase(this);
        editIdEncargado=(EditText)findViewById(R.id.editIdEncarImpresiones);

    }
    public void eliminarEncImpresiones(View v){

        editIdEncargado.setError(null);
        String idencargado=editIdEncargado.getText().toString();
        if(TextUtils.isEmpty(idencargado)){
            editIdEncargado.setError(getString(R.string.error_campo_obligatorio));
            editIdEncargado.requestFocus();
            return;
        }

        String regEliminadas;
        EncarImpresiones enc=new EncarImpresiones();

        enc.setIdencargado(editIdEncargado.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminarEncarImpresiones(enc);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
    public void limpiar(View v){

        editIdEncargado.setText("");


    }
}
