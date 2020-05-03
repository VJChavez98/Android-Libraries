package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Evaluacion_eliminar extends Activity {
    ControladorBase helper;
    EditText editCodAsignatura, editCodciclo, editNumeval;
    Spinner spinTipoeval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_eliminar);
        helper = new ControladorBase(this);
        editCodAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);
    }

    public void eliminarEvaluacion(View v){
        String regEliminados;
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setCodAsignatura(editCodAsignatura.getText().toString());
        evaluacion.setCodCiclo(editCodciclo.getText().toString());
        evaluacion.setNumeroEvaluacion(Integer.parseInt(editNumeval.getText().toString()));

        String tipoEval = spinTipoeval.getSelectedItem().toString();
        if(tipoEval == "Examen Parcial"){
            tipoEval = "EP";
        }else if(tipoEval == "Examen Discusion"){
            tipoEval = "ED";
        }else{
            tipoEval = "EL";
        }
        evaluacion.setCodTipoEval(tipoEval);

        helper.abrir();
        regEliminados = helper.eliminar(evaluacion);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodAsignatura.setText("");
        editCodciclo.setText("");
        spinTipoeval.setSelection(0);
        editNumeval.setText("");
    }
}
