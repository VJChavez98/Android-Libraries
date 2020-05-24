package sv.edu.ues.eisi.fia.procesosadministrativosfia.adaptadores;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.edu.ues.eisi.fia.procesosadministrativosfia.ControladorBase;
import sv.edu.ues.eisi.fia.procesosadministrativosfia.Docente;
import sv.edu.ues.eisi.fia.procesosadministrativosfia.PeriodoInscripcionRevision;
import sv.edu.ues.eisi.fia.procesosadministrativosfia.R;

public class ListaPerInsRevAdapter extends RecyclerView.Adapter<ListaPerInsRevAdapter.PeriodosInscripcionViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;

    ArrayList<PeriodoInscripcionRevision> listaPeriodos;

    ArrayList<String> nomCompletoDocente;



    public ListaPerInsRevAdapter(ArrayList<PeriodoInscripcionRevision> listaPeriodos ,ArrayList<String> nomdocente) {
        this.listaPeriodos = listaPeriodos;
        this.nomCompletoDocente=nomdocente;

    }

    @Override
    public PeriodosInscripcionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_periodos_inscripcion_revision,null,false);

        view.setOnClickListener(this);

        return new PeriodosInscripcionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeriodosInscripcionViewHolder holder, int position) {
        String doc = nomCompletoDocente.get(position);
        //TRATARE DE HACERLO POR MEDIO DEL PARAMETRO position
        holder.tipoRevision.setText(listaPeriodos.get(position).getTipoRevision().toString());
        holder.codAsignatura.setText(listaPeriodos.get(position).getCodAsignatura().toString());
        holder.codTipoEval.setText(listaPeriodos.get(position).getCodTipoEval().toString());
        holder.codCiclo.setText(listaPeriodos.get(position).getCodCiclo().toString());
        holder.numeroEval.setText(Integer.toString(listaPeriodos.get(position).getNumeroEval()));
        holder.nombreDocente.setText(doc);
        holder.codLocal.setText(listaPeriodos.get(position).getCodLocal().toString());
        holder.fechaRevision.setText(listaPeriodos.get(position).getFechaRevision().toString());
        holder.horaRevision.setText(listaPeriodos.get(position).getHoraRevision().toString());

    }

    @Override
    public int getItemCount() {
        return listaPeriodos.size();
    }


    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }



    //metodo para generar evento en cada elemento del RecyclerView
    @Override
    public void onClick(View v) {

        if(listener!=null){

            listener.onClick(v);
        }

    }




    public class PeriodosInscripcionViewHolder extends RecyclerView.ViewHolder {

        TextView tipoRevision, codDocente, nombreDocente, codLocal, codAsignatura, codCiclo, codTipoEval, fechaRevision, horaRevision, numeroEval;

        public PeriodosInscripcionViewHolder(View itemView) {
            super(itemView);
            tipoRevision = (TextView) itemView.findViewById(R.id.textTipoRev);
            codAsignatura = (TextView) itemView.findViewById(R.id.textCodAsig);
            codTipoEval = (TextView) itemView.findViewById(R.id.textTipoEva);
            codCiclo = (TextView) itemView.findViewById(R.id.textCiclo);
            numeroEval = (TextView) itemView.findViewById(R.id.textNumEva);
            nombreDocente = (TextView) itemView.findViewById(R.id.textNomDocente);
            codLocal = (TextView) itemView.findViewById(R.id.textLocal);
            fechaRevision = (TextView) itemView.findViewById(R.id.textFechaRev);
            horaRevision = (TextView) itemView.findViewById(R.id.textHoraRev);
        }
    }


}
