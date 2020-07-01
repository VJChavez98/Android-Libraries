package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class DocDirectorConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editIdDocenteDirector;
    EditText editIdEscuela;
    EditText editNombreDirector;
    EditText editApellidoDirector;
    EditText editCorreoDirector;
    EditText editTelefono;
    LinearLayout linear;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_director_consultar);
        helper = new ControladorBase(this);
        editIdDocenteDirector = (EditText) findViewById(R.id.editIdDocenteDirector);
        editIdEscuela = (EditText) findViewById(R.id.editIdEscuela);
        editNombreDirector = (EditText) findViewById(R.id.editNombreDirector);
        editApellidoDirector = (EditText) findViewById(R.id.editApellidoDirector);
        editCorreoDirector = (EditText) findViewById(R.id.editCorreoDirector);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        linear = findViewById(R.id.detalle);

        mEntradaVoz=findViewById(R.id.editIdDocenteDirector);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });
    }

    private void iniciarEntradaVoz(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el ID del Docente Director ");
        try {
            startActivityForResult(i, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEntradaVoz.setText(result.get(0));
                }
                break;
            }
        }
    }
    public void consultarDocDirector(View v) {

        editIdDocenteDirector.setError(null);
        editIdEscuela.setError(null);

        String iddocentedirector = editIdDocenteDirector.getText().toString();
        String idescuela = editIdEscuela.getText().toString();

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

        
        helper.abrir();
        DocDirector docDirector = helper.consultarDocDirector(editIdDocenteDirector.getText().toString(), editIdEscuela.getText().toString());

        helper.cerrar();
        if(docDirector == null)
            Toast.makeText(this, "Docente no registrado", Toast.LENGTH_LONG).show();
        else{
            linear.setVisibility(View.VISIBLE);
            editNombreDirector.setText(String.valueOf(docDirector.getNombredirector()));
            editApellidoDirector.setText(String.valueOf(docDirector.getApellidodirector()));
            editCorreoDirector.setText(String.valueOf(docDirector.getCorreodirector()));
            editTelefono.setText(String.valueOf(docDirector.getTelefono()));
        }
    }

    public void limpiarTexto(View v){
        editIdDocenteDirector.setText("");
        editIdEscuela.setText("");
        editNombreDirector.setText("");
        editApellidoDirector.setText("");
        editCorreoDirector.setText("");
        editTelefono.setText("");
        linear.setVisibility(View.GONE);
    }
}
