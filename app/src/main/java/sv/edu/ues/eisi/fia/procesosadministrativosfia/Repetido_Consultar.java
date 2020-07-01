package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Repetido_Consultar extends AppCompatActivity {
    EditText carnet, materia, numEval, local, docente, fecha, hora;
    Spinner tipoEval;
    ControladorBase helper;
    FrameLayout frameLayout;
    private int sYearIni, sMonthIni, sDayIni, sHour, sMinute;
    Calendar c;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetido__consultar);
        helper = new ControladorBase(this);
        carnet = findViewById(R.id.editCarnet);
        materia = findViewById(R.id.editAsignatura);
        numEval = findViewById(R.id.editNumeval);
        tipoEval = findViewById(R.id.spinTipoEval);
        frameLayout = findViewById(R.id.layoutDetail);
        local =findViewById(R.id.editCodlocal);
        docente = findViewById(R.id.editDocente);
        fecha = findViewById(R.id.editFechaeval);
        hora = findViewById(R.id.editHoraRealizada);

        c = Calendar.getInstance();
        sMonthIni = c.get(Calendar.MONTH)+1;
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);

        mEntradaVoz=findViewById(R.id.editCarnet);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });
    }
   /* private String colocar_fecha() {
        String fecha;
        if (String.valueOf(sMonthIni).length() == 1 && String.valueOf(sDayIni).length() == 1){
            fecha = sYearIni + "-0" + sMonthIni + "-0" + sDayIni;
        }else if (String.valueOf(sMonthIni).length() == 1){
            fecha = sYearIni + "-0" + sMonthIni + "-" + sDayIni ;
        }else if (String.valueOf(sDayIni).length() == 1) {
            fecha = sYearIni + "-" + sMonthIni + "-0" + sDayIni;
        }else {
            fecha = sYearIni + "-" + sMonthIni + "-" + sDayIni ;
        }
        return fecha;
    }
    private String colocarHora(){
        String hora;
        if (String.valueOf(sMinute).length() == 1 && String.valueOf(sHour).length() == 1){
            hora = "0"+sHour + ":0"+sMinute+":00";
        }
        else if (String.valueOf(sHour).length()==1){
            hora = "0"+sHour + ":"+sMinute+":00";
        }else if (String.valueOf(sMinute).length()==1){
            hora = sHour + ":0" + sMinute + ":00";
        }else {hora= sHour + ":" + sMinute + ":00";}
        return hora;
    }*/
    public void limpiarTexto(View view){
        carnet.setText("");
        materia.setText("");
        numEval.setText("");
        tipoEval.setSelection(0);
        local.setText("");
        docente.setText("");
        fecha.setText("");
        hora.setText("");
    }
    public void consultarRepetido(View view) {
        helper.abrir();
        DetalleEstudianteRepetido detalleRep = helper.consultarDetallEstudRep(carnet.getText().toString(), materia.getText().toString(), tipoEval.getSelectedItem().toString(), Integer.parseInt(numEval.getText().toString()));
        helper.cerrar();
        if (detalleRep!= null){
            helper.abrir();
            DetalleDiferidoRepetido detalle = helper.consultarDetalleDifRep(materia.getText().toString(),tipoEval.getSelectedItem().toString(), Integer.valueOf(numEval.getText().toString()),"Repetido");
            frameLayout.setVisibility(View.VISIBLE);
            local.setText(detalle.getIdLocal());
            docente.setText(detalle.getIdDocente());
            fecha.setText(detalle.getFechaRealizacion());
            hora.setText(detalle.getHoraRealizacion());
        }else {
            Toast.makeText(this,"No se encontraron registro",Toast.LENGTH_SHORT).show();
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
}
