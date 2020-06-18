package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SolicitudRevision_eliminar extends Activity {

    EditText editCarnet, editCodasignatura, editCodciclo, editNumeval;
    Spinner spinTiporev, spinTipoeval, spinTipogrupo;
    ControladorBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_revision_eliminar);

        helper = new ControladorBase(this);

        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        spinTiporev = (Spinner) findViewById(R.id.spinTipoRev);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);
        spinTipogrupo = (Spinner) findViewById(R.id.spinTipoGrupo);
    }

    public void eliminarSolicitudRevision(View v){
        String regEliminados;

        SolicitudRevision solRev = new SolicitudRevision();

        solRev.setCodtiporevision(spinTiporev.getSelectedItem().toString());
        solRev.setCodasignatura(editCodasignatura.getText().toString());
        solRev.setCodtipoeval(spinTipoeval.getSelectedItem().toString());
        solRev.setCodtipogrupo(spinTipogrupo.getSelectedItem().toString());
        solRev.setCodciclo(editCodciclo.getText().toString());
        //solRev.setNumeroeval(Integer.parseInt(editNumeval.getText().toString()));
        solRev.setCarnet(editCarnet.getText().toString());

        if(!editNumeval.getText().toString().isEmpty()){
            solRev.setNumeroeval(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            solRev.setNumeroeval(0);
        }

        helper.abrir();
        regEliminados = helper.eliminar(solRev);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_LONG).show();
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        editNumeval.setText("");
        spinTipoeval.setSelection(0);
        spinTiporev.setSelection(0);
        spinTipogrupo.setSelection(0);
        editCarnet.setText("");
    }
}
