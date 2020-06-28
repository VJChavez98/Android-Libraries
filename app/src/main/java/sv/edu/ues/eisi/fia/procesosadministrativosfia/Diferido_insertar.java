package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.Manifest;
import android.app.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.MediaStore;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Diferido_insertar extends AppCompatActivity {

    ControladorBase DBHelper;
    EditText editCarnet, editMateria, editGrupoTeorico, editGrupoDiscusion, editGrupoLab, editFechaEval, editHoraEval, editMotivo, editEva, ciclo;
    EditText editCarnet, editMateria, editGrupoTeorico, editGrupoDiscusion, editGrupoLab, editFechaEval, editHoraEval, editMotivo, editEva;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2, Texto3, Texto4, Texto5, Texto6, Texto7, Texto8;
    Button BtnPlay;
    private int numarch=0;

    Spinner motivos, spinTipo;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni, sHour, nHour, sMinute, nMinute;
    static final int DATE_ID = 0, HOUR_ID = 1;
    String[] tipos = {"Seleccione el tipo de evaluacion", "EP", "ED", "EL"};
    Calendar c = Calendar.getInstance();
    Button mOptions;
    final int FOTOGRAFIA = 0;
    final int GALLERY = 1;
    Uri file;
    Button srcImg, imprimir;
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

        Texto=(TextView) findViewById(R.id.editCarnet);
        Texto1=(TextView) findViewById(R.id.editAsignatura);
        Texto2=(TextView) findViewById(R.id.editGrupoTeorico);
        Texto3=(TextView) findViewById(R.id.editGrupoDiscusion);
        Texto4=(TextView) findViewById(R.id.editGrupoLab);
        Texto5=(TextView) findViewById(R.id.editCodEva);
        Texto6=(TextView) findViewById(R.id.editFechaRealizada);
        Texto7=(TextView) findViewById(R.id.editHoraRealizada);
        Texto8=(TextView) findViewById(R.id.editMotivo);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        srcImg = findViewById(R.id.rutaSolic);
        spinTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos));
        mOptions = findViewById(R.id.option);
        mOptions.setOnClickListener(mostrarOpciones);
        imprimir = findViewById(R.id.crearPdf);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("Foto") != null) {
                file = Uri.parse(savedInstanceState.getString("foto"));
            }
        }
        srcImg.setOnClickListener(showImage);
        ciclo = findViewById(R.id.editCodciclo);


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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA},1000);
        }
        imprimir.setOnClickListener(imprimirPDF);
    }
    private void createPDFfile(String path) {
        if(new File(path).exists())
            new File(path).delete();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.setPageSize(PageSize.LETTER);
            document.addCreationDate();
            document.addAuthor("Autor");
            document.addCreator("Creator");

            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","utf-8",BaseFont.EMBEDDED);

            Font titleFont = new Font(fontName, 36.0f,Font.BOLD,BaseColor.BLACK);
            addNewItem(document, "SOLICITUD DIFERIDO", Element.ALIGN_CENTER, titleFont);
            addLineSpace(document);

            Font carnet = new Font(fontName, fontSize, Font.NORMAL,colorAccent);
            addNewItem(document, "Carnet: "+editCarnet.getText().toString(), Element.ALIGN_LEFT, carnet);

            Font materia = new Font(fontName, fontSize, Font.NORMAL,BaseColor.BLACK);
            addNewItem(document, "Materia: "+editMateria.getText().toString(), Element.ALIGN_LEFT, materia);

            Font tipo = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            if (spinTipo.getSelectedItem().toString().equals(spinTipo.getItemAtPosition(0).toString())){
                addNewItem(document,"Evaluacion: ", Element.ALIGN_LEFT, tipo);
            }else {
                addNewItem(document,"Evaluacion: "+spinTipo.getSelectedItem().toString(), Element.ALIGN_LEFT, tipo);
            }

            Font eval = new Font(fontName, fontSize, Font.NORMAL,BaseColor.BLACK);
            addNewItem(document, "Numero evaluacion: "+editEva.getText().toString(), Element.ALIGN_LEFT, eval);

            Font Ciclo = new Font(fontName, fontSize, Font.NORMAL,BaseColor.BLACK);
            addNewItem(document, "Ciclo lectivo: "+ciclo.getText().toString(), Element.ALIGN_LEFT, Ciclo);

            Font gt = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Grupo teorico: "+editGrupoTeorico.getText().toString(), Element.ALIGN_LEFT,gt);

            Font gd = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Grupo de discusion: "+editGrupoDiscusion.getText().toString(), Element.ALIGN_LEFT,gd);

            Font gl = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Grupo de laboratorio: "+editGrupoLab.getText().toString(), Element.ALIGN_LEFT,gl);

            Font fecha = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document,"Fecha de evaluacion: "+editFechaEval.getText().toString(),Element.ALIGN_LEFT, fecha);

            Font hora = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document,"hora de evaluacion: "+editHoraEval.getText(), Element.ALIGN_LEFT, hora);

            Font motivo = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            if (motivos.getSelectedItem().toString().equals(motivos.getItemAtPosition(0).toString())){
                addNewItem(document, "Motivo de diferido: ",Element.ALIGN_LEFT, motivo);
            }else {
                addNewItem(document, "Motivo de diferido: "+motivos.getSelectedItem().toString(),Element.ALIGN_LEFT, motivo);
            }
            addLineSpace(document);

            document.close();
            Toast.makeText(this, "Success",Toast.LENGTH_SHORT).show();
            printPDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(this, Common.getAppPath(this)+"sol_dif.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addLineSeparator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0,68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }
    private void addLineSpace(Document document) throws DocumentException{
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException{
        Chunk chunk = new Chunk(text,font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }
    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font fontLeft, Font fontRight) throws DocumentException {
        Chunk chunkLeft = new Chunk(textLeft,fontLeft);
        Chunk chunkRight = new Chunk(textRight, fontRight);
        Paragraph paragraph = new Paragraph(chunkLeft);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(chunkRight);
        document.add(paragraph);
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
        if (!areEmpty()) {
            String regInsertados;
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
            SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
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
            solicitudDiferido.setCiclo(ciclo.getText().toString());
            solicitudDiferido.setEstado("Pendiente");
            solicitudDiferido.setRutaJustificante(file.getLastPathSegment());
            DBHelper.abrir();
            regInsertados = DBHelper.insertar(solicitudDiferido);
            DBHelper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            StorageReference storageReference = mStorage.child("Justificante").child(file.getLastPathSegment());
            storageReference.putFile(file);
        }else Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
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
        ciclo.setText("");
        file = null;
        srcImg.setVisibility(View.GONE);
        mOptions.setVisibility(View.VISIBLE);
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
            if(ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
             {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA},1000);
            }
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
    public boolean areEmpty(){
        return (editCarnet.getText().toString().isEmpty() || ciclo.getText().toString().isEmpty()
                || editMateria.getText().toString().isEmpty() || editGrupoTeorico.getText().toString().isEmpty() ||
                editGrupoDiscusion.getText().toString().isEmpty()
                || spinTipo.getSelectedItem().toString().equals(spinTipo.getItemAtPosition(0).toString())
                || editEva.getText().toString().isEmpty() || editFechaEval.getText().toString().isEmpty() ||
                editHoraEval.getText().toString().isEmpty() || motivos.getSelectedItem().toString().equals(motivos.getItemAtPosition(0).toString())
                ||file == null || file.getLastPathSegment() == null);
    }
    public Activity getActivity(){
        return this;
    }
    View.OnClickListener imprimirPDF = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }
            createPDFfile(Common.getAppPath(v.getContext())+"sol_dif.pdf");
        }
    };
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
                tts.speak(Texto8.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
