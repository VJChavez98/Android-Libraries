package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuEstudiante extends ListActivity {
    Intent intent;
    String carnet;
    ArrayList<String> acceso;
    String [] menu;
    String [] activities;
    boolean doubleBackToExitPressedOnce = false;
    ControladorBase helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        carnet = intent.getStringExtra("carnet");
        helper = new ControladorBase(this);
        helper.abrir();
        acceso = helper.consultarAcceso(carnet);
        menu = menu(acceso);
        activities = actividades(acceso);
        helper.cerrar();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
    }

    protected void onListItemClick(ListView listView, View view, int position, long id){
        super.onListItemClick(listView, view, position, id);
        String nombreValue = activities[position];
        try {
            Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia." + nombreValue);
            Intent intent = new Intent(this, clase);
            this.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public String[] actividades(ArrayList<String> id){
        String[] actividades = new String[id.size()];
        for (int i = 0; i<id.size(); i++){
            actividades[i] = helper.consultarOpcionCrud(id.get(i));
        }
        return actividades;
    }
    public String[] menu(ArrayList<String> id){
        String[] menu = new String[id.size()];
        int i = 0;
        while (i <id.size()){
            switch (id.get(i)) {
                case "001": {
                    menu[i] = "Estudiante";
                    i++;
                    break;
                }
                case "002":{
                    menu[i] = "Solicitud Repetido";
                    i++;
                    break;
                }
                case "003":{
                    menu[i] = "Solicitud Diferido";
                    i++;
                    break;
                }
                case "004": {
                    menu[i] = "Detalle Diferido/Repetido";
                    i++;
                    break;
                }
                case "005":{
                    menu[i] = "Listado Estudiantes-Diferido";
                    i++;
                    break;
                }
                case "006":{
                    menu[i] = "Listado Estudiantes-Repetido";
                    i++;
                    break;
                }
                case "007": {
                    menu[i] = "Local";
                    i++;
                    break;
                }
                case "008":{
                    menu[i] = "Evaluación";
                    i++;
                    break;
                }
                case "009":{
                    menu[i] = "Inscripción Primera Revisión";
                    i++;
                    break;
                }
                case "010": {
                    menu[i] = "Primera Revisión";
                    i++;
                    break;
                }
                case "011":{
                    menu[i] = "Aprobar Solicitudes Diferido";
                    i++;
                    break;
                }
                case "012":{
                    menu[i] = "Ciclo";
                    i++;
                    break;
                }
                case "013": {
                    menu[i] = "Carga Academica";
                    i++;
                    break;
                }
                case "014":{
                    menu[i] = "Docente";
                    i++;
                    break;
                }
                case "015":{
                    menu[i] = "Asignatura";
                    i++;
                    break;
                }
                case "016": {
                    menu[i] = "Solicitud Impresión";
                    i++;
                    break;
                }
                case "017":{
                    menu[i] = "Docente Director";
                    i++;
                    break;
                }
                case "018":{
                    menu[i] = "Estado Impresión";
                    i++;
                    break;
                }
                case "019":{
                    menu[i] = "Encargado Impresiones";
                    i++;
                    break;
                }
                case "020":{
                    menu[i] = "Solicitud Revision";
                    i++;
                    break;
                }
                case "021":{
                    menu[i] = "Lista de Revisiones";
                    i++;
                    break;
                }
                case "022":{
                    menu[i] = "Segunda Revisión.";
                    i++;
                    break;
                }
                case "023":{
                    menu[i] = "Lista de Revisiones Docente";
                    i++;
                }
            }
        }
        return menu;
    }
}
