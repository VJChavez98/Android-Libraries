package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;
import java.util.Locale;

public class Diferido_consultar extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    EditText editNumEval, editCarnet, editCodMateria, editGT, editGD, editGL, editFechaEval, editHoraEval, editOtroMotivo, estadoSoli, ciclo;


    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5, Texto6, Texto7;
    Button BtnPlay;
    File ultima;
    private int numarch=0;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;


    ControladorBase helper;
    TextView lblMateria, lblTipoEva, lblGT, lblGD, lblGL, lblFecha, lblHora, lblMotivo, lblOtro, lblEstado, lblJustificante;
    Button eliminarBtn, modificarBtn;
    Spinner tipoEval, motivos;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID = 1;
    Calendar c = Calendar.getInstance();
    Button mOptions;
    final int FOTOGRAFIA = 0;
    final int GALLERY = 1;
    Uri file;
    File localFile;
    String ruta;
    ImageView srcImg;
    FirebaseAuth mAuth;
    StorageReference mStorage;
    ProgressDialog mProgress;
    String antigua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferido_consultar);
        helper = new ControladorBase(this);
        editNumEval = (EditText) findViewById(R.id.editNumeval);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editCodMateria = (EditText) findViewById(R.id.editAsignatura);
        editGT = (EditText) findViewById(R.id.editGrupoTeorico);
        editGD = (EditText) findViewById(R.id.editGrupoDiscusion);
        editGL = (EditText) findViewById(R.id.editGrupoLab);
        editFechaEval = (EditText) findViewById(R.id.editFechaRealizada);
        editHoraEval = (EditText) findViewById(R.id.editHoraRealizada);
        editOtroMotivo = (EditText) findViewById(R.id.editMotivo);
        estadoSoli = findViewById(R.id.editEstadoSolicitud);
        tipoEval = (Spinner) findViewById(R.id.spinTipoEval);
        motivos = (Spinner) findViewById(R.id.spinMotivos);
        ciclo = findViewById(R.id.editCodciclo);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("Foto") != null) {
                file = Uri.parse(savedInstanceState.getString("foto"));
            }
        }
        mProgress = new ProgressDialog(this);
        srcImg = findViewById(R.id.justificante);
        srcImg.setVisibility(View.GONE);
        lblJustificante = findViewById(R.id.lblJustificante);
        lblJustificante.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("Justificante");

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editAsignatura);
        Texto2=(TextView) findViewById(R.id.editGrupoTeorico);
        Texto3=(TextView) findViewById(R.id.editGrupoDiscusion);
        Texto4=(TextView) findViewById(R.id.editGrupoLab);
        Texto5=(TextView) findViewById(R.id.editFechaRealizada);
        Texto6=(TextView) findViewById(R.id.editHoraRealizada);
        Texto7=(TextView) findViewById(R.id.editMotivo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        mEntradaVoz=findViewById(R.id.editCarnet);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setVisibility(View.GONE);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });


        lblMateria = (TextView) findViewById(R.id.lblCodMat);
        lblTipoEva = (TextView) findViewById(R.id.lblTipoEval);
        lblGT = (TextView) findViewById(R.id.lblGT);
        lblGD = (TextView) findViewById(R.id.lblGD);
        lblGL = (TextView) findViewById(R.id.lblGL);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblHora = (TextView) findViewById(R.id.lblHora);
        lblMotivo = (TextView) findViewById(R.id.lblMotivo);
        lblOtro = (TextView) findViewById(R.id.lblOtro);
        lblEstado = findViewById(R.id.lblEstadoSoli);
        eliminarBtn = (Button) findViewById(R.id.EliminarBtn);
        modificarBtn = (Button) findViewById(R.id.ModificarBtn);

        eliminarBtn.setVisibility(View.GONE);
        modificarBtn.setVisibility(View.GONE);

        lblMateria.setVisibility(View.VISIBLE);
        lblTipoEva.setVisibility(View.VISIBLE);
        lblGT.setVisibility(View.GONE);
        lblGD.setVisibility(View.GONE);
        lblGL.setVisibility(View.GONE);
        lblFecha.setVisibility(View.GONE);
        lblHora.setVisibility(View.GONE);
        lblMotivo.setVisibility(View.GONE);
        lblOtro.setVisibility(View.GONE);
        lblEstado.setVisibility(View.GONE);
        editCodMateria.setVisibility(View.VISIBLE);
        editCodMateria.setEnabled(true);
        editGT.setVisibility(View.GONE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.GONE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.GONE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.GONE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.GONE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.GONE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        tipoEval.setVisibility(View.VISIBLE);
        tipoEval.setEnabled(true);
        motivos.setVisibility(View.GONE);
        motivos.setEnabled(false);
        estadoSoli.setVisibility(View.GONE);
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

        srcImg.setOnClickListener(showImage);
    }//FINAL DEL ONCREATE!!

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        if (file != null) {
            bundle.putString("Foto", file.toString());
        }
        super.onSaveInstanceState(bundle);
    }

    public void consultarSolicitudDiferido(View view) throws IOException {
        if (!areEmpty()) {
            helper.abrir();
            SolicitudDiferido solicitudDiferido = helper.consultarSolicitudDiferido(editCarnet.getText().toString(), editCodMateria.getText().toString(), ciclo.getText().toString(), tipoEval.getSelectedItem().toString(), editNumEval.getText().toString());
            helper.cerrar();
            if (solicitudDiferido == null)
                Toast.makeText(this, "Solicitud no encontrada", Toast.LENGTH_LONG).show();
            else {
                editCarnet.setEnabled(false);
                editNumEval.setEnabled(false);
                editCodMateria.setEnabled(false);
                tipoEval.setEnabled(false);
                lblGT.setVisibility(View.VISIBLE);
                lblGD.setVisibility(View.VISIBLE);
                lblGL.setVisibility(View.VISIBLE);
                lblFecha.setVisibility(View.VISIBLE);
                lblHora.setVisibility(View.VISIBLE);
                lblMotivo.setVisibility(View.VISIBLE);
                lblEstado.setVisibility(View.VISIBLE);
                estadoSoli.setVisibility(View.VISIBLE);
                editGT.setVisibility(View.VISIBLE);
                editGD.setVisibility(View.VISIBLE);
                editGL.setVisibility(View.VISIBLE);
                editFechaEval.setVisibility(View.VISIBLE);
                editHoraEval.setVisibility(View.VISIBLE);
                motivos.setVisibility(View.VISIBLE);
                editOtroMotivo.setText(solicitudDiferido.getOtroMotivo());
                lblOtro.setVisibility(View.VISIBLE);
                editOtroMotivo.setVisibility(View.VISIBLE);
                editCodMateria.setText(solicitudDiferido.getCodMateria());
                editGT.setText(solicitudDiferido.getGT());
                editGT.setEnabled(true);
                editGD.setText(solicitudDiferido.getGD());
                editGD.setEnabled(true);
                editGL.setText(solicitudDiferido.getGL());
                editGL.setEnabled(true);
                editFechaEval.setText(solicitudDiferido.getFechaEva());
                editFechaEval.setEnabled(true);
                editHoraEval.setEnabled(true);
                editHoraEval.setText(solicitudDiferido.getHoraEva());
                eliminarBtn.setVisibility(View.VISIBLE);
                modificarBtn.setVisibility(View.VISIBLE);
                modificarBtn.setEnabled(true);
                eliminarBtn.setEnabled(true);
                tipoEval.setSelection(tipoEval(solicitudDiferido.getTipoEva()));
                motivos.setSelection(colocarMotivo(solicitudDiferido.getMotivo()));
                motivos.setEnabled(true);
                mBotonhablar.setVisibility(View.VISIBLE);
                editOtroMotivo.setEnabled(true);
                if (solicitudDiferido.getOtroMotivo().isEmpty()) {
                    editOtroMotivo.setVisibility(View.GONE);
                    lblOtro.setVisibility(View.GONE);
                }
                estadoSoli.setText(solicitudDiferido.getEstado());
                srcImg.setVisibility(View.VISIBLE);
                lblJustificante.setVisibility(View.VISIBLE);
                mProgress.setTitle("Cargando justificante");
                mProgress.setMessage("Por favor espere");
                mProgress.show();
                file = Uri.parse(solicitudDiferido.getRutaJustificante());
                ruta = solicitudDiferido.getRutaJustificante();
                localFile = File.createTempFile(ruta, "jpg");
                mStorage.child(ruta).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Se descargo correctamente", Toast.LENGTH_SHORT).show();
                        Bitmap b = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Glide.with(getApplicationContext()).load(b).into(srcImg);
                        mProgress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(), "Error al descargar la foto", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();

    }

    private void iniciarEntradaVoz(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Carné");
        try {
            startActivityForResult(i, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
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

    public void limpiarTexto(View view) {
        editCarnet.setText("");
        editNumEval.setText("");
        editCodMateria.setText("");
        editGT.setText("");
        editGD.setText("");
        editGL.setText("");
        editFechaEval.setText("");
        editHoraEval.setText("");
        editOtroMotivo.setText("");
        tipoEval.setSelection(0);
        motivos.setSelection(0);
        editCarnet.setEnabled(true);
        editNumEval.setEnabled(true);
        eliminarBtn.setVisibility(View.GONE);
        modificarBtn.setVisibility(View.GONE);
        editCodMateria.setEnabled(true);
        tipoEval.setEnabled(true);
        lblGT.setVisibility(View.GONE);
        lblGD.setVisibility(View.GONE);
        lblGL.setVisibility(View.GONE);
        lblFecha.setVisibility(View.GONE);
        lblHora.setVisibility(View.GONE);
        lblMotivo.setVisibility(View.GONE);
        lblOtro.setVisibility(View.GONE);
        editGT.setVisibility(View.GONE);
        editGT.setEnabled(false);
        editGD.setVisibility(View.GONE);
        editGD.setEnabled(false);
        editGL.setVisibility(View.GONE);
        editGL.setEnabled(false);
        editFechaEval.setVisibility(View.GONE);
        editFechaEval.setInputType(InputType.TYPE_NULL);
        editFechaEval.setEnabled(false);
        editHoraEval.setVisibility(View.GONE);
        editHoraEval.setInputType(InputType.TYPE_NULL);
        editHoraEval.setEnabled(false);
        editOtroMotivo.setVisibility(View.GONE);
        editOtroMotivo.setEnabled(false);
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        motivos.setVisibility(View.GONE);
        motivos.setEnabled(false);
        estadoSoli.setText("");
        estadoSoli.setVisibility(View.GONE);
        lblEstado.setVisibility(View.GONE);
        ciclo.setText("");
        srcImg.setVisibility(View.GONE);
        lblJustificante.setVisibility(View.GONE);
        mBotonhablar.setVisibility(View.GONE);
    }

    public void EliminarSolicitud(View view) {
        SolicitudDiferido solicitudDiferido = new SolicitudDiferido();

        solicitudDiferido.setCarnet(String.valueOf(editCarnet.getText()));
        solicitudDiferido.setNumeroEval(Integer.parseInt(String.valueOf(editNumEval.getText())));
        solicitudDiferido.setCodMateria(String.valueOf(editCodMateria.getText()));
        solicitudDiferido.setCiclo(ciclo.getText().toString());
        solicitudDiferido.setTipoEva(String.valueOf(tipoEval.getSelectedItem()));
        mStorage.child(ruta).delete();
        helper.abrir();
        String regAfectados = helper.eliminar(solicitudDiferido);
        helper.cerrar();
        Toast.makeText(this, regAfectados, Toast.LENGTH_SHORT).show();
        limpiarTexto(view);
    }

    public void ModificarSolicitudDiferido(View view) throws IOException {
        if (!areEmpty2()) {
            SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
            solicitudDiferido.setCarnet(String.valueOf(editCarnet.getText()));
            solicitudDiferido.setCodMateria(String.valueOf(editCodMateria.getText()));
            solicitudDiferido.setCiclo(ciclo.getText().toString());
            solicitudDiferido.setNumeroEval(Integer.parseInt(String.valueOf(editNumEval.getText())));
            solicitudDiferido.setTipoEva(String.valueOf(tipoEval.getSelectedItem()));
            solicitudDiferido.setGT(editGT.getText().toString());
            solicitudDiferido.setGD(editGD.getText().toString());
            solicitudDiferido.setGL(editGL.getText().toString());
            solicitudDiferido.setFechaEva(editFechaEval.getText().toString());
            solicitudDiferido.setHoraEva(editHoraEval.getText().toString());
            solicitudDiferido.setMotivo(motivos.getSelectedItem().toString());
            solicitudDiferido.setOtroMotivo(editOtroMotivo.getText().toString());
            solicitudDiferido.setRutaJustificante(file.getLastPathSegment());
            helper.abrir();
            String regAfectados = helper.actualizar(solicitudDiferido);
            helper.cerrar();

            mProgress.setTitle("Subiendo imagen");
            mProgress.setMessage("Espere por favor");
            mProgress.show();
            StorageReference storageReference = mStorage.child(file.getLastPathSegment());
            storageReference.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mProgress.dismiss();
                }
            });
            Toast.makeText(this, regAfectados, Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getApplicationContext(),"Por favor llenar todos los campos", Toast.LENGTH_SHORT).show();
    }

    public int tipoEval(String tipo) {
        switch (tipo) {
            case "EP":
                return 1;
            case "ED":
                return 2;
            case "EL":
                return 3;
            default:
                return 0;
        }
    }

    public int colocarMotivo(String motivo) {
        switch (motivo) {
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
            default:
                return 0;
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    public void takePicture() {
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
                    if (which == 0) {
                        selectImage();
                    } else {
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
                srcImg.setImageURI(file);

            } else {
                Toast.makeText(getApplicationContext(), "Fotografia no tomada", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == GALLERY) {
            if (resultCode == RESULT_OK) {
                mStorage.child(ruta).delete();
                file = data.getData();
                srcImg.setImageURI(file);
            }
        } else if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    mEntradaVoz.setText(result.get(0));
                }
            }
        }
    }

    View.OnClickListener showImage = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.mostar_imagen, null, false);
            builder.setView(dialogView);
            builder.setTitle("Justificante Diferido");
            final ImageView justificante = dialogView.findViewById(R.id.mostarImagen);
            Log.d("RUTAA", ruta);
            Log.d("LOCALFILE", localFile.getAbsolutePath());
            Log.d("FILEEE", file.toString());
            if (localFile.getAbsolutePath().contains(ruta)&& ruta.equals(file.toString())) {
                Glide.with(getApplicationContext()).load(localFile).into(justificante);
            } else {
                justificante.setImageURI(file);
            }
            builder.setNegativeButton("Seleccionar otra imagen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mostrarOpciones.onClick(v);
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
    public boolean areEmpty(){return (editCarnet.getText().toString().isEmpty() ||
            editCodMateria.getText().toString().isEmpty() ||
            tipoEval.getSelectedItem().toString().equals(tipoEval.getItemAtPosition(0).toString())
            || editNumEval.getText().toString().isEmpty()
            ||ciclo.getText().toString().isEmpty());
    }
    public boolean areEmpty2(){
        return (editGT.getText().toString().isEmpty() || editGD.getText().toString().isEmpty() || editFechaEval.getText().toString().isEmpty() || editHoraEval.getText().toString().isEmpty() || file == null || file.getLastPathSegment().isEmpty()|| motivos.getSelectedItem().toString().equals(motivos.getItemAtPosition(0).toString()));
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
                tts.speak(Texto6.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                tts.speak(Texto7.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
