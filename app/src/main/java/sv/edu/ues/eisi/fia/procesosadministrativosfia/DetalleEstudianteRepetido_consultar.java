package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DetalleEstudianteRepetido_consultar extends AppCompatActivity {
    EditText editMateria, editNumEval, editFecha;
    Spinner spinTipoEval;
    FrameLayout frame;
    ListView listaDetalle;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante_repetido_consultar);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editCodasignatura);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        editNumEval = findViewById(R.id.editNumeval);
        editFecha = findViewById(R.id.editFechaInscrip);
        frame = findViewById(R.id.frameConsulta);
        listaDetalle = findViewById(R.id.listDetalles);
    }

    public void consultarDetalle(View view) {
        frame.setVisibility(View.VISIBLE);
        helper.abrir();
        final ArrayList<String> solicitudes = helper.consultarEstudiantesInscritos(editMateria.getText().toString(),spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()), "Repetido");
        helper.cerrar();
        if (solicitudes.size() > 0) {

            listaDetalle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicitudes));
            listaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    helper.abrir();
                    final DetalleEstudianteRepetido detalle = helper.consultarDetallEstudRep(solicitudes.get(position), editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()));
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
                    fechaInscripcion.setText(detalle.getFechaInscripcion());
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

        } else {
            frame.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No se encontraron registros", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View view) {
        editMateria.setText("");
        editMateria.requestFocus();
        spinTipoEval.setSelection(0);
        editNumEval.setText("");
        frame.setVisibility(View.GONE);
    }
}