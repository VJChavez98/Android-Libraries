package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DetalleDiferidoRepetido_consultar extends AppCompatActivity {
    EditText editMateria, editNumEval,editLocal,editDocente,editFechaDesde,editFechaHasta,editFechaEval,editHoraEval;
    Spinner spinTipoEval, spinTipoDifRep;
    TextView lblLocal, lblDocente, lblFechaDesde, lblFechaHasta, lblFechaEval, lblHora;
    ControladorBase helper;
    private int nYearIni, nMonthIni, nDayIni,nYearIni2, nMonthIni2, nDayIni2,nYearIni3, nMonthIni3, nDayIni3;
    private int sYearIni, sMonthIni, sDayIni,sYearIni2, sMonthIni2, sDayIni2,sYearIni3, sMonthIni3, sDayIni3;
    private int sHour, nHour, sMinute, nMinute;
    static final int DATE_ID1 = 0, DATE_ID2 =1, DATE_ID3=2, HOUR_ID=3;
    Calendar c = Calendar.getInstance();
    private final String[] TIPO_EVAL = {"Seleccione el tipo de evaluación","EP","ED","EL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diferido_repetido_consultar);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editCodasignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editNumeval);
        lblLocal = findViewById(R.id.Local);
        lblLocal.setVisibility(View.GONE);
        editLocal = findViewById(R.id.editCodlocal);
        editLocal.setVisibility(View.GONE);
        lblDocente = findViewById(R.id.lblDocente);
        lblDocente.setVisibility(View.GONE);
        editDocente = findViewById(R.id.editDocente);
        editDocente.setVisibility(View.GONE);
        lblFechaDesde = findViewById(R.id.lblFechaDesde);
        lblFechaDesde.setVisibility(View.GONE);
        editFechaDesde = findViewById(R.id.editFechaDesde);
        editFechaDesde.setVisibility(View.GONE);
        lblFechaHasta = findViewById(R.id.lblFechaHasta);
        lblFechaHasta.setVisibility(View.GONE);
        editFechaHasta = findViewById(R.id.editFechaHasta);
        editFechaHasta.setVisibility(View.GONE);
        lblFechaEval = findViewById(R.id.lblFecha);
        lblFechaEval.setVisibility(View.GONE);
        editFechaEval = findViewById(R.id.editFechaeval);
        editFechaEval.setVisibility(View.GONE);
        lblHora = findViewById(R.id.lblHora);
        lblHora.setVisibility(View.GONE);
        editHoraEval = findViewById(R.id.editHoraRealizada);
        editHoraEval.setVisibility(View.GONE);

        spinTipoEval.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,TIPO_EVAL));

        editFechaEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID3);
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
                showDialog(DATE_ID1);
            }
        });
        editFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID2);
            }
        });

    }
    private void colocar_fecha(int id) {
        switch (id){
            case R.id.editFechaDesde:
            {
                if (String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1) {
                    editFechaDesde.setText(nYearIni + "-0" + nMonthIni + "-0" + nDayIni);
                } else if (String.valueOf(nMonthIni).length() == 1) {
                    editFechaDesde.setText(nYearIni + "-0" + nMonthIni + "-" + nDayIni);
                } else if (String.valueOf(nDayIni).length() == 1) {
                    editFechaDesde.setText(nYearIni + "-" + nMonthIni + "-0" + nDayIni);
                } else {
                    editFechaDesde.setText(nYearIni + "-" + nMonthIni + "-" + nDayIni);
                }
            }break;
            case R.id.editFechaHasta:
            {
                if (String.valueOf(nMonthIni2).length() == 1 && String.valueOf(nDayIni2).length() == 1) {
                    editFechaHasta.setText(nYearIni2 + "-0" + nMonthIni2 + "-0" + nDayIni2);
                } else if (String.valueOf(nMonthIni).length() == 1) {
                    editFechaHasta.setText(nYearIni2 + "-0" + nMonthIni2 + "-" + nDayIni2);
                } else if (String.valueOf(nDayIni).length() == 1) {
                    editFechaHasta.setText(nYearIni2 + "-" + nMonthIni2 + "-0" + nDayIni2);
                } else {
                    editFechaHasta.setText(nYearIni2 + "-" + nMonthIni2 + "-" + nDayIni2);
                }
            }break;
            case R.id.editFechaeval:
            {
                if (String.valueOf(nMonthIni3).length() == 1 && String.valueOf(nDayIni3).length() == 1) {
                    editFechaEval.setText(nYearIni3 + "-0" + nMonthIni3 + "-0" + nDayIni3);
                } else if (String.valueOf(nMonthIni).length() == 1) {
                    editFechaEval.setText(nYearIni3 + "-0" + nMonthIni3 + "-" + nDayIni3);
                } else if (String.valueOf(nDayIni).length() == 1) {
                    editFechaEval.setText(nYearIni3 + "-" + nMonthIni3 + "-0" + nDayIni3);
                } else {
                    editFechaEval.setText(nYearIni3 + "-" + nMonthIni3 + "-" + nDayIni3);
                }
            }break;
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
    private DatePickerDialog.OnDateSetListener DESDE = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nYearIni = year;
            nMonthIni = month;
            nDayIni = dayOfMonth;
            colocar_fecha(R.id.editFechaDesde);        }
    };
    private DatePickerDialog.OnDateSetListener HASTA = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nYearIni2 = year;
            nMonthIni2 = month;
            nDayIni2 = dayOfMonth;
            colocar_fecha(R.id.editFechaHasta);
        }
    };
    private DatePickerDialog.OnDateSetListener REALIZACION = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            nYearIni3 = year;
            nMonthIni3 = month;
            nDayIni3 = dayOfMonth;
            colocar_fecha(R.id.editFechaeval);
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID1:
                return new DatePickerDialog(this, DESDE, sYearIni, sMonthIni, sDayIni);
            case DATE_ID2:
                return new DatePickerDialog(this,HASTA, sYearIni2, sMonthIni2,sDayIni2);
            case DATE_ID3:
                return new DatePickerDialog(this,REALIZACION,sYearIni3,sMonthIni3,sDayIni3);
            case HOUR_ID:
                return new TimePickerDialog(this, mTimeSetListener,nHour,nMinute,true);
        }
        return null;
    }
    public void limpiarTexto(View view){

    }
    public void consultarDetalle(View view){

    }
    public void eliminarDetalle(View view){

    }
    public void actualizarDetalle(View view){

    }
}
