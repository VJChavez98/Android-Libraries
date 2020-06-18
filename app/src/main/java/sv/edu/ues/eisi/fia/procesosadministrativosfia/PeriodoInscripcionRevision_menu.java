package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PeriodoInscripcionRevision_menu extends ListActivity {
    String[] menu = {"Insertar Inscripcion a Revision", "Consultar Inscripcion a Revision", "Actualizar Inscripcion a Revision", "Eliminar Inscripcion a Revision"};
    String[] activities = {"PeriodoInscripcionRevision_insertar", "PeriodoInscripcionRevision_consultar", "PeriodoInscripcionRevision_actualizar", "PeriodoInscripcionRevision_eliminar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String nombreValues = activities[position];
        try {
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia." + nombreValues);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
