package org.aplie.android.contentprovider.dialogs;

import android.app.Dialog;
import android.content.ContentResolver;
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

public class DialogoEliminar extends DialogFragment {
    private final Context context;

    public  DialogoEliminar(){
        this.context = null;
    }

    public  DialogoEliminar(Context context){
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_eliminar, null);
        final EditText etNombre = (EditText) view.findViewById(R.id.editTextNombre);

        builder.setTitle("Eliminar Cliente");
        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = etNombre.getText().toString();
                        if(!"".equals(nombre)){
                            ContentResolver cr = context.getContentResolver();

                            cr.delete(DBContract.ClienteEntry.CONTENT_URI,
                                    DBContract.ClienteEntry.COLUMN_NOMBRE + " = '"+nombre+"'", null);
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
