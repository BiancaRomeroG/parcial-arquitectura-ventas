package com.example.ventasparcialarquitectura.presentacion.vendedor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NVendedor;

import java.util.List;

public class VendedorAdapter extends ArrayAdapter<Object[]> {

    private final Context context;
    private final List<Object[]> vendedores;
    private final NVendedor nVendedor;

    public VendedorAdapter(Context context, List<Object[]> vendedores, NVendedor nVendedor) {
        super(context, R.layout.item_vendedor, vendedores);
        this.context = context;
        this.vendedores = vendedores;
        this.nVendedor = nVendedor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_vendedor, parent, false);

        TextView tvVendedorNombre = rowView.findViewById(R.id.tvVendedorNombre);
        TextView tvVendedorEmail = rowView.findViewById(R.id.tvVendedorEmail);
        TextView tvVendedorTelefono = rowView.findViewById(R.id.tvVendedorTelefono);

        ImageButton btnEditarVendedor = rowView.findViewById(R.id.btnEditarVendedor);
        ImageButton btnEliminarVendedor = rowView.findViewById(R.id.btnEliminarVendedor);

        tvVendedorNombre.setText(vendedores.get(position)[3].toString());
        tvVendedorEmail.setText(vendedores.get(position)[4].toString());
        tvVendedorTelefono.setText(vendedores.get(position)[5].toString());

        btnEditarVendedor.setOnClickListener(view -> {
            int idVendedor = (int) vendedores.get(position)[0];
            Intent intent = new Intent(context, PEditarVendedor.class);
            intent.putExtra("vendedorId", idVendedor);
            context.startActivity(intent);
        });

        btnEliminarVendedor.setOnClickListener(view -> {
            int idVendedor = (int) vendedores.get(position)[0];
            new EliminarVendedorTask().execute(idVendedor);
            vendedores.remove(position);
            notifyDataSetChanged();

        });

        return rowView;
    }

    private class EliminarVendedorTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            nVendedor.eliminar(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
