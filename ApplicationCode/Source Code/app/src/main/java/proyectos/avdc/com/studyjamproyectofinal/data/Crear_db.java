package proyectos.avdc.com.studyjamproyectofinal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by andresvasquez on 3/30/15.
 */
public class Crear_db  extends SQLiteOpenHelper
{
    public static final String NOMBREBD = "golazo.db";
    public static final int VERSION = 2;

    public Crear_db(Context context)
    {
        super(context, NOMBREBD, null, VERSION);
    }


    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_PARTIDOS_TABLE = "CREATE TABLE " + GolazoContract.PartidosEntry.TABLE_NAME + " (" +
                GolazoContract.PartidosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GolazoContract.PartidosEntry.COLUMN_TIME + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_LOCATION + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_CITY + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_HOME_TEAM + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_HOME_BADGE + " VARCHAR, " +
                GolazoContract.PartidosEntry.COLUMN_HOME_SCORE + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_AWAY_TEAM + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_AWAY_BADGE + " VARCHAR, " +
                GolazoContract.PartidosEntry.COLUMN_AWAY_SCORE + " VARCHAR NOT NULL, " +
                GolazoContract.PartidosEntry.COLUMN_MATCH_STATUS + " VARCHAR " +
                " );";

        final String SQL_CREATE_POSICIONES_TABLE = "CREATE TABLE " + GolazoContract.PosicionesEntry.TABLE_NAME + " (" +
                GolazoContract.PosicionesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GolazoContract.PosicionesEntry.COLUMN_NAME + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_PLAYED + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_WON + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_DRAWN + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_LOST + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_GOALS_FOR + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_GOALS_AGAINST + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_GOALS_DIFFERENCE + " VARCHAR, " +
                GolazoContract.PosicionesEntry.COLUMN_POINTS + " VARCHAR " +
                " );";

        final String SQL_CREATE_EQUIPOS_TABLE = "CREATE TABLE " + GolazoContract.EquiposEntry.TABLE_NAME + " (" +
                GolazoContract.EquiposEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GolazoContract.EquiposEntry.COLUMN_ID_EQUIPO + " INTEGER, " +
                GolazoContract.EquiposEntry.COLUMN_NAME + " VARCHAR, " +
                GolazoContract.EquiposEntry.COLUMN_BADGE + " VARCHAR, " +
                GolazoContract.EquiposEntry.COLUMN_IMG + " INTEGER " +
                " );";

        final String SQL_CREATE_JUGADORES_TABLE = "CREATE TABLE " + GolazoContract.JugadoresEntry.TABLE_NAME + " (" +
                GolazoContract.JugadoresEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GolazoContract.JugadoresEntry.COLUMN_ID_TEAM + " INTEGER, " +
                GolazoContract.JugadoresEntry.COLUMN_FIRST_NAME + " VARCHAR, " +
                GolazoContract.JugadoresEntry.COLUMN_LAST_NAME + " VARCHAR " +
                " );";

        db.execSQL(SQL_CREATE_EQUIPOS_TABLE);
        db.execSQL(SQL_CREATE_PARTIDOS_TABLE);
        db.execSQL(SQL_CREATE_POSICIONES_TABLE);
        db.execSQL(SQL_CREATE_JUGADORES_TABLE);
        Log.d("Todos los tablas: ", "Se crearon las tablas");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GolazoContract.PartidosEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GolazoContract.EquiposEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GolazoContract.PosicionesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GolazoContract.JugadoresEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}