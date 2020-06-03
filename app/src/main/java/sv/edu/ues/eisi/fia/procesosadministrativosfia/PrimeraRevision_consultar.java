package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PrimeraRevision_consultar extends Activity {
    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet, editNotaFinal, editObservaciones, editEstado, editAsistencia, editMotCambio;
    Spinner spinTipoEval;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_revision_consultar);
        helper = new ControladorBase(this);

        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editNomdocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNotaFinal = (EditText) findViewById(R.id.editNotaFinal);
        editObservaciones = (EditText) findViewById(R.id.editObservaciones);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        editEstado = (EditText) findViewById(R.id.editEstado);
        editAsistencia = (EditText) findViewById(R.id.editAsitencia);
        editMotCambio = (EditText) findViewById(R.id.editMotivoCambio);
    }

    public void consultarPrimeraRevision(View v){
        PrimeraRevision primRev = new PrimeraRevision();
        if(!editNumEval.getText().toString().isEmpty()){
            primRev.setNumeroeval(Integer.parseInt(editNumEval.getText().toString()));
        }else{
            primRev.setNumeroeval(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoEval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            primRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            primRev.setCodtipoeval(tipoEval);
        }else if(spinTipoEval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            primRev.setCodtipoeval(tipoEval);
        }else{
            primRev.setCodtipoeval("");
        }

        helper.abrir();
        PrimeraRevision primer = helper.consultarPrimerRevision(editAsignatura.getText().toString(), editCiclo.getText().toString(), primRev.getNumeroeval(), editCarnet.getText().toString(), primRev.getCodtipoeval());
        helper.cerrar();
        if(primer == null){
            Toast.makeText(this, "Revision no Registrada", Toast.LENGTH_LONG).show();
        }else{
            editEstado.setText(primer.getEstadoprimerrevision());
            editAsistencia.setText(primer.getAsistio());
            editNotaFinal.setText(String.valueOf(primer.getNotadespuesprimerarevision()));
            editMotCambio.setText(primer.getMotivoCambioNota());
            editObservaciones.setText(primer.getObservacionesprimerarev());

            helper.abrir();
            Docente doc = helper.consultarNomDocente(primer.getCoddocente());
            MotivoCambioNota motCamb = helper.consultarMotCambNota(primer.getMotivoCambioNota());
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
        editEstado.setText("");
        editDocente.setText("");
        editAsistencia.setText("");
        editNotaFinal.setText("");
        editMotCambio.setText("");
        editObservaciones.setText("");
    }
}
