package es.upm.miw.climahoy.models.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClimaDAO {

    @Query("SELECT * FROM " + ClimaHistorial.TABLA + " ORDER BY fechaConsulta DESC")
    LiveData<List<ClimaHistorial>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ClimaHistorial climaHistorial);

    @Query("DELETE FROM " + ClimaHistorial.TABLA )
    void deleteAll();

}
