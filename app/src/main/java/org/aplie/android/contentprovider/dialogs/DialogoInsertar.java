package org.aplie.android.contentprovider.dialogs;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.aplie.android.contentprovider.R;
import org.aplie.android.contentprovider.data.DBContract;

public class DialogoInsertar extends DialogFragment {
    private final Context context;

    public  DialogoInsertar(){
        this.context = null;
    }

    public  DialogoInsertar(Context context){
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_insertar, null);
        final EditText etNombre = (EditText) view.findViewById(R.id.editTextNombre);
        final EditText etTelefono = (EditText) view.findViewById(R.id.editTextTelefono);
        final EditText etEmail = (EditText) view.findViewById(R.id.editTextEmail);

        builder.setTitle("Insertar Cliente");
        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = etNombre.getText().toString();
                        String telefono = etTelefono.getText().toString();
                        String email = etEmail.getText().toString();
                        if(!"".equals(nombre) && !"".equals(telefono) && !"".equals(email)){
                            ContentValues values = new ContentValues();

                            values.put(DBContract.ClienteEntry.COLUMN_NOMBRE, nombre);
                            values.put(DBContract.ClienteEntry.COLUMN_TELEFONO, telefono);
                            values.put(DBContract.ClienteEntry.COLUMN_EMAIL, email);

                            ContentResolver cr = context.getContentResolver();

                            cr.insert(DBContract.ClienteEntry.CONTENT_URI, values);
                        }
                        dialog.cancel();
                    }
                });

        builder.setView(view)
                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
