package org.aplie.android.contentprovider.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class DBProvider extends ContentProvider{
    private DBSqliteHelper dataBaseHelper;

    private static final int CLIENTES = 1;
    private static final int CLIENTES_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.PATH_CLIENTE, CLIENTES);
        uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.PATH_CLIENTE+"/#", CLIENTES_ID);
    }

    @Override
    public boolean onCreate() {
        dataBaseHelper = new DBSqliteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CLIENTES:
            {
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                cursor = db.query(DBContract.ClienteEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case CLIENTES_ID: {
                String where = selection;
                where = "_id=" + uri.getLastPathSegment();
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                cursor = db.query(DBContract.ClienteEntry.TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case CLIENTES:
                return DBContract.ClienteEntry.CONTENT_TYPE;
            case CLIENTES_ID:
                return DBContract.ClienteEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        regId = db.insert(DBContract.ClienteEntry.TABLE_NAME, null, values);

        Uri newUri = ContentUris.withAppendedId(DBContract.ClienteEntry.CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont = 0;
        if(uriMatcher.match(uri) == CLIENTES){
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            cont = db.delete(DBContract.ClienteEntry.TABLE_NAME, selection, selectionArgs);
        }
        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont = 0;
        if(uriMatcher.match(uri) == CLIENTES){
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            cont = db.update(DBContract.ClienteEntry.TABLE_NAME, values, selection, selectionArgs);
        }
        return cont;
    }
}
