package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CicloConsultarActivity extends Activity {

    ControladorBase helper;
    EditText editCodciclo;
    EditText editFechadesde;
    EditText editFechahasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_consultar);
        helper = new ControladorBase(this);

        editCodciclo=(EditText) findViewById(R.id.editCodciclo);
        editFechadesde=(EditText) findViewById(R.id.editFechadesde);
        editFechahasta=(EditText) findViewById(R.id.editFechahasta);

    }
    public void consultarCiclo(View v){
        helper.abrir();
        Ciclo ciclo=
                helper.consultarCiclo(editCodciclo.getText().toString());
        helper.cerrar();
        if(ciclo==null)
        Toast.makeText(this,"Ciclo con Código"+editCodciclo.getText().toString()+"no encontrado",Toast.LENGTH_SHORT).show();
        else{
            editCodciclo.setText(ciclo.getCodciclo());
            editFechadesde.setText(ciclo.getFechadesde());
            editFechahasta.setText(ciclo.getFechahasta());


        }

    }

    public void limpiarTexto(View v){
        editCodciclo.setText("");
        editFechadesde.setText("");
        editFechahasta.setText("");
    }
}
