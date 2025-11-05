package es.upm.miw.climahoy.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.upm.miw.climahoy.models.local.ClimaHistorial;
import es.upm.miw.climahoy.models.local.ClimaRepositorio;

public class ClimaViewModel extends AndroidViewModel {

    private ClimaRepositorio mRepository;
    private LiveData<List<ClimaHistorial>> mAllHistorial;


    public ClimaViewModel(Application application) {
        super(application);
        mRepository = new ClimaRepositorio(application);
        mAllHistorial = mRepository.getAllHistorial();
    }

    public LiveData<List<ClimaHistorial>> getHistorial() {
        return mAllHistorial;
    }

    public void insert(ClimaHistorial registro) {
        mRepository.insert(registro);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}
