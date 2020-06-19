package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SegundaRevisionConsultar extends Activity {
    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet, editNotaFinal, editObservaciones, editMotCambio;
    Spinner spinTipoEval;
    ControladorBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_revision_consultar);
        helper = new ControladorBase(this);

        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editNomdocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNotaFinal = (EditText) findViewById(R.id.editNotaFinal);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        editMotCambio = (EditText) findViewById(R.id.editMotivoCambio);
    }

    public void consultarSegundaRevision(View v){
        SegundaRevision segRev = new SegundaRevision();
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
        SegundaRevision segunda = helper.consultarSegundaRevision(editAsignatura.getText().toString(), segRev.getCodtipoeval(), segRev.getNumeroeval(), editCiclo.getText().toString(), editCarnet.getText().toString());
        helper.cerrar();
        if(segunda == null){
            Toast.makeText(this, "Revision no Registrada.", Toast.LENGTH_LONG).show();
        }else{
            editNotaFinal.setText(String.valueOf(segunda.getNotafinalsegundarev()));
            editMotCambio.setText(segunda.getMotivoCambioNota());
            editObservaciones.setText(segunda.getObservacionessegundarev());

            helper.abrir();
            Docente doc = helper.consultarNomDocente(segunda.getCoddocente());
            MotivoCambioNota motCamb = helper.consultarMotCambNota(segunda.getMotivoCambioNota());
            helper.cerrar();
            if(doc == null){
                editDocente.setText("");
            }else{
                editDocente.setText(doc.getNomdocente()+" "+doc.getApellidodocente());
            }
            if(motCamb == null){
                editMotCambio.setText("");
            }else{
                editMotCambio.setText(motCamb.getMotivo());
            }
        }
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        spinTipoEval.setSelection(0);
        editCarnet.setText("");
        editDocente.setText("");
        editNotaFinal.setText("");
        editMotCambio.setText("");
        editObservaciones.setText("");
    }
}
