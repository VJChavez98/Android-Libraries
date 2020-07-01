package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocDirectorInsertarActivity extends Activity {
    ControladorBase helper;
    EditText editIdDocenteDirector;
    EditText editIdEscuela;
    EditText editNombreDirector;
    EditText editApellidoDirector;
    EditText editCorreoDirector;
    EditText editTelefono;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5;
    Button BtnPlay;
    private int numarch=0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_director_insertar);
        helper = new ControladorBase(this);
        editIdDocenteDirector = (EditText) findViewById(R.id.editIdDocenteDirector);
        editIdEscuela = (EditText) findViewById(R.id.editIdEscuela);
        editNombreDirector = (EditText) findViewById(R.id.editNombreDirector);
        editApellidoDirector = (EditText) findViewById(R.id.editApellidoDirector);
        editCorreoDirector = (EditText) findViewById(R.id.editCorreoDirector);
        editTelefono = (EditText) findViewById(R.id.editTelefono);

        Texto=(TextView) findViewById(R.id.editIdDocenteDirector);
        Texto1=(TextView) findViewById(R.id.editIdEscuela);
        Texto2=(TextView) findViewById(R.id.editNombreDirector);
        Texto3=(TextView) findViewById(R.id.editApellidoDirector);
        Texto4=(TextView) findViewById(R.id.editCorreoDirector);
        Texto5=(TextView) findViewById(R.id.editTelefono);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

    }


    private boolean validarEmail(String email) {
        String emailInput = editCorreoDirector.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return false;
        } else {
            editCorreoDirector.setError(null);
            return true;
        }
    }

    private static boolean validarTelefono(String telefono) {
        Pattern pat = Pattern.compile("^[672]\\d{7}$");
        Matcher mat = pat.matcher(telefono);
        if (mat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void insertarDocDirector(View v) {

        editIdDocenteDirector.setError(null);
        editIdEscuela.setError(null);
        editNombreDirector.setError(null);
        editApellidoDirector.setError(null);
        editCorreoDirector.setError(null);
        editTelefono.setError(null);

        String iddocentedirector = editIdDocenteDirector.getText().toString();
        String idescuela = editIdEscuela.getText().toString();
        String nombredirector = editNombreDirector.getText().toString();
        String apellidodirector = editApellidoDirector.getText().toString();
        String correodirector = editCorreoDirector.getText().toString();
        String telefono = editTelefono.getText().toString();
        String regInsertados;

        if (TextUtils.isEmpty(iddocentedirector)) {
            editIdDocenteDirector.setError(getString(R.string.error_campo_obligatorio));
            editIdDocenteDirector.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(idescuela)) {
            editIdEscuela.setError(getString(R.string.error_campo_obligatorio));
            editIdEscuela.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nombredirector)) {
            editNombreDirector.setError(getString(R.string.error_campo_obligatorio));
            editNombreDirector.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(apellidodirector)) {
            editApellidoDirector.setError(getString(R.string.error_campo_obligatorio));
            editApellidoDirector.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(correodirector)) {
                editCorreoDirector.setError(getString(R.string.error_campo_obligatorio));
                editCorreoDirector.requestFocus();
                return;
        }

        if (TextUtils.isEmpty(telefono)) {
            editTelefono.setError(getString(R.string.error_campo_obligatorio));
            editTelefono.requestFocus();
            return;
        }

        if (validarEmail(correodirector)==false ){
            editCorreoDirector.setError("Direccion de Correo No Valida");
            editCorreoDirector.requestFocus();
            return;
        }

        if(validarTelefono(telefono)==false){
            editTelefono.setError("Telefono no Valido");
            editTelefono.requestFocus();
            return;
        }

        Integer telefonoN = Integer.valueOf(editTelefono.getText().toString());

        DocDirector doc = new DocDirector();

        doc.setIddocentedirector(iddocentedirector);
        doc.setIdescuela(idescuela);
        doc.setNombredirector(nombredirector);
        doc.setApellidodirector(apellidodirector);
        doc.setCorreodirector(correodirector);
        doc.setTelefono(telefonoN);

        helper.abrir();
        regInsertados = helper.insertarDocDirector(doc);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdDocenteDirector.setText("");
        editIdEscuela.setText("");
        editNombreDirector.setText("");
        editApellidoDirector.setText("");
        editCorreoDirector.setText("");
        editTelefono.setText("");
    }
    TextToSpeech.OnInitListener OnInit= new TextToSpeech.OnInitListener(){
        @Override
        public void onInit(int status){
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else{
                Toast.makeText(getApplicationContext(), "TTS No Disponible", Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener(){
        @SuppressLint("SdCardPath")
        public void onClick(View v){
            if (v.getId()==R.id.btnText2SpeechPlay){
                tts.speak(Texto.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto4.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto5.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
