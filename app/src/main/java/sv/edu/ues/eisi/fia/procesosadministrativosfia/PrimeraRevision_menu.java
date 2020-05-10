package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrimeraRevision_menu extends ListActivity {
    String[] menu = {"Insertar Primera Revision", "Consultar Primera Revision", "Actualizar Primera Revision", "Eliminar Primera Revision"};
    String[] activities = {"PrimeraRevision_insertar", "PrimeraRevision_consultar", "PrimeraRevision_actualizar", "PrimeraRevision_eliminar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);
    }

    protected void OnListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        String nombreValues = activities[position];
        l.getChildAt(position).setBackgroundColor(Color.rgb(100, 100, 230));
        try{
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia."+nombreValues);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
