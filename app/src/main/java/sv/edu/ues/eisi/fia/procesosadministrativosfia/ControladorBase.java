package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ControladorBase {

    private static final String[] camposUsuario = {"username", "password", "nombre_usuario"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControladorBase(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String NOMBRE_BASE = "ProcesosAdmin.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(@Nullable Context context) {
            super(context, NOMBRE_BASE, null, VERSION);

        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                /*
                *
                * Sector de creacion de tablas sin llaves foraneas
                *
                 */
                db.execSQL("CREATE TABLE Usuario(username VARCHAR(7) NOT NULL PRIMARY KEY, password VARCHAR(10), nombre_usuario VARCHAR (256));");
                db.execSQL("CREATE TABLE Estudiante(carnet VARCHAR(7) NOT NULL PRIMARY KEY, nombreEstudiante VARCHAR(50), apellidoEstudiante VARCHAR(50), carrera VARCHAR(50))");
                db.execSQL("CREATE TABLE Ciclo(idCiclo CHARACTER(5) NOT NULL PRIMARY KEY, fechaDesde DATE NOT NULL, fechaHasta DATE NOT NULL )");
                db.execSQL("CREATE TABLE Docente(idDocente CHARACTER(10) NOT NULL PRIMARY KEY, nombreDocente VARCHAR(50) NOT NULL, apellidoDocente VARCHAR(50) NOT NULL)");
                db.execSQL("CREATE TABLE TipoEval(idTipoEval CHARACTER(2) NOT NULL PRIMARY KEY, nombreTipoEval VARCHAR(20) NOT NULL)");
                db.execSQL("CREATE TABLE Local(idLocal CHARACTER(10) NOT NULL PRIMARY KEY, nombreLocal VARCHAR(50) NOT NULL, ubicacion VARCHAR(50))");
                db.execSQL("CREATE TABLE TipoDiferidoRepetido(idTipoDiferidoRepetido CHARACTER(10) NOT NULL PRIMARY KEY, nombreTipo VARCHAR(50));");
                db.execSQL("CREATE TABLE Asignatura(idAsignatura CHARACTER(6) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(50) NOT NULL)");
                //Finaliza sector de tablas sin llaves foraneas

                /*
                 *
                 * Sector de creacion de tablas con llaves foraneas
                 *
                 */


                db.execSQL("CREATE TABLE Evaluacion(idEvaluacion CHARACTER(10) NOT NULL PRIMARY KEY, idTipoEval CHARACTER(2) NOT NULL, idCiclo CHARACTER(5) NOT NULL, idAsignatura CHARACTER(6) NOT NULL, numeroEvaluacion NUMBER(12,2) NOT NULL, fechaEvaluacion DATE NOT NULL, FOREIGN KEY (idTipoEval) REFERENCES TipoEval(idTipoEval), FOREIGN KEY (idCiclo) REFERENCES Ciclo(idCiclo), FOREIGN KEY (idAsignatura) REFERENCES Asignatura(idAsignatura))");
                db.execSQL("CREATE TABLE DetalleDiferidoRepetido(idDetalleDiferidoRepetido CHARACTER(10) NOT NULL PRIMARY KEY,idLocal CHARACTER(10) NOT NULL, idEvaluacion CHARACTER(10) NOT NULL, idDocente CHARACTER(10) NOT NULL, idTipoDiferidoRepetido CHARACTER(10) NOT NULL,idTipo CHARACTER(10) NOT NULL, fechaDesde DATE NOT NULL, fechaHasta DATE NOT NULL, fechaRealizacion DATE NOT NULL, horaRealizacion TIME NOT NULL, FOREIGN KEY (idLocal) REFERENCES Local(idLocal),  FOREIGN KEY (idEvaluacion) REFERENCES Evaluacion(idEvaluacion),  FOREIGN KEY (idDocente) REFERENCES Docente(idDocente),  FOREIGN KEY (idTipoDiferidoRepetido) REFERENCES TipoDiferidoRepetido (idTipoDiferidoRepetido));");
                db.execSQL("CREATE TABLE DetalleEstudianteDiferido(idEstudianteDiferido CHARACTER(10) NOT NULL PRIMARY KEY, carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL,FechaInscripcionDiferido DATE NOT NULL ,FOREIGN KEY (carnet) REFERENCES Estudiante(carnet),FOREIGN KEY (idDetalleDiferidoRepetido) REFERENCES DetalleDiferidoRepedito(idDetalleDiferidoRepetido))");
                db.execSQL("CREATE TABLE DetalleEstudianteRepetido(idDetalleEstudianteRepetido CHARACTER(10) NOT NULL PRIMARY KEY, carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL, fechaInscripcionRepetido DATE NOT NULL, FOREIGN KEY (carnet) REFERENCES Estudiante(carnet), FOREIGN KEY (idDetalleDiferidoRepetido) REFERENCES DetalleDiferidoRepetido(idDetalleDiferidoRepetido))");


                //Finaliza sector de tablas con llaves foraneas
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //UPDATE DATABASE COMMANDS
        }
    }

    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }

    public void cerrar() {
        db.close();
    }

    public boolean consultarUsuario(String username, String password) {
        String[] id = {username};
        Cursor cursor = db.rawQuery("select * from usuario where username ='" + username + "' and password ='" + password+"';", null);
        if (cursor.moveToFirst() == true) {
            String user = cursor.getString(0);
            String pass = cursor.getString(1);
            cerrar();
            if (user.equals(username) && pass.equals(password)) {
                return true;
            }
            else return false;
        }else return false;
    }

    public String insertar(Usuario user){
        String regAfectados = "Registro insertado Nª= ";
        long contador = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("nombre_usuario", user.getNombreUsuario());
        contador=db.insert("usuario",null,contentValues);
        if (contador == -1 || contador==0){
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        }else {
            regAfectados=regAfectados+contador;
        }
        return regAfectados;
    }
    public String insertar(Estudiante estudiante){
        return "";
    }
    public String LlenarDatos(){
        final String[] usersId = {"CM17048","RM17039","AG17023","MM14030","PR17017"};
        final String[] names = {"Victor","Shaky","Daniel","Cristian","Roberto"};
        final String[] userPass = {"0123456789","0123456789","0123456789","0123456789","0123456789"};

        abrir();
        db.execSQL("DELETE FROM usuario;");
        Usuario user = new Usuario();
        for (int i = 0; i<5; i++){
            user.setUsername(usersId[i]);
            user.setNombreUsuario(names[i]);
            user.setPassword(userPass[i]);
            insertar(user);
        }
        cerrar();
        return "Guardado correctamente";
    }

    /*public boolean verificarIntegridadReferencial(Object object, int relacion) throws SQLException{

        switch (relacion){
            case 1:
                Usuario user = (Usuario)object;
                String [] id = {user.getUsername()};
                Cursor cursor = db.query("usuario",null,"username = ?", id,null,null,null);
                if(cursor.moveToFirst()){
                    return true;
                }
                else return false;


            default: return false;
        }

    }*/

}
