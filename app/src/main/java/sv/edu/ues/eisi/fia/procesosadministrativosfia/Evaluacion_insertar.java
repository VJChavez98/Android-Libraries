package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class Evaluacion_insertar extends Activity {
    ControladorBase helper;
    EditText editAsignatura, editCiclo, editNumero, editTipoEvaluacion, editEvaluacion, editFecha;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_insertar);
        helper = new ControladorBase(this);
        editAsignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCiclo = (EditText) findViewById(R.id.editCodciclo);
        editTipoEvaluacion = (EditText) findViewById(R.id.editCodtipoeval);
        editEvaluacion = (EditText) findViewById(R.id.editCodigo);
        editFecha = (EditText) findViewById(R.id.editFechaeval);
        editNumero = (EditText) findViewById(R.id.editNumeval);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
    }

    private void colocar_fecha() {
        editFecha.setText( nDayIni + "-" + (nMonthIni + 1) + "-" + nYearIni );
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    nYearIni = year;
                    nMonthIni = monthOfYear;
                    nDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void insertarEvaluacion(View v){
        String asignatura = editAsignatura.getText().toString();
        String ciclo = editCiclo.getText().toString();
        String tipoEval = editTipoEvaluacion.getText().toString();
        String evaluacion = editEvaluacion.getText().toString();
        Date fecha = Date.valueOf(editFecha.getText().toString());
        Integer numero = Integer.valueOf(editNumero.getText().toString());
        String regInsertados;

        Evaluacion eval = new Evaluacion();
        eval.setCodAsignatura(asignatura);
        eval.setCodCiclo(ciclo);
        eval.setCodTipoEval(tipoEval);
        eval.setIdEvaluacion(evaluacion);
        eval.setFechaEvaluacion(fecha);
        eval.setNumeroEvaluacion(numero);
        helper.abrir();
        regInsertados = helper.insertar(eval);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        editTipoEvaluacion.setText("");
        editEvaluacion.setText("");
        editFecha.setText("");
        editNumero.setText("");
    }
}
