package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SegundaRevisionInsertar extends Activity {
    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet, editNotaFinal, editObservaciones;
    Spinner spinTipoEval, spinMotCambNota, spinTipoGrupo;
    ControladorBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_revision_insertar);

        helper = new ControladorBase(this);
        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editCoddocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNotaFinal = (EditText) findViewById(R.id.editNotaDespues);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        spinMotCambNota = (Spinner) findViewById(R.id.spinMotCambioNota);
        spinTipoGrupo = (Spinner) findViewById(R.id.spinTipoGrupo);
    }

    public void insertarSegundaRevision(View v){
        String asignatura = editAsignatura.getText().toString();
        String ciclo = editCiclo.getText().toString();
        String numEval = editNumEval.getText().toString();
        String docente = editDocente.getText().toString();
        String carnet = editCarnet.getText().toString();
        String notafinal = editNotaFinal.getText().toString();
        String observaciones = editObservaciones.getText().toString();
        String tipoRevision = "SR";
        String regInsertados;

        SegundaRevision segundaRevision = new SegundaRevision();
        segundaRevision.setCarnet(carnet);
        segundaRevision.setCodasignatura(asignatura);
        segundaRevision.setCodciclo(ciclo);
        segundaRevision.setCoddocente(docente);
        segundaRevision.setObservacionessegundarev(observaciones);
        segundaRevision.setCodtiporevision(tipoRevision);

        if(!editNumEval.getText().toString().isEmpty()){
            segundaRevision.setNumeroeval(Integer.parseInt(numEval));
        }else{
            segundaRevision.setNumeroeval(0);
        }

        if(!editNotaFinal.getText().toString().isEmpty()){
            segundaRevision.setNotafinalsegundarev(Float.parseFloat(notafinal));
        }else{
            segundaRevision.setNotafinalsegundarev(0.0f);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoEval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            segundaRevision.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            segundaRevision.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            segundaRevision.setCodtipoeval(tipoEval);
        }else{
            segundaRevision.setCodtipoeval("");
        }

        if(spinMotCambNota.getSelectedItem().toString().equals("Error de suma")){
            String motivoCambNota = "ERRSUM";
            segundaRevision.setMotivoCambioNota(motivoCambNota);
        }else if(spinMotCambNota.getSelectedItem().toString().equals("Error de elaboracion de preguntas")){
            String motivoCambNota = "ERRELPR";
            segundaRevision.setMotivoCambioNota(motivoCambNota);
        }else if(spinMotCambNota.getSelectedItem().toString().equals("Error de calificacion")){
            String motivoCambNota = "ERRCAL";
            segundaRevision.setMotivoCambioNota(motivoCambNota);
        }else{
            segundaRevision.setMotivoCambioNota("");
        }

        if(spinTipoGrupo.getSelectedItem().toString().equals("GT")){
            String tipoGrupo = "GT";
            segundaRevision.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GD")){
            String tipoGrupo = "GD";
            segundaRevision.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GL")){
            String tipoGrupo = "GL";
            segundaRevision.setCodtipogrupo(tipoGrupo);
        }else{
            segundaRevision.setCodtipogrupo("");
        }

        helper.abrir();
        regInsertados = helper.insertar(segundaRevision);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        editDocente.setText("");
        editCarnet.setText("");
        editNotaFinal.setText("");
        editObservaciones.setText("");
        spinTipoEval.setSelection(0);
        spinMotCambNota.setSelection(0);
        spinTipoGrupo.setSelection(0);
    }
}
