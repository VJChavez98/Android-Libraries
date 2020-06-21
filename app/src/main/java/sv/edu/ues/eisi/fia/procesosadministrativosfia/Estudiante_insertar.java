package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Estudiante_insertar extends AppCompatActivity {
    EditText editCarnet, editNombre, editApellido, editCarrera;
    ControladorBase helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_insertar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editNombre = findViewById(R.id.editNombreEstudiante);
        editApellido = findViewById(R.id.editApellidoEstudiante);
        editCarrera = findViewById(R.id.editCarreraEstudiante);
    }

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editCarrera.setText("");
    }

    public void insertarEstudiante(View view) {
        if (!(editCarnet.getText().toString().isEmpty() && editNombre.getText().toString().isEmpty() && editApellido.getText().toString().isEmpty() && editCarrera.getText().toString().isEmpty())){
            if (!editCarnet.getText().toString().isEmpty()) {
                if (!editNombre.getText().toString().isEmpty()) {
                    if (!editApellido.getText().toString().isEmpty()) {
                        if (!editCarrera.getText().toString().isEmpty()) {
                            Estudiante estudiante = new Estudiante();
                            estudiante.setCarnet(editCarnet.getText().toString());
                            estudiante.setNombre(editNombre.getText().toString());
                            estudiante.setApellido(editApellido.getText().toString());
                            estudiante.setCarrera(editCarrera.getText().toString());
                            helper.abrir();
                            String resultado = helper.insertar(estudiante);
                            helper.cerrar();
                            Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Carrera",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Apellidos",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Nombres",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), "Campo obligatorio: Carnet",Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getApplicationContext(), "Campos vacios",Toast.LENGTH_SHORT).show();
    }
}
