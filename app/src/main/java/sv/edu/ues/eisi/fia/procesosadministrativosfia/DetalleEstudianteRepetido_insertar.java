package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleEstudianteRepetido_insertar extends AppCompatActivity {

    EditText editCarnet, editNumEval, editFecha, editAsignatura;
    Spinner spinTipoEval;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_repetido_insertar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editAsignatura = findViewById(R.id.editCodasignatura);
    }

    public void insertarDetalle(View view) {
        DetalleEstudianteRepetido detalle = new DetalleEstudianteRepetido();
        detalle.setCarnet(editCarnet.getText().toString());
        detalle.setFechaInscripcion(editFecha.getText().toString());
        String detalleId = editAsignatura.getText().toString()+spinTipoEval.getSelectedItem().toString()+editNumEval.getText().toString()+"Repetido";
        detalle.setIdDetalleDifRep(detalleId);
        detalle.setIdDetalleEstuRep(editCarnet.getText().toString()+detalleId);
        helper.abrir();
        //String resultado = helper.insertar(detalle);
        helper.cerrar();
        //Toast.makeText(this,resultado,Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View view){
        editCarnet.setText("");
        editAsignatura.setText("");
        editNumEval.setText("");
        editFecha.setText("");
        spinTipoEval.setSelection(0);
    }
}
