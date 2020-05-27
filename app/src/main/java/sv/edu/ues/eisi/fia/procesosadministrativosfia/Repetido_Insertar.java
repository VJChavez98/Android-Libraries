package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Repetido_Insertar extends AppCompatActivity {
    EditText carnet, materia, numEval;
    Spinner spinTipoEval;
    ControladorBase helper;
    Calendar c;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetido__insertar);
        helper = new ControladorBase(this);
        carnet = findViewById(R.id.editCarnet);
        materia = findViewById(R.id.editAsignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        numEval = findViewById(R.id.editNumeval);
        c = Calendar.getInstance();
        sMonthIni = c.get(Calendar.MONTH)+1;
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);
    }

    public void limpiarTexto(View view){

    }
    public void insertarRepetido(View view) {
        int numeroEval = 0;
        String resultado;
        DetalleDiferidoRepetido detalleDiferidoRepetido;
        if (!numEval.getText().toString().isEmpty()) {
            numeroEval = Integer.parseInt(numEval.getText().toString());
        }
        helper.abrir();
        detalleDiferidoRepetido = helper.consultarDetalleDifRep(materia.getText().toString(),spinTipoEval.getSelectedItem().toString(),numeroEval,"Repetido");
        helper.cerrar();
        if (detalleDiferidoRepetido != null){
            DetalleEstudianteRepetido detalle = new DetalleEstudianteRepetido();
            detalle.setCarnet(carnet.getText().toString());
            detalle.setIdDetalleDifRep(detalleDiferidoRepetido.getIdDetalle());
            detalle.setFechaInscripcion(colocar_fecha());
            helper.abrir();
            resultado = helper.insertar(detalle);
            helper.cerrar();
        }else {
            resultado = "No existe repetido para la evaluacion o materia";
        }

        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }
    private String colocar_fecha() {
        String fecha = "";
        if (String.valueOf(sMonthIni).length() == 1 && String.valueOf(sDayIni).length() == 1){
            fecha = nYearIni + "-0" + sMonthIni + "-0" + sDayIni;
        }else if (String.valueOf(sMonthIni).length() == 1){
            fecha = sYearIni + "-0" + sMonthIni + "-" + sDayIni ;
        }else if (String.valueOf(sDayIni).length() == 1) {
            fecha = sYearIni + "-" + sMonthIni + "-0" + sDayIni;
        }else {
            fecha = sYearIni + "-" + sMonthIni + "-" + sDayIni ;
        }
        return fecha;
    }
}
