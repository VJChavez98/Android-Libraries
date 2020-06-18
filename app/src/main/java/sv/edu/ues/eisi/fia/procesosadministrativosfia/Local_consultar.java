package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Local_consultar extends Activity{
    EditText editCodlocal, editNomlocal, editUbicacionlocal;
    ControladorBase helper;
    boolean resultado;

    Button obtenerDir;
    EditText edtLatitud, edtLongitud, edtAltitud;
    TextView edtDireccion;
    LocationManager locationManager;

    TextToSpeech tts;
    TextView Texto, Texto1, Texto2;
    Button BtnPlay;
    private int numarch=0;

    private static final int REQ_CODE_SPEECH_INPUT=100;
    private TextView mEntradaVoz;
    private Button mBotonhablar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_consultar);
        helper = new ControladorBase(this);

        editCodlocal = (EditText) findViewById(R.id.editCodlocal);
        editNomlocal = (EditText) findViewById(R.id.editNomlocal);
        editUbicacionlocal = (EditText) findViewById(R.id.editUbicacionlocal);

        Texto=(TextView) findViewById(R.id.editCodlocal);
        Texto1=(TextView) findViewById(R.id.editNomlocal);
        Texto2=(TextView) findViewById(R.id.editUbicacionlocal);
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);

        mEntradaVoz=findViewById(R.id.editCodlocal);
        mBotonhablar=findViewById(R.id.bvoice);
        mBotonhablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });

        //obtenerDir = (Button) findViewById(R.id.btnObtenerDatosPos);
        edtLatitud = (EditText) findViewById(R.id.edtLatitud);
        edtLongitud = (EditText) findViewById(R.id.edtLongitud);
        edtAltitud= (EditText) findViewById(R.id.edtAltitud);
        edtDireccion = (TextView) findViewById(R.id.edtDireccion);
        //obtenerDir.setOnClickListener(onClickDireccion);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    private void iniciarEntradaVoz(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el Código del Local");
        try {
            startActivityForResult(i, REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEntradaVoz.setText(result.get(0));
                }
                break;
            }
        }
    }

    public void consultarLocal(View v){
        helper.abrir();
        Local local = helper.consultarLocal(editCodlocal.getText().toString());
        helper.cerrar();
        if(local == null){
            Toast.makeText(this, "Local No Registrado.", Toast.LENGTH_SHORT).show();
            this.resultado = false;
        }else{
            findViewById(R.id.detalle).setVisibility(View.VISIBLE);
            editNomlocal.setText(local.getNomlocal());
            editUbicacionlocal.setText(local.getUbicacionlocal());
            this.resultado = true;
        }
    }

    public void consultarUbicacion(View v){
        if(this.resultado == true){
            String LcodLocal = editCodlocal.getText().toString();
            char[] letLocal = LcodLocal.toCharArray();

            if((letLocal[0] == 'B' || letLocal[0] == 'b') && (letLocal[1] == '1' || letLocal[1] == '2' || letLocal[1] == '3' || letLocal[1] == '4')){
                Intent intent = new Intent(this, MapaConsultarB.class);
                //intent.putExtra("codlocal", codlocal2);
                startActivity(intent);
            }else if((letLocal[0] == 'C' || letLocal[0] == 'c') && (letLocal[1] == '1' || letLocal[1] == '2' || letLocal[1] == '3' || letLocal[1] == '4')){
                Intent intent = new Intent(this, MapaConsultarC.class);
                startActivity(intent);
            }else if((letLocal[0] == 'D' || letLocal[0] == 'd') && (letLocal[1] == '1' || letLocal[1] == '2' || letLocal[1] == '3' || letLocal[1] == '4')){
                Intent intent = new Intent(this, MapaConsultarD.class);
                startActivity(intent);
            }else if((letLocal[0] == 'F' || letLocal[0] == 'f') && (letLocal[1] == '1') && (letLocal[2] == '3') && (letLocal[3] == '1') && (letLocal[4] == '2')){
                Intent intent = new Intent(this, MapaConsultarF.class);
                startActivity(intent);
            }else if((letLocal[0] == 'B' || letLocal[0] == 'b') && (letLocal[1] == 'I' || letLocal[1] == 'i') && (letLocal[2] == 'B' || letLocal[2] == 'b')){
                Intent intent = new Intent(this, MapaConsultarBIB.class);
                startActivity(intent);
            }else if((letLocal[0] == 'C' || letLocal[0] == 'c') && (letLocal[1] == 'U' || letLocal[1] == 'u')){
                Intent intent = new Intent(this, MapaConsultarE.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "No hay ubicación para este Local.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Local No Registrado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultarPosicion(View v){
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    0
            );
        }

        Geocoder g = new Geocoder(getApplicationContext());
        List<Address> ad = null;
        try{
            ad = g.getFromLocation(Double.valueOf(edtLatitud.getText().toString()), Double.valueOf(edtLongitud.getText().toString()), 1);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(ad != null && ad.isEmpty() == false){
            edtDireccion.setText(ad.get(0).getThoroughfare() + ", " + ad.get(0).getSubAdminArea() + ", " + ad.get(0).getCountryName());
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            edtLatitud.setText(String.valueOf(location.getLatitude()));
            edtLongitud.setText(String.valueOf(location.getLongitude()));
            edtAltitud.setText(String.valueOf(location.getAltitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void limpiarTexto(View v){
        editCodlocal.setText("");
        editNomlocal.setText("");
        editUbicacionlocal.setText("");
        edtLongitud.setText("");
        edtLatitud.setText("");
        edtAltitud.setText("");
        findViewById(R.id.detalle).setVisibility(View.GONE);
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

            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }
}
