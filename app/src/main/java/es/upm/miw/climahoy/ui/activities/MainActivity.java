package es.upm.miw.climahoy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import es.upm.miw.climahoy.R;
import es.upm.miw.climahoy.models.Ciudad;
import es.upm.miw.climahoy.models.CiudadList;
import es.upm.miw.climahoy.models.Clima;
import es.upm.miw.climahoy.models.ClimaActual;
import es.upm.miw.climahoy.models.HistorialClimaConsultada;
import es.upm.miw.climahoy.models.local.ClimaHistorial;
import es.upm.miw.climahoy.models.local.ClimaRepositorio;
import es.upm.miw.climahoy.models.local.ClimaRoomDatabase;
import es.upm.miw.climahoy.network.GeoAPI.GeoAPIService;
import es.upm.miw.climahoy.network.GeoAPI.RetrofitCiudadClient;
import es.upm.miw.climahoy.network.WeatherAPI.RetrofitClimaClient;
import es.upm.miw.climahoy.network.WeatherAPI.WeatherAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MiW";

    private GeoAPIService geoAPIService;
    private WeatherAPIService weatherAPIService;

    private ImageButton btnBuscar;
    private TextView tvRespuesta;
    private AutoCompleteTextView etConsultarClima;
    private ArrayAdapter<String> sugerenciasAdapter;
    private Map<String, Ciudad> mapaResultados = new HashMap<>();
    private Timer timer = new Timer();

    private boolean busquedaRealizada = false;
    private String ultimaBusqueda = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inicializarInsets();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = (user.getDisplayName() != null ? user.getDisplayName() : "Sin nombre");
            String email = user.getEmail() != null ? user.getEmail() : "Sin email";
            Toast.makeText(this, "Usuario: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        etConsultarClima = findViewById(R.id.etConsultarClima);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvRespuesta = findViewById(R.id.tvRespuesta);

        geoAPIService = RetrofitCiudadClient.getInstance().create(GeoAPIService.class);
        weatherAPIService = RetrofitClimaClient.getInstance().create(WeatherAPIService.class);

        sugerenciasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        etConsultarClima.setAdapter(sugerenciasAdapter);

        etConsultarClima.addTextChangedListener(new TextWatcher() {
            private final long DELAY = 600;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (busquedaRealizada && !s.toString().trim().equalsIgnoreCase(ultimaBusqueda.trim())) {
                    btnBuscar.setEnabled(true);
                    busquedaRealizada = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> buscarSugerencias(s.toString()));
                    }
                }, DELAY);
            }
        });

        etConsultarClima.setOnItemClickListener((parent, view, position, id) -> {
            String seleccion = (String) parent.getItemAtPosition(position);
            Ciudad ciudad = mapaResultados.get(seleccion);
            if (ciudad != null) {
                obtenerClima(ciudad.getLatitude(), ciudad.getLongitude(), ciudad);
                btnBuscar.setEnabled(false);
                ultimaBusqueda = seleccion;
                busquedaRealizada = true;
            }
        });

        btnBuscar.setOnClickListener(v -> {
            String textoActual = etConsultarClima.getText().toString().trim();

            // Evita ejecutar si está vacío
            if (textoActual.isEmpty()) {
                Toast.makeText(this, "Introduce una ciudad o municipio", Toast.LENGTH_SHORT).show();
                return;
            }

            // Evita búsqueda repetida sin cambios
            if (busquedaRealizada && textoActual.equalsIgnoreCase(ultimaBusqueda)) {
                Log.i("MiW", "Click ignorado: misma búsqueda sin cambios.");
                return;
            }

            // Ejecuta búsqueda y deshabilita botón
            obtenerInfoBusqueda(v);
            ultimaBusqueda = textoActual;
            busquedaRealizada = true;
            btnBuscar.setEnabled(false);
            Log.i("MiW", "Click ejecutado: búsqueda realizada y botón deshabilitado.");
        });

        busquedaPorENTER();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.opcAjustes) {
            Log.i(LOG_TAG, "opción AJUSTES");
            Log.i(LOG_TAG, "-------------------------------------------------------");
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;

        } else if (itemId == R.id.opcAcercaDe) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about_title)
                    .setMessage(R.string.about_message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return true;

        } else if (itemId == R.id.opcCerrarSesion) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    });
            return true;

        } else {

            return super.onOptionsItemSelected(item);
        }
    }

    private void buscarSugerencias(String query) {
        if (query.trim().isEmpty() || query.length() < 2) return;

        geoAPIService.getCityByName(query, 100, "es").enqueue(new Callback<CiudadList>() {
            @Override
            public void onResponse(Call<CiudadList> call, Response<CiudadList> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    List<Ciudad> ciudades = response.body().getResults();
                    List<String> nombres = new ArrayList<>();
                    mapaResultados.clear();

                    String[] palabras = query.toLowerCase().split(" ");

                    for (Ciudad c : ciudades) {
                        String textoCompleto = (c.getName() + " " + c.getAdmin1() + " " + c.getCountry()).toLowerCase();

                        boolean coincide = true;
                        for (String p : palabras) {
                            if (!textoCompleto.contains(p)) {
                                coincide = false;
                                break;
                            }
                        }
                        if (!coincide) continue;

                        String nombreMostrado = c.getName();
                        if (c.getAdmin1() != null && !c.getAdmin1().isEmpty())
                            nombreMostrado += " (" + c.getAdmin1();
                        if (c.getCountry() != null && !c.getCountry().isEmpty())
                            nombreMostrado += ", " + c.getCountry() + ")";
                        else
                            nombreMostrado += ")";

                        nombres.add(nombreMostrado);
                        mapaResultados.put(nombreMostrado, c);
                    }

                    sugerenciasAdapter.clear();
                    sugerenciasAdapter.addAll(nombres);
                    sugerenciasAdapter.notifyDataSetChanged();
                    etConsultarClima.showDropDown();
                }
            }

            @Override
            public void onFailure(Call<CiudadList> call, Throwable t) {
                Log.e(LOG_TAG, "Error al buscar sugerencias: " + t.getMessage());
            }
        });
    }

    public void obtenerInfoBusqueda(View view) {
        String name = etConsultarClima.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Introduce una ciudad o municipio", Toast.LENGTH_SHORT).show();
            return;
        }

        geoAPIService.getCityByName(name, 100, "es").enqueue(new Callback<CiudadList>() {
            @Override
            public void onResponse(Call<CiudadList> call, Response<CiudadList> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    List<Ciudad> ciudades = response.body().getResults();
                    if (ciudades.isEmpty()) {
                        tvRespuesta.setText("⚠️ No se encontraron resultados.");
                        return;
                    }

                    Ciudad ciudad = ciudades.get(0);
                    obtenerClima(ciudad.getLatitude(), ciudad.getLongitude(), ciudad);

                } else {
                    tvRespuesta.setText("⚠️ No se encontraron resultados o la respuesta fue vacía.");
                }
            }

            @Override
            public void onFailure(Call<CiudadList> call, Throwable t) {
                tvRespuesta.setText("❌ Error de conexión: " + t.getMessage());
            }
        });
    }

    private void obtenerClima(double lat, double lon, Ciudad ciudad) {
        try {
            weatherAPIService.getCurrentWeather(lat, lon, true).enqueue(new Callback<Clima>() {
                @Override
                public void onResponse(Call<Clima> call, Response<Clima> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Clima clima = response.body();

                        String info = "📍 " + ciudad.getName() + " (" + ciudad.getCountry() + ")\n" +
                                "🌡️ Temperatura: " + clima.getClimaActual().getTemperature() + " °C\n" +
                                "💨 Viento: " + clima.getClimaActual().getWindspeed() + " km/h\n" +
                                "🧭 Dirección: " + clima.getClimaActual().getWinddirection() + "°\n" +
                                "⏰ Hora: " + clima.getClimaActual().getTime() + "\n" +
                                "🌐 Zona horaria: " + clima.getTimezone();
                        tvRespuesta.setText(info);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("historial_clima_consulta");

                        String fechaConsulta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                .format(new Date());

                        HistorialClimaConsultada registro = new HistorialClimaConsultada(
                                ciudad.getName(),
                                ciudad.getCountry(),
                                clima.getClimaActual().getTemperature(),
                                clima.getClimaActual().getWindspeed(),
                                fechaConsulta
                        );

                        ref.push().setValue(registro)
                                .addOnSuccessListener(aVoid -> Log.i(LOG_TAG, "Historial guardado en Firebase"))
                                .addOnFailureListener(e -> Log.e(LOG_TAG, "Error al guardar en Firebase", e));

                        new Thread(() -> {
                            ClimaHistorial climaHistorial = new ClimaHistorial(
                                    ciudad.getName(),
                                    ciudad.getCountry(),
                                    clima.getClimaActual().getTemperature(),
                                    clima.getClimaActual().getWindspeed(),
                                    fechaConsulta
                            );
                            ClimaRoomDatabase.getDatabase(getApplicationContext())
                                    .climaDAO()
                                    .insert(climaHistorial);
                        }).start();

                    } else {
                        tvRespuesta.setText("⚠️ No se pudo obtener el clima actual.");
                    }
                    btnBuscar.setEnabled(true);
                }

                @Override
                public void onFailure(Call<Clima> call, Throwable t) {
                    tvRespuesta.setText("❌ Error al obtener el clima: " + t.getMessage());
                    btnBuscar.setEnabled(true);
                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error en obtenerClima: " + e.getMessage());
        }
    }

    private void inicializarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void busquedaPorENTER() {
        etConsultarClima.setOnEditorActionListener((v, actionId, event) -> {
            boolean esEnter = (actionId == EditorInfo.IME_ACTION_SEARCH) ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN);

            if (esEnter) {
                String textoActual = etConsultarClima.getText().toString().trim();

                if (textoActual.isEmpty()) {
                    return true;
                }

                if (busquedaRealizada && textoActual.equalsIgnoreCase(ultimaBusqueda)) {
                    Log.i(LOG_TAG, "ENTER ignorado: misma búsqueda sin cambios.");
                    return true;
                }

                // Ejecutar búsqueda
                obtenerInfoBusqueda(v);
                ultimaBusqueda = textoActual;
                busquedaRealizada = true;
                btnBuscar.setEnabled(false);
                Log.i(LOG_TAG, "ENTER ejecutado: búsqueda realizada y botón deshabilitado.");
                return true;
            }

            return false;
        });

        etConsultarClima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoNuevo = s.toString().trim();
                if (busquedaRealizada && !textoNuevo.equalsIgnoreCase(ultimaBusqueda)) {
                    btnBuscar.setEnabled(true);
                    busquedaRealizada = false;
                    Log.i(LOG_TAG, "Texto modificado: botón habilitado nuevamente.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}