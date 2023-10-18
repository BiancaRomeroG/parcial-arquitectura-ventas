package com.example.ventasparcialarquitectura.presentacion.producto;

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
import com.example.ventasparcialarquitectura.negocio.NProducto;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<Object[]> {
    private final Context context;
    private final List<Object[]> productos;
    private final NProducto nProducto;

    public ProductoAdapter(Context context, List<Object[]> productos, NProducto nProducto) {
        super(context, R.layout.item_producto, productos);
        this.context = context;
        this.productos = productos;
        this.nProducto = nProducto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_producto, parent, false);

        TextView tvProductoNombre = rowView.findViewById(R.id.tvProductoNombre);
        TextView tvProductoDescripcion = rowView.findViewById(R.id.tvProductoDescripcion);
        TextView tvProductoPrecio = rowView.findViewById(R.id.tvProductoPrecio);

        ImageButton btnEditarProducto = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminarProducto = rowView.findViewById(R.id.btnEliminar);

        tvProductoNombre.setText(productos.get(position)[3].toString());
        tvProductoDescripcion.setText(productos.get(position)[4].toString());
        tvProductoPrecio.setText("$" + productos.get(position)[5].toString());

        btnEditarProducto.setOnClickListener(view -> {
            int idProducto = (int) productos.get(position)[0];
            Intent intent = new Intent(context, PEditarProducto.class);
            intent.putExtra("productoId", idProducto);
            context.startActivity(intent);
        });

        btnEliminarProducto.setOnClickListener(view -> {
            int idProducto = (int) productos.get(position)[0];
            new EliminarProductoTask().execute(idProducto);
            productos.remove(position);
            notifyDataSetChanged();
        });

        return rowView;
    }

    private class EliminarProductoTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... ids) {
            nProducto.eliminar(ids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
