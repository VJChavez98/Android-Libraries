package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class Diferido_consultar extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    EditText editNumEval, editCarnet, editCodMateria, editGT, editGD, editGL, editFechaEval, editHoraEval, editOtroMotivo;
    ControladorBase helper;
    TextView lblMateria, lblTipoEva, lblGT,lblGD,lblGL, lblFecha, lblHora, lblMotivo, lblOtro;
    Button eliminarBtn, modificarBtn;
    Spinner tipoEval, motivos;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID=1;
    Calendar c = Calendar.getInstance();
    String[] tipos ={"Seleccione el tipo de evaluacion","EP","ED","EL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_consultar);
        helper = new ControladorBase(this);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editCodMateria = (EditText) findViewById(R.id.editAsignatura);
        editGT = (EditText) findViewById(R.id.editGrupoTeorico);
        editGD = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGL = (EditText) findViewById(R.id.editGrupoLab);
        editFechaEval = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval = (EditText) findViewById(R.id.editHoraRealizada);
        editOtroMotivo = (EditText) findViewById(R.id.editMotivo);

        tipoEval=(Spinner) findViewById(R.id.spinTipoEval);
        motivos = (Spinner) findViewById(R.id.spinMotivos);


        lblMateria = (TextView) findViewById(R.id.lblCodMat);
        lblTipoEva = (TextView) findViewById(R.id.lblTipoEval);
        lblGT = (TextView) findViewById(R.id.lblGT);
        lblGD = (TextView) findViewById(R.id.lblGD);
        lblGL =(TextView) findViewById(R.id.lblGL);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblHora = (TextView) findViewById(R.id.lblHora);
        lblMotivo = (TextView) findViewById(R.id.lblMotivo);
        lblOtro = (TextView) findViewById(R.id.lblOtro);

        eliminarBtn = (Button) findViewById(R.id.EliminarBtn);
        modificarBtn = (Button) findViewById(R.id.ModificarBtn);

        eliminarBtn.setVisibility(View.INVISIBLE);
        modificarBtn.setVisibility(View.INVISIBLE);

        lblMateria.setVisibility(View.VISIBLE);
        lblTipoEva.setVisibility(View.VISIBLE);
        lblGT.setVisibility(View.INVISIBLE);
        lblGD.setVisibility(View.INVISIBLE);
        lblGL.setVisibility(View.INVISIBLE);
        lblFecha.setVisibility(View.INVISIBLE);
        lblHora.setVisibility(View.INVISIBLE);
        lblMotivo.setVisibility(View.INVISIBLE);
        lblOtro.setVisibility(View.INVISIBLE);
        editCodMateria.setVisibility(View.VISIBLE);
        editCodMateria.setEnabled(true);
        editGT.setVisibility(View.INVISIBLE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.INVISIBLE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.INVISIBLE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.INVISIBLE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.INVISIBLE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.INVISIBLE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        tipoEval.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos));
        tipoEval.setVisibility(View.VISIBLE);
        tipoEval.setEnabled(true);
        motivos.setVisibility(View.INVISIBLE);
        motivos.setEnabled(false);
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

    public void consultarSolicitudDiferido(View view) {
        helper.abrir();
        SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(editCarnet.getText().toString(),editCodMateria.getText().toString(),tipoEval.getSelectedItem().toString(), editNumEval.getText().toString());
        helper.cerrar();
        if(solicitudDiferido == null)
            Toast.makeText(this, "Solicitud no encontrada", Toast.LENGTH_LONG).show();
        else{
            editCarnet.setEnabled(false);
            editNumEval.setEnabled(false);
            editCodMateria.setEnabled(false);
            tipoEval.setEnabled(false);
            lblGT.setVisibility(View.VISIBLE);
            lblGD.setVisibility(View.VISIBLE);
            lblGL.setVisibility(View.VISIBLE);
            lblFecha.setVisibility(View.VISIBLE);
            lblHora.setVisibility(View.VISIBLE);
            lblMotivo.setVisibility(View.VISIBLE);
            editGT.setVisibility(View.VISIBLE);
            editGD.setVisibility(View.VISIBLE);
            editGL.setVisibility(View.VISIBLE);
            editFechaEval.setVisibility(View.VISIBLE);
            editHoraEval.setVisibility(View.VISIBLE);
            motivos.setVisibility(View.VISIBLE);
            editOtroMotivo.setText(solicitudDiferido.getOtroMotivo());
            lblOtro.setVisibility(View.VISIBLE);editOtroMotivo.setVisibility(View.VISIBLE);
            editCodMateria.setText(solicitudDiferido.getCodMateria());
            editGT.setText(solicitudDiferido.getGT());
            editGT.setEnabled(true);
            editGD.setText(solicitudDiferido.getGD());
            editGD.setEnabled(true);
            editGL.setText(solicitudDiferido.getGL());
            editGL.setEnabled(true);
            editFechaEval.setText(solicitudDiferido.getFechaEva());
            editFechaEval.setEnabled(true);
            editHoraEval.setEnabled(true);
            editHoraEval.setText(solicitudDiferido.getHoraEva());
            eliminarBtn.setVisibility(View.VISIBLE);
            modificarBtn.setVisibility(View.VISIBLE);
            modificarBtn.setEnabled(true);
            eliminarBtn.setEnabled(true);
            tipoEval.setSelection(tipoEval(solicitudDiferido.getTipoEva()));
            motivos.setSelection(colocarMotivo(solicitudDiferido.getMotivo()));
            motivos.setEnabled(true);
            editOtroMotivo.setEnabled(true);


        }


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

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editNumEval.setText("");
        editCodMateria.setText("");
        editGT.setText("");
        editGD.setText("");
        editGL.setText("");
        editFechaEval.setText("");
        editHoraEval.setText("");
        editOtroMotivo.setText("");
        tipoEval.setSelection(0);
        motivos.setSelection(0);
        editCarnet.setEnabled(true);
        editNumEval.setEnabled(true);
        eliminarBtn.setVisibility(View.INVISIBLE);
        modificarBtn.setVisibility(View.INVISIBLE);
        editCodMateria.setEnabled(true);
        tipoEval.setEnabled(true);
        lblGT.setVisibility(View.INVISIBLE);
        lblGD.setVisibility(View.INVISIBLE);
        lblGL.setVisibility(View.INVISIBLE);
        lblFecha.setVisibility(View.INVISIBLE);
        lblHora.setVisibility(View.INVISIBLE);
        lblMotivo.setVisibility(View.INVISIBLE);
        lblOtro.setVisibility(View.INVISIBLE);
        editGT.setVisibility(View.INVISIBLE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.INVISIBLE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.INVISIBLE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.INVISIBLE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.INVISIBLE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.INVISIBLE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        motivos.setVisibility(View.INVISIBLE);
        motivos.setEnabled(false);
    }

    public void EliminarSolicitud(View view) {
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();

        solicitudDiferido.setCarnet(String.valueOf(editCarnet.getText()));
        solicitudDiferido.setNumeroEval(Integer.parseInt(String.valueOf(editNumEval.getText())));
        solicitudDiferido.setCodMateria(String.valueOf(editCodMateria.getText()));
        solicitudDiferido.setTipoEva(String.valueOf(tipoEval.getSelectedItem()));
        solicitudDiferido.setIdSolicitud();
        helper.abrir();
        String regAfectados = helper.eliminar(solicitudDiferido);
        helper.cerrar();
        Toast.makeText(this, regAfectados, Toast.LENGTH_SHORT).show();
        limpiarTexto(view);
    }

    public void ModificarSolicitudDiferido(View view) {
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
        solicitudDiferido.setCarnet(String.valueOf(editCarnet.getText()));
        solicitudDiferido.setCodMateria(String.valueOf(editCodMateria.getText()));
        solicitudDiferido.setNumeroEval(Integer.parseInt(String.valueOf(editNumEval.getText())));
        solicitudDiferido.setTipoEva(String.valueOf(tipoEval.getSelectedItem()));
        solicitudDiferido.setGT(editGT.getText().toString());
        solicitudDiferido.setGD(editGD.getText().toString());
        solicitudDiferido.setGL(editGL.getText().toString());
        solicitudDiferido.setFechaEva(editFechaEval.getText().toString());
        solicitudDiferido.setHoraEva(editHoraEval.getText().toString());
        solicitudDiferido.setMotivo(motivos.getSelectedItem().toString());
        solicitudDiferido.setOtroMotivo(editOtroMotivo.getText().toString());
        solicitudDiferido.setIdSolicitud();
        helper.abrir();
        String regAfectados = helper.actualizar(solicitudDiferido);
        helper.cerrar();
        Toast.makeText(this, regAfectados, Toast.LENGTH_SHORT).show();

    }
    public int tipoEval(String tipo){
        switch (tipo){
            case "EP":
                return 1;
            case "ED":
                return 2;
            case "EL":
                return 3;
            default: return 0;
        }
    }
    public int colocarMotivo(String motivo){
        switch (motivo){
            case "Salud":
                return 1;
            case "Trabajo":
                return 2;
            case "Interferencia":
                return 3;
            case "Viaje":
                return 4;
            case "Duelo":
                return 5;
            case "Otro":
                return 6;
            default: return 0;
        }
    }

}
