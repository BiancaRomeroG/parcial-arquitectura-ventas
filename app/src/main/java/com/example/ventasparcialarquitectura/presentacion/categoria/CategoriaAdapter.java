package com.example.ventasparcialarquitectura.presentacion.categoria;

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
import com.example.ventasparcialarquitectura.negocio.NCategoria;

import java.util.List;

public class CategoriaAdapter extends ArrayAdapter<Object[]> {
    private final Context context;
    private final List<Object[]> categorias;
    private final NCategoria nCategoria;

    public CategoriaAdapter(Context context, List<Object[]> categorias, NCategoria nCategoria) {
        super(context, R.layout.item_categoria, categorias);
        this.context = context;
        this.categorias = categorias;
        this.nCategoria = nCategoria;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_categoria, parent, false);

        TextView tvCategoriaNombre = rowView.findViewById(R.id.tvCategoriaNombre);
        ImageButton btnEditar = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminar = rowView.findViewById(R.id.btnEliminar);

        tvCategoriaNombre.setText(categorias.get(position)[1].toString());

        btnEditar.setOnClickListener(view -> {
            int idCategoria = (int) categorias.get(position)[0];
            Intent intent = new Intent(context, PEditarCategoria.class);
            intent.putExtra("categoriaId", idCategoria);
            context.startActivity(intent);
        });

        btnEliminar.setOnClickListener(view -> {
            int idCategoria = (int) categorias.get(position)[0];
            new EliminarCategoriaTask().execute(idCategoria);
            categorias.remove(position);
            notifyDataSetChanged();
        });

        return rowView;
    }

    private class EliminarCategoriaTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... ids) {
            nCategoria.eliminar(ids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
