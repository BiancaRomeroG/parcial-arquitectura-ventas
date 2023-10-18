package com.example.ventasparcialarquitectura.presentacion.nivel;

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
import com.example.ventasparcialarquitectura.negocio.NNivel;

import java.util.List;

public class NivelAdapter extends ArrayAdapter<Object[]> {

    private final Context context;
    private final List<Object[]> niveles;
    private final NNivel nNivel;

    public NivelAdapter(Context context, List<Object[]> niveles, NNivel nNivel) {
        super(context, R.layout.item_nivel, niveles);
        this.context = context;
        this.niveles = niveles;
        this.nNivel = nNivel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_nivel, parent, false);

        TextView tvNivelNombre = rowView.findViewById(R.id.tvNivelNombre);
        ImageButton btnEditar = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminar = rowView.findViewById(R.id.btnEliminar);

        tvNivelNombre.setText(niveles.get(position)[1].toString());

        btnEditar.setOnClickListener(view -> {
            int idNivel = (int) niveles.get(position)[0];
            Intent intent = new Intent(context, PEditarNivel.class);
            intent.putExtra("nivelId", idNivel);
            context.startActivity(intent);
        });

        btnEliminar.setOnClickListener(view -> {
            int idNivel = (int) niveles.get(position)[0];
            new EliminarNivelTask().execute(idNivel);
            niveles.remove(position);
            notifyDataSetChanged();
        });

        return rowView;
    }

    private class EliminarNivelTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... ids) {
            nNivel.eliminar(ids[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
