package com.example.ventasparcialarquitectura.presentacion.notaVenta;

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
import com.example.ventasparcialarquitectura.negocio.NNotaVenta;
import com.example.ventasparcialarquitectura.presentacion.producto.PEditarProducto;

import java.util.List;


public class NotaVentaAdapter extends ArrayAdapter<Object[]> {
    private final Context context;
    private final List<Object[]> notaVentas;
    private final NNotaVenta nNotaVenta;

    public NotaVentaAdapter(Context context, List<Object[]> notaVentas, NNotaVenta nNotaVenta) {
        super(context, R.layout.item_nota_venta, notaVentas);
        this.context = context;
        this.notaVentas = notaVentas;
        this.nNotaVenta = nNotaVenta;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View rowView = inflater.inflate(R.layout.item_nota_venta, parent, false);

        TextView tvNotaVentaCliente = rowView.findViewById(R.id.tvNotaVentaCliente);
        TextView tvNotaVentaFecha = rowView.findViewById(R.id.tvNotaVentaFecha);
        TextView tvNotaVentaTotal = rowView.findViewById(R.id.tvNotaVentaTotal);

        ImageButton btnEliminarNotaVenta = rowView.findViewById(R.id.btnEliminarNotaVenta);
        ImageButton btnVerNotaVenta = rowView.findViewById(R.id.btnVerNotaVenta);

        tvNotaVentaCliente.setText(notaVentas.get(position)[2].toString());
        tvNotaVentaFecha.setText(notaVentas.get(position)[4].toString());
        tvNotaVentaTotal.setText("$" + notaVentas.get(position)[5].toString());

        btnEliminarNotaVenta.setOnClickListener(view -> {
            int idNotaVenta = (int) notaVentas.get(position)[0];
            new EliminarNotaVentaTask().execute(idNotaVenta);
            notaVentas.remove(position);
            notifyDataSetChanged();
        });

        btnVerNotaVenta.setOnClickListener(view -> {
            Intent intent = new Intent(context, PVerNotaVenta.class);
            intent.putExtra("notaVentaId", (int) notaVentas.get(position)[0]);
            context.startActivity(intent);
        });

        return rowView;
    }

    private class EliminarNotaVentaTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... ids) {
            nNotaVenta.eliminar(ids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }


}

