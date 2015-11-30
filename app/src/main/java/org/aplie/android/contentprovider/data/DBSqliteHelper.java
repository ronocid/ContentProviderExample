package org.aplie.android.contentprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSqliteHelper extends SQLiteOpenHelper {
    private static final String BD_NOMBRE = "DBClientes";
    private static final int BD_VERSION = 1;
    private String sqlCreate = "CREATE TABLE "+ DBContract.ClienteEntry.TABLE_NAME +
            "("+ DBContract.ClienteEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.ClienteEntry.COLUMN_NOMBRE +" TEXT, " +
            DBContract.ClienteEntry.COLUMN_TELEFONO +" TEXT, " +
            DBContract.ClienteEntry.COLUMN_EMAIL +" TEXT )";

    public DBSqliteHelper(Context contexto) {
        super(contexto, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);

        for(int i=1; i<=15; i++)
        {
            String nombre = "Cliente" + i;
            String telefono = "900-123-00" + i;
            String email = "email" + i + "@mail.com";

            db.execSQL("INSERT INTO "+ DBContract.ClienteEntry.TABLE_NAME+" ("+
                    DBContract.ClienteEntry.COLUMN_NOMBRE + ", " +
                    DBContract.ClienteEntry.COLUMN_TELEFONO + ", " +
                    DBContract.ClienteEntry.COLUMN_EMAIL+") " +
                    "VALUES ('" + nombre + "', '" + telefono +"', '" + email + "')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.ClienteEntry.TABLE_NAME);
        db.execSQL(sqlCreate);
    }
}
