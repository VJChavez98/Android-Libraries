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

public class Diferido_insertar extends AppCompatActivity {

    ControladorBase DBHelper;
    EditText editCarnet, editMateria, editGrupoTeorico, editGrupoDiscusion, editGrupoLab, editFechaEval, editHoraEval, editMotivo, editEva;
    Spinner motivos, spinTipo;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID=1;
    String[] tipos ={"Seleccione el tipo de evaluacion","EP","ED","EL"};
    Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_insertar);
        DBHelper = new ControladorBase(getApplicationContext());
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editMateria = (EditText) findViewById(R.id.editAsignatura);
        editGrupoTeorico = (EditText) findViewById(R.id.editGrupoTeorico);
        editGrupoDiscusion = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGrupoLab = (EditText) findViewById(R.id.editGrupoLab);
        editEva = (EditText) findViewById(R.id.editCodEva);
        spinTipo = (Spinner) findViewById(R.id.spinTipoEval);
        editFechaEval = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval = (EditText) findViewById(R.id.editHoraRealizada);
        editMotivo = (EditText) findViewById(R.id.editMotivo);
        motivos = (Spinner) findViewById(R.id.spinMotivos);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setInputType(InputType.TYPE_NULL);

        spinTipo.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos));


        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);


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


    public void insertarDiferido(View view) {
        String regInsertados;
        String codSolicitud;
        String carnet = editCarnet.getText().toString();
        String asignatura = editMateria.getText().toString();
        String GT = editGrupoTeorico.getText().toString();
        String GD = editGrupoDiscusion.getText().toString();
        String GL = editGrupoLab.getText().toString();
        int numEva = Integer.parseInt(editEva.getText().toString());
        String tipoEval = spinTipo.getSelectedItem().toString();
        String fechaEval = editFechaEval.getText().toString();
        String hora = editHoraEval.getText().toString();
        String motivoSpin = motivos.getSelectedItem().toString();
        String motivoEdit = editMotivo.getText().toString();
        codSolicitud = carnet+asignatura+tipoEval+numEva;
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
        solicitudDiferido.setIdSolicitud(codSolicitud);
        solicitudDiferido.setCarnet(carnet);
        solicitudDiferido.setCodMateria(asignatura);
        solicitudDiferido.setNumeroEval(numEva);
        solicitudDiferido.setGT(GT);
        solicitudDiferido.setGD(GD);
        solicitudDiferido.setGL(GL);
        solicitudDiferido.setTipoEva(tipoEval);
        solicitudDiferido.setFechaEva(fechaEval);
        solicitudDiferido.setHoraEva(hora);
        solicitudDiferido.setMotivo(motivoSpin);
        solicitudDiferido.setOtroMotivo(motivoEdit);
        DBHelper.abrir();
        regInsertados=DBHelper.insertar(solicitudDiferido);
        DBHelper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editCarnet.setText("");
        editCarnet.setText("");
        editMateria.setText("");
        editGrupoTeorico.setText("");
        editGrupoDiscusion.setText("");
        editGrupoLab.setText("");
        editEva.setText("");
        spinTipo.setSelection(0);
        editFechaEval.setText("");
        editHoraEval.setText("");
        editMotivo.setText("");
        motivos.setSelection(0);
    }
}
