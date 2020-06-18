package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CicloInsertarActivity extends Activity {

    ControladorBase helper;
    EditText editCodciclo;
    EditText editFechadesde;
    EditText editFechahasta;
    private int dYearIni, dMonthIni, dDayIni, sdYearIni, sdMonthIni, sdDayIni;
    private int hYearIni, hMonthIni, hDayIni, shYearIni, shMonthIni, shDayIni;
    static final int DATE_ID = 0;
    static final int DATE_ID2 = 1;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_insertar);
        helper = new ControladorBase(this);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editFechadesde = (EditText) findViewById(R.id.editFechadesde);
        editFechahasta = (EditText) findViewById(R.id.editFechahasta);

        sdMonthIni = c.get(Calendar.MONTH);
        sdDayIni = c.get(Calendar.DAY_OF_MONTH);
        sdYearIni = c.get(Calendar.YEAR);

        shMonthIni = c.get(Calendar.MONTH);
        shDayIni = c.get(Calendar.DAY_OF_MONTH);
        shYearIni = c.get(Calendar.YEAR);

        editFechadesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
        editFechahasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID2);
            }
        });
    }
        private void colocar_fechaD() {
            if (String.valueOf(dMonthIni).length() == 1 && String.valueOf(dDayIni).length() == 1){
                editFechadesde.setText( dYearIni + "-0" + (dMonthIni + 1) + "-0" + dDayIni );
            }else if (String.valueOf(dMonthIni).length() == 1){
                editFechadesde.setText( dYearIni + "-0" + (dMonthIni + 1) + "-" + dDayIni );
            }else if (String.valueOf(dDayIni).length() == 1) {
                editFechadesde.setText( dYearIni + "-" + (dMonthIni + 1) + "-0" + dDayIni );
            }else {
                editFechadesde.setText( dYearIni + "-" + (dMonthIni + 1) + "-" + dDayIni );
            }
    }
    private void colocar_fechaH() {
        if (String.valueOf(hMonthIni).length() == 1 && String.valueOf(hDayIni).length() == 1){
            editFechahasta.setText( hYearIni + "-0" + (hMonthIni + 1) + "-0" + hDayIni );
        }else if (String.valueOf(hMonthIni).length() == 1){
            editFechahasta.setText( hYearIni + "-0" + (hMonthIni + 1) + "-" + hDayIni );
        }else if (String.valueOf(hDayIni).length() == 1) {
            editFechahasta.setText( hYearIni + "-" + (hMonthIni + 1) + "-0" + hDayIni );
        }else {
            editFechahasta.setText( hYearIni + "-" + (hMonthIni + 1) + "-" + hDayIni );
        }
    }
    private DatePickerDialog.OnDateSetListener dDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dYearIni = year;
            dMonthIni = monthOfYear;
            dDayIni = dayOfMonth;
            colocar_fechaD();
        }
    };
    private DatePickerDialog.OnDateSetListener hDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            hYearIni = year;
            hMonthIni = monthOfYear;
            hDayIni = dayOfMonth;
            colocar_fechaH();
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, dDateSetListener, sdYearIni, sdMonthIni, sdDayIni);
            case DATE_ID2:
                return new DatePickerDialog(this, hDateSetListener, shYearIni, shMonthIni, shDayIni);

        }
        return null;
    }








    public void insertarCiclo(View v){
        String codciclo=editCodciclo.getText().toString();
        String fechadesde=editFechadesde.getText().toString();
        String fechahasta=editFechahasta.getText().toString();
        String regInsertados;
        Ciclo ciclo=new Ciclo();
        ciclo.setCodciclo(codciclo);
        ciclo.setFechadesde(fechadesde);
        ciclo.setFechahasta(fechahasta);
        helper.abrir();
        regInsertados=helper.insertar(ciclo);
        helper.cerrar();
        Toast.makeText(this, regInsertados,Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v){
        editCodciclo.setText("");
        editFechadesde.setText("");
        editFechahasta.setText("");
    }
}
