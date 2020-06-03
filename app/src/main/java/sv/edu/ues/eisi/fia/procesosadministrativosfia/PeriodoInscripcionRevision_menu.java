package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PeriodoInscripcionRevision_menu extends ListActivity {
    String[] menu = {"Insertar Inscripcion a Primera Revision", "Consultar Inscripcion a Primera Revision", "Actualizar Inscripcion a Primera Revision", "Eliminar Inscripcion a Primera Revision"};
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
        l.getChildAt(position).setBackgroundColor(Color.rgb(100, 100, 230));
        try {
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia." + nombreValues);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
