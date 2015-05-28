package proyectos.avdc.com.studyjamproyectofinal.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by andresvasquez on 5/24/15.
 */
public class GolazoProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private Crear_db mOpenHelper;

    static final int PARTIDOS = 100;
    static final int POSICIONES = 200;
    static final int EQUIPOS = 300;
    static final int EQUIPOS_POR_ID = 301;

    private static final String sEquipoSelection =
            GolazoContract.EquiposEntry.TABLE_NAME +
                    "." + GolazoContract.EquiposEntry.COLUMN_ID_EQUIPO + " = ? ";

    private Cursor getEquipoById(Uri uri, String[] projection, String sortOrder) {
        String[] selectionArgs=new String[]{};
        String selection="";
        long idEquipo = GolazoContract.EquiposEntry.getIdEquipoFromUri(uri);

        if (idEquipo != 0) {
            selection = sEquipoSelection;
            selectionArgs = new String[]{Long.toString(idEquipo)};
        }

        return mOpenHelper.getReadableDatabase().query(
                GolazoContract.EquiposEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GolazoContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, GolazoContract.PATH_PARTIDOS, PARTIDOS);
        matcher.addURI(authority, GolazoContract.PATH_POSICIONES, POSICIONES);
        matcher.addURI(authority, GolazoContract.PATH_EQUIPOS, EQUIPOS);
        matcher.addURI(authority, GolazoContract.PATH_EQUIPOS + "/*", EQUIPOS_POR_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new Crear_db(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "weather/*"
            case EQUIPOS_POR_ID: {
                retCursor = getEquipoById(uri, projection, sortOrder);
                break;
            }
            case EQUIPOS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        GolazoContract.EquiposEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PARTIDOS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        GolazoContract.PartidosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case POSICIONES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        GolazoContract.PosicionesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case EQUIPOS_POR_ID:
                return GolazoContract.EquiposEntry.CONTENT_ITEM_TYPE;
            case EQUIPOS:
                return GolazoContract.EquiposEntry.CONTENT_TYPE;
            case PARTIDOS:
                return GolazoContract.PartidosEntry.CONTENT_TYPE;
            case POSICIONES:
                return GolazoContract.PosicionesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case PARTIDOS: {
                long _id = db.insert(GolazoContract.PartidosEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = GolazoContract.PartidosEntry.buildPartidosUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case POSICIONES: {
                long _id = db.insert(GolazoContract.PosicionesEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = GolazoContract.PosicionesEntry.buildPosicionesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EQUIPOS: {
                long _id = db.insert(GolazoContract.EquiposEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = GolazoContract.EquiposEntry.buildEquiposnUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (match) {
            case POSICIONES:
                rowsDeleted = db.delete(
                        GolazoContract.PosicionesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PARTIDOS:
                rowsDeleted = db.delete(
                        GolazoContract.PartidosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EQUIPOS:
                rowsDeleted = db.delete(
                        GolazoContract.EquiposEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PARTIDOS:
                rowsUpdated = db.update(GolazoContract.PartidosEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case POSICIONES:
                rowsUpdated = db.update(GolazoContract.PosicionesEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case EQUIPOS:
                rowsUpdated = db.update(GolazoContract.EquiposEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PARTIDOS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(GolazoContract.PartidosEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case POSICIONES:
                db.beginTransaction();
                int returnCount1 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(GolazoContract.PosicionesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount1++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount1;
            case EQUIPOS:
                db.beginTransaction();
                int returnCount2 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(GolazoContract.EquiposEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount2++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount2;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
