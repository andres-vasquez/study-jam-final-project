package proyectos.avdc.com.studyjamproyectofinal.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by andresvasquez on 5/24/15.
 */
public class GolazoContract {

    public static final String CONTENT_AUTHORITY = "proyectos.avdc.com.studyjamproyectofinal";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PARTIDOS = "partidos";
    public static final String PATH_POSICIONES = "posiciones";
    public static final String PATH_EQUIPOS = "equipos";


    public static final class EquiposEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EQUIPOS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EQUIPOS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EQUIPOS;

        public static final String TABLE_NAME = "equipos";
        public static final String COLUMN_ID_EQUIPO = "id_equipo";
        public static final String COLUMN_NAME= "name";
        public static final String COLUMN_BADGE = "badge";
        public static final String COLUMN_IMG = "img";

        public static Uri buildEquiposnUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdEquipoFromUri(Uri uri) {
            String idString = uri.getQueryParameter(COLUMN_ID_EQUIPO);
            if (null != idString && idString.length() > 0)
                return Long.parseLong(idString);
            else
                return 0;
        }
    }

    public static final class PartidosEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARTIDOS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARTIDOS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARTIDOS;

        public static final String TABLE_NAME = "partidos";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_LOCATION= "location";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_HOME_TEAM = "home_team";
        public static final String COLUMN_HOME_BADGE = "home_badge";
        public static final String COLUMN_HOME_SCORE = "home_score";
        public static final String COLUMN_AWAY_TEAM = "away_team";
        public static final String COLUMN_AWAY_BADGE = "away_badge";
        public static final String COLUMN_AWAY_SCORE = "away_score";
        public static final String COLUMN_MATCH_STATUS = "match_status";

        public static Uri buildPartidosUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PosicionesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSICIONES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POSICIONES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POSICIONES;

        public static final String TABLE_NAME = "posiciones";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PLAYED = "played";
        public static final String COLUMN_WON = "won";
        public static final String COLUMN_DRAWN = "drawn";
        public static final String COLUMN_LOST = "lost";
        public static final String COLUMN_GOALS_FOR = "goals_for";
        public static final String COLUMN_GOALS_AGAINST = "goals_against";
        public static final String COLUMN_GOALS_DIFFERENCE = "goals_difference";
        public static final String COLUMN_POINTS = "points";

        public static Uri buildPosicionesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
