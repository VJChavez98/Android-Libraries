package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SolicitudDiferido_consultarDocente extends AppCompatActivity {
    EditText editMateria, editNumEval, ciclo;
    Spinner spinTipoEval, spinEstadoSoli;
    ListView listView1;
    FrameLayout frame;
    ControladorBase helper;
    StorageReference mStorage;
    File file;
    String ruta;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_diferido_consultar_docente);
        helper = new ControladorBase(this);
        editMateria = findViewById(R.id.editAsignatura);
        editNumEval = findViewById(R.id.editNumeval);
        spinTipoEval = findViewById(R.id.spinTipoEval);
        spinEstadoSoli = findViewById(R.id.estadoSolicitud);
        listView1 = findViewById(R.id.listaSolicitudes);
        frame = findViewById(R.id.fragmentSolicitudes);
        mStorage = FirebaseStorage.getInstance().getReference().child("Justificante");
        mProgress = new ProgressDialog(this);
        ciclo = findViewById(R.id.editCodciclo);

    }

    public void consultarSolicitudes(View view) {
        if (!(editMateria.getText().toString().isEmpty() && editNumEval.getText().toString().isEmpty() && ciclo.getText().toString().isEmpty() && spinTipoEval.getSelectedItem().toString().equals(spinTipoEval.getItemAtPosition(0).toString()) && spinEstadoSoli.getSelectedItem().toString().equals(spinEstadoSoli.getItemAtPosition(0).toString()))) {
            if (!editMateria.getText().toString().isEmpty()) {
                if (!spinTipoEval.getSelectedItem().toString().equals(spinTipoEval.getItemAtPosition(0).toString())) {
                    if (!ciclo.getText().toString().isEmpty()) {
                        if (!editNumEval.getText().toString().isEmpty()) {
                            if (!spinEstadoSoli.getSelectedItem().toString().equals(spinEstadoSoli.getItemAtPosition(0).toString())) {
                                helper.abrir();
                                final ArrayList<String> solicitudes = helper.consultarSolicitudesPendiente(editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), Integer.parseInt(editNumEval.getText().toString()), spinEstadoSoli.getSelectedItem().toString(), ciclo.getText().toString());
                                helper.cerrar();
                                frame.setVisibility(View.VISIBLE);
                                if (solicitudes.size() > 0) {

            listView1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicitudes));
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    helper.abrir();
                    final SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(solicitudes.get(position), editMateria.getText().toString(), spinTipoEval.getSelectedItem().toString(), editNumEval.getText().toString());
                    helper.cerrar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_diferido_actualizar, null, false);
                    builder.setView(dialogView);
                    builder.setTitle("Aprobar/Denegar solicitud");
                    EditText carnet = dialogView.findViewById(R.id.editCarnet);
                    EditText materia = dialogView.findViewById(R.id.editAsignatura);
                    EditText numEval = dialogView.findViewById(R.id.editCodEva);
                    EditText GT = dialogView.findViewById(R.id.editGrupoTeorico);
                    EditText GD = dialogView.findViewById(R.id.editGrupoDiscusion);
                    EditText GL = dialogView.findViewById(R.id.editGrupoLab);
                    EditText fecha = dialogView.findViewById(R.id.editFechaRealizada);
                    EditText hora = dialogView.findViewById(R.id.editHoraRealizada);
                    EditText descrip = dialogView.findViewById(R.id.editMotivo);
                    Spinner tipoEval = dialogView.findViewById(R.id.spinTipoEval);
                    EditText ciclo = dialogView.findViewById(R.id.editCodciclo);
                    Spinner motivo = dialogView.findViewById(R.id.spinMotivos);
                    final ImageView srcImg = dialogView.findViewById(R.id.justificante);
                    final Spinner estado = dialogView.findViewById(R.id.estadoSolicitud);
                    carnet.setText(solicitudDiferido.getCarnet());
                    materia.setText(solicitudDiferido.getCodMateria());
                    GT.setText(solicitudDiferido.getGT());
                    GD.setText(solicitudDiferido.getGD());
                    GL.setText(solicitudDiferido.getGL());
                    tipoEval.setSelection(tipoEvaluacion(solicitudDiferido.getTipoEva()));
                    numEval.setText(String.valueOf(solicitudDiferido.getNumeroEval()));
                    fecha.setText(solicitudDiferido.getFechaEva());
                    hora.setText(solicitudDiferido.getHoraEva());
                    motivo.setSelection(colocarMotivo(solicitudDiferido.getMotivo()));
                    descrip.setText(solicitudDiferido.getOtroMotivo());
                    estado.setSelection(colocarEstado(solicitudDiferido.getEstado()));
                    ciclo.setText(solicitudDiferido.getCiclo());
                    ruta = solicitudDiferido.getRutaJustificante();
                    mProgress.setTitle("Cargando justificante");
                    mProgress.setMessage("Por favor espere");
                    mProgress.show();
                    try {
                        file = File.createTempFile(ruta, "jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mStorage.child(ruta).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Se descargo correctamente",Toast.LENGTH_SHORT).show();
                            Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
                            Glide.with(view.getContext()).load(b).centerCrop().into(srcImg);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error al descargar justificante", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                            mProgress.dismiss();
                        }
                    });
                    tipoEval.setEnabled(false);
                    motivo.setEnabled(false);
                    builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helper.abrir();
                            solicitudDiferido.setEstado(estado.getSelectedItem().toString());
                            String resultado = helper.actualizarEstado(solicitudDiferido);
                            helper.cerrar();
                            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    srcImg.setOnClickListener(showImage);
               }
            });

                                } else {
                                    frame.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "No se encontraron solicitudes", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(getApplicationContext(), "Campo obligatorio: Estado", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Campo obligatorio: Numero evaluacion", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Campo obligatorio: Ciclo", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Campo obligatorio: Tipo evaluacion", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Campo obligatorio: Codigo materia", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View view){
        editMateria.setText("");
        editNumEval.setText("");
        spinTipoEval.setSelection(0);
        spinEstadoSoli.setSelection(0);
        frame.setVisibility(View.GONE);
        ciclo.setText("");
        editMateria.requestFocus();

    }

    public int tipoEvaluacion(String tipo){
        switch (tipo){
            case "EP":
                return 1;
            case "ED":
                return 2;
            case "EL":
                return 3;
            default: return 0;
        }
    }
    public int colocarMotivo(String motivo){
        switch (motivo){
            case "Salud":
                return 1;
            case "Trabajo":
                return 2;
            case "Interferencia":
                return 3;
            case "Viaje":
                return 4;
            case "Duelo":
                return 5;
            case "Otro":
                return 6;
            default: return 0;
        }
    }
    public int colocarEstado(String estado){
        switch (estado){
            case "Pendiente": return 1;
            case "Aprobada" : return 2;
            case "Denegada" : return 3;
            default:return 0;
        }
    }
    View.OnClickListener showImage = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.mostar_imagen, null, false);
            builder.setView(dialogView);
            builder.setTitle("Justificante Diferido");
            final ImageView justificante = dialogView.findViewById(R.id.mostarImagen);
            Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
            Glide.with(getApplicationContext()).load(b).into(justificante);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

    };
}
