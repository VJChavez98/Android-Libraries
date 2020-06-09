package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuEstudiante extends ListActivity{
    String [] menu = {"Estudiante","Solicitud Repetido", "Solicitud Diferido","Detalle Diferido/Repetido","Listado estudiantes-diferido","Listado estudiantes-repetido", "Local", "Evaluacion",  "Inscripcion a Primera Revision", "Primera Revision","Solicitudes Docente","Tabla Ciclo","Tabla Carga Académica","Tabla Docente","Tabla Asignatura","Tabla SolImpresion", "Tabla DocDirector", "TablaEstadoImpresion", "TablaEncarImpresiones", "Cerrar sesión"};
    String [] activities = {"Estudiante_menu","Repetido_menu", "Diferido_menu","DetalleDiferidoRepetido_menu","DetalleEstudianteDiferido_consultar", "DetalleEstudianteRepetido_consultar","Local_menu", "Evaluacion_menu", "PeriodoInscripcionRevision_menu", "PrimeraRevision_menu","SolicitudDiferido_consultarDocente","CicloMenuActivity","CargaAcademicaMenuActivity","DocenteMenuActivity", "AsignaturaMenuActivity","SolImpresionMenuActivity", "DocDirectorMenuActivity", "EstadoImpresionMenuActivity", "EncarImpresionesMenuActivity"};
    boolean doubleBackToExitPressedOnce = false;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    protected void onListItemClick(ListView listView, View view, int position, long id){
        super.onListItemClick(listView, view, position, id);
        if (position!=19) {
            String nombreValue = activities[position];
            try {
                Class<?> clase = Class.forName("sv.edu.ues.eisi.fia.procesosadministrativosfia." + nombreValue);
                Intent intent = new Intent(this, clase);
                this.startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            signOut();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void signOut() {
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            signOut();
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
