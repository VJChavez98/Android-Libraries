package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class Diferido_insertar extends AppCompatActivity {

    ControladorBase DBHelper;
    EditText editCarnet, editMateria, editGrupoTeorico, editGrupoDiscusion, editGrupoLab, editFechaEval, editHoraEval, editMotivo, editEva;
    Spinner motivos, spinTipo;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID = 1;
    String[] tipos = {"Seleccione el tipo de evaluacion", "EP", "ED", "EL"};
    Calendar c = Calendar.getInstance();
    Button mOptions;
    final int FOTOGRAFIA = 0;
    final int GALLERY = 1;
    Uri file;
    Button srcImg;
    FirebaseAuth mAuth;
    StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_insertar);
        DBHelper = new ControladorBase(getApplicationContext());
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editMateria = (EditText) findViewById(R.id.editAsignatura);
        editGrupoTeorico = (EditText) findViewById(R.id.editGrupoTeorico);
        editGrupoDiscusion = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGrupoLab = (EditText) findViewById(R.id.editGrupoLab);
        editEva = (EditText) findViewById(R.id.editCodEva);
        spinTipo = (Spinner) findViewById(R.id.spinTipoEval);
        editFechaEval = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval = (EditText) findViewById(R.id.editHoraRealizada);
        editMotivo = (EditText) findViewById(R.id.editMotivo);
        motivos = (Spinner) findViewById(R.id.spinMotivos);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        srcImg = findViewById(R.id.rutaSolic);
        spinTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos));
        mOptions = findViewById(R.id.option);
        mOptions.setOnClickListener(mostrarOpciones);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("Foto") != null) {
                file = Uri.parse(savedInstanceState.getString("foto"));
            }
        }
        srcImg.setOnClickListener(showImage);
        sMonthIni = c.get(Calendar.MONTH);
        sDayIni = c.get(Calendar.DAY_OF_MONTH);
        sYearIni = c.get(Calendar.YEAR);
        sHour = c.get(Calendar.HOUR_OF_DAY);
        sMinute = c.get(Calendar.MINUTE);


        editFechaEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
        editHoraEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HOUR_ID);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (file != null) {
            bundle.putString("Foto", file.toString());
        }
        super.onSaveInstanceState(bundle);
    }

    private void colocar_fecha() {
        if (String.valueOf(nMonthIni).length() == 1 && String.valueOf(nDayIni).length() == 1) {
            editFechaEval.setText(nYearIni + "-0" + nMonthIni + "-0" + nDayIni);
        } else if (String.valueOf(nMonthIni).length() == 1) {
            editFechaEval.setText(nYearIni + "-0" + nMonthIni + "-" + nDayIni);
        } else if (String.valueOf(nDayIni).length() == 1) {
            editFechaEval.setText(nYearIni + "-" + nMonthIni + "-0" + nDayIni);
        } else {
            editFechaEval.setText(nYearIni + "-" + nMonthIni + "-" + nDayIni);
        }
    }

    private void colocarHora() {
        if (String.valueOf(nMinute).length() == 1 && String.valueOf(nHour).length() == 1) {
            editHoraEval.setText("0" + nHour + ":0" + nMinute + ":00");
        } else if (String.valueOf(nHour).length() == 1) {
            editHoraEval.setText("0" + nHour + ":" + nMinute + ":00");
        } else if (String.valueOf(nMinute).length() == 1) {
            editHoraEval.setText(nHour + ":0" + nMinute + ":00");
        } else {
            editHoraEval.setText(nHour + ":" + nMinute + ":00");
        }
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            nHour = hourOfDay;
            nMinute = minute;
            colocarHora();
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    nYearIni = year;
                    nMonthIni = monthOfYear;
                    nDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
            case HOUR_ID:
                return new TimePickerDialog(this, mTimeSetListener, nHour, nMinute, true);
        }
        return null;
    }


    public void insertarDiferido(View view) {
        String regInsertados;
        String codSolicitud;
        String carnet = editCarnet.getText().toString();
        String asignatura = editMateria.getText().toString();
        String GT = editGrupoTeorico.getText().toString();
        String GD = editGrupoDiscusion.getText().toString();
        String GL = editGrupoLab.getText().toString();
        int numEva = Integer.parseInt(editEva.getText().toString());
        String tipoEval = spinTipo.getSelectedItem().toString();
        String fechaEval = editFechaEval.getText().toString();
        String hora = editHoraEval.getText().toString();
        String motivoSpin = motivos.getSelectedItem().toString();
        String motivoEdit = editMotivo.getText().toString();
        codSolicitud = carnet + asignatura + tipoEval + numEva;
        final SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
        solicitudDiferido.setIdSolicitud(codSolicitud);
        solicitudDiferido.setCarnet(carnet);
        solicitudDiferido.setCodMateria(asignatura);
        solicitudDiferido.setNumeroEval(numEva);
        solicitudDiferido.setGT(GT);
        solicitudDiferido.setGD(GD);
        solicitudDiferido.setGL(GL);
        solicitudDiferido.setTipoEva(tipoEval);
        solicitudDiferido.setFechaEva(fechaEval);
        solicitudDiferido.setHoraEva(hora);
        solicitudDiferido.setMotivo(motivoSpin);
        solicitudDiferido.setOtroMotivo(motivoEdit);
        solicitudDiferido.setEstado("Pendiente");
        solicitudDiferido.setRutaJustificante(file.getLastPathSegment());
        DBHelper.abrir();
        regInsertados = DBHelper.insertar(solicitudDiferido);
        DBHelper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        StorageReference storageReference = mStorage.child("Justificante").child(file.getLastPathSegment());
        storageReference.putFile(file);
    }
    public void limpiarTexto(View v) {
        editCarnet.setText("");
        editCarnet.setText("");
        editMateria.setText("");
        editGrupoTeorico.setText("");
        editGrupoDiscusion.setText("");
        editGrupoLab.setText("");
        editEva.setText("");
        spinTipo.setSelection(0);
        editFechaEval.setText("");
        editHoraEval.setText("");
        editMotivo.setText("");
        motivos.setSelection(0);
    }
    public void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }
    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), c.getTimeInMillis() + ".jpg");
        file = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, FOTOGRAFIA);
    }
    View.OnClickListener mostrarOpciones = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setTitle("Elegir acción");
            builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0){
                        selectImage();
                    }else {
                      takePicture();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOTOGRAFIA) {
            if (resultCode == RESULT_OK) {
                mOptions.setVisibility(View.GONE);
                srcImg.setVisibility(View.VISIBLE);
                srcImg.setText("Mostrar foto");

            } else {
                Toast.makeText(getApplicationContext(), "Fotografia no tomada", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == GALLERY){
            if (resultCode == RESULT_OK){
                mOptions.setVisibility(View.GONE);
                srcImg.setVisibility(View.VISIBLE);
                file = data.getData();
                srcImg.setText("Mostrar imagen seleccionada");
                Log.d("RUTAAAAAAAA", file.toString());
            }
        }
    }
    View.OnClickListener showImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.mostar_imagen, null, false);
            builder.setView(dialogView);
            builder.setTitle("Justificante Diferido");
            ImageView justificante = dialogView.findViewById(R.id.mostarImagen);
            justificante.setImageURI(file);
            builder.setNegativeButton("Seleccionar otra imagen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectImage();
                }
            });
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
