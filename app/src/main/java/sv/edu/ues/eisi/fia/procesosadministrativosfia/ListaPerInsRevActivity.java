package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.adaptadores.ListaPerInsRevAdapter;

public class ListaPerInsRevActivity extends Activity {

    ArrayList<PeriodoInscripcionRevision> listaPeriodos;
    RecyclerView recyclerViewPeriodos;

    ArrayList<String> docente= null;

    ControladorBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_per_ins_rev);

        helper= new ControladorBase(this);

        listaPeriodos= new ArrayList<>();

        recyclerViewPeriodos= (RecyclerView) findViewById(R.id.recyclerPeriodo);
        recyclerViewPeriodos.setLayoutManager(new LinearLayoutManager(this));

        consultarPeriodosIncripcionRevision();

        ListaPerInsRevAdapter adapter=new ListaPerInsRevAdapter(listaPeriodos, recuperarDocente());


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PeriodoInscripcionRevision periodoSeleccionado= null;
                periodoSeleccionado= (PeriodoInscripcionRevision) listaPeriodos.get(recyclerViewPeriodos.getChildAdapterPosition(v));

                //Muestro con un Toast el objeto seleccionado
                //Toast.makeText(getApplicationContext(),periodo.getNombre(),Toast.LENGTH_SHORT).show();

                //Envío el objeto seleccionado a SolicitudRevisionActivity
                /*Intent intent = new Intent(ListaPerInsRevActivity.this,SolicitudRevision.class);

                Bundle bundle= new Bundle();
                bundle.putSerializable("periodo",periodoSeleccionado);

                intent.putExtras(bundle);
                //startActivity(intent);
                startActivityForResult(intent,1);*/

                Intent intent = new Intent(ListaPerInsRevActivity.this, SolicitudRevisionActivity.class);
                intent.putExtra("periodo", periodoSeleccionado);
                startActivity(intent);

            }
        });

        recyclerViewPeriodos.setAdapter(adapter);

    }

    private void consultarPeriodosIncripcionRevision() {

        SQLiteDatabase db = helper.abrirLeer();

        PeriodoInscripcionRevision periodo= null;

        String sql= "SELECT * FROM periodoinscripcionrevision";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){

            periodo = new PeriodoInscripcionRevision();

            periodo.setFechaDesde(cursor.getString(0));
            periodo.setFechaHasta(cursor.getString(1));
            periodo.setFechaRevision(cursor.getString(2));
            periodo.setHoraRevision(cursor.getString(3));
            periodo.setTipoRevision(cursor.getString(4));
            periodo.setCodDocente(cursor.getString(5));
            periodo.setCodLocal(cursor.getString(6));
            periodo.setCodAsignatura(cursor.getString(7));
            periodo.setCodCiclo(cursor.getString(8));
            periodo.setCodTipoEval(cursor.getString(9));
            periodo.setNumeroEval(cursor.getInt(10));

            listaPeriodos.add(periodo);
            //recuperarDocente(cursor.getString(5));
        }

        //recuperarDocente();
        //cursor.close();
        //db.close();

    }

    /*private String[] recuperarCodDocente(){

        String[] coddoc = new String[listaPeriodos.size()];

        for (int i=0; i<=listaPeriodos.size(); i++){

            coddoc[i] = listaPeriodos.get(i).getCodDocente();

        }

        return coddoc;
    }*/

   private ArrayList<String> recuperarDocente(/*String s*/){

        SQLiteDatabase db = helper.abrirLeer();

        //String[] parametros = {s};
       Cursor cursor = null;
       //String doc = null;
       //String[] listaCodDocentes= recuperarCodDocente();

       for (int i=0; i<=listaPeriodos.size(); i++){

            cursor = db.rawQuery("SELECT nombredocente, apellidodocente FROM Docente WHERE coddocente = " + listaPeriodos.get(i).getCodDocente(), null);

            cursor.moveToFirst();

            String doc= cursor.getString(0)+" "+ cursor.getString(1);


            docente.add(doc);

           //asignarDocente(doc);

       }

       return docente;

       //db.close();

    }




    /*private void asignarDocente(String s){

        docente=s;
    }*/


}
