package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleEstudianteDiferido_consultar extends AppCompatActivity {
    EditText editCarnet, editMateria, editNumEval, editFecha;
    Spinner spinTipoEval;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_diferido_consultar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editMateria = findViewById(R.id.editCodasignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);
    }

    public void consultarDetalleEstudiante(View view) {
    }
}
