package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class PeriodoInscripcionRevision_insertar extends Activity {
    EditText editCoddocente, editCodlocal, editCodasignatura, editCodciclo, editNumeval, editFechadesde, editFechahasta, editFecharev, editHorarev;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5, Texto6, Texto7, Texto8;
    Button BtnPlay;
    private int numarch=0;

    Spinner spinTiporev, spinTipoeval;
    ControladorBase helper;
    private int pdYearIni, pdMonthIni, pdDayIni, sdYearIni, sdMonthIni, sdDayIni, sdHour, pdHour, sdMinute, pdMinute;
    private int phYearIni, phMonthIni, phDayIni, shYearIni, shMonthIni, shDayIni, shHour, phHour, shMinute, phMinute;
    private int prYearIni, prMonthIni, prDayIni, srYearIni, srMonthIni, srDayIni, srHour, prHour, srMinute, prMinute;
    static final int DATE_ID_D = 0, DATE_ID_H = 1, DATE_ID_R = 2, HOUR_ID=3;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodo_inscripcion_revision_insertar);

        helper = new ControladorBase(this);
        editCoddocente = (EditText) findViewById(R.id.editCoddocente);
        editCodasignatura = (EditText) findViewById(R.id.editCodasignatura);
        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editCodciclo = (EditText) findViewById(R.id.editCodciclo);
        editNumeval = (EditText) findViewById(R.id.editNumeval);
        editFechadesde = (EditText) findViewById(R.id.editFechaDesdeRev);
        editFechahasta = (EditText) findViewById(R.id.editFechaHastaRev);
        editFecharev = (EditText) findViewById(R.id.editFechaRev);
        editHorarev = (EditText) findViewById(R.id.editHoraRevision);
        spinTiporev = (Spinner) findViewById(R.id.spinTipoRev);
        spinTipoeval = (Spinner) findViewById(R.id.spinTipoEval);

        Texto=(TextView) findViewById(R.id.editCoddocente);
        Texto1=(TextView) findViewById(R.id.editCodasignatura);
        Texto2=(TextView) findViewById(R.id.editCodlocal);
        Texto3=(TextView) findViewById(R.id.editCodciclo);
        Texto4=(TextView) findViewById(R.id.editNumeval);
        Texto5=(TextView) findViewById(R.id.editFechaDesdeRev);
        Texto6=(TextView) findViewById(R.id.editFechaHastaRev);
        Texto7=(TextView) findViewById(R.id.editFechaRev);
        Texto8=(TextView) findViewById(R.id.editHoraRevision);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        sdMonthIni = c.get(Calendar.MONTH);
        sdDayIni = c.get(Calendar.DAY_OF_MONTH);
        sdYearIni = c.get(Calendar.YEAR);
        sdHour = c.get(Calendar.HOUR_OF_DAY);
        sdMinute = c.get(Calendar.MINUTE);

        shMonthIni = c.get(Calendar.MONTH);
        shDayIni = c.get(Calendar.DAY_OF_MONTH);
        shYearIni = c.get(Calendar.YEAR);
        shHour = c.get(Calendar.HOUR_OF_DAY);
        shMinute = c.get(Calendar.MINUTE);

        srMonthIni = c.get(Calendar.MONTH);
        srDayIni = c.get(Calendar.DAY_OF_MONTH);
        srYearIni = c.get(Calendar.YEAR);
        srHour = c.get(Calendar.HOUR_OF_DAY);
        srMinute = c.get(Calendar.MINUTE);

        editFechadesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID_D);
            }
        });
        editFechahasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID_H);
            }
        });
        editFecharev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID_R);
            }
        });
        editHorarev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HOUR_ID);
            }
        });
    }

    private void colocar_fechaD() {
        if (String.valueOf(pdMonthIni).length() == 1 && String.valueOf(pdDayIni).length() == 1){
            editFechadesde.setText( pdYearIni + "-0" + (pdMonthIni + 1) + "-0" + pdDayIni );
        }else if (String.valueOf(pdMonthIni).length() == 1){
            editFechadesde.setText( pdYearIni + "-0" + (pdMonthIni + 1) + "-" + pdDayIni );
        }else if (String.valueOf(pdDayIni).length() == 1) {
            editFechadesde.setText( pdYearIni + "-" + (pdMonthIni + 1) + "-0" + pdDayIni );
        }else {
            editFechadesde.setText( pdYearIni + "-" + (pdMonthIni + 1) + "-" + pdDayIni );
        }
    }
    private void colocar_fechaH() {
        if (String.valueOf(phMonthIni).length() == 1 && String.valueOf(phDayIni).length() == 1){
            editFechahasta.setText( phYearIni + "-0" + (phMonthIni + 1) + "-0" + phDayIni );
        }else if (String.valueOf(phMonthIni).length() == 1){
            editFechahasta.setText( phYearIni + "-0" + (phMonthIni + 1) + "-" + phDayIni );
        }else if (String.valueOf(phDayIni).length() == 1) {
            editFechahasta.setText( phYearIni + "-" + (phMonthIni + 1) + "-0" + phDayIni );
        }else {
            editFechahasta.setText( phYearIni + "-" + (phMonthIni + 1) + "-" + phDayIni );
        }
    }
    private void colocar_fechaR() {
        if (String.valueOf(prMonthIni).length() == 1 && String.valueOf(prDayIni).length() == 1){
            editFecharev.setText( prYearIni + "-0" + (prMonthIni + 1) + "-0" + prDayIni );
        }else if (String.valueOf(prMonthIni).length() == 1){
            editFecharev.setText( prYearIni + "-0" + (prMonthIni + 1) + "-" + prDayIni );
        }else if (String.valueOf(prDayIni).length() == 1) {
            editFecharev.setText( prYearIni + "-" + (prMonthIni + 1) + "-0" + prDayIni );
        }else {
            editFecharev.setText( prYearIni + "-" + (prMonthIni + 1) + "-" + prDayIni );
        }
    }
    private void colocarHora(){
        if (String.valueOf(pdMinute).length() == 1 && String.valueOf(pdHour).length() == 1){
            editHorarev.setText("0"+pdHour + ":0"+pdMinute+":00");
        } else if (String.valueOf(pdHour).length()==1){
            editHorarev.setText("0"+pdHour + ":"+pdMinute+":00");
        }else if (String.valueOf(pdMinute).length()==1){
            editHorarev.setText(pdHour + ":0" + pdMinute + ":00");
        }else {
            editHorarev.setText(pdHour + ":" + pdMinute + ":00");
        }
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pdHour = hourOfDay;
            pdMinute = minute;
            colocarHora();
        }
    };
    private DatePickerDialog.OnDateSetListener dDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            pdYearIni = year;
            pdMonthIni = monthOfYear;
            pdDayIni = dayOfMonth;
            colocar_fechaD();
        }
    };
    private DatePickerDialog.OnDateSetListener hDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            phYearIni = year;
            phMonthIni = monthOfYear;
            phDayIni = dayOfMonth;
            colocar_fechaH();
        }
    };
    private DatePickerDialog.OnDateSetListener rDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            prYearIni = year;
            prMonthIni = monthOfYear;
            prDayIni = dayOfMonth;
            colocar_fechaR();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID_D:
                return new DatePickerDialog(this, dDateSetListener, sdYearIni, sdMonthIni, sdDayIni);
            case DATE_ID_H:
                return new DatePickerDialog(this, hDateSetListener, shYearIni, shMonthIni, shDayIni);
            case DATE_ID_R:
                return new DatePickerDialog(this, rDateSetListener, srYearIni, srMonthIni, srDayIni);
            case HOUR_ID:
                return new TimePickerDialog(this, mTimeSetListener, pdHour, pdMinute, true);
        }
        return null;
    }

    public void insertarPeriodoRevision(View v){
        String regInsertados;

        PeriodoInscripcionRevision perInscRev = new PeriodoInscripcionRevision();
        perInscRev.setCodAsignatura(editCodasignatura.getText().toString());
        perInscRev.setCodCiclo(editCodciclo.getText().toString());
        perInscRev.setCodDocente(editCoddocente.getText().toString());
        perInscRev.setCodLocal(editCodlocal.getText().toString());
        perInscRev.setFechaDesde(editFechadesde.getText().toString());
        perInscRev.setFechaHasta(editFechahasta.getText().toString());
        perInscRev.setFechaRevision(editFecharev.getText().toString());
        perInscRev.setHoraRevision(editHorarev.getText().toString());

        if(!editNumeval.getText().toString().isEmpty()){
            perInscRev.setNumeroEval(Integer.parseInt(editNumeval.getText().toString()));
        }else{
            perInscRev.setNumeroEval(0);
        }

        //Validacion de los Spinner para guardar los codigos.
        if(spinTipoeval.getSelectedItem().toString().equals("Examen Parcial")){
            String tipoEval = "EP";
            perInscRev.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Discusion")){
            String tipoEval = "ED";
            perInscRev.setCodTipoEval(tipoEval);
        }else if(spinTipoeval.getSelectedItem().toString().equals("Examen Laboratorio")){
            String tipoEval = "EL";
            perInscRev.setCodTipoEval(tipoEval);
        }else{
            perInscRev.setCodTipoEval("");
        }

        if(spinTiporev.getSelectedItem().toString().equals("Primer Revisión")){
            String tipoEval = "PR";
            perInscRev.setTipoRevision(tipoEval);
        }else if(spinTiporev.getSelectedItem().toString().equals("Segunda Revisión")){
            String tipoEval = "SR";
            perInscRev.setTipoRevision(tipoEval);
        }else{
            perInscRev.setTipoRevision("");
        }


        helper.abrir();
        regInsertados = helper.insertar(perInscRev);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editCodasignatura.setText("");
        editCodciclo.setText("");
        editCoddocente.setText("");
        editCodlocal.setText("");
        editNumeval.setText("");
        spinTipoeval.setSelection(0);
        spinTiporev.setSelection(0);
        editFechadesde.setText("");
        editFechahasta.setText("");
        editFecharev.setText("");
        editHorarev.setText("");
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
                tts.speak(Texto8.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
