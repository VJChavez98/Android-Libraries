package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaConsultarE extends Activity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_consultar_e);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapE);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE
            );

        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Marcadores
        LatLng edifE = new LatLng(13.721284, -89.200247);
        mMap.addMarker(new MarkerOptions()
                .position(edifE)
                .title("Escuela de Sistemas"));

        //Para ajustar la camara
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(edifE)
                .zoom(10)
                .build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Para visualizar Mapa Hibrido
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }
}

