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

public class PrimeraRevision_eliminar extends Activity {
    ControladorBase helper;

    EditText editAsignatura, editCiclo, editNumEval, editDocente, editCarnet;
    Spinner spinTipoEval, spinTipoGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_revision_eliminar);
        helper = new ControladorBase(this);
        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editDocente = (EditText) findViewById(R.id.editCoddocente);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        spinTipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        spinTipoGrupo = (Spinner) findViewById(R.id.spinTipoGrupo);
    }

    public void eliminarPrimeraRevision(View v){
        String regEliminados;
        PrimeraRevision primRev = new PrimeraRevision();
        primRev.setCodasignatura(editAsignatura.getText().toString());
        primRev.setCodciclo(editCiclo.getText().toString());
        primRev.setCoddocente(editDocente.getText().toString());
        primRev.setCarnet(editCarnet.getText().toString());
        primRev.setCodtiporevision("PR");

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

        if(spinTipoGrupo.getSelectedItem().toString().equals("GT")){
            String tipoGrupo = "GT";
            primRev.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GD")){
            String tipoGrupo = "GD";
            primRev.setCodtipogrupo(tipoGrupo);
        }else if(spinTipoGrupo.getSelectedItem().toString().equals("GL")){
            String tipoGrupo = "GL";
            primRev.setCodtipogrupo(tipoGrupo);
        }else{
            primRev.setCodtipogrupo("");
        }

        helper.abrir();
        regEliminados = helper.eliminar(primRev);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editNumEval.setText("");
        editDocente.setText("");
        editCarnet.setText("");
        spinTipoEval.setSelection(0);
        spinTipoGrupo.setSelection(0);
    }
}
