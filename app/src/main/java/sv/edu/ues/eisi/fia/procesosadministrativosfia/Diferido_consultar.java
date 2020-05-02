package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Diferido_consultar extends AppCompatActivity {

    EditText editCodEval, editCarnet, editCodMateria, editGT, editGD, editGL, editFechaEval, editHoraEval, editOtroMotivo, editTipo, editMotivo;
    ControladorBase helper;
    TextView lblMateria, lblTipoEva, lblGT,lblGD,lblGL, lblFecha, lblHora, lblMotivo, lblOtro;

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
        editMotivo = (EditText) findViewById(R.id.editMotivos);
        editTipo = (EditText) findViewById(R.id.editTipoEvaluacion);

        lblMateria = (TextView) findViewById(R.id.lblCodMat);
        lblTipoEva = (TextView) findViewById(R.id.lblTipoEval);
        lblGT = (TextView) findViewById(R.id.lblGT);
        lblGD = (TextView) findViewById(R.id.lblGD);
        lblGL =(TextView) findViewById(R.id.lblGL);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblHora = (TextView) findViewById(R.id.lblHora);
        lblMotivo = (TextView) findViewById(R.id.lblMotivo);
        lblOtro = (TextView) findViewById(R.id.lblOtro);

        lblMateria.setVisibility(View.INVISIBLE);
        lblTipoEva.setVisibility(View.INVISIBLE);
        lblGT.setVisibility(View.INVISIBLE);
        lblGD.setVisibility(View.INVISIBLE);
        lblGL.setVisibility(View.INVISIBLE);
        lblFecha.setVisibility(View.INVISIBLE);
        lblHora.setVisibility(View.INVISIBLE);
        lblMotivo.setVisibility(View.INVISIBLE);
        lblOtro.setVisibility(View.INVISIBLE);
        editCodMateria.setVisibility(View.INVISIBLE);
        editCodMateria.setEnabled(false);
        editGT.setVisibility(View.INVISIBLE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.INVISIBLE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.INVISIBLE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.INVISIBLE);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.INVISIBLE);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.INVISIBLE);
        editOtroMotivo.setEnabled(false);
        editTipo.setVisibility(View.INVISIBLE);
        editTipo.setEnabled(false);
        editMotivo.setVisibility(View.INVISIBLE);
        editMotivo.setEnabled(false);

    }

    public void consultarSolicitudDiferido(View view) {
        helper.abrir();
        SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(editCarnet.getText().toString(), editCodEval.getText().toString());
        helper.cerrar();
        if(solicitudDiferido == null)
            Toast.makeText(this, "Solicitud no encontrada", Toast.LENGTH_LONG).show();
        else{

            lblMateria.setVisibility(View.VISIBLE);
            lblTipoEva.setVisibility(View.VISIBLE);
            lblGT.setVisibility(View.VISIBLE);
            lblGD.setVisibility(View.VISIBLE);
            lblGL.setVisibility(View.VISIBLE);
            lblFecha.setVisibility(View.VISIBLE);
            lblHora.setVisibility(View.VISIBLE);
            lblMotivo.setVisibility(View.VISIBLE);
            editCodMateria.setVisibility(View.VISIBLE);
            editGT.setVisibility(View.VISIBLE);
            editGD.setVisibility(View.VISIBLE);
            editGL.setVisibility(View.VISIBLE);
            editFechaEval.setVisibility(View.VISIBLE);
            editHoraEval.setVisibility(View.VISIBLE);
            editMotivo.setVisibility(View.VISIBLE);
            editTipo.setVisibility(View.VISIBLE);
            editOtroMotivo.setText(solicitudDiferido.getOtroMotivo());
            if(editOtroMotivo.getText().equals("")) {
                lblOtro.setVisibility(View.VISIBLE);
                editOtroMotivo.setVisibility(View.VISIBLE);
            }
            editCodMateria.setText(solicitudDiferido.getCodMateria());
            editGT.setText(solicitudDiferido.getGT());
            editGD.setText(solicitudDiferido.getGD());
            editGL.setText(solicitudDiferido.getGL());
            editFechaEval.setText(solicitudDiferido.getFechaEva());
            editHoraEval.setText(solicitudDiferido.getHoraEva());
            editMotivo.setText(solicitudDiferido.getMotivo());
            editTipo.setText(solicitudDiferido.getTipoEva());
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
        editOtroMotivo.setText("");
        editMotivo.setText("");
        editTipo.setText("");
    }
}
