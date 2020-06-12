package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetalleEstudianteDiferido_consultar extends AppCompatActivity {
    EditText editMateria, editNumEval, editFecha;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    Spinner spinTipoEval;
    FrameLayout frame;
    ListView listaDetalle;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_diferido_consultar);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editCodasignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);

        Texto=(TextView) findViewById(R.id.editCodasignatura);
        Texto1=(TextView) findViewById(R.id.editNumeval);
        Texto2=(TextView) findViewById(R.id.editFechaInscrip);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        frame = findViewById(R.id.frameConsulta);
        listaDetalle = findViewById(R.id.listDetalles);
    }

    public void consultarDetalle(View view) {
        frame.setVisibility(View.VISIBLE);
        helper.abrir();
        final ArrayList<String> solicitudes = helper.consultarEstudiantesInscritos(editMateria.getText().toString(),spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()), "Diferido");
        helper.cerrar();
        if (solicitudes.size() > 0) {

            listaDetalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicitudes));
            listaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    helper.abrir();
                    final DetalleEstudianteDiferido detalle = helper.consultarDetallEstuDifIndividual(solicitudes.get(position),editMateria.getText().toString(),spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()));
                    final Estudiante estudiante = helper.consultarEstudiante(detalle.getCarnet());
                    helper.cerrar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.estudiante_detalle, null, false);
                    builder.setView(dialogView);
                    builder.setTitle("Detalles");
                    EditText carnet = dialogView.findViewById(R.id.editCarnet);
                    EditText nombre = dialogView.findViewById(R.id.editNombreEstudiante);
                    EditText apellido = dialogView.findViewById(R.id.editApellidoEstudiante);
                    EditText carrera = dialogView.findViewById(R.id.editCarreraEstudiante);
                    EditText fechaInscripcion = dialogView.findViewById(R.id.editFechaInscrip);
                    carnet.setText(estudiante.getCarnet());
                    nombre.setText(estudiante.getNombre());
                    apellido.setText(estudiante.getApellido());
                    carrera.setText(estudiante.getCarrera());
                    fechaInscripcion.setText(detalle.getFechaInscripcionDiferido());
                    carnet.setEnabled(false);
                    nombre.setEnabled(false);
                    apellido.setEnabled(false);
                    carrera.setEnabled(false);
                    fechaInscripcion.setEnabled(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }else {
            frame.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No se encontraron registros",Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View view) {
        editMateria.setText("");
        editMateria.requestFocus();
        spinTipoEval.setSelection(0);
        editNumEval.setText("");
        frame.setVisibility(View.GONE);
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

            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
