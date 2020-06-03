package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
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

        if(!editNumeval.getText().toString().isEmpty()){
            evaluacion.setNumeroEvaluacion(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            evaluacion.setNumeroEvaluacion(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            evaluacion.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            evaluacion.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            evaluacion.setCodTipoEval(tipoEval);
        }else{
            evaluacion.setCodTipoEval("");
        }

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
