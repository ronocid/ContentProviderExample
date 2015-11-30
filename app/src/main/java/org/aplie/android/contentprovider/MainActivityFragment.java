package org.aplie.android.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.aplie.android.contentprovider.data.DBContract;
import org.aplie.android.contentprovider.dialogs.DialogConsulta;
import org.aplie.android.contentprovider.dialogs.DialogoEliminar;
import org.aplie.android.contentprovider.dialogs.DialogoInsertar;

public class MainActivityFragment extends Fragment {

    private TextView txtResultados;
    private Button btnConsultar;
    private Button btnInsertar;
    private Button btnEliminar;
    private Button btnTodos;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        txtResultados = (TextView)view.findViewById(R.id.TxtResultados);
        btnConsultar = (Button)view.findViewById(R.id.BtnConsultar);
        btnInsertar = (Button)view.findViewById(R.id.BtnInsertar);
        btnEliminar = (Button)view.findViewById(R.id.BtnEliminar);
        btnTodos = (Button)view.findViewById(R.id.BtnTodos);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DialogConsulta dialog = new DialogConsulta(getActivity(),txtResultados);
                dialog.show(fragmentManager, "tagConsulta");
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DialogoInsertar dialog = new DialogoInsertar(getActivity());
                dialog.show(fragmentManager, "tagInsertar");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DialogoEliminar dialog = new DialogoEliminar(getActivity());
                dialog.show(fragmentManager, "tagEliminar");
            }
        });

        btnTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaClientes();
            }
        });

        consultaClientes();
        return view;
    }

    private void consultaClientes() {
        String[] projection = new String[] {
                DBContract.ClienteEntry._ID,
                DBContract.ClienteEntry.COLUMN_NOMBRE,
                DBContract.ClienteEntry.COLUMN_TELEFONO,
                DBContract.ClienteEntry.COLUMN_EMAIL};

        Uri clientesUri =  DBContract.ClienteEntry.CONTENT_URI;

        ContentResolver cr = getActivity().getContentResolver();

        Cursor cur = cr.query(clientesUri,
                projection, //Columnas a devolver
                null,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (cur.moveToFirst())
        {
            String nombre;
            String telefono;
            String email;

            int colNombre = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_NOMBRE);
            int colTelefono = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_TELEFONO);
            int colEmail = cur.getColumnIndex(DBContract.ClienteEntry.COLUMN_EMAIL);

            txtResultados.setText("");
            do{

                nombre = cur.getString(colNombre);
                telefono = cur.getString(colTelefono);
                email = cur.getString(colEmail);

                txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

            } while (cur.moveToNext());
        }
    }
}
