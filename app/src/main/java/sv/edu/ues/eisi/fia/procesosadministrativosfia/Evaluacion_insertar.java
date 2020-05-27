package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Evaluacion_insertar extends Activity{
    ControladorBase helper;
    EditText editAsignatura, editCiclo, editNumero, editFecha;
    Spinner spinTipoeval;
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
        editFecha = (EditText) findViewById(R.id.editFechaeval);
        editNumero = (EditText) findViewById(R.id.editNumeval);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);

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
        if(String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1){
            editFecha.setText( nYearIni + "-0" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else if(String.valueOf(nMonthIni).length() == 1){
            editFecha.setText( nYearIni + "-0" + (nMonthIni + 1) + "-" +  nDayIni );
        }else if(String.valueOf(nDayIni).length() == 1){
            editFecha.setText( nYearIni + "-" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else{
            editFecha.setText( nYearIni + "-" + (nMonthIni + 1) + "-" +  nDayIni );
        }
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
        String fecha = editFecha.getText().toString();
        String regInsertados;

        Evaluacion eval = new Evaluacion();
        eval.setCodAsignatura(asignatura);
        eval.setCodCiclo(ciclo);
        eval.setFechaEvaluacion(fecha);

        if(!editNumero.getText().toString().isEmpty()){
            eval.setNumeroEvaluacion(Integer.parseInt(editNumero.getText().toString()));
        }else{
            eval.setNumeroEvaluacion(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            eval.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            eval.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            eval.setCodTipoEval(tipoEval);
        }else{
            eval.setCodTipoEval("");
        }

        helper.abrir();
        regInsertados = helper.insertar(eval);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editAsignatura.setText("");
        editCiclo.setText("");
        spinTipoeval.setSelection(0);
        editFecha.setText("");
        editNumero.setText("");
    }
}
