package es.upm.miw.climahoy.models.local.climaconsultas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClimaConsultasDAO {

    @Query("SELECT * FROM " + ClimaConsultas.TABLA + " ORDER BY fechaConsulta DESC LIMIT 10")
    LiveData<List<ClimaConsultas>> getUltimasConsultas();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ClimaConsultas climaConsultas);

    @Query("DELETE FROM " + ClimaConsultas.TABLA )
    void deleteAll();

}
