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
    private static final String[] camposEvaluacion = {"codasignatura", "codciclo", "codtipoeval", "numeroeval", "fechaevaluacion"};
    private static final String[] camposLocal = {"codlocal", "nomlocal", "ubicacionlocal"};
    private static final String[] camposPerInscRev = {"fechadesde", "fechahasta", "fecharevision", "horarevision", "codtiporevision", "coddocente", "codlocal", "codasignatura", "codtipoeval", "codciclo", "numeroeval"};


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
                db.execSQL("CREATE TABLE usuario(username VARCHAR(7) NOT NULL PRIMARY KEY, password VARCHAR(10), nombre_usuario VARCHAR (256));");

                db.execSQL("CREATE TABLE asignatura(codasignatura VARCHAR(6) NOT NULL PRIMARY KEY, nomasignatura VARCHAR(30) NOT NULL, unidadesval VARCHAR(1));");
                db.execSQL("CREATE TABLE docente(coddocente VARCHAR(10) NOT NULL PRIMARY KEY, nombredocente VARCHAR(50) NOT NULL, apellidodocente VARCHAR(50));");
                db.execSQL("CREATE TABLE ciclo(codciclo VARCHAR(5) NOT NULL PRIMARY KEY, fechadesde DATE, fechahasta DATE);");

                db.execSQL("CREATE TABLE tipoevaluacion(codtipoeval VARCHAR(2) NOT NULL PRIMARY KEY, nomtipoeval VARCHAR(20) NOT NULL);");
                db.execSQL("CREATE TABLE tiporevision(codtiporevision VARCHAR(2) PRIMARY KEY NOT NULL, nomtiporevision VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE local(codlocal VARCHAR(10) NOT NULL PRIMARY KEY, nomlocal VARCHAR(30) NOT NULL, ubicacionlocal VARCHAR(30));");
                db.execSQL("CREATE TABLE evaluacion(codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, fechaevaluacion DATE, " +
                        "PRIMARY KEY(codasignatura, codciclo, codtipoeval, numeroeval));");

                db.execSQL("CREATE TABLE periodoinscripcionrevision(fechadesde DATE, fechahasta DATE, fecharevision DATE, horarevision TIME, " +
                        "codtiporevision VARCHAR(2) NOT NULL, coddocente VARCHAR(10) NOT NULL, codlocal VARCHAR(10) NOT NULL, " +
                        "codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, " +
                        "PRIMARY KEY(codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval));");

                /*
                *
                * Sector de creacion de tablas sin llaves foraneas
                *
                 */
                db.execSQL("CREATE TABLE Estudiante(carnet VARCHAR(7) NOT NULL PRIMARY KEY, nombreEstudiante VARCHAR(50), apellidoEstudiante VARCHAR(50), carrera VARCHAR(50))");
                db.execSQL("CREATE TABLE TipoDiferidoRepetido(idTipoDiferidoRepetido CHARACTER(10) NOT NULL PRIMARY KEY, nombreTipo VARCHAR(50));");
                db.execSQL("CREATE TABLE MotivoDiferido(nombreMotivo CHARACTER(13) NOT NULL PRIMARY KEY)");
                //Finaliza sector de tablas sin llaves foraneas

                /*
                 *
                 * Sector de creacion de tablas con llaves foraneas
                 *
                 */


                db.execSQL("CREATE TABLE DetalleDiferidoRepetido(idDetalleDiferidoRepetido CHARACTER(10) NOT NULL PRIMARY KEY,idLocal CHARACTER(10) NOT NULL, idEvaluacion CHARACTER(10) NOT NULL, idDocente CHARACTER(10) NOT NULL, idTipoDiferidoRepetido CHARACTER(10) NOT NULL,idTipo CHARACTER(10) NOT NULL, fechaDesde DATE NOT NULL, fechaHasta DATE NOT NULL, fechaRealizacion DATE NOT NULL, horaRealizacion TIME NOT NULL, FOREIGN KEY (idLocal) REFERENCES Local(idLocal),  FOREIGN KEY (idEvaluacion) REFERENCES Evaluacion(idEvaluacion),  FOREIGN KEY (idDocente) REFERENCES Docente(idDocente),  FOREIGN KEY (idTipoDiferidoRepetido) REFERENCES TipoDiferidoRepetido (idTipoDiferidoRepetido));");
                db.execSQL("CREATE TABLE DetalleEstudianteDiferido(idEstudianteDiferido CHARACTER(10) NOT NULL PRIMARY KEY, carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL,FechaInscripcionDiferido DATE NOT NULL ,FOREIGN KEY (carnet) REFERENCES Estudiante(carnet),FOREIGN KEY (idDetalleDiferidoRepetido) REFERENCES DetalleDiferidoRepedito(idDetalleDiferidoRepetido))");
                db.execSQL("CREATE TABLE DetalleEstudianteRepetido(idDetalleEstudianteRepetido CHARACTER(10) NOT NULL PRIMARY KEY, carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL, fechaInscripcionRepetido DATE NOT NULL, FOREIGN KEY (carnet) REFERENCES Estudiante(carnet), FOREIGN KEY (idDetalleDiferidoRepetido) REFERENCES DetalleDiferidoRepetido(idDetalleDiferidoRepetido))");
                db.execSQL("CREATE TABLE SolicitudDiferido(idSolicitudDiferido NOT NULL, carnet VARCHAR(7) NOT NULL, numeroeval INTEGER NOT NULL, idMotivoDiferido CHARACTER(13) NOT NULL, fechaEvaluacion DATE NOT NULL,  horaEvaluacion TIME NOT NULL, descripcionMotivo VARCHAR(256), idAsignatura CHARACTER(6) NOT NULL, GT NUMERIC(2,0) NOT NULL, GD NUMERIC(2,0) NOT NULL, GL NUMERIC(2,0), tipoEvaluacion CHARACTER(2), PRIMARY KEY(idSolicitudDiferido, carnet, idAsignatura, tipoEvaluacion,numeroeval) )");

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
        return;
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
    public String insertar(SolicitudDiferido solicitudDiferido) {
        String regAfectados = "Registro insertado Nª= ";
        long contador = 0;
        if (verificarIntegridadReferencial(solicitudDiferido, 5)) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("idSolicitudDiferido", solicitudDiferido.getIdSolicitud());
            contentValues.put("carnet", solicitudDiferido.getCarnet());
            contentValues.put("numeroeval", solicitudDiferido.getNumeroEval());
            contentValues.put("tipoEvaluacion", solicitudDiferido.getTipoEva());
            contentValues.put("idMotivoDiferido", solicitudDiferido.getMotivo());
            contentValues.put("fechaEvaluacion", solicitudDiferido.getFechaEva());
            contentValues.put("horaEvaluacion", solicitudDiferido.getHoraEva());
            contentValues.put("descripcionMotivo", solicitudDiferido.getOtroMotivo());
            contentValues.put("GT", solicitudDiferido.getGT());
            contentValues.put("GD", solicitudDiferido.getGD());
            contentValues.put("GL", solicitudDiferido.getGL());
            contentValues.put("idAsignatura", solicitudDiferido.getCodMateria());
            contentValues.put("descripcionMotivo", solicitudDiferido.getOtroMotivo());
            contador=db.insert("SolicitudDiferido",null,contentValues);
        }


        if (contador == -1 || contador==0){
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        }else {
            regAfectados=regAfectados+contador;
        }
        return regAfectados;
    }
    public String actualizar(SolicitudDiferido solicitudDiferido){
        if (verificarIntegridadReferencial(solicitudDiferido,5)){
            String[] id = {solicitudDiferido.getIdSolicitud()};
            ContentValues cv = new ContentValues();
            cv.put("GT",solicitudDiferido.getGT());
            cv.put("GD",solicitudDiferido.getGD());
            cv.put("GL",solicitudDiferido.getGL());
            cv.put("fechaEvaluacion", solicitudDiferido.getFechaEva());
            cv.put("horaEvaluacion", solicitudDiferido.getHoraEva());
            cv.put("idMotivoDiferido", solicitudDiferido.getMotivo());
            cv.put("descripcionMotivo",solicitudDiferido.getOtroMotivo());
            db.update("SolicitudDiferido", cv, "idSolicitudDiferido = ?", id);
            return "Registro Actualizado Correctamente";
        }else return "Registro no existe";


    }
    public String insertar(MotivoDiferido motivoDiferido){
        String regAfectados = "Registro insertado Nº= ";
        long contador = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreMotivo", motivoDiferido.getNombreMotivoDiferido());
        contador=db.insert("MotivoDiferido",null,contentValues);
        if (contador == -1 || contador==0){
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        }else {
            regAfectados=regAfectados+contador;
        }
        return regAfectados;
    }

    public SolicitudDiferido consultarSolicitudDiferido(String carnet,String codMateria,String tipoEval, String numEval){
        String[] id = {carnet+codMateria+tipoEval+numEval};
        Cursor cursor = db.query("SolicitudDiferido",null,"idSolicitudDiferido = ?",id,null,null,null );
        if (cursor.moveToFirst()){
            SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
            solicitudDiferido.setIdSolicitud(cursor.getString(0));
            solicitudDiferido.setCarnet(cursor.getString(1));
            solicitudDiferido.setNumeroEval(cursor.getInt(2));
            solicitudDiferido.setMotivo(cursor.getString(3));
            solicitudDiferido.setFechaEva(cursor.getString(4));
            solicitudDiferido.setHoraEva(cursor.getString(5));
            solicitudDiferido.setOtroMotivo(cursor.getString(6));
            solicitudDiferido.setCodMateria(cursor.getString(7));
            solicitudDiferido.setGT(cursor.getString(8));
            solicitudDiferido.setGD(cursor.getString(9));
            solicitudDiferido.setGL(cursor.getString(10));
            solicitudDiferido.setTipoEva(cursor.getString(11));
            return solicitudDiferido;
        }else return null;
    }
    public String eliminar(SolicitudDiferido solicitudDiferido){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("SolicitudDiferido", "idSolicitudDiferido='"+solicitudDiferido.getIdSolicitud()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }
    public String insertar(Estudiante estudiante){
        String regAfectados = "Registro insertado Nº: ";
        long contador =0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("carnet", estudiante.getCarnet());
        contentValues.put("nombreEstudiante", estudiante.getNombre());
        contentValues.put("apellidoEstudiante", estudiante.getApellido());
        contentValues.put("carrera", estudiante.getCarrera());
        contador = db.insert("Estudiante",null,contentValues);
        if (contador == -1 || contador==0){
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        }else {
            regAfectados=regAfectados+contador;
        }
        return regAfectados;
    }
    public String actualizar(Estudiante estudiante){
        if (verificarIntegridadReferencial(estudiante,6)){
            String[] id = {estudiante.getCarnet()};
            ContentValues cv = new ContentValues();
            cv.put("nombreEstudiante",estudiante.getNombre());
            cv.put("apellidoEstudiante",estudiante.getApellido());
            cv.put("carrera",estudiante.getCarrera());
            db.update("Estudiante", cv, "carnet = ?", id);
            return "Registro Actualizado Correctamente";
        }else return "Registro no existe";
    }
    public Estudiante consultarEstudiante(String carnet){
        String[] id = {carnet};
        Cursor cursor = db.query("Estudiante", null, "carnet = ?", id, null, null, null);
        if(cursor.moveToFirst()) {
            Estudiante estudiante = new Estudiante();
            estudiante.setCarnet(cursor.getString(0));
            estudiante.setNombre(cursor.getString(1));
            estudiante.setApellido(cursor.getString(2));
            estudiante.setCarrera(cursor.getString(3));
            return estudiante;
        }else return null;
    }
    public String eliminar(Estudiante estudiante){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("Estudiante", "carnet='"+estudiante.getCarnet()+"'", null);
        regAfectados+=contador;
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

    public String insertar(Docente docente){
        String regInsertados = "Regitro No. = ";
        long contador = 0;

        ContentValues doc = new ContentValues();
        doc.put("coddocente", docente.getCoddocente());
        doc.put("nombredocente", docente.getNomdocente());
        doc.put("apellidodocente", docente.getApellidodocente());
        contador = db.insert("docente", null, doc);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(TipoRevision tipoRevision){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues tiprev = new ContentValues();
        tiprev.put("codtiporevision", tipoRevision.getCodTipoRev());
        tiprev.put("nomtiporevision", tipoRevision.getNomTipoRev());
        contador = db.insert("tiporevision", null, tiprev);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Isercion";
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
            evaluaciones.put("fechaevaluacion", evaluacion.getFechaEvaluacion());
            evaluaciones.put("numeroeval", evaluacion.getNumeroEvaluacion());
            contador = db.insert("evaluacion", null, evaluaciones);
        }
        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(Local local){
        String regInsertados = "Registro No. = ";
        long contador = 0;

        ContentValues locales = new ContentValues();
        locales.put("codlocal", local.getCodlocal());
        locales.put("nomlocal", local.getNomlocal());
        locales.put("ubicacionlocal", local.getUbicacionlocal());
        contador = db.insert("local", null, locales);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(PeriodoInscripcionRevision perInscRev){
        String regInsertados = "Registro No. = ";
        long contador = 0;

        if(verificarIntegridadReferencial(perInscRev, 3)){
            ContentValues inscripciones = new ContentValues();
            inscripciones.put("fechadesde", perInscRev.getFechaDesde());
            inscripciones.put("fechahasta", perInscRev.getFechaHasta());
            inscripciones.put("fecharevision", perInscRev.getFechaRevision());
            inscripciones.put("horarevision", perInscRev.getHoraRevision());
            inscripciones.put("coddocente", perInscRev.getCodDocente());
            inscripciones.put("codasignatura", perInscRev.getCodAsignatura());
            inscripciones.put("codciclo", perInscRev.getCodCiclo());
            inscripciones.put("codtipoeval", perInscRev.getCodTipoEval());
            inscripciones.put("codtiporevision", perInscRev.getTipoRevision());
            inscripciones.put("codlocal", perInscRev.getCodLocal());
            inscripciones.put("numeroeval", perInscRev.getNumeroEval());
            contador = db.insert("periodoinscripcionrevision", null, inscripciones);
        }
        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }



    public Asignatura consultarNomAsignatura(String codAsignatura){
        String[] id = {codAsignatura};
        String[] camposAsigConsul = {"nomasignatura"};
        Cursor cursor = db.query("asignatura", camposAsigConsul, "codasignatura = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Asignatura asig = new Asignatura();
            asig.setNomasignatura(cursor.getString(0));
            return asig;
        }else{
            return null;
        }
    }

    public Local consultarLocal(String codLocal){
        String[] id = {codLocal};
        Cursor cursor = db.query("local", camposLocal, "codlocal = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Local local = new Local();
            local.setCodlocal(cursor.getString(0));
            local.setNomlocal(cursor.getString(1));
            local.setUbicacionlocal(cursor.getString(2));
            return local;
        }else{
            return null;
        }
    }

    public Evaluacion consultarEvaluacion(String codAsignatura, String codCiclo, String codTipoEval, int numEval){
        String[] id = {codAsignatura, codCiclo, codTipoEval, String.valueOf(numEval)};
        Cursor cursor = db.query("evaluacion", camposEvaluacion, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setCodAsignatura(cursor.getString(0));
            evaluacion.setCodCiclo(cursor.getString(1));
            evaluacion.setCodTipoEval(cursor.getString(2));
            evaluacion.setNumeroEvaluacion(Integer.parseInt(cursor.getString(3)));
            evaluacion.setFechaEvaluacion(cursor.getString(4));
            return evaluacion;
        }else{
            return null;
        }
    }

    public PeriodoInscripcionRevision consultarPeriodoInscripcion(String codTipoRev, String codAsignatura, String codTipoEva, String codCiclo, int numEva){
        String[] id = {codTipoRev, codAsignatura, codTipoEva, codCiclo, String.valueOf(numEva)};
        Cursor cursor = db.query("periodoinscripcionrevision", camposPerInscRev, "codtiporevision = ? AND codasignatura = ? AND codtipoeval = ? AND codciclo = ? AND numeroeval = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            PeriodoInscripcionRevision perInsRev = new PeriodoInscripcionRevision();
            perInsRev.setFechaDesde(cursor.getString(0));
            perInsRev.setFechaHasta(cursor.getString(1));
            perInsRev.setFechaRevision(cursor.getString(2));
            perInsRev.setHoraRevision(cursor.getString(3));
            perInsRev.setTipoRevision(cursor.getString(4));
            perInsRev.setCodDocente(cursor.getString(5));
            perInsRev.setCodLocal(cursor.getString(6));
            perInsRev.setCodAsignatura(cursor.getString(7));
            perInsRev.setCodCiclo(cursor.getString(8));
            perInsRev.setCodTipoEval(cursor.getString(9));
            perInsRev.setNumeroEval(Integer.parseInt(cursor.getString(10)));
            return perInsRev;
        }else{
            return null;
        }
    }



    public String actualizar(Evaluacion evaluacion){
        if(verificarIntegridadReferencial(evaluacion, 2)){
            String[] id = {evaluacion.getCodAsignatura(), evaluacion.getCodCiclo(), evaluacion.getCodTipoEval(), String.valueOf(evaluacion.getNumeroEvaluacion())};
            ContentValues cv = new ContentValues();
            cv.put("fechaevaluacion", evaluacion.getFechaEvaluacion());
            db.update("evaluacion", cv, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?",id);
            return "Registro Actualizado correctamente";
        }else{
            return "Registro no Existe";
        }
    }

    public String actualizar(Local local){
        String[] id = {local.getCodlocal(), local.getNomlocal()};
        ContentValues cv = new ContentValues();
        cv.put("ubicacionlocal", local.getUbicacionlocal());
        db.update("local", cv, "codlocal = ? AND nomlocal = ?", id);
        return "Registro Actualizado correctamente";
    }

    public String actualizar (PeriodoInscripcionRevision perInscRev){
        if(verificarIntegridadReferencial(perInscRev, 4)){
            String[] id = {perInscRev.getTipoRevision(), perInscRev.getCodAsignatura(), perInscRev.getCodTipoEval(), perInscRev.getCodCiclo(), String.valueOf(perInscRev.getNumeroEval())};
            ContentValues cv = new ContentValues();
            cv.put("coddocente", perInscRev.getCodDocente());
            cv.put("codlocal", perInscRev.getCodLocal());
            cv.put("fechadesde", perInscRev.getFechaDesde());
            cv.put("fechahasta", perInscRev.getFechaHasta());
            cv.put("fecharevision", perInscRev.getFechaRevision());
            cv.put("horarevision", perInscRev.getHoraRevision());
            db.update("periodoinscripcionrevision", cv, "codtiporevision = ? AND codasignatura = ? AND codtipoeval = ? AND codciclo = ?  AND numeroeval = ?", id);
            return "Registro Actualizado correctamente";
        }else{
            return "Registro no Existe";
        }
    }



    public String eliminar(Evaluacion evaluacion){
        String regAfectados = "Filas afectadas = ";
        int contador = 0;

        String where = "codasignatura = '"+evaluacion.getCodAsignatura()+"'";
        where = where + "AND codciclo = '"+evaluacion.getCodCiclo()+"'";
        where = where + "AND codtipoeval = '"+evaluacion.getCodTipoEval()+"'";
        where = where + "AND numeroeval = '"+evaluacion.getNumeroEvaluacion()+"'";
        contador += db.delete("evaluacion", where, null);
        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar(Local local){
        String regAfectados = "Filas afectadas = ";
        int contador = 0;

        String where = "codlocal = '"+local.getCodlocal()+"'";
        where = where + "AND nomlocal = '"+local.getNomlocal()+"'";
        contador += db.delete("local", where, null);
        regAfectados += contador;
        return regAfectados;
    }


    public boolean verificarIntegridadReferencial(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1:
            {
                //Verificar que al Insertar Evaluacion exista el TipoEvaluacion, Asignatura y Ciclo
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
                }else return false;
            }
            case 2:
            {
                //verificar que al Modificar Evaluacion exista el TipoEvaluacion, Asignatura, Ciclo y Numero de Evaluacion
                Evaluacion evaluacion = (Evaluacion) dato;
                String[] ids = {evaluacion.getCodAsignatura(), evaluacion.getCodCiclo(), evaluacion.getCodTipoEval(), String.valueOf(evaluacion.getNumeroEvaluacion())};
                abrir();

                Cursor c = db.query("evaluacion", null, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", ids, null, null, null);

                if(c.moveToFirst()){
                    return true;
                }else return false;
            }
            case 3:
            {
                //Verificar que al Insertar PeriodoInscripcionRevision exista Asignatura, Ciclo, TipoEvaluacion, NumeroEvaluacion, Docente, Local, TipoRevision
                PeriodoInscripcionRevision perInscRev = (PeriodoInscripcionRevision) dato;
                String[] id1 = {perInscRev.getCodAsignatura()};
                String[] id2 = {perInscRev.getCodCiclo()};
                String[] id3 = {perInscRev.getCodTipoEval()};
                String[] id4 = {String.valueOf(perInscRev.getNumeroEval())};
                String[] id5 = {perInscRev.getCodDocente()};
                String[] id6 = {perInscRev.getCodLocal()};
                String[] id7 = {perInscRev.getTipoRevision()};
                abrir();

                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                Cursor cursor2 = db.query("ciclo", null, "codciclo = ?", id2, null, null, null);
                Cursor cursor3 = db.query("tipoevaluacion", null, "codtipoeval = ?", id3, null, null, null);
                Cursor cursor4 = db.query("evaluacion", null, "numeroeval = ?", id4, null, null, null);
                Cursor cursor5 = db.query("docente", null, "coddocente = ?", id5, null, null, null);
                Cursor cursor6 = db.query("local", null, "codlocal = ?", id6, null, null, null);
                Cursor cursor7 = db.query("tiporevision", null, "codtiporevision = ?", id7, null, null, null);

                if(cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst() && cursor6.moveToFirst() && cursor7.moveToFirst()){
                    return true;
                }else return false;
            }
            case 4:
            {
                //Verificar que al modificar el PeriodoInscripcionRevision exista Asignatura, Ciclo, TipoEvaluacion, NumeroEvaluacion y TipoRevision
                PeriodoInscripcionRevision perInscRev = (PeriodoInscripcionRevision) dato;
                String[] ids = {perInscRev.getCodAsignatura(), perInscRev.getCodCiclo(), perInscRev.getCodTipoEval(), String.valueOf(perInscRev.getNumeroEval()), perInscRev.getTipoRevision()};
                abrir();


                Cursor c = db.query("periodoinscripcionrevision", null, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ? AND codtiporevision = ?", ids, null, null, null);

                if(c.moveToFirst()){
                    return true;
                }else return false;

            }
            case 5: {
                SolicitudDiferido solicitudDiferido = (SolicitudDiferido) dato;
                String[] id1 = {solicitudDiferido.getCodMateria()};
                //String[] id3 = {solicitudDiferido.getCarnet()};
                String[] id4 = {solicitudDiferido.getMotivo()};
                String[] id5 = {solicitudDiferido.getCodMateria(), solicitudDiferido.getTipoEva()};
                abrir();
                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                //Cursor cursor3 = db.query("estudiante",null,"carnet = ?",id3,null,null,null);
                Cursor cursor4 = db.query("MotivoDiferido", null, "nombreMotivo = ?", id4, null, null, null);
                Cursor cursor5 = db.query("evaluacion", null, "codasignatura = ? AND codtipoeval = ?", id5, null, null, null);

                if (cursor1.moveToFirst() && /*cursor3.moveToFirst() &&*/  cursor4.moveToFirst() && cursor5.moveToFirst()) {
                    return true;
                } else return false;
            }
            case 6:
            {
                Estudiante estudiante = (Estudiante) dato;
                String[] id = {estudiante.getCarnet()};
                abrir();
                Cursor cursor = db.query("Estudiante",null,"carnet = ?",id, null,null,null);
                if(cursor.moveToFirst()){
                    return true;
                }else return false;
            }
            default:
                return false;
        }
    }
    public String LlenarDatos(){
        final String[] usersId = {"CM17048","RM17039","AG17023","MM14030","PR17017"};
        final String[] names = {"Victor","Shaky","Daniel","Cristian","Roberto"};
        final String[] userPass = {"0123456789","0123456789","0123456789","0123456789","0123456789"};
        final String[] motivos = {"Salud", "Trabajo", "Interferencia","Viaje","Duelo","Otro"};

        final String[] TTEcodtipoeval = {"EP", "ED", "EL"};
        final String[] TTEnomtipoeval = {"Examen Parcial", "Examen Discusion", "Examen Laboratorio"};

        final String[] TRcodrevision = {"PR", "SR"};
        final String[] TRnomrevision = {"Primera Revision", "Segunda Revision"};


        final String[] TAcodasignatura = {"MAT115", "FIR115"};
        final String[] TAnomasignatura = {"Matematicas I", "Fisicas I"};
        final String[] TAunidadesval = {"4", "4"};

        final String[] TCcodciclo = {"12020", "22020"};
        final Date[] TCfechadesde = {Date.valueOf("2020-02-20"), Date.valueOf("2020-10-08")};
        final Date[] TCfechahasta = {Date.valueOf("2020-06-20"), Date.valueOf("2020-12-20")};

        final String[] TDcoddocente = {"CV00001", "MN00002"};
        final String[] TDnombredocente = {"Rudy Wilfredo", "Boris Alexander"};
        final String[] TDapellidodocente = {"Chicas Villegas", "Montano Navarrete"};

        abrir();
        db.execSQL("DELETE FROM usuario;");
        db.execSQL("DELETE FROM asignatura");
        db.execSQL("DELETE FROM ciclo");
        db.execSQL("DELETE FROM tipoevaluacion");
        db.execSQL("DELETE FROM tiporevision");
        db.execSQL("DELETE FROM docente");;

        Usuario user = new Usuario();
        for (int i = 0; i<usersId.length; i++){
            user.setUsername(usersId[i]);
            user.setNombreUsuario(names[i]);
            user.setPassword(userPass[i]);
            insertar(user);
        }

        MotivoDiferido motivoDiferido = new MotivoDiferido();
        for (int i =0;i<motivos.length;i++){
            motivoDiferido.setNombreMotivoDiferido(motivos[i]);
            insertar(motivoDiferido);
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

        TipoRevision tiporev = new TipoRevision();
        for(int i=0; i<TRcodrevision.length; i++){
            tiporev.setCodTipoRev(TRcodrevision[i]);
            tiporev.setNomTipoRev(TRnomrevision[i]);
            insertar(tiporev);
        }

        Docente docente = new Docente();
        for(int i=0; i<TDcoddocente.length; i++){
            docente.setCoddocente(TDcoddocente[i]);
            docente.setNomdocente(TDnombredocente[i]);
            docente.setApellidodocente(TDapellidodocente[i]);
            insertar(docente);
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
