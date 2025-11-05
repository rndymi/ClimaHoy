package es.upm.miw.climahoy.models.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClimaRepositorio {

    private ClimaDAO mClimaDAO;
    private LiveData<List<ClimaHistorial>> historial;

    public ClimaRepositorio(Application application) {
        ClimaRoomDatabase db = ClimaRoomDatabase.getDatabase(application);
        mClimaDAO = db.climaDAO();
        historial = mClimaDAO.getAll();
    }

    public LiveData<List<ClimaHistorial>> getAllHistorial() {
        return historial;
    }

    public void insert(ClimaHistorial registro) {
        mClimaDAO.insert(registro);
    }

    public void deleteAll() {
        mClimaDAO.deleteAll();
    }

}
