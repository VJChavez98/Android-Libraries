package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuEstudiante extends ListActivity {
    String [] menu = {"Repetido", "Diferido", "Local", "Evaluacion",  "Inscripcion a Primera Revision", "Primera Revision"};
    String [] activities = {"Repetido_menu", "Diferido_menu", "Local_menu", "Evaluacion_menu", "PeriodoInscripcionRevision_menu", "PrimeraRevision_menu"};
    ControladorBase DBHelper;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        DBHelper = new ControladorBase(this);
    }

    protected void onListItemClick(ListView listView, View view, int position, long id){
        super.onListItemClick(listView, view, position, id);
        if(position != 6) {
        String nombreValue = activities[position];
        try {
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia." + nombreValue);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }else{
            DBHelper.abrir();
            String tost = DBHelper.LlenarDatos();
            DBHelper.cerrar();
            Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
