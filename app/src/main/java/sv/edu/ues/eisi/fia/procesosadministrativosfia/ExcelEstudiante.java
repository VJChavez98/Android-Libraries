package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelEstudiante extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_estudiante);

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
        if(ContextCompat.checkSelfPermission(ExcelEstudiante.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    ExcelEstudiante.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );

        }
    }

    public void exportarCSV(){
        ControladorBase.DatabaseHelper helper;
        helper = new ControladorBase.DatabaseHelper(this);

        final Cursor cursor = helper.obtenerEstudiantes();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "Estudiantes.xls";

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
            WritableSheet sheet = workbook.createSheet("ListaEstudiantes", 0);

            // columnas y filas
            sheet.addCell(new Label(0, 0, "Carnet"));
            sheet.addCell(new Label(1, 0, "NombreEstudiante"));
            sheet.addCell(new Label(2, 0, "ApellidoEstudiante"));
            sheet.addCell(new Label(3, 0, "Carrera"));

            if (cursor.moveToFirst()) {
                do {
                    String carnet = cursor.getString(cursor.getColumnIndex("carnet"));
                    String nomestudiante = cursor.getString(cursor.getColumnIndex("nombreEstudiante"));
                    String apellidoestudiante = cursor.getString(cursor.getColumnIndex("apellidoEstudiante"));
                    String carrera = cursor.getString(cursor.getColumnIndex("carrera"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, carnet));
                    sheet.addCell(new Label(1, i, nomestudiante));
                    sheet.addCell(new Label(2, i, apellidoestudiante));
                    sheet.addCell(new Label(3, i, carrera));
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
