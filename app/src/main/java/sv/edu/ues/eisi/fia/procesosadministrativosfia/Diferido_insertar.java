package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Diferido_insertar extends AppCompatActivity {

    ControladorBase DBHelper;
    EditText editCarnet, editMateria, editGrupoTeorico, editGrupoDiscusion, editGrupoLab, editFechaEval, editHoraEval, editMotivo, editEva;
    Spinner motivos, spinTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_insertar);
        DBHelper = new ControladorBase(getApplicationContext());
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editMateria =  (EditText) findViewById(R.id.editAsignatura);
        editGrupoTeorico  = (EditText) findViewById(R.id.editGrupoTeorico);
        editGrupoDiscusion  = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGrupoLab  = (EditText) findViewById(R.id.editGrupoLab);
        editEva = (EditText) findViewById(R.id.editCodEva);
        spinTipo  = (Spinner) findViewById(R.id.spinTipoEval);
        editFechaEval  = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval  = (EditText) findViewById(R.id.editHoraRealizada);
        editMotivo = (EditText) findViewById(R.id.editMotivo);
        motivos = (Spinner) findViewById(R.id.spinMotivos);
    }


    public void insertarDiferido(View view) {
        String regInsertados;
        String codSolicitud;
        String carnet = editCarnet.getText().toString();
        String asignatura = editMateria.getText().toString();
        String GT = editGrupoTeorico.getText().toString();
        String GD = editGrupoDiscusion.getText().toString();
        String GL = editGrupoLab.getText().toString();
        String codEva = editEva.getText().toString();
        String tipoEval = spinTipo.getSelectedItem().toString();
        String fechaEval = editFechaEval.getText().toString();
        String hora = editHoraEval.getText().toString();
        String motivoSpin = motivos.getSelectedItem().toString();
        String motivoEdit = editMotivo.getText().toString();
        codSolicitud = carnet+codEva;
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
        solicitudDiferido.setIdSolicitud(codSolicitud);
        solicitudDiferido.setCarnet(carnet);
        solicitudDiferido.setCodMateria(asignatura);
        solicitudDiferido.setCodEva(codEva);
        solicitudDiferido.setGT(GT);
        solicitudDiferido.setGD(GD);
        solicitudDiferido.setGL(GL);
        solicitudDiferido.setTipoEva(tipoEval);
        solicitudDiferido.setFechaEva(fechaEval);
        solicitudDiferido.setHoraEva(hora);
        solicitudDiferido.setMotivo(motivoSpin);
        solicitudDiferido.setOtroMotivo(motivoEdit);
        DBHelper.abrir();
        regInsertados=DBHelper.insertar(solicitudDiferido);
        DBHelper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editCarnet.setText("");

    }
}
