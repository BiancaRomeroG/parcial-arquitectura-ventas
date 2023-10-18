package com.example.ventasparcialarquitectura.presentacion.comision;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NComision;

import java.util.List;

public class ComisionAdapter extends ArrayAdapter<Object[]> {
    private final Context context;
    private final List<Object[]> comisiones;
    private final NComision nComision = new NComision();

    public ComisionAdapter(Context context, List<Object[]> comisiones) {
        super(context, R.layout.activity_listar_comisiones, comisiones);
        this.context = context;
        this.comisiones = comisiones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_comision, parent, false);

        TextView tvUsuario = rowView.findViewById(R.id.tvUsuario);
        TextView tvNotaVenta = rowView.findViewById(R.id.tvNotaVenta);
        TextView tvNivel = rowView.findViewById(R.id.tvNivel);
        TextView tvMonto = rowView.findViewById(R.id.tvMonto);

        tvUsuario.setText("Vendedor: "  + comisiones.get(position)[1].toString());
        tvNotaVenta.setText("NotaVentaId: "  + comisiones.get(position)[2].toString());
        tvNivel.setText("Nivel: "  + comisiones.get(position)[3].toString());
        tvMonto.setText("Monto: $"  + comisiones.get(position)[4].toString());

        return rowView;
    }
}
