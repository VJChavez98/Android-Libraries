package sv.edu.ues.eisi.fia.procesosadministrativosfia;

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
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DetalleDiferidoRepetido_consultar extends AppCompatActivity {
    EditText editMateria, editNumEval,editLocal,editDocente,editFechaDesde,editFechaHasta,editFechaEval,editHoraEval;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2,Texto3, Texto4, Texto5, Texto6, Texto7;
    Button BtnPlay;
    private int numarch=0;

    Spinner spinTipoEval, spinTipoDifRep;
    TextView lblLocal, lblDocente, lblFechaDesde, lblFechaHasta, lblFechaEval, lblHora;
    ControladorBase helper;
    Button modificar;
    private int nYearIni, nMonthIni, nDayIni,nYearIni2, nMonthIni2, nDayIni2,nYearIni3, nMonthIni3, nDayIni3;
    private int sYearIni, sMonthIni, sDayIni,sYearIni2, sMonthIni2, sDayIni2,sYearIni3, sMonthIni3, sDayIni3;
    private int sHour, nHour, sMinute, nMinute;
    static final int DATE_ID1 = 0, DATE_ID2 =1, DATE_ID3=2, HOUR_ID=3;
    Calendar c = Calendar.getInstance();
    String[] tipos ={"Seleccione el tipo de evaluacion","EP","ED","EL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diferido_repetido_consultar);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editAsignatura);
        editNumEval = findViewById(R.id.editNumeval);
        editLocal = findViewById(R.id.editCodlocal);
        editDocente = findViewById(R.id.editDocente);
        editFechaDesde = findViewById(R.id.editFechaDesde);
        editFechaHasta = findViewById(R.id.editFechaHasta);
        editFechaEval = findViewById(R.id.editFechaeval);
        editHoraEval = findViewById(R.id.editHoraRealizada);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        spinTipoDifRep = findViewById(R.id.spinTipoDifRep);
        editFechaDesde.setInputType(InputType.TYPE_NULL);
        editFechaHasta.setInputType(InputType.TYPE_NULL);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setInputType(InputType.TYPE_NULL);

        Texto=(TextView) findViewById(R.id.editAsignatura);
        Texto1=(TextView) findViewById(R.id.editNumeval);
        Texto2=(TextView) findViewById(R.id.editCodlocal);
        Texto3=(TextView) findViewById(R.id.editDocente);
        Texto4=(TextView) findViewById(R.id.editFechaDesde);
        Texto5=(TextView) findViewById(R.id.editFechaHasta);
        Texto6=(TextView) findViewById(R.id.editFechaeval);
        Texto7=(TextView) findViewById(R.id.editHoraRealizada);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        spinTipoEval.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos));
        lblLocal = findViewById(R.id.lblLocal);
        lblLocal.setVisibility(View.GONE);
        editLocal.setVisibility(View.GONE);
        lblDocente = findViewById(R.id.lblDocente);
        lblDocente.setVisibility(View.GONE);
        editDocente.setVisibility(View.GONE);
        lblFechaDesde = findViewById(R.id.lblFechaDesde);
        lblFechaDesde.setVisibility(View.GONE);
        editFechaDesde.setVisibility(View.GONE);
        lblFechaHasta = findViewById(R.id.lblFechaHasta);
        lblFechaHasta.setVisibility(View.GONE);
        editFechaHasta.setVisibility(View.GONE);
        lblFechaEval = findViewById(R.id.lblFecha);
        lblFechaEval.setVisibility(View.GONE);
        editFechaEval.setVisibility(View.GONE);
        lblHora = findViewById(R.id.lblHora);
        lblHora.setVisibility(View.GONE);
        editHoraEval.setVisibility(View.GONE);
        editFechaDesde.setInputType(InputType.TYPE_NULL);
        editFechaHasta.setInputType(InputType.TYPE_NULL);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        modificar=findViewById(R.id.ModificarBtn);
        modificar.setVisibility(View.GONE);

        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sMonthIni2 = c.get(Calendar.MONTH);
        sDayIni2 = c.get(Calendar.DAY_OF_MONTH);
        sYearIni2 = c.get(Calendar.YEAR);
        sMonthIni3 = c.get(Calendar.MONTH);
        sDayIni3 = c.get(Calendar.DAY_OF_MONTH);
        sYearIni3 = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);

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
        editMateria.setText("");
        editNumEval.setText("");
        spinTipoDifRep.setSelection(0);
        spinTipoEval.setSelection(0);
        lblLocal.setVisibility(View.GONE);
        editLocal.setVisibility(View.GONE);
        editLocal.setText("");
        lblDocente.setVisibility(View.GONE);
        editDocente.setVisibility(View.GONE);
        editDocente.setText("");
        lblFechaDesde.setVisibility(View.GONE);
        editFechaDesde.setVisibility(View.GONE);
        editFechaDesde.setText("");
        lblFechaHasta.setVisibility(View.GONE);
        editFechaHasta.setVisibility(View.GONE);
        editFechaHasta.setText("");
        lblFechaEval.setVisibility(View.GONE);
        editFechaEval.setVisibility(View.GONE);
        editFechaEval.setText("");
        lblHora.setVisibility(View.GONE);
        editHoraEval.setVisibility(View.GONE);
        editHoraEval.setText("");
        editMateria.setEnabled(true);
        spinTipoEval.setEnabled(true);
        editNumEval.setEnabled(true);
        spinTipoDifRep.setEnabled(true);
        modificar.setVisibility(View.GONE);

    }
    public void consultarDetalle(View view){
        helper.abrir();
        DetalleDiferidoRepetido detalle = helper.consultarDetalleDifRep(editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()),spinTipoDifRep.getSelectedItem().toString());
        helper.cerrar();
        if(detalle == null){
            Toast.makeText(this,"Detalle no encontrado",Toast.LENGTH_SHORT).show();
        }else {
            lblLocal.setVisibility(View.VISIBLE);
            editLocal.setText(detalle.getIdLocal());
            editLocal.setVisibility(View.VISIBLE);
            lblDocente.setVisibility(View.VISIBLE);
            editDocente.setText(detalle.getIdDocente());
            editDocente.setVisibility(View.VISIBLE);
            lblFechaDesde.setVisibility(View.VISIBLE);
            editFechaDesde.setText(detalle.getFechaDesde());
            editFechaDesde.setVisibility(View.VISIBLE);
            lblFechaHasta.setVisibility(View.VISIBLE);
            editFechaHasta.setText(detalle.getFechaHasta());
            editFechaHasta.setVisibility(View.VISIBLE);
            lblFechaEval.setVisibility(View.VISIBLE);
            editFechaEval.setText(detalle.getFechaRealizacion());
            editFechaEval.setVisibility(View.VISIBLE);
            lblHora.setVisibility(View.VISIBLE);
            editHoraEval.setText(detalle.getHoraRealizacion());
            editHoraEval.setVisibility(View.VISIBLE);
            editMateria.setEnabled(false);
            spinTipoEval.setEnabled(false);
            editNumEval.setEnabled(false);
            spinTipoDifRep.setEnabled(false);
            modificar.setVisibility(View.VISIBLE);
        }
    }

    public void actualizarDetalle(View view) {
        DetalleDiferidoRepetido detalle = new DetalleDiferidoRepetido();
        detalle.setIdAsignatura(String.valueOf(editMateria.getText()));
        detalle.setIdTipoEval(String.valueOf(spinTipoEval.getSelectedItem()));
        detalle.setNumEval(Integer.parseInt(String.valueOf(editNumEval.getText())));
        detalle.setIdTipoDifRep(String.valueOf(spinTipoDifRep.getSelectedItem()));
        detalle.setIdDetalle();
        detalle.setIdDocente(editDocente.getText().toString());
        detalle.setIdLocal(editLocal.getText().toString());
        detalle.setFechaDesde(editFechaDesde.getText().toString());
        detalle.setFechaHasta(editFechaHasta.getText().toString());
        detalle.setFechaRealizacion(editFechaEval.getText().toString());
        detalle.setHoraRealizacion(editHoraEval.getText().toString());
        helper.abrir();
        String resultado = helper.actualizar(detalle);
        helper.cerrar();
        Toast.makeText(this,resultado,Toast.LENGTH_SHORT).show();

    }

    TextToSpeech.OnInitListener OnInit= new TextToSpeech.OnInitListener(){
        @Override
        public void onInit(int status){
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else{
                Toast.makeText(getApplicationContext(), "TTS No Disponible", Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener(){
        @SuppressLint("SdCardPath")
        public void onClick(View v){
            if (v.getId()==R.id.btnText2SpeechPlay){
                tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto4.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto5.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto6.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto7.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
