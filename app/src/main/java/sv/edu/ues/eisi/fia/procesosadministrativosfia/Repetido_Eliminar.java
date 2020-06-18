package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Repetido_Eliminar extends AppCompatActivity {
    EditText carnet, materia, numEval;
    Spinner tipoEval;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetido__eliminar);
        helper = new ControladorBase(this);
        carnet = findViewById(R.id.editCarnet);
        materia = findViewById(R.id.editAsignatura);
        numEval = findViewById(R.id.editNumeval);
        tipoEval = findViewById(R.id.spinTipoEval);
    }
    public void eliminarRepetido(View view){

        DetalleEstudianteRepetido detalleRep = new DetalleEstudianteRepetido();
        detalleRep.setCarnet(carnet.getText().toString());
        if (!(numEval.getText().toString().isEmpty())) {
            detalleRep.setIdDetalleDifRep(materia.getText().toString() + tipoEval.getSelectedItem().toString() + numEval.getText().toString() + "Repetido");
        }else detalleRep.setIdDetalleDifRep(materia.getText().toString() + tipoEval.getSelectedItem().toString() + 0 + "Repetido");
        helper.abrir();
        String resultado = helper.eliminar(detalleRep);
        helper.cerrar();
        Toast.makeText(this,resultado,Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View view){
        carnet.setText("");
        materia.setText("");
        numEval.setText("");
        tipoEval.setSelection(0);
    }
}
