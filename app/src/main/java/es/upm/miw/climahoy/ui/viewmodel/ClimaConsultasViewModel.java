package es.upm.miw.climahoy.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultas;
import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultasRepositorio;

public class ClimaConsultasViewModel extends AndroidViewModel {

    private final ClimaConsultasRepositorio repositorio;
    private final LiveData<List<ClimaConsultas>> ultimasConsultas;

    public ClimaConsultasViewModel(@NonNull Application application) {
        super(application);
        repositorio = new ClimaConsultasRepositorio(application);
        ultimasConsultas = repositorio.getUltimasConsultas();
    }

    public LiveData<List<ClimaConsultas>> getUltimasConsultas() {
        return ultimasConsultas;
    }

}
