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

public class SolicitudDiferido_consultarDocente extends AppCompatActivity {
    EditText editMateria, editNumEval;
    Spinner spinTipoEval, spinEstadoSoli;
    ListView listView1;
    FrameLayout frame;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_diferido_consultar_docente);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editAsignatura);
        editNumEval = findViewById(R.id.editNumeval);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        spinEstadoSoli = findViewById(R.id.estadoSolicitud);
        listView1 = findViewById(R.id.listaSolicitudes);
        frame = findViewById(R.id.fragmentSolicitudes);

    }

    public void consultarSolicitudes(View view) {
        frame.setVisibility(View.VISIBLE);
        helper.abrir();
        final ArrayList<String> solicitudes = helper.consultarSolicitudesPendiente(editMateria.getText().toString(),spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()), spinEstadoSoli.getSelectedItem().toString());
        helper.cerrar();
        if (solicitudes.size() > 0) {

            listView1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicitudes));
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    helper.abrir();
                    final SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(solicitudes.get(position), editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), editNumEval.getText().toString());
                    helper.cerrar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_diferido_actualizar, null, false);
                    builder.setView(dialogView);
                    builder.setTitle("Aprobar/Denegar solicitud");
                    EditText carnet = dialogView.findViewById(R.id.editCarnet);
                    EditText materia = dialogView.findViewById(R.id.editAsignatura);
                    EditText numEval = dialogView.findViewById(R.id.editCodEva);
                    EditText GT = dialogView.findViewById(R.id.editGrupoTeorico);
                    EditText GD = dialogView.findViewById(R.id.editGrupoDiscusion);
                    EditText GL = dialogView.findViewById(R.id.editGrupoLab);
                    EditText fecha = dialogView.findViewById(R.id.editFechaRealizada);
                    EditText hora = dialogView.findViewById(R.id.editHoraRealizada);
                    EditText descrip = dialogView.findViewById(R.id.editMotivo);
                    Spinner tipoEval = dialogView.findViewById(R.id.spinTipoEval);
                    Spinner motivo = dialogView.findViewById(R.id.spinMotivos);
                    final Spinner estado = dialogView.findViewById(R.id.estadoSolicitud);
                    carnet.setText(solicitudDiferido.getCarnet());
                    materia.setText(solicitudDiferido.getCodMateria());
                    GT.setText(solicitudDiferido.getGT());
                    GD.setText(solicitudDiferido.getGD());
                    GL.setText(solicitudDiferido.getGL());
                    tipoEval.setSelection(tipoEvaluacion(solicitudDiferido.getTipoEva()));
                    numEval.setText(String.valueOf(solicitudDiferido.getNumeroEval()));
                    fecha.setText(solicitudDiferido.getFechaEva());
                    hora.setText(solicitudDiferido.getHoraEva());
                    motivo.setSelection(colocarMotivo(solicitudDiferido.getMotivo()));
                    descrip.setText(solicitudDiferido.getOtroMotivo());
                    estado.setSelection(colocarEstado(solicitudDiferido.getEstado()));
                    tipoEval.setEnabled(false);
                    motivo.setEnabled(false);
                    builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helper.abrir();
                            solicitudDiferido.setEstado(estado.getSelectedItem().toString());
                            String resultado = helper.actualizarEstado(solicitudDiferido);
                            helper.cerrar();
                            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
            Toast.makeText(getApplicationContext(), "No se encontraron solicitudes",Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View view){

    }

    public int tipoEvaluacion(String tipo){
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
    public int colocarEstado(String estado){
        switch (estado){
            case "Pendiente": return 1;
            case "Aprobada" : return 2;
            case "Denegada" : return 3;
            default:return 0;
        }
    }

}
