package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SegundaRevisionEliminar extends Activity {
    ControladorBase helper;

    EditText editAsignatura, editCiclo, editNumEval, editCarnet;
    Spinner spinTipoEval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_revision_eliminar);
        helper = new ControladorBase(this);
        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
    }

    public void eliminarSegundaRevision(View v){
        String regEliminados;
        SegundaRevision segRev = new SegundaRevision();
        segRev.setCodasignatura(editAsignatura.getText().toString());
        segRev.setCodciclo(editCiclo.getText().toString());
        segRev.setCarnet(editCarnet.getText().toString());
        segRev.setCodtiporevision("SR");

        if(!editNumEval.getText().toString().isEmpty()){
            segRev.setNumeroeval(Integer.parseInt(editNumEval.getText().toString()));
        }else{
            segRev.setNumeroeval(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoEval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            segRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            segRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            segRev.setCodtipoeval(tipoEval);
        }else{
            segRev.setCodtipoeval("");
        }

        helper.abrir();
        regEliminados = helper.eliminar(segRev);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        editCarnet.setText("");
        spinTipoEval.setSelection(0);
    }
}
