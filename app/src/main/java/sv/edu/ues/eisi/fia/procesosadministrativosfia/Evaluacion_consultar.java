package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Evaluacion_consultar extends Activity {
    EditText editCodasignatura, editCodciclo, editNumeval, editFechaeval, editNomasignatura;
    Spinner spinTipoeval;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_consultar);
        helper = new ControladorBase(this);

        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        editFechaeval = (EditText) findViewById(R.id.editFechaeval);
        editNomasignatura = (EditText) findViewById(R.id.editNomasignatura);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);
    }

    public void consultarEvaluacion(View v){
        Evaluacion eval = new Evaluacion();
        if(!editNumeval.getText().toString().isEmpty()){
            eval.setNumeroEvaluacion(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            eval.setNumeroEvaluacion(0);
        }

        helper.abrir();
        Evaluacion evaluacion = helper.consultarEvaluacion(editCodasignatura.getText().toString(), editCodciclo.getText().toString(), spinTipoeval.getSelectedItem().toString(), eval.getNumeroEvaluacion());
        Asignatura asignatura = helper.consultarNomAsignatura(editCodasignatura.getText().toString());
        helper.cerrar();
        if(evaluacion == null){
            Toast.makeText(this, "Evaluacion no registrada", Toast.LENGTH_LONG).show();
        }else{
            editFechaeval.setText(evaluacion.getFechaEvaluacion());
            editNomasignatura.setText(asignatura.getNomasignatura());
        }
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        spinTipoeval.setSelection(0);
        editNumeval.setText("");
        editFechaeval.setText("");
        editNomasignatura.setText("");
    }
}
