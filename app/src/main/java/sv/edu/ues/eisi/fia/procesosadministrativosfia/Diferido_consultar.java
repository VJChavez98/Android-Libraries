package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Diferido_consultar extends AppCompatActivity {

    EditText editCodEval, editCarnet, editCodMateria, editGT, editGD, editGL, editFechaEval, editHoraEval, editOtroMotivo;
    Spinner spinTipo, spinMotivo;
    ControladorBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_consultar);
        helper = new ControladorBase(this);
        editCodEval = (EditText) findViewById(R.id.editCodEva);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editCodMateria = (EditText) findViewById(R.id.editAsignatura);
        editGT = (EditText) findViewById(R.id.editGrupoTeorico);
        editGD = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGL = (EditText) findViewById(R.id.editGrupoLab);
        editFechaEval = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval = (EditText) findViewById(R.id.editHoraRealizada);
        editOtroMotivo = (EditText) findViewById(R.id.editMotivo);
        spinMotivo = (Spinner) findViewById(R.id.spinMotivos);
        spinTipo = (Spinner) findViewById(R.id.spinTipoEval);
        editCodMateria.setVisibility(View.INVISIBLE);
        editGT.setVisibility(View.INVISIBLE);
        editGD.setVisibility(View.INVISIBLE);
        editGL.setVisibility(View.INVISIBLE);
        editFechaEval.setVisibility(View.INVISIBLE);

    }

    public void consultarSolicitudDiferido(View view) {
        helper.abrir();
        SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(editCarnet.getText().toString(), editCodEval.getText().toString());
        helper.cerrar();
        if(solicitudDiferido == null)
            Toast.makeText(this, "Solicitud no encontrada", Toast.LENGTH_LONG).show();
        else{
            editCodMateria.setText(solicitudDiferido.getCodMateria());
            editGT.setText(solicitudDiferido.getGT());
            editGD.setText(solicitudDiferido.getGD());
            editGL.setText(solicitudDiferido.getGL());
            editFechaEval.setText(solicitudDiferido.getFechaEva());
            editHoraEval.setText(solicitudDiferido.getHoraEva());

            editOtroMotivo.setText(solicitudDiferido.getOtroMotivo());
        }


    }

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editCodEval.setText("");
        editCodMateria.setText("");
        editGT.setText("");
        editGD.setText("");
        editGL.setText("");
        editFechaEval.setText("");
        editHoraEval.setText("");
        spinMotivo.setSelection(0);
        spinTipo.setSelection(0);
        editOtroMotivo.setText("");
    }
}
