package org.aplie.android.contentprovider.dialogs;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.aplie.android.contentprovider.R;
import org.aplie.android.contentprovider.data.DBContract;

public class DialogConsulta extends DialogFragment {
    private final Context context;
    private final TextView textView;

    public DialogConsulta() {
        this.context = null;
        textView = null;
    }

    public DialogConsulta(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_consulta, null);
        final EditText etNombre = (EditText) view.findViewById(R.id.editTextNombre);

        builder.setTitle("Consulta Cliente");
        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = etNombre.getText().toString();
                        if (!"".equals(nombre)) {
                            String[] projection = new String[] {
                                    DBContract.ClienteEntry._ID,
                                    DBContract.ClienteEntry.COLUMN_NOMBRE,
                                    DBContract.ClienteEntry.COLUMN_TELEFONO,
                                    DBContract.ClienteEntry.COLUMN_EMAIL};

                            Uri clientesUri =  DBContract.ClienteEntry.CONTENT_URI;

                            ContentResolver cr = context.getContentResolver();
                            String selection = DBContract.ClienteEntry.COLUMN_NOMBRE+ "=?";
                            String [] selectionArgs = new String []{nombre};
                            Cursor cur = cr.query(clientesUri,
                                    projection, //Columnas a devolver
                                    selection,       //Condición de la query
                                    selectionArgs,       //Argumentos variables de la query
                                    null);      //Orden de los resultados

                            if (cur.moveToFirst())
                            {
                                String sNombre;
                                String sTelefono;
                                String sEmail;

                                int colNombre = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_NOMBRE);
                                int colTelefono = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_TELEFONO);
                                int colEmail = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_EMAIL);

                                textView.setText("");
                                do{

                                    sNombre = cur.getString(colNombre);
                                    sTelefono = cur.getString(colTelefono);
                                    sEmail = cur.getString(colEmail);

                                    textView.append(sNombre + " - " + sTelefono + " - " + sEmail + "\n");

                                } while (cur.moveToNext());
                            }
                        }
                        dialog.cancel();
                    }
                });

        builder.setView(view)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}