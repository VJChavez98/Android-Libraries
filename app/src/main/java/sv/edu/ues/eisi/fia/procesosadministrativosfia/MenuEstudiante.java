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
        Log.d("CARNET", carnet);
        helper = new ControladorBase(this);
        helper.abrir();
        acceso = helper.consultarAcceso(carnet);
        helper.cerrar();
        menu = menu(acceso);
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
    public String[] menu(ArrayList<String> id){
        String[] menu = new String[id.size()];
        activities = new String[id.size()];
        int i = 0;
        while (i <id.size()){
            switch (id.get(i)) {
                case "001": {
                    menu[i] = "Estudiante";
                    activities[i] ="Estudiante_menu";
                    i++;
                    break;
                }
                case "002":{
                    menu[i] = "Solicitud Repetido";
                    activities[i] ="Repetido_menu";
                    i++;
                    break;
                }
                case "003":{
                    menu[i] = "Solicitud Diferido";
                    activities[i] ="Diferido_menu";
                    i++;
                    break;
                }
                case "004": {
                    menu[i] = "Detalle Diferido/Repetido";
                    activities[i] ="DetalleDiferidoRepetido_menu";
                    i++;
                    break;
                }
                case "005":{
                    menu[i] = "Listado Estudiantes-Diferido";
                    activities[i] ="DetalleEstudianteDiferido_consultar";
                    i++;
                    break;
                }
                case "006":{
                    menu[i] = "Listado Estudiantes-Repetido";
                    activities[i] ="DetalleEstudianteRepetido_consultar";
                    i++;
                    break;
                }
                case "007": {
                    menu[i] = "Local";
                    activities[i] ="Local_menu";
                    i++;
                    break;
                }
                case "008":{
                    menu[i] = "Evaluación";
                    activities[i] ="Evaluacion_menu";
                    i++;
                    break;
                }
                case "009":{
                    menu[i] = "Inscripción Primera Revisión";
                    activities[i] ="PeriodoInscripcionRevision_menu";
                    i++;
                    break;
                }
                case "010": {
                    menu[i] = "Primera Revisión";
                    activities[i] ="PrimeraRevision_menu";
                    i++;
                    break;
                }
                case "011":{
                    menu[i] = "Aprobar Solicitudes Diferido";
                    activities[i] ="SolicitudDiferido_consultarDocente";
                    i++;
                    break;
                }
                case "012":{
                    menu[i] = "Ciclo";
                    activities[i] ="CicloMenuActivity";
                    i++;
                    break;
                }
                case "013": {
                    menu[i] = "Carga Academica";
                    activities[i] ="CargaAcademicaMenuActivity";
                    i++;
                    break;
                }
                case "014":{
                    menu[i] = "Docente";
                    activities[i] ="DocenteMenuActivity";
                    i++;
                    break;
                }
                case "015":{
                    menu[i] = "Asignatura";
                    activities[i] ="AsignaturaMenuActivity";
                    i++;
                    break;
                }
                case "016": {
                    menu[i] = "Solicitud Impresión";
                    activities[i] ="SolImpresionMenuActivity";
                    i++;
                    break;
                }
                case "017":{
                    menu[i] = "Docente Director";
                    activities[i] ="DocDirectorMenuActivity";
                    i++;
                    break;
                }
                case "018":{
                    menu[i] = "Estado Impresión";
                    activities[i] ="EstadoImpresionMenuActivity";
                    i++;
                    break;
                }
                case "019":{
                    menu[i] = "Encargado Impresiones";
                    activities[i] ="EncarImpresionesMenuActivity";
                    i++;
                    break;
                }
                case "020":{
                    menu[i] = "Segunda Revisión.";
                    activities[i] ="SegundaRevisionMenu";
                    i++;
                    break;
                }
            }
        }
        return menu;
    }
}
