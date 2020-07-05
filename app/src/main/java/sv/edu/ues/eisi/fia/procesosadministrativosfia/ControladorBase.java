package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;

public class ControladorBase {



    private static final String[] camposUsuario = {"username", "password", "nombre_usuario"};
    private static final String[] camposEvaluacion = {"codasignatura", "codciclo", "codtipoeval", "numeroeval", "fechaevaluacion"};
    private static final String[] camposLocal = {"codlocal", "nomlocal", "ubicacionlocal"};
    private static final String[] camposPerInscRev = {"fechadesde", "fechahasta", "fecharevision", "horarevision", "codtiporevision", "coddocente", "codlocal", "codasignatura", "codtipoeval", "codciclo", "numeroeval"};
    private static final String[] camposPrimerRevision = {"estadoprimerrevision", "notadespuesprimerarevision", "asistio", "observacionesprimerarev", "coddocente", "codtipogrupo", "carnet", "codasignatura", "codciclo", "codtipoeval", "numeroeval", "codtiporevision", "codmotivocambionota"};
    private static final String[] camposSegundaRevision = {"codmotivocambionota", "codtipogrupo", "coddocente", "notafinalsegundarev", "observacionessegundarev", "carnet", "codasignatura", "codciclo", "codtipoeval", "numeroeval", "codtiporevision"};
    private static final String[]camposCiclo = new String[]
            {"codciclo", "fechadesde", "fechahasta"};
    private static final String[]camposCargaAcademica = new String[]
            {"idcargaacademica", "codciclo", "coddocente", "codasignatura", "idtipodocenteciclo"};
    private static final String[]camposDocente = new String[]
            {"coddocente", "nombredocente", "apellidodocente"};
    private static final String[]camposAsignatura = new String[]
            {"codasignatura", "nomasignatura", "unidadesval"};
    private static final String[]camposTipoDocenteCiclo = new String[]
            {"idtipodocenteciclo", "nomtipodocenteciclo"};
    private static final String[] campossolImpresion = new String[]
            {"idsolicitudimpresion", "iddocente", "iddocentedirector", "cantidadexamenes", "hojasempaque", "estadoaprobacion"};
    private static final String[] camposdocDirector = new String[]
            {"iddocentedirector", "idescuela", "nombredirector", "apellidodirector", "correodirector", "telefono"};
    private static final String[] camposencarImpresiones = new String[]
            {"idencargado", "nombreencargado", "apellidoencargado"};
    private static final String[] camposestadoImpresion = new String[]
            {"idestadoimpresion", "idsolicitudimpresion", "idencargado", "idmotivoimpresion", "realizado", "observaciones"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControladorBase(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String NOMBRE_BASE = "ProcesosAdmin.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(@Nullable Context context) {
            super(context, NOMBRE_BASE, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL("CREATE TABLE calendario(id INTEGER PRIMARY KEY AUTOINCREMENT,objFecha VARCHAR NOT NULL, evento VARCHAR NOT NULL);");


                db.execSQL("CREATE TABLE asignatura(codasignatura VARCHAR(6) NOT NULL PRIMARY KEY, nomasignatura VARCHAR(30) NOT NULL, unidadesval VARCHAR(1));");
                db.execSQL("CREATE TABLE docente(coddocente VARCHAR(10) NOT NULL PRIMARY KEY, nombredocente VARCHAR(50) NOT NULL, apellidodocente VARCHAR(50));");
                db.execSQL("CREATE TABLE ciclo(codciclo VARCHAR(5) NOT NULL PRIMARY KEY, fechadesde DATE, fechahasta DATE);");
                db.execSQL("CREATE TABLE tipodocenteciclo(idtipodocenteciclo VARCHAR(5) NOT NULL PRIMARY KEY, nomtipodocenteciclo VARCHAR(50));");
                db.execSQL("CREATE TABLE cargaacademica(idcargaacademica VARCHAR(5) NOT NULL PRIMARY KEY, codciclo VARCHAR(5) NOT NULL, coddocente VARCHAR(6) NOT NULL, codasignatura VARCHAR(6) NOT NULL, idtipodocenteciclo VARCHAR(5) NOT NULL);");
                db.execSQL("CREATE TABLE tipoevaluacion(codtipoeval VARCHAR(2) NOT NULL PRIMARY KEY, nomtipoeval VARCHAR(20) NOT NULL);");
                db.execSQL("CREATE TABLE tiporevision(codtiporevision VARCHAR(2) PRIMARY KEY NOT NULL, nomtiporevision VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE local(codlocal VARCHAR(10) NOT NULL PRIMARY KEY, nomlocal VARCHAR(30) NOT NULL, ubicacionlocal VARCHAR(30));");
                db.execSQL("CREATE TABLE evaluacion(codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, fechaevaluacion DATE, " +
                        "PRIMARY KEY(codasignatura, codciclo, codtipoeval, numeroeval));");

                db.execSQL("CREATE TABLE solicitudimpresion(idsolicitudimpresion VARCHAR(10) NOT NULL, coddocente VARCHAR(10) NOT NULL, iddocentedirector VARCHAR(10) NOT NULL, cantidadexamenes INTEGER(4), hojasempaque BOOLEAN, estadoaprobacion BOOLEAN, PRIMARY KEY(idsolicitudimpresion, coddocente, iddocentedirector));");
                db.execSQL("CREATE TABLE docentedirector(iddocentedirector VARCHAR(10), idescuela VARCHAR(10) NOT NULL, nombredirector VARCHAR(50) NOT NULL, apellidodirector VARCHAR(50) NOT NULL, correodirector VARCHAR(50) NOT NULL, telefono INTEGER(8) NOT NULL, PRIMARY KEY(iddocentedirector, idescuela));");
                db.execSQL("CREATE TABLE estadoimpresion(idestadoimpresion VARCHAR(10) NOT NULL, idsolicitudimpresion VARCHAR(10) NOT NULL, idencargado VARCHAR(10) NOT NULL, idmotivoimpresion VARCHAR(10) NOT NULL, realizado BOOLEAN, observaciones VARCHAR(50), PRIMARY KEY(idestadoimpresion, idsolicitudimpresion, idencargado, idmotivoimpresion));");
                db.execSQL("CREATE TABLE encargadodeimpresiones(idencargado VARCHAR(10) NOT NULL PRIMARY KEY, nombreencargado VARCHAR(50) NOT NULL, apellidoencargado VARCHAR(10) NOT NULL);");
                db.execSQL("CREATE TABLE escuela(idescuela VARCHAR(10) NOT NULL PRIMARY KEY, nombreescuela VARCHAR(50) NOT NULL, facultad VARCHAR(50) NOT NULL );");
                db.execSQL("CREATE TABLE motivoimpresion(idmotivoimpresion VARCHAR(10) NOT NULL PRIMARY KEY, motivo VARCHAR(200) );");

                db.execSQL("CREATE TABLE periodoinscripcionrevision(fechadesde DATE, fechahasta DATE, fecharevision DATE, horarevision TIME, " +
                        "codtiporevision VARCHAR(2) NOT NULL, coddocente VARCHAR(10) NOT NULL, codlocal VARCHAR(10) NOT NULL, " +
                        "codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, " +
                        "PRIMARY KEY(codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval));");

                db.execSQL("CREATE TABLE primerrevision(estadoprimerrevision VARCHAR(15) NOT NULL, notadespuesprimerarevision REAL NOT NULL, asistio VARCHAR(2) NOT NULL, observacionesprimerarev VARCHAR(200), coddocente VARCHAR(10) NOT NULL,  codtipogrupo VARCHAR(2) NOT NULL," +
                        "carnet VARCHAR(7) NOT NULL, codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, codtiporevision VARCHAR(2) NOT NULL, codmotivocambionota VARCHAR(10) NOT NULL," +
                        "PRIMARY KEY(coddocente, carnet, codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval, codtipogrupo));");

                db.execSQL("CREATE TABLE motivocambionota(codmotivocambionota VARCHAR(10) NOT NULL PRIMARY KEY, motivo VARCHAR(200) NOT NULL);");
                db.execSQL("CREATE TABLE solicitudrevision(fechasolicitudrevision DATE, notaantesrevision REAL NOT NULL, codtipogrupo VARCHAR(2) NOT NULL, numerogrupo INTEGER NOT NULL, motivorevision VARCHAR(200)," +
                        "carnet VARCHAR(7) NOT NULL, codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, codtiporevision VARCHAR(2) NOT NULL," +
                        "PRIMARY KEY(carnet, codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval, codtipogrupo));");

                db.execSQL("CREATE TABLE segundarevision(codmotivocambionota VARCHAR(10) NOT NULL, codtipogrupo VARCHAR(2) NOT NULL, coddocente VARCHAR(10) NOT NULL, notafinalsegundarev REAL NOT NULL, observacionessegundarev VARCHAR(200), " +
                        "carnet VARCHAR(7) NOT NULL, codasignatura VARCHAR(6) NOT NULL, codciclo VARCHAR(5) NOT NULL, codtipoeval VARCHAR(2) NOT NULL, numeroeval INTEGER NOT NULL, codtiporevision VARCHAR(2) NOT NULL," +
                        "PRIMARY KEY(codtiporevision, codasignatura, codtipoeval, numeroeval, codciclo, carnet));");

                db.execSQL("CREATE TABLE tipogrupo(codtipogrupo VARCHAR(2) NOT NULL PRIMARY KEY, nombretipogrupo VARCHAR(50) NOT NULL);");

                /*
                 *
                 * Sector de creacion de tablas sin llaves foraneas
                 *
                 */
                db.execSQL("CREATE TABLE Estudiante(carnet VARCHAR(7) NOT NULL PRIMARY KEY, nombreEstudiante VARCHAR(50), apellidoEstudiante VARCHAR(50), carrera VARCHAR(50))");
                db.execSQL("CREATE TABLE TipoDiferidoRepetido(idTipoDiferidoRepetido CHARACTER(10) NOT NULL PRIMARY KEY, nombreTipo VARCHAR(50));");
                db.execSQL("CREATE TABLE MotivoDiferido(nombreMotivo CHARACTER(13) NOT NULL PRIMARY KEY)");
                db.execSQL("CREATE TABLE usuario(username VARCHAR(7) NOT NULL PRIMARY KEY, password VARCHAR(10), nombre_usuario VARCHAR (256));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion CHARACTER(3) NOT NULL PRIMARY KEY, descOpcion VARCHAR(30), numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario(idOpcion CHARACTER(3) NOT NULL, username CHARACTER(7) NOT NULL, PRIMARY KEY(idOpcion, username));");
                //Finaliza sector de tablas sin llaves foraneas

                /*
                 *
                 * Sector de creacion de tablas con llaves foraneas
                 *
                 */


                db.execSQL("CREATE TABLE DetalleDiferidoRepetido(idDetalleDiferidoRepetido CHARACTER(25) NOT NULL PRIMARY KEY,idLocal CHARACTER(10) NOT NULL, numEval INTEGER NOT NULL,tipoEval CHARACTER(2) NOT NULL,codAsignatura CHARACTER(6), idDocente CHARACTER(10) NOT NULL, idTipoDiferidoRepetido CHARACTER(10) NOT NULL, fechaDesde DATE NOT NULL, fechaHasta DATE NOT NULL, fechaRealizacion DATE NOT NULL, horaRealizacion TIME NOT NULL);");
                db.execSQL("CREATE TABLE DetalleEstudianteDiferido(carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL,FechaInscripcionDiferido DATE NOT NULL ,PRIMARY KEY(carnet,idDetalleDiferidoRepetido))");
                db.execSQL("CREATE TABLE DetalleEstudianteRepetido(carnet CHARACTER(7) NOT NULL, idDetalleDiferidoRepetido CHARACTER(10) NOT NULL, fechaInscripcionRepetido DATE NOT NULL, PRIMARY KEY(carnet, idDetalleDiferidoRepetido))");
                db.execSQL("CREATE TABLE SolicitudDiferido( carnet VARCHAR(7) NOT NULL, numeroeval INTEGER NOT NULL, idMotivoDiferido CHARACTER(13) NOT NULL, fechaEvaluacion DATE NOT NULL,  horaEvaluacion TIME NOT NULL, descripcionMotivo VARCHAR(256), idAsignatura CHARACTER(6) NOT NULL, GT NUMERIC(2,0) NOT NULL, GD NUMERIC(2,0) NOT NULL, GL NUMERIC(2,0), tipoEvaluacion CHARACTER(2), estadoSolicitud CHARACTER(10) NOT NULL,ciclo varchar(5) not null, rutajustificante TEXT,PRIMARY KEY(carnet, idAsignatura,ciclo, tipoEvaluacion,numeroeval) )");
                //Finaliza sector de tablas con llaves foraneas
                /*
                *
                *
                * TRIGGERS
                *
                *
                */
                db.execSQL("CREATE TRIGGER ActualizarSolDif AFTER UPDATE  ON SolicitudDiferido FOR EACH ROW WHEN NEW.estadoSolicitud <> 'Aprobada' AND OLD.estadoSolicitud = 'Aprobada' BEGIN DELETE FROM DetalleEstudianteDiferido WHERE DetalleEstudianteDiferido.carnet = OLD.carnet AND DetalleEstudianteDiferido.idDetalleDiferidoRepetido = (OLD.idAsignatura||OLD.tipoEvaluacion||OLD.numeroeval||'Diferido'); END");
                db.execSQL("CREATE TRIGGER AfterDeleteSolDif AFTER DELETE ON SolicitudDiferido FOR EACH ROW BEGIN DELETE FROM DetalleEstudianteDiferido WHERE DetalleEstudianteDiferido.carnet = OLD.carnet AND DetalleEstudianteDiferido.idDetalleDiferidoRepetido = (OLD.idAsignatura||OLD.tipoEvaluacion||OLD.numeroeval||'Diferido'); END");
                db.execSQL("CREATE TRIGGER AprobarSolicitudDiferido AFTER UPDATE  ON SolicitudDiferido FOR EACH ROW WHEN NEW.estadoSolicitud = 'Aprobada' AND (OLD.estadoSolicitud = 'Pendiente' OR OLD.estadoSolicitud = 'Denegada') BEGIN INSERT INTO DetalleEstudianteDiferido(carnet,idDetalleDiferidoRepetido,FechaInscripcionDiferido) VALUES(OLD.carnet,OLD.idAsignatura||OLD.tipoEvaluacion||OLD.numeroeval||'Diferido',CURRENT_DATE ); END");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //UPDATE DATABASE COMMANDS
        }

        public Cursor obtenerDocentes() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor doc = db.rawQuery("select * from docente",null);
            return doc;
        }

        public Cursor obtenerEstudiantes() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor doc = db.rawQuery("select * from Estudiante",null);
            return doc;
        }
    }

    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }

    public SQLiteDatabase abrirLeer() throws SQLException{
        db = DBHelper.getReadableDatabase();
        return db;
    }

    public void cerrar() {
        db.close();
    }

    public String insertar(AccesoUsuario accesoUsuario) throws SQLException{
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",accesoUsuario.getUsername());
        contentValues.put("idOpcion", accesoUsuario.getIdOpcion());
        long contador = 0;
        contador = db.insert("accesoUsuario", null, contentValues);
        if (contador == -1 || contador == 0){
            return "Error al insertar los datos";
        }else return "Registro número: "+contador;
    }
    public String insertar(OpcionCrud opcion) throws SQLException{
        ContentValues contentValues = new ContentValues();
        contentValues.put("idOpcion",opcion.getIdOpcion());
        contentValues.put("descOpcion", opcion.getDescOpcion());
        contentValues.put("numCrud", opcion.getNumCrud());
        long contador = 0;
        contador = db.insert("opcionCrud", null, contentValues);
        if (contador == -1 || contador == 0){
            return "Error al insertar los datos";
        }else return "Registro número: "+contador;

    }
    public ArrayList<String> consultarAcceso(String username) {
        ArrayList<String> acceso = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from accesoUsuario where username ='" + username + "';", null);
        if (cursor.moveToFirst() == true) {
            do {
                acceso.add(cursor.getString(0));
            }while (cursor.moveToNext());
        } else return null;
        return acceso;
    }
    public String consultarOpcionCrud(String idOpcion) {
        String crud = null;
        Cursor cursor = db.rawQuery("select * from opcionCrud where idOpcion ='" + idOpcion + "';", null);
        if (cursor.moveToFirst() == true) {
                crud= cursor.getString(1);
        } else return null;
        return crud;
    }

    public boolean consultarUsuario(String username) {
        String[] id = {username};
        Cursor cursor = db.rawQuery("select * from usuario where username ='" + username + "'",null);
                /*"' and password ='" + password + "';", null);*/
        if (cursor.moveToFirst()) {
                return true;
            } else return false;
    }

    public String insertar(Usuario user) {
        String regAfectados = "Registro insertado Nª= ";
        long contador = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("nombre_usuario", user.getNombreUsuario());
        contador = db.insert("usuario", null, contentValues);
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public String insertar(SolicitudDiferido solicitudDiferido) {
        String regAfectados = "Registro insertado Nª= ";
        long contador = 0;
        if (verificarIntegridadReferencial(solicitudDiferido, 5)) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("carnet", solicitudDiferido.getCarnet());
            contentValues.put("numeroeval", solicitudDiferido.getNumeroEval());
            contentValues.put("ciclo", solicitudDiferido.getCiclo());
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
            contentValues.put("estadoSolicitud", solicitudDiferido.getEstado());
            contentValues.put("rutajustificante",solicitudDiferido.getRutaJustificante());
            contador = db.insert("SolicitudDiferido", null, contentValues);
            if (contador == -1 || contador == 0) {
                regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
            } else {
                regAfectados = regAfectados + contador;
            }
        }else return "Error al insertar, Datos incorrectos o vacios";
        return regAfectados;
    }

    public String actualizar(SolicitudDiferido solicitudDiferido) {
        if (verificarIntegridadReferencial(solicitudDiferido, 5)) {
            String[] id = {solicitudDiferido.getCarnet(), solicitudDiferido.getCodMateria(), solicitudDiferido.getCiclo(), solicitudDiferido.getTipoEva(), String.valueOf(solicitudDiferido.getNumeroEval())};
            ContentValues cv = new ContentValues();
            cv.put("GT", solicitudDiferido.getGT());
            cv.put("GD", solicitudDiferido.getGD());
            cv.put("GL", solicitudDiferido.getGL());
            cv.put("idMotivoDiferido", solicitudDiferido.getMotivo());
            cv.put("fechaEvaluacion", solicitudDiferido.getFechaEva());
            cv.put("horaEvaluacion", solicitudDiferido.getHoraEva());
            cv.put("descripcionMotivo", solicitudDiferido.getOtroMotivo());
            cv.put("rutajustificante",solicitudDiferido.getRutaJustificante());
            db.update("SolicitudDiferido", cv, "carnet = ? AND idAsignatura = ? AND ciclo = ? AND tipoEvaluacion = ? AND numeroeval = ?", id);
            return "Registro Actualizado Correctamente";
        } else return "Registro no existe";
    }

    public String insertar(DetalleDiferidoRepetido detalle) {
        String regAfectados = "Registro insertado Nº= ";
        long contador = 0;
        if (verificarIntegridadReferencial(detalle, 7)) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("idDetalleDiferidoRepetido", detalle.getIdDetalle());
            contentValues.put("idLocal", detalle.getIdLocal());
            contentValues.put("numEval", detalle.getNumEval());
            contentValues.put("tipoEval", detalle.getIdTipoEval());
            contentValues.put("codAsignatura", detalle.getIdAsignatura());
            contentValues.put("idDocente", detalle.getIdDocente());
            contentValues.put("idTipoDiferidoRepetido", detalle.getIdTipoDifRep());
            contentValues.put("fechaDesde", detalle.getFechaDesde());
            contentValues.put("fechaHasta", detalle.getFechaHasta());
            contentValues.put("fechaRealizacion", detalle.getFechaRealizacion());
            contentValues.put("horaRealizacion", detalle.getHoraRealizacion());
            contador = db.insert("DetalleDiferidoRepetido", null, contentValues);
        }
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public DetalleDiferidoRepetido consultarDetalleDifRep(String materia, String tipoEval, Integer numeroEval, String tipoDifRep) {
        String[] id = {materia + tipoEval + numeroEval + tipoDifRep};
        Cursor cursor = db.query("DetalleDiferidoRepetido", null, "idDetalleDiferidoRepetido = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            DetalleDiferidoRepetido detalle = new DetalleDiferidoRepetido();
            detalle.setIdDetalle(cursor.getString(0));
            detalle.setIdLocal(cursor.getString(1));
            detalle.setNumEval(cursor.getInt(2));
            detalle.setIdTipoEval(cursor.getString(3));
            detalle.setIdAsignatura(cursor.getString(4));
            detalle.setIdDocente(cursor.getString(5));
            detalle.setIdTipoDifRep(cursor.getString(6));
            detalle.setFechaDesde(cursor.getString(7));
            detalle.setFechaHasta(cursor.getString(8));
            detalle.setFechaRealizacion(cursor.getString(9));
            detalle.setHoraRealizacion(cursor.getString(10));
            return detalle;
        } else return null;
    }

    public String eliminar(DetalleDiferidoRepetido detalleDiferidoRepetido) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("DetalleDiferidoRepetido", "idDetalleDiferidoRepetido='" + detalleDiferidoRepetido.getIdDetalle() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public String actualizar(DetalleDiferidoRepetido detalle) {
        if (verificarIntegridadReferencial(detalle, 7)) {
            String[] id = {detalle.getIdDetalle()};
            ContentValues cv = new ContentValues();
            cv.put("idLocal", detalle.getIdLocal());
            cv.put("idDocente", detalle.getIdDocente());
            cv.put("fechaDesde", detalle.getFechaDesde());
            cv.put("fechaHasta", detalle.getFechaHasta());
            cv.put("fechaRealizacion", detalle.getFechaRealizacion());
            cv.put("horaRealizacion", detalle.getHoraRealizacion());
            db.update("DetalleDiferidoRepetido", cv, "idDetalleDiferidoRepetido = ?", id);
            return "Registro Actualizado Correctamente";
        } else return "Registro no existe";
    }

    //Insertar Calendario
    public String insertarCalendario(CalendarioN calendario) {
        String regInsertados = "Registro No. = ";
        long contador = 0;

        ContentValues calen = new ContentValues();
        calen.put("objFecha", calendario.getObjetoFecha());
        calen.put("evento", calendario.getEvento());
        contador = db.insert("calendario", null, calen);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        } else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    //Agregar a Lista para recuperar en el activity
    public ArrayList<String> conInsertarCalendario() {

        ArrayList<String> listCal = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, objFecha, evento FROM calendario", null);
        if (cursor.moveToFirst()) {
            do {

                listCal.add(cursor.getString(1) +" "+ cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return listCal;
    }


    public String insertar(DetalleEstudianteDiferido detalle) {
        String regAfectados = "Registro insertado Nº= ";
        long contador = 0;
        if (verificarIntegridadReferencial(detalle, 8)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("carnet", detalle.getCarnet());
            contentValues.put("idDetalleDiferidoRepetido", detalle.getIdDetalleDifeRep());
            contentValues.put("FechaInscripcionDiferido", detalle.getFechaInscripcionDiferido());
            contador = db.insert("DetalleEstudianteDiferido", null, contentValues);
        }
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public String insertar(DetalleEstudianteRepetido detalle) {
        String regAfectados = "Registro insertado Nº= ";
        long contador = 0;
        if (verificarIntegridadReferencial(detalle, 9)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("carnet", detalle.getCarnet());
            contentValues.put("idDetalleDiferidoRepetido", detalle.getIdDetalleDifRep());
            contentValues.put("FechaInscripcionRepetido", detalle.getFechaInscripcion());
            contador = db.insert("DetalleEstudianteRepetido", null, contentValues);
        }
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public DetalleEstudianteRepetido consultarDetallEstudRep(String carnet, String materia, String tipoEval, int numEval) {
        String id = materia + tipoEval + numEval;
        String idcarnet = carnet;
        Cursor cursor = db.query("DetalleEstudianteRepetido", null, "carnet = '" + carnet + "' AND idDetalleDiferidoRepetido = '" + id + "Repetido'", null, null, null, null);
        if (cursor.moveToFirst()) {
            DetalleEstudianteRepetido detalle = new DetalleEstudianteRepetido();
            detalle.setCarnet(cursor.getString(0));
            detalle.setIdDetalleDifRep(cursor.getString(1));
            detalle.setFechaInscripcion(cursor.getString(2));
            return detalle;
        } else return null;
    }

    public String eliminar(DetalleEstudianteRepetido detalleRep) throws SQLException {
        String regAfectados;
        int contador = 0;
        String[] id = {detalleRep.getCarnet(), detalleRep.getIdDetalleDifRep()};
        contador += db.delete("DetalleEstudianteRepetido", "carnet = ? AND idDetalleDiferidoRepetido= ?", id);
        if (!(contador == -1 || contador == 0)) {
            regAfectados = "Se eliminó el registro correctamente";
        } else regAfectados = "No existen registros, revise los datos";
        return regAfectados;
    }

    public String actualizarEstado(SolicitudDiferido solicitudDiferido) throws SQLException {
        if (verificarIntegridadReferencial(solicitudDiferido, 5)) {
            String[] id = {solicitudDiferido.getCarnet(), solicitudDiferido.getCodMateria(), solicitudDiferido.getCiclo(), solicitudDiferido.getTipoEva(), String.valueOf(solicitudDiferido.getNumeroEval())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("estadoSolicitud", solicitudDiferido.getEstado());
            db.update("SolicitudDiferido", contentValues, "carnet = ? AND idAsignatura = ? AND ciclo = ? AND tipoEvaluacion = ? AND numeroeval = ?", id);

            return "Registro Actualizado Correctamente";
        } else return "Registro no existe";
    }

    public ArrayList<String> consultarEstudiantesInscritos(String materia, String tipoEval, int numEval, String tipo) {
        ArrayList<String> solicitudes = new ArrayList<>();
        String[] id = {materia + tipoEval + numEval + tipo};
        Cursor cursor = db.query("DetalleEstudiante" + tipo, null, "idDetalleDiferidoRepetido = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                solicitudes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return solicitudes;
    }

    public DetalleEstudianteDiferido consultarDetallEstuDifIndividual(String carnet, String materia, String tipoEval, int numEval) {
        String[] id = {carnet, materia + tipoEval + numEval + "Diferido"};
        DetalleEstudianteDiferido detalle = new DetalleEstudianteDiferido();
        Cursor cursor = db.query("DetalleEstudianteDiferido", null, "carnet = ? AND idDetalleDiferidoRepetido = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            detalle.setCarnet(cursor.getString(0));
            detalle.setIdDetalleDifeRep(cursor.getString(1));
            detalle.setFechaInscripcionDiferido(cursor.getString(2));
            return detalle;
        } else return null;
    }

    public String insertar(MotivoDiferido motivoDiferido) {
        String regAfectados = "Registro insertado Nº= ";
        long contador = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreMotivo", motivoDiferido.getNombreMotivoDiferido());
        contador = db.insert("MotivoDiferido", null, contentValues);
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public ArrayList<String> consultarSolicitudesPendiente(String materia, String tipoEval, int numeroEval, String estado, String ciclo) {
        ArrayList<String> solicitudes = new ArrayList<>();
        String[] ids = {materia, tipoEval, String.valueOf(numeroEval), estado};
        Cursor cursor = db.rawQuery("SELECT * FROM SolicitudDiferido WHERE idAsignatura ='" + materia + "' AND ciclo = '"+ciclo+"' AND tipoEvaluacion = '" + tipoEval + "' AND numeroeval = " + numeroEval + " AND estadoSolicitud = '" + estado + "'", null);
        if (cursor.moveToFirst()) {
            do {

                solicitudes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return solicitudes;
    }

    public SolicitudDiferido consultarSolicitudDiferido(String carnet, String codMateria, String ciclo, String tipoEval, String numEval) {
        String[] id = {carnet, codMateria, ciclo, tipoEval,  numEval};
        Cursor cursor = db.query("SolicitudDiferido", null, "carnet = ? AND idAsignatura = ? AND ciclo = ? AND tipoEvaluacion = ? AND numeroeval = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            SolicitudDiferido solicitudDiferido = new SolicitudDiferido();
            solicitudDiferido.setCarnet(cursor.getString(0));
            solicitudDiferido.setNumeroEval(cursor.getInt(1));
            solicitudDiferido.setMotivo(cursor.getString(2));
            solicitudDiferido.setFechaEva(cursor.getString(3));
            solicitudDiferido.setHoraEva(cursor.getString(4));
            solicitudDiferido.setOtroMotivo(cursor.getString(5));
            solicitudDiferido.setCodMateria(cursor.getString(6));
            solicitudDiferido.setGT(cursor.getString(7));
            solicitudDiferido.setGD(cursor.getString(8));
            solicitudDiferido.setGL(cursor.getString(9));
            solicitudDiferido.setTipoEva(cursor.getString(10));
            solicitudDiferido.setEstado(cursor.getString(11));
            solicitudDiferido.setCiclo(cursor.getString(12));
            solicitudDiferido.setRutaJustificante(cursor.getString(13));
            return solicitudDiferido;
        } else return null;
    }

    public String eliminar(SolicitudDiferido solicitudDiferido) {
        String regAfectados = "filas afectadas= ";
        String[] id = {solicitudDiferido.getCarnet(), solicitudDiferido.getCodMateria(), solicitudDiferido.getCiclo(), solicitudDiferido.getTipoEva(), String.valueOf(solicitudDiferido.getNumeroEval())};
        int contador = 0;
        contador += db.delete("SolicitudDiferido", "carnet=? AND idAsignatura = ? AND ciclo =? AND tipoEvaluacion = ? AND numeroeval = ?", id);
        regAfectados += contador;
        return regAfectados;
    }

    public String insertar(Estudiante estudiante) {
        String regAfectados = "Registro insertado Nº: ";
        long contador = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("carnet", estudiante.getCarnet());
        contentValues.put("nombreEstudiante", estudiante.getNombre());
        contentValues.put("apellidoEstudiante", estudiante.getApellido());
        contentValues.put("carrera", estudiante.getCarrera());
        contador = db.insert("Estudiante", null, contentValues);
        if (contador == -1 || contador == 0) {
            regAfectados = "Error al Insertar el registro, Registro duplicado. Verificar inserción";
        } else {
            regAfectados = regAfectados + contador;
        }
        return regAfectados;
    }

    public String actualizar(Estudiante estudiante) {
        if (verificarIntegridadReferencial(estudiante, 6)) {
            String[] id = {estudiante.getCarnet()};
            ContentValues cv = new ContentValues();
            cv.put("nombreEstudiante", estudiante.getNombre());
            cv.put("apellidoEstudiante", estudiante.getApellido());
            cv.put("carrera", estudiante.getCarrera());
            db.update("Estudiante", cv, "carnet = ?", id);
            return "Registro Actualizado Correctamente";
        } else return "Registro no existe";
    }

    public Estudiante consultarEstudiante(String carnet) {
        String[] id = {carnet};
        Cursor cursor = db.query("Estudiante", null, "carnet = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Estudiante estudiante = new Estudiante();
            estudiante.setCarnet(cursor.getString(0));
            estudiante.setNombre(cursor.getString(1));
            estudiante.setApellido(cursor.getString(2));
            estudiante.setCarrera(cursor.getString(3));
            return estudiante;
        } else return null;
    }

    public String eliminar(Estudiante estudiante) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        contador += db.delete("Estudiante", "carnet='" + estudiante.getCarnet() + "'", null);
        regAfectados += contador;
        return regAfectados;
    }

    public String insertar(MotivoCambioNota motivo){
        String regInsertados = "Registro No. = ";
        long contador = 0;

        ContentValues mot = new ContentValues();
        mot.put("codmotivocambionota", motivo.getCodmotivocambionota());
        mot.put("motivo", motivo.getMotivo());
        contador = db.insert("motivocambionota", null, mot);

        if (contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        }else{
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(SolicitudRevision solicitud){
        String regInsertados = "Registro No. = ";
        long contador = 0;

        if(verificarIntegridadReferencial(solicitud, 33)){

            ContentValues sol = new ContentValues();
            sol.put("fechasolicitudrevision", solicitud.getFechasolicitudrevision());
            sol.put("notaantesrevision", solicitud.getNotaantesrevision());
            sol.put("numerogrupo", solicitud.getNumerogrupo());
            sol.put("codtipogrupo", solicitud.getCodtipogrupo());
            sol.put("motivorevision", solicitud.getMotivorevision());
            sol.put("carnet", solicitud.getCarnet());
            sol.put("codasignatura", solicitud.getCodasignatura());
            sol.put("codciclo", solicitud.getCodciclo());
            sol.put("codtipoeval", solicitud.getCodtipoeval());
            sol.put("numeroeval", solicitud.getNumeroeval());
            sol.put("codtiporevision", solicitud.getCodtiporevision());
            contador = db.insert("solicitudrevision", null, sol);

        }

        if (contador == -1 || contador == 0){
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
//************************************************************************
    public String insertar(TipoGrupo tipoGrupo){
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues tipgru = new ContentValues();
        tipgru.put("codtipogrupo", tipoGrupo.getCodtipogrupo());
        tipgru.put("nombretipogrupo", tipoGrupo.getNombreTipoGrupo());
        contador = db.insert("tipogrupo", null, tipgru);

        if(contador == -1 || contador == 0){
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Isercion";
        } else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(TipoEvaluacion tipoEval) {
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        ContentValues teval = new ContentValues();
        teval.put("codtipoeval", tipoEval.getCodTipoEval());
        teval.put("nomtipoeval", tipoEval.getNomTipoEval());
        contador = db.insert("tipoevaluacion", null, teval);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        } else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(Evaluacion evaluacion) {
        String regInsertados = "Registro Insertado No. = ";
        long contador = 0;

        if (verificarIntegridadReferencial(evaluacion, 1)) {
            ContentValues evaluaciones = new ContentValues();
            evaluaciones.put("codasignatura", evaluacion.getCodAsignatura());
            evaluaciones.put("codciclo", evaluacion.getCodCiclo());
            evaluaciones.put("codtipoeval", evaluacion.getCodTipoEval());
            evaluaciones.put("fechaevaluacion", evaluacion.getFechaEvaluacion());
            evaluaciones.put("numeroeval", evaluacion.getNumeroEvaluacion());
            contador = db.insert("evaluacion", null, evaluaciones);
        }
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        } else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }

    public String insertar(Local local) {
        String regInsertados = "Registro No. = ";
        long contador = 0;

        ContentValues locales = new ContentValues();
        locales.put("codlocal", local.getCodlocal());
        locales.put("nomlocal", local.getNomlocal());
        locales.put("ubicacionlocal", local.getUbicacionlocal());
        contador = db.insert("local", null, locales);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        } else {
            regInsertados = regInsertados + contador;
        }

        return regInsertados;
    }
    public void insertar(TipoDiferidoRepetido tipo){
        try {
            long counter = 0;
            ContentValues contentValues = new ContentValues();
            contentValues.put("idTipoDiferidoRepetido", tipo.getIdTipo());
            contentValues.put("nombreTipo", tipo.getNombreTipo());
            counter = db.insert("TipoDiferidoRepetido", null, contentValues);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public String insertar(PeriodoInscripcionRevision perInscRev){
        String regInsertados = "Registro No. = ";
        long contador = 0;

        if (verificarIntegridadReferencial(perInscRev, 3)) {
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
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar Insercion";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public String insertar(PrimeraRevision primRev){
        String regInsertados = "Revisión Insertada con Exito, Registro No. = ";
        long contador = 0;

        if(verificarIntegridadReferencial(primRev, 10)){
            ContentValues primerrevision = new ContentValues();
            primerrevision.put("estadoprimerrevision", primRev.getEstadoprimerrevision());
            primerrevision.put("notadespuesprimerarevision", primRev.getNotadespuesprimerarevision());
            primerrevision.put("asistio", primRev.getAsistio());
            primerrevision.put("observacionesprimerarev", primRev.getObservacionesprimerarev());
            primerrevision.put("coddocente", primRev.getCoddocente());
            primerrevision.put("carnet", primRev.getCarnet());
            primerrevision.put("codasignatura", primRev.getCodasignatura());
            primerrevision.put("codciclo", primRev.getCodciclo());
            primerrevision.put("codtipoeval", primRev.getCodtipoeval());
            primerrevision.put("numeroeval", primRev.getNumeroeval());
            primerrevision.put("codtiporevision", primRev.getCodtiporevision());
            primerrevision.put("codmotivocambionota", primRev.getMotivoCambioNota());
            primerrevision.put("codtipogrupo", primRev.getCodtipogrupo());
            contador = db.insert("primerrevision", null, primerrevision);
        }
        if (contador == -1 || contador == 0){
            regInsertados = "Error al insertar Primera Revisión, Registro Duplicado o Campos Incompletos. Verificar Inserción.";
        }else{
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public String insertar(SegundaRevision segRev){
        String regInsertados = "Revisión Insertada con Exito, Registro No. = ";
        long contador = 0;

        String[] id = {segRev.getCarnet(), segRev.getCodtiporevision(), segRev.getCodasignatura(), segRev.getCodciclo(), segRev.getCodtipoeval(), segRev.getCodtipogrupo(), String.valueOf(segRev.getNumeroeval())};
        Cursor c = db.rawQuery("SELECT * FROM solicitudrevision WHERE carnet = ? AND codtiporevision = ? AND codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND codtipogrupo = ? AND numeroeval = ?;", id);
        if(c.moveToFirst()){
            if(verificarIntegridadReferencial(segRev, 36)){
                ContentValues segundarevision = new ContentValues();
                segundarevision.put("codmotivocambionota", segRev.getMotivoCambioNota());
                segundarevision.put("codtipogrupo", segRev.getCodtipogrupo());
                segundarevision.put("coddocente", segRev.getCoddocente());
                segundarevision.put("notafinalsegundarev", segRev.getNotafinalsegundarev());
                segundarevision.put("observacionessegundarev", segRev.getObservacionessegundarev());
                segundarevision.put("carnet", segRev.getCarnet());
                segundarevision.put("codasignatura", segRev.getCodasignatura());
                segundarevision.put("codciclo", segRev.getCodciclo());
                segundarevision.put("codtipoeval", segRev.getCodtipoeval());
                segundarevision.put("numeroeval", segRev.getNumeroeval());
                segundarevision.put("codtiporevision", segRev.getCodtiporevision());
                contador = db.insert("segundarevision", null, segundarevision);
            }
        }else{
            Toast.makeText(context, "No existe Solicitud", Toast.LENGTH_SHORT).show();
        }

        //if(verificarIntegridadReferencial(segRev, 36)) {
        /*ContentValues segundarevision = new ContentValues();

        segundarevision.put("codmotivocambionota", segRev.getMotivoCambioNota());
        segundarevision.put("codtipogrupo", segRev.getCodtipogrupo());
        segundarevision.put("coddocente", segRev.getCoddocente());
        segundarevision.put("notafinalsegundarev", segRev.getNotafinalsegundarev());
        segundarevision.put("observacionessegundarev", segRev.getObservacionessegundarev());
        segundarevision.put("carnet", segRev.getCarnet());
        segundarevision.put("codasignatura", segRev.getCodasignatura());
        segundarevision.put("codciclo", segRev.getCodciclo());
        segundarevision.put("codtipoeval", segRev.getCodtipoeval());
        segundarevision.put("numeroeval", segRev.getNumeroeval());
        segundarevision.put("codtiporevision", segRev.getCodtiporevision());
        contador = db.insert("segundarevision", null, segundarevision);
        //}*/
        if (contador == -1 || contador == 0){
            regInsertados = "Error al insertar Segunda Revisión, Registro Duplicado o Campos Incompletos. Verificar Inserción.";
        }else{
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }



    public Asignatura consultarNomAsignatura(String codAsignatura){
        String[] id = {codAsignatura};
        String[] camposAsigConsul = {"nomasignatura"};
        Cursor cursor = db.query("asignatura", camposAsigConsul, "codasignatura = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Asignatura asig = new Asignatura();
            asig.setNomasignatura(cursor.getString(0));
            return asig;
        } else {
            return null;
        }
    }

    public Docente consultarNomDocente(String codDocente) {
        String[] id = {codDocente};
        String[] camposDocente = {"nombredocente", "apellidodocente"};
        Cursor cursor = db.query("docente", camposDocente, "coddocente = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Docente doc = new Docente();
            doc.setNomdocente(cursor.getString(0));
            doc.setApellidodocente(cursor.getString(1));
            return doc;
        } else {
            return null;
        }
    }

    public MotivoCambioNota consultarMotCambNota(String codMotCambNota) {
        String[] id = {codMotCambNota};
        String[] camposMotCambNota = {"motivo"};
        Cursor cursor = db.query("motivocambionota", camposMotCambNota, "codmotivocambionota = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            MotivoCambioNota motCambio = new MotivoCambioNota();
            motCambio.setMotivo(cursor.getString(0));
            return motCambio;
        } else {
            return null;
        }
    }

    public Local consultarLocal(String codLocal) {
        String[] id = {codLocal};
        Cursor cursor = db.query("local", camposLocal, "codlocal = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Local local = new Local();
            local.setCodlocal(cursor.getString(0));
            local.setNomlocal(cursor.getString(1));
            local.setUbicacionlocal(cursor.getString(2));
            return local;
        } else {
            return null;
        }
    }

    public Evaluacion consultarEvaluacion(String codAsignatura, String codCiclo, String codTipoEval, int numEval) {
        String[] id = {codAsignatura, codCiclo, codTipoEval, String.valueOf(numEval)};
        Cursor cursor = db.query("evaluacion", camposEvaluacion, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setCodAsignatura(cursor.getString(0));
            evaluacion.setCodCiclo(cursor.getString(1));
            evaluacion.setCodTipoEval(cursor.getString(2));
            evaluacion.setNumeroEvaluacion(Integer.parseInt(cursor.getString(3)));
            evaluacion.setFechaEvaluacion(cursor.getString(4));
            return evaluacion;
        } else {
            return null;
        }
    }

    public PeriodoInscripcionRevision consultarPeriodoInscripcion(String codTipoRev, String codAsignatura, String codTipoEva, String codCiclo, int numEva) {
        String[] id = {codTipoRev, codAsignatura, codTipoEva, codCiclo, String.valueOf(numEva)};
        Cursor cursor = db.query("periodoinscripcionrevision", camposPerInscRev, "codtiporevision = ? AND codasignatura = ? AND codtipoeval = ? AND codciclo = ? AND numeroeval = ?", id, null, null, null);
        if (cursor.moveToFirst()) {
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
        } else {
            return null;
        }
    }

    public PrimeraRevision consultarPrimerRevision(String codAsignatura, String codCiclo, int numEval, String carnet, String tipoEval, String codtipogrupo){
        String[] id = {codAsignatura, codCiclo, String.valueOf(numEval), carnet, tipoEval, codtipogrupo};
        Cursor cursor = db.query("primerrevision", camposPrimerRevision, "codasignatura = ? AND codciclo = ? AND numeroeval = ? AND carnet = ? AND codtipoeval = ? AND codtipogrupo = ?", id, null, null, null);
        if(cursor.moveToFirst()) {
            PrimeraRevision primRev = new PrimeraRevision();
            primRev.setEstadoprimerrevision(cursor.getString(0));
            primRev.setNotadespuesprimerarevision(Float.parseFloat(cursor.getString(1)));
            primRev.setAsistio(cursor.getString(2));
            primRev.setObservacionesprimerarev(cursor.getString(3));
            primRev.setCoddocente(cursor.getString(4));
            primRev.setCodtipogrupo(cursor.getString(5));
            primRev.setCarnet(cursor.getString(6));
            primRev.setCodasignatura(cursor.getString(7));
            primRev.setCodciclo(cursor.getString(8));
            primRev.setCodtipoeval(cursor.getString(9));
            primRev.setNumeroeval(Integer.parseInt(cursor.getString(10)));
            primRev.setCodtiporevision(cursor.getString(11));
            primRev.setMotivoCambioNota(cursor.getString(12));
            return primRev;
        }else{
            return null;
        }
    }

    public SegundaRevision consultarSegundaRevision(String codAsignatura, String codTipoEval, int numeroEval, String codCiclo, String carnet){
        String[] id = {codAsignatura, codTipoEval, String.valueOf(numeroEval), codCiclo, carnet};
        Cursor cursor = db.query("segundarevision", camposSegundaRevision, "codasignatura = ? AND codtipoeval = ? AND numeroeval = ? AND codciclo = ? AND carnet = ?", id, null, null, null);
        if(cursor.moveToFirst()) {
            SegundaRevision segRev = new SegundaRevision();
            segRev.setMotivoCambioNota(cursor.getString(0));
            segRev.setCodtipogrupo(cursor.getString(1));
            segRev.setCoddocente(cursor.getString(2));
            segRev.setNotafinalsegundarev(Float.parseFloat(cursor.getString(3)));
            segRev.setObservacionessegundarev(cursor.getString(4));
            segRev.setCarnet(cursor.getString(5));
            segRev.setCodasignatura(cursor.getString(6));
            segRev.setCodciclo(cursor.getString(7));
            segRev.setCodtipoeval(cursor.getString(8));
            segRev.setNumeroeval(Integer.parseInt(cursor.getString(9)));
            segRev.setCodtiporevision(cursor.getString(10));
            return segRev;
        }else{
            return null;
        }
    }


    public String actualizar(Evaluacion evaluacion) {
        if (verificarIntegridadReferencial(evaluacion, 2)) {
            String[] id = {evaluacion.getCodAsignatura(), evaluacion.getCodCiclo(), evaluacion.getCodTipoEval(), String.valueOf(evaluacion.getNumeroEvaluacion())};
            ContentValues cv = new ContentValues();
            cv.put("fechaevaluacion", evaluacion.getFechaEvaluacion());
            db.update("evaluacion", cv, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", id);
            return "Registro Actualizado correctamente";
        } else {
            return "Registro no Existe";
        }
    }

    public String actualizar(Local local) {
        if (verificarIntegridadReferencial(local, 12)) {
            String[] id = {local.getCodlocal()};
            ContentValues cv = new ContentValues();
            cv.put("nomlocal", local.getNomlocal());
            cv.put("ubicacionlocal", local.getUbicacionlocal());
            db.update("local", cv, "codlocal = ? ", id);
            return "Registro Actualizado Correctamente";
        } else {
            return "Registro no Existe";
        }
    }

    public String actualizar(PeriodoInscripcionRevision perInscRev) {
        if (verificarIntegridadReferencial(perInscRev, 4)) {
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
        } else {
            return "Registro no Existe";
        }
    }

    public String actualizar (PrimeraRevision primRev){
        if(verificarIntegridadReferencial(primRev, 11)){
            String[] id = {primRev.getCoddocente() ,primRev.getCarnet(), primRev.getCodasignatura(), primRev.getCodtiporevision(), primRev.getCodciclo(), primRev.getCodtipoeval(), primRev.getCodtipogrupo(), String.valueOf(primRev.getNumeroeval())};
            ContentValues cv = new ContentValues();
            cv.put("estadoprimerrevision", primRev.getEstadoprimerrevision());
            cv.put("asistio", primRev.getAsistio());
            cv.put("notadespuesprimerarevision", primRev.getNotadespuesprimerarevision());
            cv.put("codmotivocambionota", primRev.getMotivoCambioNota());
            cv.put("observacionesprimerarev", primRev.getObservacionesprimerarev());
            db.update("primerrevision", cv, "coddocente = ? AND carnet = ? AND codasignatura = ? AND codtiporevision = ? AND codciclo = ? AND codtipoeval = ? AND codtipogrupo = ? AND numeroeval = ?", id);
            return "Revisión Actualizada Correctamente";
        }else{
            return "Error, está Revisión No Existe";
        }
    }

    public String actualizar (SegundaRevision segRev){
        if(verificarIntegridadReferencial(segRev, 37)){
            String[] id = {segRev.getCodtiporevision(), segRev.getCodasignatura(), segRev.getCodtipoeval(), String.valueOf(segRev.getNumeroeval()), segRev.getCodciclo(), segRev.getCarnet()};
            ContentValues cv = new ContentValues();

            cv.put("codtiporevision", segRev.getCodtiporevision());
            cv.put("codasignatura", segRev.getCodasignatura());
            cv.put("codtipoeval", segRev.getCodtipoeval());
            cv.put("numeroeval", segRev.getNumeroeval());
            cv.put("codciclo", segRev.getCodciclo());
            cv.put("codmotivocambionota", segRev.getMotivoCambioNota());
            cv.put("codtipogrupo", segRev.getCodtipogrupo());
            cv.put("carnet", segRev.getCarnet());
            cv.put("coddocente", segRev.getCoddocente());
            cv.put("notafinalsegundarev", segRev.getNotafinalsegundarev());
            cv.put("observacionessegundarev", segRev.getObservacionessegundarev());
            db.update("segundarevision", cv, "codtiporevision = ? AND codasignatura = ? AND codtipoeval = ? AND numeroeval = ? AND codciclo = ? AND carnet = ?", id);
            return "Revisión Actualizada Correctamente";
        }else{
            return "Error, está Revisión No Existe";
        }
    }

    public String eliminar(Evaluacion evaluacion) {
        String regAfectados = "Filas afectadas = ";
        int contador = 0;

        if(verificarIntegridadReferencial(evaluacion, 32)){
            String nosepuede = "Error, No se puede eliminar, existen registros de esta Evaluación en otras Tablas.";
            return nosepuede;
        }

        String where = "codasignatura = '"+evaluacion.getCodAsignatura()+"'";
        where = where + "AND codciclo = '"+evaluacion.getCodCiclo()+"'";
        where = where + "AND codtipoeval = '"+evaluacion.getCodTipoEval()+"'";
        where = where + "AND numeroeval = '"+evaluacion.getNumeroEvaluacion()+"'";
        contador += db.delete("evaluacion", where, null);

        if(contador == 0){
            return "Está Evaluación No Existe o Campos Incompletos.";
        }

        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar(Local local){
        String regAfectados = "Local Eliminado con exito, Filas afectadas = ";
        int contador = 0;

        if(verificarIntegridadReferencial(local, 31)){
            String nosepuede = "Error, existen registros de este Local en otras Tablas.";
            return nosepuede;
        }

        String where = "codlocal = '"+local.getCodlocal()+"'";
        where = where + "AND nomlocal = '"+local.getNomlocal()+"'";
        contador += db.delete("local", where, null);

        if(contador == 0){
            return "Esté Local No Existe o Campos Incompletos.";
        }

        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar(PeriodoInscripcionRevision perInscRev){
        String regAfectados = "Período Revisión Eliminado con exito, Filas afectadas = ";
        int contador = 0;

        String where = "codtiporevision = '"+perInscRev.getTipoRevision()+"'";
        where = where + "AND codasignatura = '"+perInscRev.getCodAsignatura()+"'";
        where = where + "AND codciclo = '"+perInscRev.getCodCiclo()+"'";
        where = where + "AND codtipoeval = '"+perInscRev.getCodTipoEval()+"'";
        where = where + "AND numeroeval = '"+perInscRev.getNumeroEval()+"'";
        contador += db.delete("periodoinscripcionrevision", where, null);

        if(contador == 0){
            return "Período Revisión No Existe";
        }

        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar (PrimeraRevision primRev){
        String regAfectados = "Primera Revisión Eliminada con exito, Filas afectadas = ";
        int contador = 0;

        if(verificarIntegridadReferencial(primRev, 38)){
            String nosepuede = "Error, existen registros de este Revision en otras Tablas.";
            return nosepuede;
        }

        String where = "coddocente = '"+primRev.getCoddocente()+"'";
        where = where + "AND carnet = '"+primRev.getCarnet()+"'";
        where = where + "AND codasignatura = '"+primRev.getCodasignatura()+"'";
        where = where + "AND codtiporevision = '"+primRev.getCodtiporevision()+"'";
        where = where + "AND codciclo = '"+primRev.getCodciclo()+"'";
        where = where + "AND codtipoeval = '"+primRev.getCodtipoeval()+"'";
        where = where + "AND codtipogrupo = '"+primRev.getCodtipogrupo()+"'";
        where = where + "AND numeroeval = '"+primRev.getNumeroeval()+"'";
        contador += db.delete("primerrevision", where, null);

        if(contador == 0){
            return "Primera Revisión No Existe";
        }

        regAfectados += contador;
        return regAfectados;
    }

    public String eliminar (SegundaRevision segRev){
        String regAfectados = "Segunda Revisión Eliminada con exito, Filas afectadas = ";
        int contador = 0;

        String where = "codtiporevision = '"+segRev.getCodtiporevision()+"'";
        where = where + "AND codasignatura = '"+segRev.getCodasignatura()+"'";
        where = where + "AND codtipoeval = '"+segRev.getCodtipoeval()+"'";
        where = where + "AND numeroeval = '"+segRev.getNumeroeval()+"'";
        where = where + "AND codciclo = '"+segRev.getCodciclo()+"'";
        where = where + "AND carnet = '"+segRev.getCarnet()+"'";
        contador += db.delete("segundarevision", where, null);

        if(contador == 0){
            return "Segunda Revisión No Existe.";
        }

        regAfectados += contador;
        return regAfectados;
    }

    public String insertar(Ciclo ciclo){
        String regInsertados="Registro Insertado N°=";
        long contador=0;

        ContentValues cic=new ContentValues();
        cic.put("codciclo", ciclo.getCodciclo());
        cic.put("fechadesde", ciclo.getFechadesde());
        cic.put("fechahasta", ciclo.getFechahasta());
        contador=db.insert("ciclo", null, cic);

        if (contador==-1 || contador==0)
        {
            regInsertados="Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }else{
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public String insertar(CargaAcademica cargaAcademica){

        String regInsertados="Registro Insertado N°=";
        long contador=0;
        if(verificarIntegridadReferencial(cargaAcademica, 13)) {
            ContentValues carga = new ContentValues();
            carga.put("idcargaacademica", cargaAcademica.getIdcargaacademica());
            carga.put("codciclo", cargaAcademica.getCodciclo());
            carga.put("coddocente", cargaAcademica.getCoddocente());
            carga.put("codasignatura", cargaAcademica.getCodasignatura());
            carga.put("idtipodocenteciclo", cargaAcademica.getIdtipodocenteciclo());
            contador = db.insert("cargaacademica", null, carga);
        }
        if (contador==-1 || contador==0)
        {
            regInsertados="Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }else{
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }
    public String insertar(Docente docente){
        String regInsertados="Registro Insertado N°=";
        long contador=0;

        ContentValues doc=new ContentValues();
        doc.put("coddocente", docente.getCoddocente());
        doc.put("nombredocente", docente.getNomdocente());
        doc.put("apellidodocente", docente.getApellidodocente());
        contador=db.insert("docente", null, doc);

        if(contador==-1 || contador==0){
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }else{
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public String insertar(Asignatura asignatura){
        String regInsertados="Registro Insertado N°=";
        long contador=0;

        ContentValues as=new ContentValues();
        as.put("codasignatura", asignatura.getCodasignatura());
        as.put("nomasignatura", asignatura.getNomasignatura());
        as.put("unidadesval", asignatura.getUnidadesval());
        contador=db.insert("asignatura", null, as);

        if(contador==-1 || contador==0){
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }else{
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public String insertar(TipoDocenteCiclo tipoDocenteCiclo){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues tipo = new ContentValues();
        tipo.put("idtipodocenteciclo", tipoDocenteCiclo.getIdtipodocenteciclo());
        tipo.put("nomtipodocenteciclo", tipoDocenteCiclo.getNomtipodocenteciclo());
        contador=db.insert("tipodocenteciclo", null, tipo);
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public String actualizar(Ciclo ciclo){

        if(verificarIntegridadReferencial(ciclo, 20)){
            String[] id = {ciclo.getCodciclo()};
            ContentValues cv = new ContentValues();
            cv.put("fechadesde", ciclo.getFechadesde());
            cv.put("fechahasta", ciclo.getFechahasta());
            db.update("ciclo", cv, "codciclo = ?", id);
            return"Registro Actualizado Correctamente";

        }else{
            return "Registro con Código"+ciclo.getCodciclo()+"no existe";
        }

    }
    public String actualizar(CargaAcademica cargaAcademica){

        if(verificarIntegridadReferencial(cargaAcademica, 14)){
            String[] id = {cargaAcademica.getIdcargaacademica()};
            ContentValues cv = new ContentValues();

            cv.put("codciclo", cargaAcademica.getCodciclo());
            cv.put("coddocente", cargaAcademica.getCoddocente());
            cv.put("codasignatura", cargaAcademica.getCodasignatura());
            cv.put("idtipodocenteciclo", cargaAcademica.getIdtipodocenteciclo());
            db.update("cargaacademica", cv, "idcargaacademica = ?", id);
            return"Registro Actualizado Correctamente";

        }else{
            return "Registro con Código "+cargaAcademica.getIdcargaacademica()+"no existe";
        }
    }
    public String actualizar(Docente docente){
        if(verificarIntegridadReferencial(docente, 21)){
            String[] id = {docente.getCoddocente()};
            ContentValues cv = new ContentValues();

            cv.put("nombredocente", docente.getNomdocente());
            cv.put("apellidodocente", docente.getApellidodocente());
            db.update("docente", cv, "coddocente = ?", id);
            return"Registro Actualizado Correctamente";

        }else{
            return "Registro con Código"+docente.getCoddocente()+"no existe";
        }
    }
    public String actualizar(Asignatura asignatura){
        if(verificarIntegridadReferencial(asignatura, 19)){
            String[] id = {asignatura.getCodasignatura()};
            ContentValues cv = new ContentValues();
            cv.put("codasignatura", asignatura.getCodasignatura());
            cv.put("nomasignatura",asignatura.getNomasignatura());
            cv.put("unidadesval", asignatura.getUnidadesval());
            db.update("asignatura", cv, "codasignatura = ?", id);
            return"Registro Actualizado Correctamente";

        }else{
            return "Registro con Código"+asignatura.getCodasignatura()+"no existe";
        }
    }
    //public String actualizar(TipoDocenteCiclo tipoDocenteCiclo){
    //  return null;
    //}
    public String eliminar(Ciclo ciclo){
        String regAfectados="Filas afectadas= ";
        int contador=0;
        String where="codciclo='"+ciclo.getCodciclo()+"'";
        contador+=db.delete("ciclo", where, null);
        regAfectados+=contador;
        return regAfectados;
    }
    public String eliminar(CargaAcademica cargaAcademica){

        String regAfectados="Filas afectadas= ";
        int contador=0;
        String where="idcargaacademica='"+cargaAcademica.getIdcargaacademica()+"'";
        where=where+" AND codciclo='"+cargaAcademica.getCodciclo()+"'";
        where=where+" AND coddocente='"+cargaAcademica.getCoddocente()+"'";
        where=where+" AND codasignatura='"+cargaAcademica.getCodasignatura()+"'";
        where=where+" AND idtipodocenteciclo='"+cargaAcademica.getIdtipodocenteciclo()+"'";
        contador+=db.delete("cargaacademica", where, null);
        regAfectados+=contador;
        return regAfectados;

    }
    public String eliminar(Docente docente){
        String regAfectados="Filas afectadas= ";
        int contador=0;
        String where="coddocente='"+docente.getCoddocente()+"'";
        where=where+" AND nombredocente='"+docente.getNomdocente()+"'";
        where=where+" AND apellidodocente='"+docente.getApellidodocente()+"'";
        contador+=db.delete("docente", where, null);
        regAfectados+=contador;
        return regAfectados;
    }
    public String eliminar(Asignatura asignatura){
        String regAfectados="Filas afectadas= ";
        int contador=0;
        String where="codasignatura='"+asignatura.getCodasignatura()+"'";
        where=where+" AND nomasignatura='"+asignatura.getNomasignatura()+"'";
        where=where+" AND unidadesval="+asignatura.getUnidadesval();
        contador+=db.delete("asignatura", where, null);
        regAfectados+=contador;
        return regAfectados;
    }
    /*public String eliminar(TipoDocenteCiclo tipoDocenteCiclo){

    }*/
    public Ciclo consultarCiclo(String codciclo){
        String[] id = {codciclo};
        Cursor cursor = db.query("ciclo", camposCiclo, "codciclo = ?", id, null, null, null);

        if(cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setCodciclo(cursor.getString(0));
            ciclo.setFechadesde(cursor.getString(1));
            ciclo.setFechahasta(cursor.getString(2));
            return ciclo;
        }else{
            return null;
        }
    }
    public CargaAcademica consultarCargaAcademica(String idcargaacademica){
        String[] id = {idcargaacademica};
        Cursor cursor = db.query("cargaacademica", camposCargaAcademica, "idcargaacademica = ?",
                id, null, null, null);

        if(cursor.moveToFirst()){
            CargaAcademica cargaAcademica = new CargaAcademica();
            cargaAcademica.setIdcargaacademica(cursor.getString(0));
            cargaAcademica.setCodciclo(cursor.getString(1));
            cargaAcademica.setCoddocente(cursor.getString(2));
            cargaAcademica.setCodasignatura(cursor.getString(3));
            cargaAcademica.setIdtipodocenteciclo(cursor.getString(4));
            return cargaAcademica;
        }else{
            return null;
        }
    }
    public Docente consultarDocente(String coddocente){
        String[] id = {coddocente};
        Cursor cursor = db.query("docente", camposDocente, "coddocente = ?",
                id, null, null, null);

        if(cursor.moveToFirst()){
            Docente docente = new Docente();
            docente.setCoddocente(cursor.getString(0));
            docente.setNomdocente(cursor.getString(1));
            docente.setApellidodocente(cursor.getString(2));

            return docente;
        }else{
            return null;
        }
    }
    public Asignatura consultarAsignatura(String codasignatura){
        String[] id = {codasignatura};
        Cursor cursor = db.query("asignatura", camposAsignatura, "codasignatura = ?",
                id, null, null, null);

        if(cursor.moveToFirst()){
            Asignatura asignatura = new Asignatura();
            asignatura.setCodasignatura(cursor.getString(0));
            asignatura.setNomasignatura(cursor.getString(1));
            asignatura.setUnidadesval(cursor.getString(2));

            return asignatura;
        }else{
            return null;
        }
    }
    //################################ CRUD SOLICITUD DE IMPRESION ###################################

    public String insertar(SolImpresion solImpresion) {

        String regInsertados = "Registro Insertado Nº= ";

        long contador = 0;

        // 1 Verificar integridad referencial
        if (verificarIntegridadReferencial(solImpresion, 28)) {

            ContentValues sol = new ContentValues();

            sol.put("idsolicitudimpresion", solImpresion.getIdsolicitudimpresion());
            sol.put("coddocente", solImpresion.getIddocente());
            sol.put("iddocentedirector", solImpresion.getIddocentedirector());
            sol.put("cantidadexamenes", solImpresion.getCantidadexamenes());
            sol.put("hojasempaque", solImpresion.getHojasempaque());
            sol.put("estadoaprobacion", solImpresion.getEstadoaprobacion());

            contador = db.insert("solicitudimpresion", null, sol);
            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador;
            }
        }
        else
        {
            regInsertados= "Error al Insertar el registro, Registro sin referencias. Verificar inserción";
        }


        return regInsertados;
    }

    public String actualizar(SolImpresion solImpresion) {
        if (verificarIntegridadReferencial(solImpresion, 23)) {
            String[] id = {solImpresion.getIdsolicitudimpresion(), solImpresion.getIddocente(), solImpresion.getIddocentedirector()};

            ContentValues cv = new ContentValues();
            cv.put("cantidadexamenes", solImpresion.getCantidadexamenes());
            cv.put("hojasempaque", solImpresion.getHojasempaque());
            cv.put("estadoaprobacion", solImpresion.getEstadoaprobacion());
            db.update("solicitudimpresion", cv, "idsolicitudimpresion = ? AND coddocente = ? AND iddocentedirector = ?", id);
            return "Registro Actualizado Correctamente";
        } else {
            return "Registro no Existe";
        }
    }

    public String eliminarSolImpresion(SolImpresion solImpresion) {

        String regAfectados = "filas afectadas= ";
        int contador = 0;

        if (verificarIntegridadReferencial(solImpresion, 23)) {
            String where = "idsolicitudimpresion='" + solImpresion.getIdsolicitudimpresion() + "'";
            where = where + " AND coddocente='" + solImpresion.getIddocente() + "'";
            where = where + " AND iddocentedirector='" + solImpresion.getIddocentedirector() + "'";

            contador += db.delete("solicitudimpresion", where, null);
            regAfectados += contador;
            return regAfectados;
        }else{
            return "Registro no existe";
        }
    }

    public SolImpresion consultarSolImpresion(String idsolicitudimpresion, String iddocente, String iddocentedirector) {

        String[] id = {idsolicitudimpresion, iddocente, iddocentedirector};
        Cursor cursor = db.query("solicitudimpresion", null, "idsolicitudimpresion = ? AND coddocente = ? AND iddocentedirector = ?", id, null, null, null);

        if (cursor.moveToFirst()) {
            SolImpresion sol = new SolImpresion();
            sol.setIdsolicitudimpresion(cursor.getString(0));
            sol.setIddocente(cursor.getString(1));
            sol.setIddocentedirector(cursor.getString(2));
            sol.setCantidadexamenes(cursor.getInt(3));
            sol.setHojasempaque(cursor.getInt(4) > 0);
            sol.setEstadoaprobacion(cursor.getInt(5) > 0);
            return sol;
        } else {
            return null;
        }
    }

    //#################################### CRUD DOCENTE DIRECTOR ########################################

    public String insertarDocDirector(DocDirector docDirector) {


        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        if (verificarIntegridadReferencial(docDirector, 24)) {

            ContentValues dir = new ContentValues();
            dir.put("iddocentedirector", docDirector.getIddocentedirector());
            dir.put("idescuela", docDirector.getIdescuela());
            dir.put("nombredirector", docDirector.getNombredirector());
            dir.put("apellidodirector", docDirector.getApellidodirector());
            dir.put("correodirector", docDirector.getCorreodirector());
            dir.put("telefono", docDirector.getTelefono());

            contador = db.insert("docentedirector", null, dir);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado";
            } else {
                regInsertados = regInsertados + contador;
            }
            return regInsertados;
        } else {
            regInsertados = "Error al Insertar el registro, Registro sin referencias. Verificar inserción";
        }
        return regInsertados;
    }


    public String actualizarDocDirector (DocDirector docDirector){

        if (verificarIntegridadReferencial(docDirector, 25)) {

            String[] id = {docDirector.getIddocentedirector(), docDirector.getIdescuela()};
            ContentValues cv = new ContentValues();

            cv.put("nombredirector", docDirector.getNombredirector());
            cv.put("apellidodirector", docDirector.getApellidodirector());
            cv.put("telefono", docDirector.getTelefono());
            db.update("docentedirector", cv, "iddocentedirector = ? AND idescuela = ?", id);
            return "Registro Actualizado Correctamente";
        } else {
            return "Registro no Existe";
        }
    }

    public String eliminarDocDirector (DocDirector docDirector) {

        String regAfectados = "filas afectadas= ";
        int contador = 0;

        if (verificarIntegridadReferencial(docDirector, 25)) {

            if (verificarIntegridadReferencial(docDirector,29)) {
                contador+=db.delete("solicitudimpresion", "iddocentedirector='"+docDirector.getIddocentedirector()+"'", null);
            }

            String where = "iddocentedirector='" + docDirector.getIddocentedirector() + "'";
            where = where + " AND idescuela='" + docDirector.getIdescuela() + "'";

            contador += db.delete("docentedirector", where, null);
            regAfectados += contador;
            return regAfectados;
        }else{
            return "Registro con iddocentedirector " + docDirector.getIddocentedirector() + " no existe";
        }
    }

    public DocDirector consultarDocDirector (String iddocentedirector, String idescuela){
        String[] id = {iddocentedirector, idescuela};
        Cursor cursor = db.query("docentedirector", camposdocDirector, "iddocentedirector = ? AND idescuela = ?", id, null, null, null);

        if (cursor.moveToFirst()) {
            DocDirector dir = new DocDirector();
            dir.setIddocentedirector(cursor.getString(0));
            dir.setIdescuela(cursor.getString(1));
            dir.setNombredirector(cursor.getString(2));
            dir.setApellidodirector(cursor.getString(3));
            dir.setCorreodirector(cursor.getString(4));
            dir.setTelefono(cursor.getInt(5));
            return dir;
        } else {
            return null;
        }
    }


    //################################ CRUD ENCARGADO DE IMPRESIONES  ###################################
    public String insertarEncarImpresiones(EncarImpresiones encarImpresiones){

        String regInsertados="Registro Insertado Nº= ";
        long contador=0;

        ContentValues enc = new ContentValues();
        enc.put("idencargado", encarImpresiones.getIdencargado());
        enc.put("nombreencargado", encarImpresiones.getNombreencargado());
        enc.put("apellidoencargado", encarImpresiones.getApellidoencargado());

        contador=db.insert("encargadodeimpresiones", null, enc);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String actualizarEncarImpresiones(EncarImpresiones encarImpresiones){

        if(verificarIntegridadReferencial(encarImpresiones, 26)){
            String[] id = {encarImpresiones.getIdencargado()};

            ContentValues cv = new ContentValues();
            cv.put("nombreencargado", encarImpresiones.getNombreencargado());
            cv.put("apellidoencargado", encarImpresiones.getApellidoencargado());

            db.update("encargadodeimpresiones", cv, "idencargado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con id " + encarImpresiones.getIdencargado() + " no existe";
        }
    }

    public String eliminarEncarImpresiones(EncarImpresiones encarImpresiones){

        String regAfectados="filas afectadas= ";
        int contador=0;

        String where="idencargado='"+encarImpresiones.getIdencargado()+"'";

        contador+=db.delete("encargadodeimpresiones", where, null);
        regAfectados+=contador;
        return regAfectados;
    }

    public EncarImpresiones consultarEncarImpresiones(String idencargado){
        String[] id = {idencargado};
        Cursor cursor = db.query("encargadodeimpresiones", camposencarImpresiones, "idencargado = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            EncarImpresiones enc = new EncarImpresiones();
            enc.setIdencargado(cursor.getString(0));
            enc.setNombreencargado(cursor.getString(1));
            enc.setApellidoencargado(cursor.getString(2));

            return enc;
        }else {
            return null;
        }
    }


    //################################ CRUD ESTADO DE IMPRESION  ###################################
    public String insertarEstadoImpresion(EstadoImpresion estadoImpresion) {

        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        if (verificarIntegridadReferencial(estadoImpresion, 30)) {

            // 2 Verificar registro duplicado
            if(verificarIntegridadReferencial(estadoImpresion,27))
            {
                regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            }else {

                ContentValues est = new ContentValues();
                est.put("idestadoimpresion", estadoImpresion.getIdestadoimpresion());
                est.put("idsolicitudimpresion", estadoImpresion.getIdsolicitudimpresion());
                est.put("idencargado", estadoImpresion.getIdencargado());
                est.put("idmotivoimpresion", estadoImpresion.getIdmotivoimpresion());
                est.put("realizado", estadoImpresion.getRealizado());
                est.put("observaciones", estadoImpresion.getObservaciones());

                contador = db.insert("estadoimpresion", null, est);
            }
        }
        else {
            regInsertados= "Error al Insertar el registro, Registro sin referencias. Verificar inserción";
        }
        regInsertados=regInsertados+contador;
        return regInsertados;
    }

    public String actualizarEstadoImpresion(EstadoImpresion estadoImpresion) {

        if (verificarIntegridadReferencial(estadoImpresion, 27)) {
            String[] id = {estadoImpresion.getIdestadoimpresion(), estadoImpresion.getIdsolicitudimpresion(), estadoImpresion.getIdencargado(), estadoImpresion.getIdmotivoimpresion()};

            ContentValues cv = new ContentValues();
            cv.put("realizado", estadoImpresion.getRealizado());
            cv.put("observaciones", estadoImpresion.getObservaciones());

            db.update("estadoimpresion", cv, "idestadoimpresion = ? AND idsolicitudimpresion = ? AND idencargado = ? AND idmotivoimpresion = ?", id);
            return "Registro Actualizado Correctamente";
        } else {
            return "Registro no Existe";
        }
    }

    public String eliminarEstadoImpresion(EstadoImpresion estadoImpresion) {

        String regAfectados="filas afectadas= ";
        int contador=0;

        // 2 Verificar registro que exista
        if(verificarIntegridadReferencial(estadoImpresion, 27)) {
            String where = "idestadoimpresion='" + estadoImpresion.getIdestadoimpresion() + "'";
            where = where + " AND idsolicitudimpresion='" + estadoImpresion.getIdsolicitudimpresion() + "'";
            where = where + " AND idencargado='" + estadoImpresion.getIdencargado() + "'";
            where = where + " AND idmotivoimpresion='" + estadoImpresion.getIdmotivoimpresion() + "'";

            contador += db.delete("estadoimpresion", where, null);
            regAfectados += contador;
            return regAfectados;
        }else {
            return "Registro no Existe";
        }

    }

    public EstadoImpresion consultarEstadoImpresion(String idestadoimpresion, String idsolicitudimpresion, String idencargado, String idmotivoimpresion) {

        String[] id = {idestadoimpresion, idsolicitudimpresion, idencargado, idmotivoimpresion};
        Cursor cursor = db.query("estadoimpresion", camposestadoImpresion, "idestadoimpresion = ? AND idsolicitudimpresion = ? AND idencargado = ? AND idmotivoimpresion = ?", id, null, null, null);

        if (cursor.moveToFirst()) {

            EstadoImpresion est = new EstadoImpresion();

            est.setIdestadoimpresion(cursor.getString(0));
            est.setIdsolicitudimpresion(cursor.getString(1));
            est.setIdencargado(cursor.getString(2));
            est.setIdmotivoimpresion(cursor.getString(3));
            est.setRealizado(cursor.getInt(4) > 0);
            est.setObservaciones(cursor.getString(5));

            return est;
        } else {
            return null;
        }

    }










    //################################ ESCUELA #######################################
    public String insertarEscuela(Escuela escuela) {

        String regInsertados = "Registro Insertado Nº= ";

        long contador = 0;

        ContentValues esc = new ContentValues();

        esc.put("idescuela", escuela.getIdescuela());
        esc.put("nombreescuela", escuela.getNombreescuela());
        esc.put("facultad", escuela.getFacultad());

        contador = db.insert("escuela", null, esc);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    //################################ MOTIVO IMPRESION #######################################

    public String insertarMotivoImpresion(MotivoImpresion motivoImpresion) {

        String regInsertados = "Registro Insertado Nº= ";

        long contador = 0;

        ContentValues esc = new ContentValues();

        esc.put("idmotivoimpresion", motivoImpresion.getIdmotivoimpresion());
        esc.put("motivo", motivoImpresion.getMotivo());


        contador = db.insert("motivoimpresion", null, esc);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }


    //################################ DOCENTE #######################################
    public String insertarDocente(Docente docente) {

        String regInsertados = "Registro Insertado Nº= ";

        long contador = 0;

        ContentValues esc = new ContentValues();

        esc.put("coddocente", docente.getCoddocente());
        esc.put("nombredocente", docente.getNomdocente());
        esc.put("apellidodocente", docente.getApellidodocente());

        contador = db.insert("docente", null, esc);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }


    public String eliminar(SolicitudRevision solRev){//********************************************************************************************************************
        String regAfectados = "Solicitud Eliminada, Filas afectadas = ";
        int contador = 0;

        if(verificarIntegridadReferencial(solRev, 34)){

            String nosepuede = "Error, existen registros de esta Solicitud en otras tablas.";
            return nosepuede;
        }

        String where = "carnet = '"+solRev.getCarnet()+"'";
        where = where + " AND codtipogrupo = '"+solRev.getCodtipogrupo()+"'";
        where = where + " AND codtiporevision = '"+solRev.getCodtiporevision()+"'";
        where = where + " AND codasignatura = '"+solRev.getCodasignatura()+"'";
        where = where + " AND codciclo = '"+solRev.getCodciclo()+"'";
        where = where + " AND codtipoeval = '"+solRev.getCodtipoeval()+"'";
        where = where + " AND numeroeval = '"+solRev.getNumeroeval()+"'";
        contador += db.delete("solicitudrevision", where, null);

        if (contador==0){
            return  "Esta Solicitud de Revision No Existe o Campos Incompletos";
        }

        regAfectados += contador;
        return regAfectados;
    }


    public String actualizar(SolicitudRevision solRev) {

        if (verificarIntegridadReferencial(solRev, 35)) {

            String[] id = {solRev.getCarnet(), solRev.getCodtipogrupo(), solRev.getCodtiporevision(), solRev.getCodasignatura(), solRev.getCodciclo(), solRev.getCodtipoeval(), String.valueOf(solRev.getNumeroeval())};
            ContentValues cv = new ContentValues();
            cv.put("notaantesrevision", solRev.getNotaantesrevision());
            cv.put("numerogrupo", solRev.getNumerogrupo());
            cv.put("fechasolicitudrevision", solRev.getFechasolicitudrevision());
            cv.put("motivorevision", solRev.getMotivorevision());
            db.update("solicitudrevision", cv, "carnet = ? AND codtipogrupo = ? AND codtiporevision = ? AND codasignatura = ?  AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", id);
            return "Registro Actualizado correctamente";

        } else {
            return "Registro no Existe";
        }

    }
    public SolicitudRevision consultarSolicitudRevision(String carnet, String codtipogrupo, String codtiporevision, String codtipoeval, String codasignatura, String numeroeval, String codciclo) {

        String[] id = {carnet, codtipogrupo, codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval};

        Cursor cursor = db.query("solicitudrevision", null, "carnet=? AND codtipogrupo=? AND codtiporevision=? AND codasignatura=? AND codciclo=? AND codtipoeval =? AND numeroeval =?", id, null, null, null);

        if (cursor.moveToFirst()) {

            SolicitudRevision solRev = new SolicitudRevision();

            solRev.setFechasolicitudrevision(cursor.getString(0));
            solRev.setNotaantesrevision(cursor.getFloat(1));
            solRev.setCodtipogrupo(cursor.getString(2));
            solRev.setNumerogrupo(cursor.getInt(3));
            solRev.setMotivorevision(cursor.getString(4));
            solRev.setCarnet(cursor.getString(5));
            solRev.setCodasignatura(cursor.getString(6));
            solRev.setCodciclo(cursor.getString(7));
            solRev.setCodtipoeval(cursor.getString(8));
            solRev.setNumeroeval(cursor.getInt(9));
            solRev.setCodtiporevision(cursor.getString(10));
            return solRev;

        } else {

            return null;
        }
    }


    public boolean verificarIntegridadReferencial(Object dato, int relacion) throws SQLException {
        switch (relacion) {
            case 1: {
                //Verificar que al Insertar Evaluacion exista el TipoEvaluacion, Asignatura y Ciclo
                Evaluacion evaluacion = (Evaluacion) dato;
                String[] id1 = {evaluacion.getCodAsignatura()};
                String[] id2 = {evaluacion.getCodCiclo()};
                String[] id3 = {evaluacion.getCodTipoEval()};
                abrir();

                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                Cursor cursor2 = db.query("ciclo", null, "codciclo = ?", id2, null, null, null);
                Cursor cursor3 = db.query("tipoevaluacion", null, "codtipoeval = ?", id3, null, null, null);

                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst()) {
                    //Se encontraron los datos
                    return true;
                } else return false;
            }
            case 2: {
                //verificar que al Modificar Evaluacion exista el TipoEvaluacion, Asignatura, Ciclo y Numero de Evaluacion
                Evaluacion evaluacion = (Evaluacion) dato;
                String[] ids = {evaluacion.getCodAsignatura(), evaluacion.getCodCiclo(), evaluacion.getCodTipoEval(), String.valueOf(evaluacion.getNumeroEvaluacion())};
                abrir();

                Cursor c = db.query("evaluacion", null, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ?", ids, null, null, null);

                if (c.moveToFirst()) {
                    return true;
                } else return false;
            }
            case 3: {
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

                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst() && cursor6.moveToFirst() && cursor7.moveToFirst()) {
                    return true;
                } else return false;
            }
            case 4: {
                //Verificar que al modificar el PeriodoInscripcionRevision exista Asignatura, Ciclo, TipoEvaluacion, NumeroEvaluacion y TipoRevision
                PeriodoInscripcionRevision perInscRev = (PeriodoInscripcionRevision) dato;
                String[] ids = {perInscRev.getCodAsignatura(), perInscRev.getCodCiclo(), perInscRev.getCodTipoEval(), String.valueOf(perInscRev.getNumeroEval()), perInscRev.getTipoRevision()};
                abrir();

                Cursor c = db.query("periodoinscripcionrevision", null, "codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ? AND codtiporevision = ?", ids, null, null, null);

                if (c.moveToFirst()) {
                    return true;
                } else return false;

            }
            case 5: {
                SolicitudDiferido solicitudDiferido = (SolicitudDiferido) dato;
                String[] id1 = {solicitudDiferido.getCodMateria()};
                String[] id3 = {solicitudDiferido.getCarnet()};
                String[] id4 = {solicitudDiferido.getMotivo()};
                String[] id5 = {solicitudDiferido.getCodMateria(), solicitudDiferido.getTipoEva(), solicitudDiferido.getCiclo(), String.valueOf(solicitudDiferido.getNumeroEval())};
                abrir();
                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                Cursor cursor3 = db.query("Estudiante", null, "carnet = ?", id3, null, null, null);
                Cursor cursor4 = db.query("MotivoDiferido", null, "nombreMotivo = ?", id4, null, null, null);
                Cursor cursor5 = db.query("evaluacion", null, "codasignatura = ? AND codtipoeval = ? AND codciclo = ? AND numeroeval = ?", id5, null, null, null);
                if (cursor1.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst()) {
                    return true;
                } else return false;
            }
            case 6: {
                Estudiante estudiante = (Estudiante) dato;
                String[] id = {estudiante.getCarnet()};
                abrir();
                Cursor cursor = db.query("Estudiante", null, "carnet = ?", id, null, null, null);
                if (cursor.moveToFirst()) {
                    return true;
                } else return false;
            }
            case 7: {
                DetalleDiferidoRepetido detalle = (DetalleDiferidoRepetido) dato;
                String[] id1 = {detalle.getIdLocal()};
                String[] id2 = {detalle.getIdAsignatura()};
                String[] id3 = {detalle.getIdDocente()};
                String[] id4 = {detalle.getIdTipoDifRep()};
                String[] id5 = {detalle.getIdTipoEval(), String.valueOf(detalle.getNumEval()), detalle.getIdAsignatura()};


                Cursor cursor1 = db.query("local", null, "codlocal = ?", id1, null, null, null);
                Cursor cursor2 = db.query("asignatura", null, "codasignatura = ?", id2, null, null, null);
                Cursor cursor3 = db.query("docente", null, "coddocente = ?", id3, null, null, null);
                Cursor cursor4 = db.query("TipoDiferidoRepetido", null, "idTipoDiferidoRepetido = ?", id4, null, null, null);
                Cursor cursor5 = db.query("evaluacion", null, "codtipoeval = ? AND numeroeval = ? AND codasignatura = ?", id5, null, null, null);

                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 8: {
                DetalleEstudianteDiferido detalle = (DetalleEstudianteDiferido) dato;
                String[] id1 = {detalle.getCarnet()};
                String[] id2 = {detalle.getIdDetalleDifeRep()};

                Cursor cursor1 = db.query("Estudiante", null, "carnet = ?", id1, null, null, null);
                Cursor cursor2 = db.query("DetalleDiferidoRepetido", null, "idDetalleDiferidoRepetido = ?", id2, null, null, null);

                if (cursor1.moveToFirst() && cursor2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 9: {
                DetalleEstudianteRepetido detalle = (DetalleEstudianteRepetido) dato;
                String[] id1 = {detalle.getCarnet()};
                String[] id2 = {detalle.getIdDetalleDifRep()};

                Cursor cursor1 = db.query("Estudiante", null, "carnet = ?", id1, null, null, null);
                Cursor cursor2 = db.query("DetalleDiferidoRepetido", null, "idDetalleDiferidoRepetido = ?", id2, null, null, null);

                if (cursor1.moveToFirst() && cursor2.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 10: {
                //Verificar que al insertar  Primera Revision exista SolicitudRevision, Docente y Motivo Cambio Nota
                PrimeraRevision primRev = (PrimeraRevision) dato;
                String[] id1 = {primRev.getCarnet(), primRev.getCodtiporevision(), primRev.getCodasignatura(), primRev.getCodciclo(), primRev.getCodtipoeval(), primRev.getCodtipogrupo(), String.valueOf(primRev.getNumeroeval())};
                String[] id2 = {primRev.getCoddocente()};
                String[] id3 = {primRev.getMotivoCambioNota()};
                abrir();

                Cursor cursor1 = db.query("solicitudrevision", null, "carnet = ? AND codtiporevision = ? AND codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND codtipogrupo = ? AND numeroeval = ? ", id1, null, null, null);
                Cursor cursor2 = db.query("docente", null, "coddocente = ?", id2, null, null, null);
                Cursor cursor3 = db.query("motivocambionota", null, "codmotivocambionota = ?", id3, null, null, null);

                if(cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst()){
                    return true;
                }
            }
            case 11: {
                //Verificar que al modificar la PrimerRevision exista la solicitud de revision Solicitud Revision
                PrimeraRevision primRev = (PrimeraRevision) dato;
                String[] ids = {primRev.getCoddocente() ,primRev.getCarnet(), primRev.getCodasignatura(), primRev.getCodtiporevision(), primRev.getCodciclo(), primRev.getCodtipoeval(), primRev.getCodtipogrupo(), String.valueOf(primRev.getNumeroeval())};
                abrir();

                Cursor c  = db.query("primerrevision", null, "coddocente = ? AND carnet = ? AND codasignatura = ? AND codtiporevision = ? AND codciclo = ? AND codtipoeval = ? AND codtipogrupo = ? AND numeroeval = ?", ids, null, null, null);
                if(c.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 12: {
                //Verificar que al actualizar el local exista dich local
                Local loc = (Local) dato;
                String[] ids = {loc.getCodlocal()};
                abrir();

                Cursor c = db.query("local", null, "codlocal = ?", ids, null, null, null);
                if (c.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 13: {
                //verificar que al insertar carga academica existan las fk
                CargaAcademica cargaAcademica = (CargaAcademica) dato;

                String[] id1 = {cargaAcademica.getCodciclo()};
                String[] id2 = {cargaAcademica.getCoddocente()};
                String[] id3 = {cargaAcademica.getCodasignatura()};
                String[] id4 = {cargaAcademica.getIdtipodocenteciclo()};

                Cursor cursor1 = db.query("ciclo", null, "codciclo=?", id1,
                        null, null, null);
                Cursor cursor2 = db.query("docente", null, "coddocente=?", id2,
                        null, null, null);
                Cursor cursor3 = db.query("asignatura", null, "codasignatura=?", id3,
                        null, null, null);
                Cursor cursor4 = db.query("tipodocenteciclo", null, "idtipodocenteciclo=?", id4,
                        null, null, null);
                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 14: {
                //verificar que al modificar carga academica exista
                CargaAcademica cargaAcademica = (CargaAcademica) dato;
                String[] id1 = {cargaAcademica.getIdcargaacademica()};
                String[] id2 = {cargaAcademica.getCodciclo()};
                String[] id3 = {cargaAcademica.getCoddocente()};
                String[] id4 = {cargaAcademica.getCodasignatura()};
                String[] id5 = {cargaAcademica.getIdtipodocenteciclo()};
                Cursor  cursor1 = db.query("cargaacademica", null, "idcargaacademica=?", id1,
                        null,null, null);
                Cursor cursor2 = db.query("ciclo", null, "codciclo=?", id2,
                        null, null, null);
                Cursor cursor3 = db.query("docente", null, "coddocente=?", id3,
                        null, null, null);
                Cursor cursor4 = db.query("asignatura", null, "codasignatura=?", id4,
                        null, null, null);
                Cursor cursor5 = db.query("tipodocenteciclo", null, "idtipodocenteciclo=?", id5,
                        null, null, null);
                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 15: {
                Asignatura asignatura = (Asignatura) dato;
                Cursor c = db.query(true, "cargaacademica", new String[]{"codasignatura"},
                        "codasignatura='" + asignatura.getCodasignatura() + "'", null, null, null, null, null);
                if (c.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 16: {
                Ciclo ciclo = (Ciclo) dato;
                Cursor cic = db.query(true, "cargaacademica", new String[]{"codciclo"},
                        "codciclo='" + ciclo.getCodciclo() + "'", null, null, null, null, null);
                if (cic.moveToFirst())
                    return true;
                else
                    return false;
            }
            case 17: {
                Docente docente = (Docente) dato;
                Cursor doc = db.query(true, "cargaacademica", new String[]{"coddocente"},
                        "coddocente='" + docente.getCoddocente() + "'", null, null, null, null, null);
                if (doc.moveToFirst())
                    return true;
                else
                    return false;

            }
            case 18: {
                TipoDocenteCiclo tipoDocenteCiclo = (TipoDocenteCiclo) dato;
                Cursor tip = db.query(true, "cargaacademica", new String[]{"idtipodocenteciclo"},
                        "idtipodocenteciclo='" + tipoDocenteCiclo.getIdtipodocenteciclo() + "'", null, null, null, null, null);
                if (tip.moveToFirst())
                    return true;
                else
                    return false;

            }
            case 19:
            {
                //verificar que exista asignatura
                Asignatura asignatura2=(Asignatura)dato;
                String[] id={asignatura2.getCodasignatura()};
                abrir();
                Cursor as=db.query("asignatura", null, "codasignatura=?",id,
                        null, null, null);
                if(as.moveToFirst()){
                    return true;
                }
                return  false;

            }
            case 20:
            {
                //verificar que exista ciclo
                Ciclo ciclo2=(Ciclo) dato;
                String[] ci={ciclo2.getCodciclo()};
                abrir();
                Cursor as=db.query("ciclo", null, "codciclo=?",ci,
                        null, null, null);
                if(as.moveToFirst()){
                    return true;
                }
                return  false;

            }
            case 21: {
                //verificar que exista docente

                Docente docente2 = (Docente) dato;
                String[] dc = {docente2.getCoddocente()};
                abrir();
                Cursor as = db.query("docente", null, "coddocente=?", dc,
                        null, null, null);
                if (as.moveToFirst()) {
                    return true;
                }
                return false;
            }
            case 22:
            {
                //verificar que exista tipo docente ciclo
                TipoDocenteCiclo tipoDocenteCiclo2=(TipoDocenteCiclo) dato;
                String[] td={tipoDocenteCiclo2.getIdtipodocenteciclo()};
                abrir();
                Cursor as=db.query("tipodocenteciclo", null, "idtipodocenteciclo=?",td,
                        null, null, null);
                if(as.moveToFirst()){
                    return true;
                }
                return  false;
            }

            case 23: {
                //verificar que al modificar Solicitud existan los id de la solicitud
                SolImpresion sol1 = (SolImpresion) dato;
                String[] ids = {sol1.getIdsolicitudimpresion(), sol1.getIddocente(), sol1.getIddocentedirector()};
                abrir();
                Cursor c = db.query("solicitudimpresion", null, "idsolicitudimpresion = ? AND coddocente = ? AND iddocentedirector = ?", ids, null, null, null);
                if (c.moveToFirst()) {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }

            case 24: {
                //verificar que al insertar docentedirector exista idescuela de la escuela
                DocDirector dir = (DocDirector) dato;
                String[] id1 = {dir.getIdescuela()};

                //abrir();
                Cursor cursor1 = db.query("escuela", null, "idescuela = ?", id1, null, null, null);
                if(cursor1.moveToFirst()){
                    //Se encontraron datos
                    return true;
                }
                return false;
            }

            case 25: {
                //verificar que al modificar Docente Director existan los id
                DocDirector dir = (DocDirector) dato;
                String[] ids = {dir.getIddocentedirector(), dir.getIdescuela()};
                abrir();
                Cursor c = db.query("docentedirector", null, "iddocentedirector = ? AND idescuela = ?", ids, null, null, null);
                if (c.moveToFirst()) {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }

            case 26:
            {
                //verificar que exista EncargadoImpresiones
                EncarImpresiones enc2 = (EncarImpresiones) dato;
                String[] id = {enc2.getIdencargado()};
                abrir();
                Cursor c2 = db.query("encargadodeimpresiones", null, "idencargado = ?", id, null, null, null);
                if(c2.moveToFirst()){
                    //Se encontro Encargado
                    return true;
                }
                return false;
            }

            case 27: {
                //verificar que al modificar Estado de impresion existan los id de el estado
                EstadoImpresion est1 = (EstadoImpresion) dato;
                String[] ids = {est1.getIdestadoimpresion(), est1.getIdsolicitudimpresion(), est1.getIdencargado(), est1.getIdmotivoimpresion()};
                abrir();
                Cursor c = db.query("estadoimpresion", null, "idestadoimpresion = ? AND idsolicitudimpresion = ? AND idencargado = ? AND idmotivoimpresion = ?", ids, null, null, null);
                if (c.moveToFirst()) {
                    //Se encontraron datos
                    return true;
                }
                return false;
            }

            case 28:
            {
                //verificar que en la solicitud exista iddocente y iddocentedirector
                SolImpresion sol = (SolImpresion) dato;
                String[] id1 = {sol.getIddocente()};
                String[] id2 = {sol.getIddocentedirector()};

                abrir();
                Cursor cursor1 = db.query("docente", null, "coddocente = ?", id1, null, null, null);
                Cursor cursor2 = db.query("docentedirector", null, "iddocentedirector = ?", id2, null, null, null);
                if(cursor2.moveToFirst() && cursor1.moveToFirst()){
                    //Se encontraron datos ||
                    return true;
                }
                return false;
            }

            case 29:
            {//Si encuentra iddirector en la tabla solicitudimpresion elimina los registros
                DocDirector docDirector = (DocDirector) dato;
                Cursor cmat=db.query(true, "solicitudimpresion", new String[] {
                        "iddocentedirector" }, "iddocentedirector='"+docDirector.getIddocentedirector()+"'",null, null, null, null, null);
                if(cmat.moveToFirst())
                    return true;
                else
                    return false;
            }

            case 30:
            {
                //verificar que al insertar estado existan los id
                EstadoImpresion estadoImpresion = (EstadoImpresion) dato;
                String[] id1 = {estadoImpresion.getIdmotivoimpresion()};
                String[] id2 = {estadoImpresion.getIdsolicitudimpresion()};
                String[] id3 = {estadoImpresion.getIdencargado()};

                abrir();
                Cursor cursor1 = db.query("motivoimpresion", null, "idmotivoimpresion = ?", id1, null, null, null);
                Cursor cursor2 = db.query("solicitudimpresion", null, "idsolicitudimpresion = ?", id2, null, null, null);
                Cursor cursor3 = db.query("encargadodeimpresiones", null, "idencargado = ?", id3, null, null, null);
                if(cursor3.moveToFirst() && cursor2.moveToFirst() && cursor1.moveToFirst()){
                    //Se encontraron datos ||
                    return true;
                }
                return false;
            }
            case 31:{
                //Verificar que no haya registro de local en otras tablas antes de eliminar
                Local local = (Local) dato;
                Cursor c = db.query(true, "periodoinscripcionrevision", new String[]{"codlocal"}, "codlocal = '"+local.getCodlocal()+"'", null, null, null, null, null);
                if(c.moveToFirst()){
                    return true;
                }else{
                    return false;
                }
            }

            case 32:{
                //Verificar que no haya registro de local en otras tablas antes de eliminar
                Evaluacion evaluacion = (Evaluacion) dato;
                Cursor c = db.query(true, "periodoinscripcionrevision", new String[]{"codasignatura", "codciclo", "codtipoeval", "numeroeval"}, "codasignatura = '"+evaluacion.getCodAsignatura()+"' AND codciclo = '"+evaluacion.getCodCiclo()+"' AND codtipoeval = '"+evaluacion.getCodTipoEval()+"' AND numeroeval = '"+evaluacion.getNumeroEvaluacion()+"'", null, null, null, null, null);
                if(c.moveToFirst()){
                    return true;
                }else{
                    return false;
                }
            }
            case 33: {
                //Verificar que al Insertar SolicitudRevision exista Asignatura, Ciclo, TipoEvaluacion, NumeroEvaluacion, Estudiante, TipoGrupo, TipoRevision
                SolicitudRevision solicitud = (SolicitudRevision) dato;
                String[] id1 = {solicitud.getCodasignatura()};
                String[] id2 = {solicitud.getCodciclo()};
                String[] id3 = {solicitud.getCodtipoeval()};
                String[] id4 = {String.valueOf(solicitud.getNumeroeval())};
                String[] id5 = {solicitud.getCodtipogrupo()};
                String[] id6 = {solicitud.getCarnet()};
                String[] id7 = {solicitud.getCodtiporevision()};
                abrir();

                Cursor cursor1 = db.query("asignatura", null, "codasignatura = ?", id1, null, null, null);
                Cursor cursor2 = db.query("ciclo", null, "codciclo = ?", id2, null, null, null);
                Cursor cursor3 = db.query("tipoevaluacion", null, "codtipoeval = ?", id3, null, null, null);
                Cursor cursor4 = db.query("evaluacion", null, "numeroeval = ?", id4, null, null, null);
                Cursor cursor5 = db.query("tipogrupo", null, "codtipogrupo = ?", id5, null, null, null);
                Cursor cursor6 = db.query("Estudiante", null, "carnet = ?", id6, null, null, null);
                Cursor cursor7 = db.query("tiporevision", null, "codtiporevision = ?", id7, null, null, null);

                if (cursor1.moveToFirst() && cursor2.moveToFirst() && cursor3.moveToFirst() && cursor4.moveToFirst() && cursor5.moveToFirst() && cursor6.moveToFirst() && cursor7.moveToFirst()) {
                    return true;
                }

            }
            case 34: {

                //Verificar que no haya registro de solicitud revision en otras tablas antes de eliminar
                SolicitudRevision solRev = (SolicitudRevision) dato;

                //carnet, codtipogrupo, codtiporevision, codasignatura, codciclo, codtipoeval, numeroeval

                String[] idrecu = new String[]{"carnet","codtipogrupo","codtiporevision","codasignatura","codciclo","codtipoeval","numeroeval"};
                String[] parametros = {solRev.getCarnet(), solRev.getCodtipogrupo(), solRev.getCodtiporevision(), solRev.getCodasignatura(), solRev.getCodciclo(), solRev.getCodtipoeval(), String.valueOf(solRev.getNumeroeval())};
                String where = "carnet =? AND codtipogrupo =?  AND codtiporevision =? AND codasignatura =? AND codciclo =? AND codtipoeval =? AND numeroeval =?";

                Cursor c1 = db.query(true, "primerrevision", idrecu, where, parametros, null, null, null, null);
                Cursor c2 = db.query(true, "segundarevision", idrecu, where, parametros, null, null, null, null);

                if(c1.moveToFirst() || c2.moveToFirst()){
                    return true;
                }
                else {
                    return false;
                }


            }
            case 35: {
                //Verificar que al modificar una SolicitudRevision exista el registro
                SolicitudRevision solRev = (SolicitudRevision) dato;
                String[] idrecu = new String[]{"carnet","codtipogrupo","codtiporevision","codasignatura","codciclo","codtipoeval","numeroeval"};
                String where = "carnet =? AND codtipogrupo =?  AND codtiporevision =? AND codasignatura =? AND codciclo =? AND codtipoeval =? AND numeroeval =?";
                String[] ids = {solRev.getCarnet(), solRev.getCodtipogrupo(), solRev.getCodtiporevision(), solRev.getCodasignatura(), solRev.getCodciclo(), solRev.getCodtipoeval(), String.valueOf(solRev.getNumeroeval())};
                abrir();

                Cursor c = db.query("solicitudrevision", idrecu, where, ids, null, null, null);

                if (c.moveToFirst()) {
                    return true;
                } else return false;

            }

            case 36:{
                //Verificar que al insertar  Segunda Revision exista SolicitudRevision, Primera Revision y Motivo Cambio Nota
                SegundaRevision segRev = (SegundaRevision) dato;
                String[] id1 = {segRev.getCarnet(), segRev.getCodtiporevision(), segRev.getCodasignatura(), segRev.getCodciclo(), segRev.getCodtipoeval(), segRev.getCodtipogrupo(), String.valueOf(segRev.getNumeroeval())};
                String[] id2 = {segRev.getCoddocente(), segRev.getCarnet(), "PR", segRev.getCodasignatura(), segRev.getCodciclo(), segRev.getCodtipoeval(), String.valueOf(segRev.getNumeroeval()), segRev.getCodtipogrupo()};
                String[] id3 = {segRev.getMotivoCambioNota()};
                abrir();

                Cursor cursor1 = db.query("solicitudrevision", null, "carnet = ? AND codtiporevision = ? AND codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND codtipogrupo = ? AND numeroeval = ? ", id1, null, null, null);
                Cursor cursor2 = db.query("primerrevision", null, "coddocente = ? AND carnet = ? AND codtiporevision = ? AND codasignatura = ? AND codciclo = ? AND codtipoeval = ? AND numeroeval = ? AND codtipogrupo = ?", id2, null, null, null);
                Cursor cursor3 = db.query("motivocambionota", null, "codmotivocambionota = ?", id3, null, null, null);

                if(cursor2.moveToFirst() && cursor3.moveToFirst()){
                    return true;
                }

                return false;
            }

            case 37:{
                //Verificar que al modificar la SegundaRevision exista la Primer Revision
                SegundaRevision segRev = (SegundaRevision) dato;

                String[] ids = {segRev.getCodtiporevision(), segRev.getCodasignatura(), segRev.getCodtipoeval(), String.valueOf(segRev.getNumeroeval()), segRev.getCodciclo(), segRev.getCarnet()};
                abrir();

                Cursor c  = db.query("segundarevision", null, "codtiporevision = ? AND codasignatura = ? AND codtipoeval = ? AND numeroeval = ? AND codciclo = ? AND carnet = ?", ids, null, null, null);
                if(c.moveToFirst()){
                    return true;
                }
                return false;
            }

            case 38:{
                //Verificar que no haya registro de Primer Revision en otras tablas antes de eliminar
                PrimeraRevision primRev = (PrimeraRevision) dato;
                Cursor c = db.query(true, "segundarevision", new String[]{"coddocente", "carnet", "codtiporevision", "codasignatura", "codciclo", "codtipoeval", "numeroeval", "codtipogrupo"}, "coddocente = '"+primRev.getCoddocente()+"' AND carnet = '"+primRev.getCarnet()+"' AND codtiporevision = '"+primRev.getCodtiporevision()+"' AND codasignatura = '"+primRev.getCodasignatura()+"' AND codciclo = '"+primRev.getCodciclo()+"' AND codtipoeval = '"+primRev.getCodtipoeval()+"' AND numeroeval = "+primRev.getNumeroeval()+" AND codtipogrupo = '"+primRev.getCodtipogrupo()+"'", null, null, null, null, null);
                if(c.moveToFirst()){
                    return true;
                }else{
                    return false;
                }
            }
            default:
                return false;
        }
    }

    public String LlenarDatos() {
        final String[] usersId = {"cm17048@ues.edu.sv", "rm17039@ues.edu.sv", "ag17023@ues.edu.sv", "mm14030@ues.edu.sv", "pr17017@ues.edu.sv"};
        final String[] names = {"Victor", "Shaky", "Daniel", "Cristian", "Roberto"};
        final String[] userPass = {"0123456789", "0123456789", "0123456789", "0123456789", "0123456789"};
        final String[] motivos = {"Salud", "Trabajo", "Interferencia", "Viaje", "Duelo", "Otro"};
        final String[] tipoDifRep = {"Diferido", "Repetido"};
        final String[] nombreTipoDifRep = {"Evaluacion diferida", "Evaluacion repetida"};
        final String[] TTEcodtipoeval = {"EP", "ED", "EL"};
        final String[] TTEnomtipoeval = {"Examen Parcial", "Examen Discusion", "Examen Laboratorio"};

        final String[] TTGcodtipoGrupo = {"GT", "GD", "GL"};
        final String[] TTGnomtipogrupo = {"Grupo Teorico", "Grupo de Discusion", "Grupo de Laboratorio"};

        final String[] TRcodrevision = {"PR", "SR"};
        final String[] TRnomrevision = {"Primera Revision", "Segunda Revision"};


        final String[] TAcodasignatura = {"MAT115", "FIR115"};
        final String[] TAnomasignatura = {"Matematicas I", "Fisicas I"};
        final String[] TAunidadesval = {"4", "4"};

        final String[] TCcodciclo = {"12020", "22020"};
        final String[] TCfechadesde = {"2020-02-20", "2020-10-08"};
        final String[] TCfechahasta = {"2020-06-20", "2020-12-20"};

        final String[] TDcoddocente = {"CV00001", "MN00002"};
        final String[] TDnombredocente = {"Rudy Wilfredo", "Boris Alexander"};
        final String[] TDapellidodocente = {"Chicas Villegas", "Montano Navarrete"};

        final String[] TEcarnet = {"CM17048","RM17039","AG17023","MM14030","PR17017"};
        final String[] TEnombreestudiante = {"Victor","Shaky","Daniel","Cristian","Roberto"};
        final String[] TEapellidoestudiante = {"Lopes","Renderos","Amaya","Melendez","Paz"};
        final String[] TEcarrera = {"Ingenieria de Sistemas Informaticos", "Ingenieria de Sistemas Informaticos", "Ingenieria de Sistemas Informaticos", "Ingenieria de Sistemas Informaticos", "Ingenieria de Sistemas Informaticos"};


        final String[] TMCNcodmotivocambnota = {"ERRSUM", "ERRELPR", "ERRCAL"};
        final String[] TMCNmotivo = {"Error de suma", "Error de elaboracion de preguntas", "Error de calificacion"};

        final Date[] TSoRfechasolicitudrev = {Date.valueOf("2020-05-10"), Date.valueOf("2020-05-15"), Date.valueOf("2020-05-15")};
        final String[] TSoRmotivorevision = {"Mal calculo en la suma", "Pregunta incoherente", "Mal calculo en la suma",};
        final String[] TSoRcarnet = {"RM17039", "PR17017", "RM17039"};
        final String[] TSoRcodasignatura = {"MAT115", "FIR115", "MAT115"};
        final String[] TSoRcodcilo = {"12020", "22020", "12020"};
        final String[] TSoRcodtipoeval = {"EP", "ED", "EP"};
        final String[] TSoRcodtiporev = {"PR", "PR", "SR"};
        final float[] TSoRnotaantesrev = {Float.parseFloat("7.5"), Float.parseFloat("6.0"), Float.parseFloat("8.0")};
        final String[] TSoRcodtipogrupo = {"GT", "GL", "GT"};
        final int[] TSoRnumerogrupo = {Integer.parseInt("1"), Integer.parseInt("3"),  Integer.parseInt("1")};
        final int[] TSoRcodnumeroeval = {Integer.parseInt("1"), Integer.parseInt("2"), Integer.parseInt("1")};

        final String[] VTidtipodocenteciclo = {"00001", "00002", "00003", "00004" };
        final String[] VTnomtipodocenteciclo = {"Docente teórico", "Docente discusión", "Docente Laboratorio", "Tutot"};

        final String[] VDiddocente = {"ABCD","BCDE","WXYZ", "500"};
        final String[] VDnombredocente = {"Cesar","Carmeline","Jose", "Mauricio"};
        final String[] VDapellidodocente = {"Izquierdo","Gochez","Bonilla", "Zepeda"};

        final String[] VEidescuela= {"A01", "B02", "C03", "D04"};
        final String[] VEnombreescuela={"Sistemas", "Electrica", "Civil", "Mecanica"};
        final String[] VEfacultad={"FIA", "FIA", "FIA", "FIA"};

        final String[] VDDiddocentedirector = {"0123458","0236584","1711471","7185847"};
        final String[] VDDidescuela = {"A01","B02","C03", "D04"};
        final String[] VDDnombredirector = {"Juan","Pedro","Jesus","Teresa"};
        final String[] VDDapellidodirector = {"Margarito","Martinez","Zelaya","Villavicencio"};
        final String[] VDDcorreodirector = {"a@gmail.com","b@gmail.com","c@gmail.com","d@gmail.com"};
        final Integer[] VDDtelefono = {78963214,22589632, 24569875, 65412398};

        final String[] VSIidsolicitud = {"J223", "J224", "J225", "J226"};
        final String[] VSIiddocente = {"ABCD", "BCDE", "ABCD", "500"};
        final String[] VSIiddirector = {"0123458", "0236584", "1711471", "7185847"};
        final Integer[] VSICantidadExa = {200, 100, 50, 500};
        final Boolean[] VSIhojasEmpaque = {true, false, false, false};
        final Boolean[] VSIestadoApro = {true, true, false, false};

        final String[] VEIidencargado= {"XX1", "XX2", "XX3", "XX4"};
        final String[] VEInombreencargado={"Ramon", "Baptistao", "Julian", "Filemon"};
        final String[] VEIapellidoencargado={"Gutierrez", "Moreno", "Quintanilla", "Marroquin"};

        final String[] VMIidmotivoimpresion= {"A123", "A234", "A345", "A456"};
        final String[] VMImotivo= {"PARCIAL", "EVALUADO", "FOTOCOPIAS", "INTERNOS"};

        final String[] VEsIidestadoimpresion= {"123A", "234B", "345C", "456D"};
        final String[] VEsIidolicitudimpresion= {"J223", "J224", "J225", "J226"};
        final String[] VEsIidencargado= {"XX1", "XX3", "XX4", "XX1"};
        final String[] VEsIidmotivoimpresion= {"A123", "A123", "A345", "A345"};
        final Boolean[] VEsIrealizado= {true, false, true, true };
        final String[] VEsIobservaciones= {"PARCIAL", "EVALUADO", "FOTOCOPIAS", "INTERNOS"};

        final String[] idOpcion = {"001","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016","017","018","019","020","021","022","023","024","025"};
        final String[] idOpcionEstudiante ={"001","002", "003", "010","020", "024","025"};
        final String[] idOpcionDocente = {"004","005","006","008","009","010","011","016","022","023", "024","025"};
        final String[] idDocenteDirector = {"005","006","008","016","022","023", "024","025"};
        final String[] idEncargado = {"016","019", "024","025"};
        final String[] descripOpcion = {"Estudiante_menu","Repetido_menu", "Diferido_menu","DetalleDiferidoRepetido_menu","DetalleEstudianteDiferido_consultar", "DetalleEstudianteRepetido_consultar","Local_menu", "Evaluacion_menu", "PeriodoInscripcionRevision_menu", "PrimeraRevision_menu","SolicitudDiferido_consultarDocente","CicloMenuActivity","CargaAcademicaMenuActivity","DocenteMenuActivity", "AsignaturaMenuActivity","SolImpresionMenuActivity", "DocDirectorMenuActivity", "EstadoImpresionMenuActivity", "EncarImpresionesMenuActivity","SolicitudRevision_menu", "ListaPerInsRevActivity","SegundaRevisionMenu","ListaPerInsRevActivity_docente", "CalendarioActivity","BluetoothActivity"};
        final int[] numCrud = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};

        abrir();
        db.execSQL("DELETE FROM usuario;");
        db.execSQL("DELETE FROM asignatura");
        db.execSQL("DELETE FROM ciclo");
        db.execSQL("DELETE FROM tipoevaluacion");
        db.execSQL("DELETE FROM tiporevision");
        db.execSQL("DELETE FROM docente");
        db.execSQL("DELETE FROM TipoDiferidoRepetido");
        db.execSQL("DELETE FROM MotivoDiferido");
        db.execSQL("DELETE FROM motivocambionota");
        db.execSQL("DELETE FROM solicitudrevision");
        db.execSQL("DELETE FROM tipogrupo");
        db.execSQL("DELETE FROM local");
        db.execSQL("DELETE FROM evaluacion");
        db.execSQL("DELETE FROM periodoinscripcionrevision");
        db.execSQL("DELETE FROM primerrevision");
        db.execSQL("DELETE FROM segundarevision");
        db.execSQL("DELETE FROM tipodocenteciclo");
        db.execSQL("DELETE FROM escuela");
        db.execSQL("DELETE FROM docentedirector");
        db.execSQL("DELETE FROM solicitudimpresion");
        db.execSQL("DELETE FROM encargadodeimpresiones");
        db.execSQL("DELETE FROM motivoimpresion");
        db.execSQL("DELETE FROM estadoimpresion");
        db.execSQL("DELETE FROM accesoUsuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM Estudiante");

        Usuario user = new Usuario();
        for (int i = 0; i < usersId.length; i++) {
            user.setUsername(usersId[i]);
            user.setNombreUsuario(names[i]);
            user.setPassword(userPass[i]);
            insertar(user);
        }
        //Llenado acceso Administrador
        AccesoUsuario acceso = new AccesoUsuario();
        for (int i = 0; i < idOpcion.length; i++) {
            acceso.setUsername(usersId[0]);
            acceso.setIdOpcion(idOpcion[i]);
            insertar(acceso);
        }
        //Llenado acceso Estudiante
        AccesoUsuario accesoUsuario = new AccesoUsuario();
        for (int i = 0; i < idOpcionEstudiante.length; i++) {
            accesoUsuario.setUsername(usersId[1]);
            accesoUsuario.setIdOpcion(idOpcionEstudiante[i]);
            insertar(accesoUsuario);
        }
        //Llenado acceso Administrador
        AccesoUsuario accesoDocente = new AccesoUsuario();
        for (int i = 0; i < idOpcionDocente.length; i++) {
            accesoDocente.setUsername(usersId[2]);
            accesoDocente.setIdOpcion(idOpcionDocente[i]);
            insertar(accesoDocente);
        }
        //Llenado acceso DocenteDirector
        AccesoUsuario accesoDocenteDirector = new AccesoUsuario();
        for (int i = 0; i < idDocenteDirector.length; i++) {
            accesoDocenteDirector.setUsername(usersId[3]);
            accesoDocenteDirector.setIdOpcion(idDocenteDirector[i]);
            insertar(accesoDocenteDirector);
        }
        //Llenado acceso Encargado Impresiones
        AccesoUsuario accesoEncargado = new AccesoUsuario();
        for (int i = 0; i < idEncargado.length; i++) {
            accesoEncargado.setUsername(usersId[4]);
            accesoEncargado.setIdOpcion(idEncargado[i]);
            insertar(accesoEncargado);
        }
        //Llenado opciones crud
        OpcionCrud opcionCrud = new OpcionCrud();
        for (int i = 0; i<idOpcion.length; i++){
            opcionCrud.setIdOpcion(idOpcion[i]);
            opcionCrud.setDescOpcion(descripOpcion[i]);
            opcionCrud.setNumCrud(numCrud[i]);
            insertar(opcionCrud);
        }

        MotivoDiferido motivoDiferido = new MotivoDiferido();
        for (int i = 0; i < motivos.length; i++) {
            motivoDiferido.setNombreMotivoDiferido(motivos[i]);
            insertar(motivoDiferido);
        }

        Asignatura asignatura = new Asignatura();
        for (int i = 0; i < TAcodasignatura.length; i++) {
            asignatura.setCodasignatura(TAcodasignatura[i]);
            asignatura.setNomasignatura(TAnomasignatura[i]);
            asignatura.setUnidadesval(TAunidadesval[i]);
            insertar(asignatura);
        }

        Ciclo ciclo = new Ciclo();
        for (int i = 0; i < TCcodciclo.length; i++) {
            ciclo.setCodciclo(TCcodciclo[i]);
            ciclo.setFechadesde(TCfechadesde[i]);
            ciclo.setFechahasta(TCfechahasta[i]);
            insertar(ciclo);
        }

        TipoEvaluacion tipoeval = new TipoEvaluacion();
        for (int i = 0; i < TTEcodtipoeval.length; i++) {
            tipoeval.setCodTipoEval(TTEcodtipoeval[i]);
            tipoeval.setNomTipoEval(TTEnomtipoeval[i]);
            insertar(tipoeval);
        }

        TipoRevision tiporev = new TipoRevision();
        for (int i = 0; i < TRcodrevision.length; i++) {
            tiporev.setCodTipoRev(TRcodrevision[i]);
            tiporev.setNomTipoRev(TRnomrevision[i]);
            insertar(tiporev);
        }

        Docente docente = new Docente();
        for (int i = 0; i < TDcoddocente.length; i++) {
            docente.setCoddocente(TDcoddocente[i]);
            docente.setNomdocente(TDnombredocente[i]);
            docente.setApellidodocente(TDapellidodocente[i]);
            insertar(docente);
        }
        TipoDiferidoRepetido tipo = new TipoDiferidoRepetido();
        for (int i = 0; i < tipoDifRep.length; i++) {
            tipo.setIdTipo(tipoDifRep[i]);
            tipo.setNombreTipo(nombreTipoDifRep[i]);
            insertar(tipo);
        }

        Estudiante estudiante = new Estudiante();
        for(int i=0; i<TEcarnet.length; i++){
            estudiante.setCarnet(TEcarnet[i]);
            estudiante.setNombre(TEnombreestudiante[i]);
            estudiante.setApellido(TEapellidoestudiante[i]);
            estudiante.setCarrera(TEcarrera[i]);
            insertar(estudiante);
        }

        MotivoCambioNota motivo = new MotivoCambioNota();
        for (int i = 0; i < TMCNcodmotivocambnota.length; i++) {
            motivo.setCodmotivocambionota(TMCNcodmotivocambnota[i]);
            motivo.setMotivo(TMCNmotivo[i]);
            insertar(motivo);
        }

        SolicitudRevision solRev = new SolicitudRevision();
        for (int i = 0; i < TSoRcarnet.length; i++) {
            solRev.setCarnet(TSoRcarnet[i]);
            solRev.setCodasignatura(TSoRcodasignatura[i]);
            solRev.setCodciclo(TSoRcodcilo[i]);
            solRev.setCodtipoeval(TSoRcodtipoeval[i]);
            solRev.setCodtiporevision(TSoRcodtiporev[i]);
            solRev.setFechasolicitudrevision(String.valueOf(TSoRfechasolicitudrev[i]));
            solRev.setMotivorevision(TSoRmotivorevision[i]);
            solRev.setNotaantesrevision(TSoRnotaantesrev[i]);
            solRev.setNumeroeval(TSoRcodnumeroeval[i]);
            solRev.setNumerogrupo(TSoRnumerogrupo[i]);
            solRev.setCodtipogrupo(TTGcodtipoGrupo[i]);
            insertar(solRev);
        }
        TipoGrupo tipogrupo = new TipoGrupo();
        for(int i=0; i<TTGcodtipoGrupo.length; i++){
            tipogrupo.setCodtipogrupo(TTGcodtipoGrupo[i]);
            tipogrupo.setNombreTipoGrupo(TTGnomtipogrupo[i]);
            insertar(tipogrupo);
        }

        TipoDocenteCiclo tipoDocenteCiclo = new TipoDocenteCiclo();
        for (int i=0;i<4;i++){
            tipoDocenteCiclo.setIdtipodocenteciclo(VTidtipodocenteciclo[i]);
            tipoDocenteCiclo.setNomtipodocenteciclo(VTnomtipodocenteciclo[i]);
            insertar(tipoDocenteCiclo);
        }
        Escuela escuela = new Escuela();
        for(int i=0;i<4;i++){
            escuela.setIdescuela(VEidescuela[i]);
            escuela.setNombreescuela(VEnombreescuela[i]);
            escuela.setFacultad(VEfacultad[i]);
            insertarEscuela(escuela);

        }

        DocDirector dir = new DocDirector();
        for(int i=0;i<4;i++){
            dir.setIddocentedirector(VDDiddocentedirector[i]);
            dir.setIdescuela(VDDidescuela[i]);
            dir.setNombredirector(VDDnombredirector[i]);
            dir.setApellidodirector(VDDapellidodirector[i]);
            dir.setCorreodirector(VDDcorreodirector[i]);
            dir.setTelefono(VDDtelefono[i]);
            insertarDocDirector(dir);
        }

        Docente doc = new Docente();
        for(int i=0;i<4;i++){
            doc.setCoddocente(VDiddocente[i]);
            doc.setNomdocente(VDnombredocente[i]);
            doc.setApellidodocente(VDapellidodocente[i]);
            insertarDocente(doc);
        }

        SolImpresion solImpresion = new SolImpresion();

        for (int i = 0; i < 4; i++) {
            solImpresion.setIdsolicitudimpresion(VSIidsolicitud[i]);
            solImpresion.setIddocente(VSIiddocente[i]);
            solImpresion.setIddocentedirector(VSIiddirector[i]);
            solImpresion.setCantidadexamenes(VSICantidadExa[i]);
            solImpresion.setHojasempaque(VSIhojasEmpaque[i]);
            solImpresion.setEstadoaprobacion(VSIestadoApro[i]);
            insertar(solImpresion);
        }



        EncarImpresiones encarImpresiones = new EncarImpresiones();

        for (int i = 0; i < 4; i++) {
            encarImpresiones.setIdencargado(VEIidencargado[i]);
            encarImpresiones.setNombreencargado(VEInombreencargado[i]);
            encarImpresiones.setApellidoencargado(VEIapellidoencargado[i]);
            insertarEncarImpresiones(encarImpresiones);
        }


        MotivoImpresion motivoImpresion = new MotivoImpresion();

        for (int i = 0; i < 4; i++) {
            motivoImpresion.setIdmotivoimpresion(VMIidmotivoimpresion[i]);
            motivoImpresion.setMotivo(VMImotivo[i]);
            insertarMotivoImpresion(motivoImpresion);
        }

        EstadoImpresion estadoImpresion = new EstadoImpresion();

        for (int i = 0; i < 4; i++) {
            estadoImpresion.setIdestadoimpresion(VEsIidestadoimpresion[i]);
            estadoImpresion.setIdsolicitudimpresion(VEsIidolicitudimpresion[i]);
            estadoImpresion.setIdencargado(VEsIidencargado[i]);
            estadoImpresion.setIdmotivoimpresion(VEsIidmotivoimpresion[i]);
            estadoImpresion.setRealizado(VEsIrealizado[i]);
            estadoImpresion.setObservaciones(VEsIobservaciones[i]);
            insertarEstadoImpresion(estadoImpresion);
        }

        cerrar();
        return "Guardado correctamente";
    }
}
