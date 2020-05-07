package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DetalleDiferidoRepetido_insertar extends AppCompatActivity {
    EditText editMateria, editNumEval, editLocal, editDocente, editFechaDesde, editFechaHasta, editFechaEval, editHoraEval;
    Spinner spinTipoEval, spinTipoDetalle;
    ControladorBase helper;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID=1;
    Calendar c = Calendar.getInstance();
    private final String[] TIPO_EVAL = {"Seleccione el tipo de evaluación","EP","ED","EL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diferido_repetido_insertar);
        helper = new  ControladorBase(this);
        editMateria = findViewById(R.id.editAsignatura);
        editNumEval = findViewById(R.id.editNumeval);
        editLocal = findViewById(R.id.editCodlocal);
        editDocente = findViewById(R.id.editDocente);
        editFechaDesde = findViewById(R.id.editFechaDesde);
        editFechaHasta = findViewById(R.id.editFechaHasta);
        editFechaEval = findViewById(R.id.editFechaeval);
        editHoraEval = findViewById(R.id.editHoraRealizada);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        spinTipoDetalle = findViewById(R.id.spinTipoDifRep);
        editFechaDesde.setInputType(InputType.TYPE_NULL);
        editFechaHasta.setInputType(InputType.TYPE_NULL);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setInputType(InputType.TYPE_NULL);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);

        spinTipoEval.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,TIPO_EVAL));

        editFechaEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
        editHoraEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HOUR_ID);
            }
        });
        editFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });
        editFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

    }
    private void colocar_fecha() {
        if (String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1){
            editFechaEval.setText( nYearIni + "-0" + nMonthIni + "-0" + nDayIni );
        }else if (String.valueOf(nMonthIni).length() == 1){
            editFechaEval.setText( nYearIni + "-0" + nMonthIni + "-" + nDayIni );
        }else if (String.valueOf(nDayIni).length() == 1) {
            editFechaEval.setText( nYearIni + "-" + nMonthIni + "-0" + nDayIni );
        }else {
            editFechaEval.setText( nYearIni + "-" + nMonthIni + "-" + nDayIni );
        }
    }
    private void colocarHora(){
        if (String.valueOf(nMinute).length() == 1 && String.valueOf(nHour).length() == 1){
            editHoraEval.setText("0"+nHour + ":0"+nMinute+":00");
        }
        else if (String.valueOf(nHour).length()==1){
            editHoraEval.setText("0"+nHour + ":"+nMinute+":00");
        }else if (String.valueOf(nMinute).length()==1){
            editHoraEval.setText(nHour + ":0" + nMinute + ":00");
        }else {editHoraEval.setText(nHour + ":" + nMinute + ":00");}
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            nHour = hourOfDay;
            nMinute = minute;
            colocarHora();
        }
    };
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
            case HOUR_ID:
                return new TimePickerDialog(this, mTimeSetListener,nHour,nMinute,true);
        }
        return null;
    }

    public void insertarDetalle(View view) {
        DetalleDiferidoRepetido detalle = new DetalleDiferidoRepetido();
        detalle.setIdAsignatura(editMateria.getText().toString());
        detalle.setIdTipoEval(spinTipoEval.getSelectedItem().toString());
        detalle.setNumEval(Integer.parseInt(editNumEval.getText().toString()));
        detalle.setIdLocal(editLocal.getText().toString());
        detalle.setIdTipoDifRep(spinTipoDetalle.getSelectedItem().toString());
        detalle.setIdDocente(editDocente.getText().toString());
        detalle.setFechaDesde(editFechaDesde.getText().toString());
        detalle.setFechaHasta(editFechaHasta.getText().toString());
        detalle.setFechaRealizacion(editFechaEval.getText().toString());
        detalle.setHoraRealizacion(editHoraEval.getText().toString());
        detalle.setIdDetalle();
        helper.abrir();
        String result = helper.insertar(detalle);
        helper.cerrar();
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View view) {
    }
}
