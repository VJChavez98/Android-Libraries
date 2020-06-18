package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Diferido_consultar extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    EditText editNumEval, editCarnet, editCodMateria, editGT, editGD, editGL, editFechaEval, editHoraEval, editOtroMotivo, estadoSoli;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5, Texto6, Texto7;
    Button BtnPlay;
    private int numarch=0;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;


    ControladorBase helper;
    TextView lblMateria, lblTipoEva, lblGT,lblGD,lblGL, lblFecha, lblHora, lblMotivo, lblOtro, lblEstado;
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
        estadoSoli = findViewById(R.id.editEstadoSolicitud);
        tipoEval=(Spinner) findViewById(R.id.spinTipoEval);
        motivos = (Spinner) findViewById(R.id.spinMotivos);

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editAsignatura);
        Texto2=(TextView) findViewById(R.id.editGrupoTeorico);
        Texto3=(TextView) findViewById(R.id.editGrupoDiscusion);
        Texto4=(TextView) findViewById(R.id.editGrupoLab);
        Texto5=(TextView) findViewById(R.id.editFechaRealizada);
        Texto6=(TextView) findViewById(R.id.editHoraRealizada);
        Texto7=(TextView) findViewById(R.id.editMotivo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        mEntradaVoz=findViewById(R.id.editCarnet);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });


        lblMateria = (TextView) findViewById(R.id.lblCodMat);
        lblTipoEva = (TextView) findViewById(R.id.lblTipoEval);
        lblGT = (TextView) findViewById(R.id.lblGT);
        lblGD = (TextView) findViewById(R.id.lblGD);
        lblGL =(TextView) findViewById(R.id.lblGL);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblHora = (TextView) findViewById(R.id.lblHora);
        lblMotivo = (TextView) findViewById(R.id.lblMotivo);
        lblOtro = (TextView) findViewById(R.id.lblOtro);
        lblEstado = findViewById(R.id.lblEstadoSoli);
        eliminarBtn = (Button) findViewById(R.id.EliminarBtn);
        modificarBtn = (Button) findViewById(R.id.ModificarBtn);

        eliminarBtn.setVisibility(View.GONE);
        modificarBtn.setVisibility(View.GONE);

        lblMateria.setVisibility(View.VISIBLE);
        lblTipoEva.setVisibility(View.VISIBLE);
        lblGT.setVisibility(View.GONE);
        lblGD.setVisibility(View.GONE);
        lblGL.setVisibility(View.GONE);
        lblFecha.setVisibility(View.GONE);
        lblHora.setVisibility(View.GONE);
        lblMotivo.setVisibility(View.GONE);
        lblOtro.setVisibility(View.GONE);
        lblEstado.setVisibility(View.GONE);
        editCodMateria.setVisibility(View.VISIBLE);
        editCodMateria.setEnabled(true);
        editGT.setVisibility(View.GONE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.GONE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.GONE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.GONE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.GONE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.GONE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        tipoEval.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos));
        tipoEval.setVisibility(View.VISIBLE);
        tipoEval.setEnabled(true);
        motivos.setVisibility(View.GONE);
        motivos.setEnabled(false);
        estadoSoli.setVisibility(View.GONE);
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
            lblEstado.setVisibility(View.VISIBLE);
            estadoSoli.setVisibility(View.VISIBLE);
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
            if (solicitudDiferido.getOtroMotivo().isEmpty()){
                editOtroMotivo.setVisibility(View.GONE);
                lblOtro.setVisibility(View.GONE);
            }
            estadoSoli.setText(solicitudDiferido.getEstado());


        }


    }

    private void iniciarEntradaVoz(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Carné");
        try {
            startActivityForResult(i, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEntradaVoz.setText(result.get(0));
                }
                break;
            }
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
        eliminarBtn.setVisibility(View.GONE);
        modificarBtn.setVisibility(View.GONE);
        editCodMateria.setEnabled(true);
        tipoEval.setEnabled(true);
        lblGT.setVisibility(View.GONE);
        lblGD.setVisibility(View.GONE);
        lblGL.setVisibility(View.GONE);
        lblFecha.setVisibility(View.GONE);
        lblHora.setVisibility(View.GONE);
        lblMotivo.setVisibility(View.GONE);
        lblOtro.setVisibility(View.GONE);
        editGT.setVisibility(View.GONE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.GONE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.GONE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.GONE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.GONE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.GONE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        motivos.setVisibility(View.GONE);
        motivos.setEnabled(false);
        estadoSoli.setText("");
        estadoSoli.setVisibility(View.GONE);
        lblEstado.setVisibility(View.GONE);
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
