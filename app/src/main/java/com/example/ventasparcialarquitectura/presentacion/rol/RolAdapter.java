package com.example.ventasparcialarquitectura.presentacion.rol;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NRol;

import java.util.List;

public class RolAdapter extends ArrayAdapter<Object[]> {

    public interface OnRolEliminadoListener {
        void onRolEliminado();
    }

    private final Context context;
    private final List<Object[]> roles;
    private final NRol nRol;
    private OnRolEliminadoListener eliminadoListener;

    public RolAdapter(Context context, List<Object[]> roles, NRol nRol) {
        super(context, R.layout.item_rol, roles);
        this.context = context;
        this.roles = roles;
        this.nRol = nRol;
    }

    public void setOnRolEliminadoListener(OnRolEliminadoListener listener) {
        this.eliminadoListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_rol, parent, false);

        TextView tvRolNombre = rowView.findViewById(R.id.tvRolNombre);
        ImageButton btnEditar = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminar = rowView.findViewById(R.id.btnEliminar);

        tvRolNombre.setText(roles.get(position)[1].toString());

        btnEditar.setOnClickListener(view -> {
            int idRol = (int) roles.get(position)[0];
            Intent intent = new Intent(context, PEditarRol.class);
            intent.putExtra("rolId", idRol);
            context.startActivity(intent);
        });

        btnEliminar.setOnClickListener(view -> {
//            int idRol = (int) roles.get(position)[0];
//            nRol.eliminar(idRol);
//            roles.remove(position);
//            notifyDataSetChanged();
//
//            if (eliminadoListener != null) {
//                eliminadoListener.onRolEliminado();
//            }
        });

        return rowView;
    }
}
