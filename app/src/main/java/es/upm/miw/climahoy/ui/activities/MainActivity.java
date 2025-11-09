package es.upm.miw.climahoy.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import es.upm.miw.climahoy.R;
import es.upm.miw.climahoy.models.Ciudad;
import es.upm.miw.climahoy.models.CiudadList;
import es.upm.miw.climahoy.models.Clima;
import es.upm.miw.climahoy.models.ClimaActual;
import es.upm.miw.climahoy.models.HistorialClimaConsultada;
import es.upm.miw.climahoy.models.Locacion;
import es.upm.miw.climahoy.models.LocacionDireccion;
import es.upm.miw.climahoy.models.local.climaconsultas.ClimaConsultas;
import es.upm.miw.climahoy.models.local.ClimaRoomDatabase;
import es.upm.miw.climahoy.network.GeoAPI.GeoAPIService;
import es.upm.miw.climahoy.network.GeoAPI.RetrofitCiudadClient;
import es.upm.miw.climahoy.network.NominatimAPI.NominatimAPIService;
import es.upm.miw.climahoy.network.NominatimAPI.RetrofitNominatimClient;
import es.upm.miw.climahoy.network.WeatherAPI.RetrofitClimaClient;
import es.upm.miw.climahoy.network.WeatherAPI.WeatherAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MiW";

    private GeoAPIService geoAPIService;
    private WeatherAPIService weatherAPIService;
    private NominatimAPIService nominatimAPIService;

    private SharedPreferences preferencias;

    private ImageButton btnBuscar;
    private TextView tvRespuesta;
    private AutoCompleteTextView etConsultarClima;
    private ArrayAdapter<String> sugerenciasAdapter;
    private Map<String, Ciudad> mapaResultados = new HashMap<>();
    private Timer timer = new Timer();

    private boolean busquedaRealizada = false;
    private String ultimaBusqueda = "";

    private FusedLocationProviderClient fusedLocationClient;
    private long ultimaActualizacion = 0;
    private static final long COOLDOWN_MS = 3 * 60 * 1000;


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
        nominatimAPIService = RetrofitNominatimClient.getInstance().create(NominatimAPIService.class);

        preferencias = PreferenceManager.getDefaultSharedPreferences(this);

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

            if (textoActual.isEmpty()) {
                Toast.makeText(this, "Introduce una ciudad o municipio", Toast.LENGTH_SHORT).show();
                return;
            }

            if (busquedaRealizada && textoActual.equalsIgnoreCase(ultimaBusqueda)) {
                Log.i("MiW", "Click ignorado: misma búsqueda sin cambios.");
                return;
            }

            obtenerInfoBusqueda(v);
            ultimaBusqueda = textoActual;
            busquedaRealizada = true;
            btnBuscar.setEnabled(false);
            Log.i("MiW", "Click ejecutado: búsqueda realizada y botón deshabilitado.");
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //obtenerClimaUbicacionActual();

        MaterialCardView cardClimaActual = findViewById(R.id.cardClimaActual);
        cardClimaActual.setOnClickListener(v -> {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimaActualizacion >= COOLDOWN_MS) {
                obtenerClimaUbicacionActual();
                ultimaActualizacion = ahora;
            } else {
                Toast.makeText(this, "⏳ Espera unos minutos antes de actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        busquedaPorENTER();


        boolean darkMode = preferencias.getBoolean("pref_ModoOscuro", false);
        AppCompatDelegate.setDefaultNightMode(
                darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerClimaUbicacionActual();

        etConsultarClima = findViewById(R.id.etConsultarClima);
        if (etConsultarClima != null) {
            etConsultarClima.clearFocus();
            etConsultarClima.dismissDropDown();
        }
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

        } else if (itemId == R.id.opcUltimasConsultas) {
            Log.i(LOG_TAG, "opción MEJORES RESULTADOS");
            Log.i(LOG_TAG, "-------------------------------------------------------");
            Intent abrir = new Intent(this, UltimasConsultasActivity.class);
            startActivity(abrir);
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

    private void obtenerClimaUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                Log.i(LOG_TAG, "Lat: " + lat + " | Lon: " + lon);

                Call<Locacion> call = nominatimAPIService.getReverseNominatim( lat, lon, "json", 1, 18, "es");

                call.enqueue(new Callback<Locacion>() {
                    @Override
                    public void onResponse(Call<Locacion> call, Response<Locacion> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Locacion locacion = response.body();
                            LocacionDireccion direccion = locacion.getAddress();

                            final String[] nombreCiudad = {"Ubicación desconocida"};
                            if (direccion != null) {
                                if (direccion.getCity() != null) {
                                    nombreCiudad[0] = direccion.getCity();
                                } else if (direccion.getSuburb() != null) {
                                    nombreCiudad[0] = direccion.getSuburb();
                                } else if (direccion.getQuarter() != null) {
                                    nombreCiudad[0] = direccion.getQuarter();
                                }
                            }

                            Log.i(LOG_TAG, "Ciudad detectada: " + nombreCiudad[0]);

                            weatherAPIService.getCurrentWeather(
                                            lat,
                                            lon,
                                            "temperature,windspeed,winddirection,is_day,weathercode,cloudcover,precipitation"
                                    )
                                    .enqueue(new Callback<Clima>() {
                                        @Override
                                        public void onResponse(Call<Clima> call, Response<Clima> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                Clima clima = response.body();
                                                ClimaActual actual = clima.getClimaActual();

                                                String descripcion = obtenerDescripcionClima(
                                                        actual.getWeathercode(),
                                                        actual.getIsDay(),
                                                        actual.getCloudcover(),
                                                        actual.getPrecipitation()
                                                );

                                                ((TextView) findViewById(R.id.tvUbicacionActual))
                                                        .setText("📍 " + nombreCiudad[0] + ", " + direccion.getCountry());

                                                ((TextView) findViewById(R.id.tvDescripcionActual))
                                                        .setText(descripcion);

                                                ((TextView) findViewById(R.id.tvTemperaturaActual))
                                                        .setText("🌡️ " + actual.getTemperature() + " °C");

                                            } else {
                                                ((TextView) findViewById(R.id.tvUbicacionActual))
                                                        .setText("Error al obtener clima");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Clima> call, Throwable t) {
                                            Log.i(LOG_TAG, "Error en API de clima: " + t.getMessage());
                                        }
                                    });

                        } else {
                            ((TextView) findViewById(R.id.tvUbicacionActual))
                                    .setText("Ubicación no encontrada");
                            Log.i(LOG_TAG, "Respuesta vacía de Nominatim");
                        }
                    }

                    @Override
                    public void onFailure(Call<Locacion> call, Throwable t) {
                        ((TextView) findViewById(R.id.tvUbicacionActual))
                                .setText("Error al obtener ubicación");
                        Log.i(LOG_TAG, "Error en Nominatim: " + t.getMessage());
                    }
                });

            } else {
                Toast.makeText(this, "No se pudo obtener tu ubicación actual.", Toast.LENGTH_SHORT).show();
            }
        });
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
                Log.i(LOG_TAG, "Error al buscar sugerencias: " + t.getMessage());
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
            weatherAPIService.getCurrentWeather(lat, lon, "temperature,windspeed,winddirection,is_day,weathercode,cloudcover,precipitation").enqueue(new Callback<Clima>() {
                @Override
                public void onResponse(Call<Clima> call, Response<Clima> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Clima clima = response.body();

                        ClimaActual actual = clima.getClimaActual();
                        String descripcionClima = obtenerDescripcionClima(
                                clima.getClimaActual().getWeathercode(),
                                clima.getClimaActual().getIsDay(),
                                clima.getClimaActual().getCloudcover(),
                                clima.getClimaActual().getPrecipitation()
                        );
                        String iconoClima = obtenerIconoClima(actual.getWeathercode(), actual.getIsDay());

                        String info = iconoClima + " " + descripcionClima + "\n\n" +
                                "📍 " + ciudad.getName() + " (" + ciudad.getCountry() + ")\n" +
                                "🌡️ Temperatura: " + clima.getClimaActual().getTemperature() + " °C\n" +
                                "💨 Viento: " + clima.getClimaActual().getWindspeed() + " km/h\n" +
                                "🧭 Dirección: " + clima.getClimaActual().getWinddirection() + "°\n" +
                                "⏰ Hora: " + clima.getClimaActual().getTime() + "\n" +
                                "🌐 Zona horaria: " + clima.getTimezone() + "\n";

                                if (actual.getPrecipitation() != null && actual.getPrecipitation() > 0.1) {
                                    info += "☂️ Precipitación: " + actual.getPrecipitation() + " mm";
                                }
                        tvRespuesta.setText(info);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("historial_clima_consulta");

                        String fechaConsulta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                .format(new Date());

                        String descripcionFinal = descripcionClima;
                        final String descripcionFinalSafe = descripcionFinal;

                        HistorialClimaConsultada registro = new HistorialClimaConsultada(
                                ciudad.getName(),
                                ciudad.getCountry(),
                                clima.getClimaActual().getTemperature(),
                                clima.getClimaActual().getWindspeed(),
                                descripcionFinal,
                                fechaConsulta
                        );

                        ref.push().setValue(registro)
                                .addOnSuccessListener(aVoid -> Log.i(LOG_TAG, "Historial guardado en Firebase"))
                                .addOnFailureListener(e -> Log.i(LOG_TAG, "Error al guardar en Firebase", e));

                        new Thread(() -> {
                            ClimaConsultas climaConsultas = new ClimaConsultas(
                                    ciudad.getName(),
                                    ciudad.getCountry(),
                                    clima.getClimaActual().getTemperature(),
                                    clima.getClimaActual().getWindspeed(),
                                    descripcionFinalSafe,
                                    fechaConsulta
                            );

                            ClimaRoomDatabase.getDatabase(getApplicationContext())
                                    .climaConsultasDAO()
                                    .insert(climaConsultas);
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
            Log.i(LOG_TAG, "Error en obtenerClima: " + e.getMessage());
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

    private String obtenerDescripcionClima(int weathercode, int isDay, int cloudcover, Double precipitation) {
        if (precipitation != null && precipitation > 0.1) {
            if (precipitation < 0.6)
                return "Llovizna leve";
            else if (precipitation < 2.0)
                return "Lluvia ligera";
            else if (precipitation < 4.0)
                return "Lluvia moderada";
            else if (precipitation < 8.0)
                return "Lluvia fuerte";
            else if (precipitation < 15.0)
                return "Lluvia muy intensa";
            else
                return "Tormenta torrencial";
        }

        switch (weathercode) {
            case 45: case 48:
                return "Niebla";
            case 51: case 53: case 55:
                return "Llovizna";
            case 61: case 63: case 65:
                return "Lluvia";
            case 71: case 73: case 75:
                return "Nieve";
            case 95: case 96: case 99:
                return "Tormenta";
        }

        if (cloudcover >= 80) return "Muy nublado";
        if (cloudcover >= 50) return "Mayormente nublado";
        if (cloudcover >= 20) return "Parcialmente nublado";

        switch (weathercode) {
            case 0:
                return isDay == 1 ? "Despejado" : "Noche despejada";
            case 1:
                return "Mayormente despejado";
            case 2:
                return "Parcialmente soleado";
            case 3:
                return "Cubierto";
            default:
                return "Condición desconocida";
        }
    }

    private String obtenerIconoClima(Integer code, Integer isDay) {
        if (code == null) return "❔";

        switch (code) {
            case 0:
                return isDay != null && isDay == 1 ? "☀️" : "🌙";
            case 1:
                return isDay != null && isDay == 1 ? "🌤️" : "🌙☁️";
            case 2:
                return isDay != null && isDay == 1 ? "⛅" : "☁️🌙";
            case 3:
                return "☁️";
            case 45: case 48:
                return "🌫️";
            case 51: case 53: case 55:
                return "🌦️";
            case 56: case 57:
                return "🌧️❄️";
            case 61: case 63: case 65:
                return "🌧️";
            case 66: case 67:
                return "🌨️";
            case 71: case 73: case 75:
                return "❄️";
            case 77:
                return "🌨️";
            case 80: case 81: case 82:
                return "🌦️";
            case 85: case 86:
                return "🌨️❄️";
            case 95:
                return "⛈️";
            case 96: case 99:
                return "🌩️";
            default:
                return "❔";
        }
    }

}