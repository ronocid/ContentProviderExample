package org.aplie.android.contentprovider.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
    public static final String CONTENT_AUTHORITY = "org.aplie.android.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CLIENTE = "clientes";

    public static final class ClienteEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLIENTE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CLIENTE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CLIENTE;

        public static final String TABLE_NAME = "Clientes";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_EMAIL = "email";
    }
}
