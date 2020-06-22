package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class SolicitudRevisionActivity extends Activity {

    TextView codtiporevision, codasignatura, codtipoevaluacion, numeroevaluacion, codciclo;
    EditText carnet, notaactual, numerogrupo, fechasol, motrevision;
    Spinner codtipogrupo;

    ControladorBase helper;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_revision);

        helper = new ControladorBase(this);

        codtiporevision = (TextView) findViewById(R.id.codtiporev);
        codasignatura = (TextView) findViewById(R.id.codasignatura);
        codtipoevaluacion = (TextView) findViewById(R.id.codtipoeva);
        numeroevaluacion = (TextView) findViewById(R.id.numeval);
        codciclo = (TextView) findViewById(R.id.codciclo);

        carnet= (EditText) findViewById(R.id.editCarnet);
        notaactual= (EditText) findViewById(R.id.editNotaActual);

        codtipogrupo= (Spinner) findViewById(R.id.spinTipoGrupo);

        numerogrupo= (EditText) findViewById(R.id.editnumerogrupo);

        fechasol= (EditText) findViewById(R.id.editFechaSolicitud);
        motrevision= (EditText) findViewById(R.id.editmotivorev);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);

        fechasol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });




        PeriodoInscripcionRevision  periodoseleccionado = (PeriodoInscripcionRevision) getIntent().getSerializableExtra("periodo");

        if(periodoseleccionado!=null){

            codtiporevision.setText(periodoseleccionado.getTipoRevision().toString());
            codasignatura.setText(periodoseleccionado.getCodAsignatura().toString());
            codtipoevaluacion.setText(periodoseleccionado.getCodTipoEval().toString());
            numeroevaluacion.setText(Integer.toString(periodoseleccionado.getNumeroEval()));
            codciclo.setText(periodoseleccionado.getCodCiclo());

        }


    }

    private void colocar_fecha() {
        if(String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1){
            fechasol.setText( nYearIni + "-0" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else if(String.valueOf(nMonthIni).length() == 1){
            fechasol.setText( nYearIni + "-0" + (nMonthIni + 1) + "-" +  nDayIni );
        }else if(String.valueOf(nDayIni).length() == 1){
            fechasol.setText( nYearIni + "-" + (nMonthIni + 1) + "-0" +  nDayIni );
        }else{
            fechasol.setText( nYearIni + "-" + (nMonthIni + 1) + "-" +  nDayIni );
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

    public void insertarSolicitudRevision(View v){
        String regInsertados;
        String tipogrupo;
        tipogrupo = codtipogrupo.getSelectedItem().toString();

        SolicitudRevision solicitud = new SolicitudRevision();
        solicitud.setCodtiporevision(codtiporevision.getText().toString());
        solicitud.setCodasignatura(codasignatura.getText().toString());
        solicitud.setCodtipoeval(codtipoevaluacion.getText().toString());
        //solicitud.setNumeroeval(Integer.parseInt(numeroevaluacion.getText().toString()));
        solicitud.setCodciclo(codciclo.getText().toString());
        solicitud.setCarnet(carnet.getText().toString());
        //solicitud.setNotaantesrevision(Float.parseFloat(notaactual.getText().toString()));
        //solicitud.setNumerogrupo(Integer.parseInt(numerogrupo.getText().toString()));
        solicitud.setFechasolicitudrevision(fechasol.getText().toString());
        solicitud.setMotivorevision(motrevision.getText().toString());
        solicitud.setCodtipogrupo(tipogrupo);

        if(!numeroevaluacion.getText().toString().isEmpty()){
            solicitud.setNumeroeval(Integer.parseInt(numeroevaluacion.getText().toString()));
        }else{
            solicitud.setNumeroeval(0);
        }

        if(!notaactual.getText().toString().isEmpty()){
            solicitud.setNotaantesrevision(Float.parseFloat(notaactual.getText().toString()));
        }
        else{
            solicitud.setNotaantesrevision(0.0f);
        }

        if(!numerogrupo.getText().toString().isEmpty()){
            solicitud.setNumerogrupo(Integer.parseInt(numerogrupo.getText().toString()));
        }
        else{
            solicitud.setNumerogrupo(0);
        }



        helper.abrir();
        regInsertados = helper.insertar(solicitud);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }


    public void limpiarTexto(View v){
        carnet.setText("");
        notaactual.setText("");
        numerogrupo.setText("");
        fechasol.setText("");
        motrevision.setText("");
        codtipogrupo.setSelection(0);

    }
}
