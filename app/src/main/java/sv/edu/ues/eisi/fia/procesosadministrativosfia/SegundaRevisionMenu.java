package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SegundaRevisionMenu extends ListActivity {
    String[] menu = {"Insertar Segunda Revision", "Consultar Segunda Revision", "Actualizar Segunda Revision", "Eliminar Segunda Revision"};
    String[] activities = {"SegundaRevisionInsertar", "SegundaRevisionConsultar", "SegundaRevisionActualizar", "SegundaRevisionEliminar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        String nombreValues = activities[position];
        try{
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia."+nombreValues);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
