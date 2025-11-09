package es.upm.miw.climahoy.models.local.climaconsultas;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.upm.miw.climahoy.models.local.ClimaRoomDatabase;

public class ClimaConsultasRepositorio {

    private ClimaConsultasDAO mClimaConsultasDAO;
    private LiveData<List<ClimaConsultas>> historial;

    public ClimaConsultasRepositorio(Application application) {
        ClimaRoomDatabase db = ClimaRoomDatabase.getDatabase(application);
        mClimaConsultasDAO = db.climaConsultasDAO();
        historial = mClimaConsultasDAO.getAll();
    }

    public LiveData<List<ClimaConsultas>> getAllHistorial() {
        return historial;
    }

    public void insert(ClimaConsultas registro) {
        mClimaConsultasDAO.insert(registro);
    }

    public void deleteAll() {
        mClimaConsultasDAO.deleteAll();
    }

}
