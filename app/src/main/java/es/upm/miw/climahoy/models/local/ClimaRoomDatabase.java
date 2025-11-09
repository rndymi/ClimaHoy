package es.upm.miw.climahoy.models.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultasDAO;
import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultas;

@Database(entities = {ClimaConsultas.class}, version = 1, exportSchema = false)
public abstract class ClimaRoomDatabase extends RoomDatabase {

    public static final String BASE_DATOS = ClimaConsultas.TABLA + ".db";
    public abstract ClimaConsultasDAO climaConsultasDAO();
    private static volatile ClimaRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static ClimaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ClimaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ClimaRoomDatabase.class, BASE_DATOS)
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    // If you want to keep data through app restarts,
                    // comment out the following block
                    databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Populate the database in the background.
                            // If you want to start with more groups, just add them.
                            ClimaConsultasDAO dao = INSTANCE.climaConsultasDAO();
                        }
                    });
                }
            };


}
