package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Diferido_actualizar extends AppCompatActivity {
    EditText editCarnet, editMateria, editNumEval, GT, GD, GL, fecha, hora, descrip;
    Spinner spinTipoEval, spinMotivo, spinEstado;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_actualizar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editMateria = findViewById(R.id.editAsignatura);
        GT = findViewById(R.id.editGrupoTeorico);
        GD = findViewById(R.id.editGrupoDiscusion);
        GL = findViewById(R.id.editGrupoLab);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editCodEva);
        fecha = findViewById(R.id.editFechaRealizada);
        hora = findViewById(R.id.editHoraRealizada);
        spinMotivo = findViewById(R.id.spinMotivos);
        descrip = findViewById(R.id.editMotivo);
        spinEstado = findViewById(R.id.estadoSolicitud);
    }

    public void actualizarSolicitud(View view) {

    }

    public void limpiarTexto(View view) {
    }
}
