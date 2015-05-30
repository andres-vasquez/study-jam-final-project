package proyectos.avdc.com.studyjamproyectofinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import proyectos.avdc.com.studyjamproyectofinal.POJO.EquiposItem;
import proyectos.avdc.com.studyjamproyectofinal.POJO.JugadoresItem;
import proyectos.avdc.com.studyjamproyectofinal.data.Crear_db;

/**
 * Created by andresvasquez on 4/3/15.
 */
public class Funcionesdb {

    final static String LOG = "FuncionesDb";

    public static boolean LlenarEquipo(Context context, EquiposItem item) {
        boolean resultado = false;
        try {
            Crear_db db = new Crear_db(context);
            SQLiteDatabase dbo = db.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("id_equipo", item.getIntIdEquipo());
            values.put("name", new String(item.getStrNombreEquipo().getBytes("ISO-8859-1"), "UTF-8"));
            values.put("badge", item.getStrBadge());
            values.put("img", item.getIntImgEquipo());

            if (dbo.insert("equipos", null, values) > 0)
                resultado = true;
            else
                resultado = false;
            dbo.close();
        } catch (Exception e) {

        }
        return resultado;
    }

    public static List<EquiposItem> LlenarEquipos(Context context) {
        List<EquiposItem> resultado = new ArrayList<EquiposItem>();
        try {
            Crear_db db = new Crear_db(context);
            SQLiteDatabase dbo = db.getWritableDatabase();

            Cursor cursor = dbo.rawQuery("SELECT id_equipo, name, badge, img FROM equipos", null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        EquiposItem equipo = new EquiposItem();
                        equipo.setIntIdEquipo(cursor.getInt(0));
                        equipo.setStrNombreEquipo(new String(cursor.getString(1).getBytes("ISO-8859-1"), "UTF-8"));
                        equipo.setStrBadge(cursor.getString(2));
                        equipo.setIntImgEquipo(cursor.getInt(3));
                        resultado.add(equipo);
                    } catch (Exception e) {
                        Log.e("Exception", "" + e.getMessage());
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbo.close();
        } catch (Exception e) {
        }
        return resultado;
    }

    public static EquiposItem ObtenerEquipoNombre(Context context, String nombre) {
        List<EquiposItem> resultado = new ArrayList<EquiposItem>();
        try {
            Crear_db db = new Crear_db(context);
            SQLiteDatabase dbo = db.getWritableDatabase();

            Cursor cursor = dbo.rawQuery("SELECT id_equipo,name,badge,img FROM equipos WHERE name=?", new String[]{nombre});
            if (cursor.moveToFirst()) {
                do {
                    EquiposItem equipo = new EquiposItem();
                    equipo.setIntIdEquipo(cursor.getInt(0));
                    equipo.setStrNombreEquipo(cursor.getString(1));
                    equipo.setStrBadge(cursor.getString(2));
                    equipo.setIntImgEquipo(cursor.getInt(3));
                    resultado.add(equipo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbo.close();

        } catch (Exception e) {
        }

        if (resultado.size() > 0)
            return resultado.get(0);
        else {
            return new EquiposItem(99, "No encontrado", "ninguno.png", android.R.drawable.btn_star_big_off);
        }
    }

    public static EquiposItem ObtenerEquipoId(Context context, String id) {
        List<EquiposItem> resultado = new ArrayList<EquiposItem>();
        try {
            Crear_db db = new Crear_db(context);
            SQLiteDatabase dbo = db.getWritableDatabase();

            Cursor cursor = dbo.rawQuery("SELECT id_equipo,name,badge,img FROM equipos WHERE id_equipo=?", new String[]{id});
            if (cursor.moveToFirst()) {
                do {
                    EquiposItem equipo = new EquiposItem();
                    equipo.setIntIdEquipo(cursor.getInt(0));
                    equipo.setStrNombreEquipo(cursor.getString(1));
                    equipo.setStrBadge(cursor.getString(2));
                    equipo.setIntImgEquipo(cursor.getInt(3));
                    resultado.add(equipo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbo.close();
        } catch (Exception e) {
        }
        if (resultado.size() > 0)
            return resultado.get(0);
        else {
            return new EquiposItem(99, "No encontrado", "ninguno.png", android.R.drawable.btn_star_big_off);
        }
    }

    public static List<JugadoresItem> LlenarJugadores(Context context, int intIdEquipo) {
        List<JugadoresItem> resultado = new ArrayList<JugadoresItem>();

        try {
            Crear_db db = new Crear_db(context);
            SQLiteDatabase dbo = db.getReadableDatabase();

            Cursor cursor = dbo.rawQuery("SELECT _id, firstname, lastname FROM jugadores WHERE id_team=?",new String[]{String.valueOf(intIdEquipo)});
            if (cursor.moveToFirst()) {
                do {
                    try {
                        JugadoresItem jugadoresItem = new JugadoresItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                        resultado.add(jugadoresItem);
                    } catch (Exception e) {
                        Log.e("Exception", "" + e.getMessage());
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbo.close();
        } catch (Exception e) {
        }
        return resultado;
    }
}
