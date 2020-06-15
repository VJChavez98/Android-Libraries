package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    ControladorBase DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper = new ControladorBase(this);
        editUsername = (EditText) findViewById(R.id.editUser);
        editPassword = (EditText) findViewById(R.id.editPass);
        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void login(View view) {
        Usuario usuario = new Usuario();

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        usuario.setUsername(username);
        usuario.setPassword(password);
        DBHelper.abrir();
        if (DBHelper.consultarUsuario(username, password)) {
            Intent intent = new Intent(this, MenuEstudiante.class);
            intent.putExtra("carnet", editUsername.getText().toString());
            startActivity(intent);
            editUsername.setText("");
            editPassword.setText("");
            editUsername.requestFocus();

        } else {

            Toast.makeText(this, "Error, usuario o contraseña no encontrados", Toast.LENGTH_SHORT).show();
        }
    }

    public void llenar(View view) {
        String resultado = DBHelper.LlenarDatos();
        findViewById(R.id.relativeLayout).setVisibility(View.GONE);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }
}
