
package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.codekidlabs.storagechooser.StorageChooser;

import java.io.File;
import java.util.List;

public class BluetoothActivity extends AppCompatActivity {

    private static final int DISCOVER_DURATION=300;
    private static final int REQUEST_BLU=1;
    public static final int FILEPICKER_PERMISSIONS = 1;
    Button filepickerBtn;
    TextView tvRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        filepickerBtn = findViewById(R.id.button_filepicker);
        tvRuta = findViewById(R.id.tvRuta);

        filepickerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if(hasPermissions(BluetoothActivity.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(BluetoothActivity.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
                }
            }
        });
    }

    /**
     * Método que muestra el selector de archivos de StorageChooser.
     */
    public void ShowFilepicker(){
        // Inicializar diálogo
        final StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(BluetoothActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. ¡Recupere la ruta seleccionada por el usuario y muéstrela en un toast!
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                tvRuta.setText(path);
                //Toast.makeText(MainActivity.this, "The selected path is : " + path, Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Mostrar selector de archivos!
        chooser.show();
    }

    /**
     * Método auxiliar que verifica si se otorgan o no los permisos de un array determinado.
     *
     * @param context
     * @param permissions
     * @return {Boolean}
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Devolución de llamada que maneja el estado de la solicitud de permisos.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FILEPICKER_PERMISSIONS: {
                // Si se cancela la solicitud, los arrays de resultados están vacíos.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            BluetoothActivity.this,
                            "¡Permiso concedido! Haga clic en seleccionar archivo una vez más.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            BluetoothActivity.this,
                            "Permiso denegado para leer su almacenamiento externo",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }


    //Bluetooth
    public  void  sendViaBluetooth(View v){

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null) {

            Toast.makeText(this, "Tu Dispositivo no Soporta Bluetooth", Toast.LENGTH_LONG).show();
        }else {
            enableBluetooth();
        }

    }

    public void enableBluetooth(){
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        tvRuta = findViewById(R.id.tvRuta);

        String texto;
        if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("application/vnd.ms-excel");
//            File f = new File(Environment.getExternalStorageDirectory(), "Documents/Notas MOP115.xlsx");
//            File f = new File("/storage/emulated/0/Documents/Notas MOP115.xlsx");
            texto= (String) tvRuta.getText();
            File f = new File(texto);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

            PackageManager pm=getPackageManager();
            List<ResolveInfo> appList = pm.queryIntentActivities(intent, 0);

            if(appList.size()>0){
                String packpageName=null;
                String className=null;
                boolean found= false;

                for(ResolveInfo  info : appList){
                    packpageName = info.activityInfo.packageName;
                    if(packpageName.equals("com.android.bluetooth")){
                        className= info.activityInfo.name;
                        found = true;
                        break;
                    }
                }
                if(!found){
                    Toast.makeText(this, "No se ha activado Bluetooth", Toast.LENGTH_LONG).show();
                }else {
                    intent.setClassName(packpageName, className);
                    startActivity(intent);
                }
            }
        }else {
            Toast.makeText(this, "Bluetooth ha sido Cancelado", Toast.LENGTH_LONG).show();
        }
    }
}

