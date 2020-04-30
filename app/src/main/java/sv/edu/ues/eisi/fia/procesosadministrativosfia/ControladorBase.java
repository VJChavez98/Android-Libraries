package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class ControladorBase {

    private static final String[] camposUsuario = {"username", "password", "nombre_usuario"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControladorBase(Context ctx) {
        this.context = ctx;
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
                db.execSQL("CREATE TABLE usuario(username VARCHAR(7) NOT NULL PRIMARY KEY, password VARCHAR(10), nombre_usuario VARCHAR (256));");
                db.execSQL("CREATE TABLE asignatura(codasignatura VARCHAR(6) NOT NULL PRIMARY KEY, nomasignatura VARCHAR(30), unidadesval VARCHAR(1));");
                db.execSQL("CREATE TABLE ciclo(codciclo VARCHAR(5) NOT NULL PRIMARY KEY, fechadesde DATE, fechahasta DATE);");
                db.execSQL("CREATE TABLE tipoevaluacion(codtipoeval VARCHAR(2) NOT NULL PRIMARY KEY, nomtipoeval VARCHAR(20));");
                db.execSQL("CREATE TABLE evaluacion(codevaluacion VARCHAR(10) NOT NULL, codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER, fechaevaluacion DATE, PRIMARY KEY(codasignatura, codciclo, codtipoeval, codevaluacion));");
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
        return;
    }

    public void cerrar() {
        DBHelper.close();
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

    public String insertar(Asignatura asignatura){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues asig = new ContentValues();
        asig.put("codasignatura", asignatura.getCodasignatura());
        asig.put("nomasignatura", asignatura.getNomasignatura() );
        asig.put("unidadesval", asignatura.getUnidadesval());
        contador = db.insert("asignatura", null, asig);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(Ciclo ciclo){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues cic = new ContentValues();
        cic.put("codciclo", ciclo.getCodciclo());
        cic.put("fechadesde", String.valueOf(ciclo.getFechadesde()));
        cic.put("fechahasta", String.valueOf(ciclo.getFechahasta()));
        contador = db.insert("ciclo", null, cic);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(TipoEvaluacion tipoEval){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues teval = new ContentValues();
        teval.put("codtipoeval", tipoEval.getCodTipoEval());
        teval.put("nomtipoeval", tipoEval.getNomTipoEval());
        contador = db.insert("tipoevaluacion", null, teval);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(Evaluacion evaluacion){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        if(verificarIntegridadReferencial(evaluacion, 1)){
            ContentValues evaluaciones = new ContentValues();
            evaluaciones.put("codasignatura", evaluacion.getCodAsignatura());
            evaluaciones.put("codciclo", evaluacion.getCodCiclo());
            evaluaciones.put("codtipoeval", evaluacion.getCodTipoEval());
            evaluaciones.put("codevaluacion", evaluacion.getIdEvaluacion());
            evaluaciones.put("fechaevaluacion", String.valueOf(evaluacion.getFechaEvaluacion()));
            evaluaciones.put("numeroevaluacion", evaluacion.getNumeroEvaluacion());
            contador = db.insert("evaluacion", null, evaluaciones);
        }
        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public boolean verificarIntegridadReferencial(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:
            {
                //Verificar que al ingresar Evaluacion exista el TipoEvaluacion, Asignatura y Ciclo
                Evaluacion evaluacion = (Evaluacion) dato;
                String[] id1 = {evaluacion.getCodAsignatura()};
                String[] id2 = {evaluacion.getCodCiclo()};
                String[] id3 = {evaluacion.getCodTipoEval()};
                abrir();

                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                Cursor cursor2 = db.query("ciclo", null, "codciclo = ?", id2, null, null, null);
                Cursor cursor3 = db.query("tipoevaluacion", null, "codtipoeval = ?", id3, null, null, null);

                if(cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst()){
                    //Se encontraron los datos
                    return true;
                }
            }
            default:
                return false;
        }
    }

    public String LlenarDatos(){
        final String[] usersId = {"CM17048","RM17039","AG17023","MM14030","PR17017"};
        final String[] names = {"Victor","Shaky","Daniel","Cristian","Roberto"};
        final String[] userPass = {"0123456789","0123456789","0123456789","0123456789","0123456789"};

        final String[] TAcodasignatura = {"MAT115", "FIR115"};
        final String[] TAnomasignatura = {"Matematicas I", "Fisicas I"};
        final String[] TAunidadesval = {"4", "4"};

        final String[] TCcodciclo = {"12020", "22020"};
        final Date[] TCfechadesde = {Date.valueOf("2020-02-20"), Date.valueOf("2020-10-08")};
        final Date[] TCfechahasta = {Date.valueOf("2020-06-20"), Date.valueOf("2020-12-20")};

        final String[] TTEcodtipoeval = {"EP", "ED", "EL"};
        final String[] TTEnomtipoeval = {"Examen Parcial", "Examen Discusion", "Examen Laboratorio"};

        abrir();

        db.execSQL("DELETE FROM usuario;");
        db.execSQL("DELETE FROM asignatura");
        db.execSQL("DELETE FROM ciclo");
        db.execSQL("DELETE FROM tipoevaluacion");

        Usuario user = new Usuario();
        for (int i = 0; i<usersId.length; i++){
            user.setUsername(usersId[i]);
            user.setNombreUsuario(names[i]);
            user.setPassword(userPass[i]);
            insertar(user);
        }

        Asignatura asignatura = new Asignatura();
        for (int i=0; i<TAcodasignatura.length; i++){
            asignatura.setCodasignatura(TAcodasignatura[i]);
            asignatura.setNomasignatura(TAnomasignatura[i]);
            asignatura.setUnidadesval(TAunidadesval[i]);
            insertar(asignatura);
        }

        Ciclo ciclo = new Ciclo();
        for( int i=0; i<TCcodciclo.length; i++){
            ciclo.setCodciclo(TCcodciclo[i]);
            ciclo.setFechadesde(TCfechadesde[i]);
            ciclo.setFechahasta(TCfechahasta[i]);
            insertar(ciclo);
        }

        TipoEvaluacion tipoeval = new TipoEvaluacion();
        for(int i=0; i<TTEcodtipoeval.length; i++){
            tipoeval.setCodTipoEval(TTEcodtipoeval[i]);
            tipoeval.setNomTipoEval(TTEnomtipoeval[i]);
            insertar(tipoeval);
        }

        cerrar();
        return "Guardado correctamente";
    }
}
