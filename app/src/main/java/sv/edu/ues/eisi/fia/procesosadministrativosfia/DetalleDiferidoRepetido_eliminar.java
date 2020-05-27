package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

public class DetalleDiferidoRepetido_eliminar extends AppCompatActivity {
    EditText editMateria, editNumEval;
    Spinner spinTipoEval, spinTipoDifRep;
    ControladorBase helper;
    String[] tipos ={"Seleccione el tipo de evaluacion","EP","ED","EL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diferido_repetido_eliminar);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editAsignatura);
        editNumEval = findViewById(R.id.editNumeval);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        spinTipoDifRep = findViewById(R.id.spinTipoDifRep);
        spinTipoEval.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,tipos));
    }

    public void eliminarDetalle(View view) {
        DetalleDiferidoRepetido detalle = new DetalleDiferidoRepetido();
        detalle.setIdAsignatura(editMateria.getText().toString());
        if (!editNumEval.getText().toString().isEmpty()){
            detalle.setNumEval(Integer.parseInt(editNumEval.getText().toString()));
        }else{
            detalle.setNumEval(0);
        }
        detalle.setIdTipoEval(spinTipoEval.getSelectedItem().toString());
        detalle.setIdTipoDifRep(spinTipoDifRep.getSelectedItem().toString());
        detalle.setIdDetalle();
        helper.abrir();
        String resultado = helper.eliminar(detalle);
        helper.cerrar();
        Toast.makeText(this,resultado,Toast.LENGTH_SHORT).show();
        limpiarTexto(view);
    }

    public void limpiarTexto(View view) {
        editMateria.setText("");
        editNumEval.setText("");
        spinTipoEval.setSelection(0);
        spinTipoDifRep.setSelection(0);
    }
}
