package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Estudiante_consultar extends AppCompatActivity {
    EditText editCarnet, editNombre, editApellido, editCarrera;
    ControladorBase helper;
    TextView lblNombre, lblApellido, lblCarrera;
    Button eliminarBtn, actualizarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_consultar);
        helper = new ControladorBase(this);
        editCarnet = findViewById(R.id.editCarnet);
        editNombre = findViewById(R.id.editNombreEstudiante);
        editNombre.setVisibility(View.GONE);
        editApellido = findViewById(R.id.editApellidoEstudiante);
        editApellido.setVisibility(View.GONE);
        editCarrera = findViewById(R.id.editCarreraEstudiante);
        editCarrera.setVisibility(View.GONE);
        lblNombre = findViewById(R.id.lblNombre);
        lblNombre.setVisibility(View.GONE);
        lblApellido = findViewById(R.id.lblApellido);
        lblApellido.setVisibility(View.GONE);
        lblCarrera = findViewById(R.id.lblCarrera);
        lblCarrera.setVisibility(View.GONE);
        eliminarBtn = findViewById(R.id.EliminarBtn);
        eliminarBtn.setVisibility(View.GONE);
        actualizarBtn = findViewById(R.id.ModificarBtn);
        actualizarBtn.setVisibility(View.GONE);
    }
    public void limpiarTexto(View view) {
        lblNombre.setVisibility(View.GONE);
        lblApellido.setVisibility(View.GONE);
        lblCarrera.setVisibility(View.GONE);
        editCarnet.setText("");
        editCarnet.setEnabled(true);
        editNombre.setText("");
        editNombre.setVisibility(View.GONE);
        editApellido.setText("");
        editApellido.setVisibility(View.GONE);
        editCarrera.setText("");
        editCarrera.setVisibility(View.GONE);
        eliminarBtn.setVisibility(View.GONE);
        actualizarBtn.setVisibility(View.GONE);
    }
    public void consultarEstudiante(View view) {
            if (!editCarnet.getText().toString().isEmpty()) {
                helper.abrir();
                Estudiante estudiante = helper.consultarEstudiante(editCarnet.getText().toString());
                helper.cerrar();
                if (estudiante == null)
                    Toast.makeText(this, "Estudiante no encontrado", Toast.LENGTH_LONG).show();
                else {
                    editCarnet.setText(estudiante.getCarnet());
                    editCarnet.setEnabled(false);
                    editNombre.setText(estudiante.getNombre());
                    editApellido.setText(estudiante.getApellido());
                    editCarrera.setText(estudiante.getCarrera());
                    lblNombre.setVisibility(View.VISIBLE);
                    editNombre.setVisibility(View.VISIBLE);
                    lblApellido.setVisibility(View.VISIBLE);
                    editApellido.setVisibility(View.VISIBLE);
                    lblCarrera.setVisibility(View.VISIBLE);
                    editCarrera.setVisibility(View.VISIBLE);
                    eliminarBtn.setVisibility(View.VISIBLE);
                    actualizarBtn.setVisibility(View.VISIBLE); }
            }else Toast.makeText(getApplicationContext(), "Campo Obligatorio: Carnet", Toast.LENGTH_SHORT).show();
    }
    public void EliminarEstudiante(View view) {
       Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(String.valueOf(editCarnet.getText()));
        estudiante.setNombre(editNombre.getText().toString());
        estudiante.setApellido(editApellido.getText().toString());
        estudiante.setCarrera(editCarrera.getText().toString());
        helper.abrir();
        String regAfectados = helper.eliminar(estudiante);
        helper.cerrar();
        Toast.makeText(this, regAfectados, Toast.LENGTH_SHORT).show();
        limpiarTexto(view);
    }

    public void actualizarEstudiante(View view) {
        if (!areEmpty()) {
                if (!editNombre.getText().toString().isEmpty()) {
                    if (!editApellido.getText().toString().isEmpty()) {
                        if (!editCarrera.getText().toString().isEmpty()) {
                            Estudiante estudiante = new Estudiante();
                            estudiante.setCarnet(String.valueOf(editCarnet.getText()));
                            estudiante.setNombre(editNombre.getText().toString());
                            estudiante.setApellido(editApellido.getText().toString());
                            estudiante.setCarrera(editCarrera.getText().toString());
                            helper.abrir();
                            String regAfectado = helper.actualizar(estudiante);
                            helper.cerrar();
                            Toast.makeText(this,regAfectado,Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), "Campo Obligatorio: Carrera", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(), "Campo Obligatorio: Apellidos", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getApplicationContext(), "Campo Obligatorio: Nombres", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
    }
    public boolean areEmpty(){
        return (editNombre.getText().toString().isEmpty() && editApellido.getText().toString().isEmpty() && editCarrera.getText().toString().isEmpty());
    }
}
