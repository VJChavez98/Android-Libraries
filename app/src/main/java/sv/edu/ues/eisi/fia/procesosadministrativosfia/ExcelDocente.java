package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelDocente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_docente);

        pedirPermisos();

        Button btnExportar = findViewById(R.id.buttonExp);

        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportarCSV();
            }
        });
    }

    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(ExcelDocente.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    ExcelDocente.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );

        }
    }

    public void exportarCSV(){
        ControladorBase.DatabaseHelper helper;
        helper = new ControladorBase.DatabaseHelper(this);

        final Cursor cursor = helper.obtenerDocentes();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "Docentes.xls";

        File directory = new File(sd.getAbsolutePath());

        //crear directorio si no existe
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            //Path del archivo
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            //Nombre Hoja de Excel. 0 representa primer hoja
            WritableSheet sheet = workbook.createSheet("ListaDocentes", 0);

            // columnas y filas
            sheet.addCell(new Label(0, 0, "CodDocente"));
            sheet.addCell(new Label(1, 0, "NombreDocente"));
            sheet.addCell(new Label(2, 0, "ApellidoDocente"));

            if (cursor.moveToFirst()) {
                do {
                    String coddocente = cursor.getString(cursor.getColumnIndex("coddocente"));
                    String nomdocente = cursor.getString(cursor.getColumnIndex("nombredocente"));
                    String apellidodocente = cursor.getString(cursor.getColumnIndex("apellidodocente"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, coddocente));
                    sheet.addCell(new Label(1, i, nomdocente));
                    sheet.addCell(new Label(2, i, apellidodocente));
                } while (cursor.moveToNext());
            }

            //Cerrando el cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(),"Base de Datos Exportada con Exito.", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
