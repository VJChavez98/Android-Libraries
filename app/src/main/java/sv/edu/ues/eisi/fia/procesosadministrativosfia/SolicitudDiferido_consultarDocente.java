package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
                    SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(listView1.getSelectedItem().toString(), editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), editNumEval.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), Diferido_actualizar.class);
                    intent.putExtra("carnet", solicitudDiferido.getCarnet());
                    intent.putExtra("materia", solicitudDiferido.getCodMateria());
                    intent.putExtra("tipoEval", solicitudDiferido.getTipoEva());
                    intent.putExtra("numEval", solicitudDiferido.getNumeroEval());
                    intent.putExtra("motivo",solicitudDiferido.getMotivo());
                    intent.putExtra("descripMotivo", solicitudDiferido.getOtroMotivo());

               }
            });
        }else {
            Toast.makeText(getApplicationContext(), "No se encontraron solicitudes",Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTexto(View view){

    }

}
