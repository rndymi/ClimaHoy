package es.upm.miw.climahoy.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.climahoy.R;
import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultas;

public class ClimaConsultasListAdapter extends RecyclerView.Adapter<ClimaConsultasListAdapter.ConsultaViewHolder> {

    static class ConsultaViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCiudad;
        private final TextView tvDatos;
        private final TextView tvDescripcion;
        private final TextView tvFecha;

        public ConsultaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCiudad = itemView.findViewById(R.id.tvCiudad);
            tvDatos = itemView.findViewById(R.id.tvDatos);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }

    private final LayoutInflater inflater;
    private List<ClimaConsultas> consultas = new ArrayList<>();

    public ClimaConsultasListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ConsultaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item_consulta, parent, false);
        return new ConsultaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultaViewHolder holder, int position) {
        if (consultas != null) {
            ClimaConsultas actual = consultas.get(position);
            holder.tvCiudad.setText("📍 " + actual.getCiudad() + " (" + actual.getPais() + ")");
            holder.tvDatos.setText("🌡 " + actual.getTemperatura() + " °C  |  💨 " + actual.getViento() + " km/h");
            holder.tvDescripcion.setText(actual.getDescripcionClima());
            holder.tvFecha.setText(actual.getFechaConsulta());
        }
    }

    public void setConsultas(List<ClimaConsultas> nuevasConsultas) {
        this.consultas = nuevasConsultas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return consultas != null ? consultas.size() : 0;
    }

}
