package com.example.ventasparcialarquitectura.negocio;

import android.os.AsyncTask;
import com.example.ventasparcialarquitectura.datos.DRol;
import java.util.ArrayList;
import java.util.List;

public class NRol {

    public interface OnRolGuardadoListener {
        void onRolGuardado();
    }

    public interface OnRolObtenidoListener {
        void onRolObtenido(DRol rol);
    }

    private OnRolGuardadoListener guardarListener;
    private OnRolObtenidoListener obtenerListener;
    private DRol dRol;

    public NRol() {
        dRol = new DRol();
    }

    public void setOnRolGuardadoListener(OnRolGuardadoListener listener) {
        this.guardarListener = listener;
    }

    public void setOnRolObtenidoListener(OnRolObtenidoListener listener) {
        this.obtenerListener = listener;
    }

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DRol rol : dRol.getLista()) {
            lista.add(new Object[]{rol.getId(), rol.getNombre()});
        }
        return lista;
    }

    public void guardar(DRol rol) {
        new GuardarRolTask().execute(rol);
    }

    public void eliminar(int id) {
        new EliminarRolTask().execute(id);
    }

    public void obtenerRolPorId(int id) {
        new GetRolTask().execute(id);
    }

    private class GuardarRolTask extends AsyncTask<DRol, Void, Void> {
        @Override
        protected Void doInBackground(DRol... roles) {
            dRol.guardar(roles[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (guardarListener != null) {
                guardarListener.onRolGuardado();
            }
        }
    }

    public void actualizar(DRol rol) {
        new ActualizarRolTask().execute(rol);
    }

    private class ActualizarRolTask extends AsyncTask<DRol, Void, Void> {
        @Override
        protected Void doInBackground(DRol... roles) {
            dRol.actualizar(roles[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (guardarListener != null) {
                guardarListener.onRolGuardado();
            }
        }
    }

    private class GetRolTask extends AsyncTask<Integer, Void, DRol> {
        @Override
        protected DRol doInBackground(Integer... ids) {
            return dRol.getPorId(ids[0]);
        }

        @Override
        protected void onPostExecute(DRol result) {
            if (obtenerListener != null) {
                obtenerListener.onRolObtenido(result);
            }
        }
    }

    private class EliminarRolTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... ids) {
            dRol.eliminar(ids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (guardarListener != null) {
                guardarListener.onRolGuardado();
            }
        }
    }

}
