package es.upm.miw.climahoy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.gson.Gson;

import org.jspecify.annotations.NonNull;

import java.util.List;

import es.upm.miw.climahoy.models.Ciudad;
import es.upm.miw.climahoy.models.CiudadList;
import es.upm.miw.climahoy.models.Clima;
import es.upm.miw.climahoy.models.ClimaActual;
import es.upm.miw.climahoy.network.GeoAPIService;
import es.upm.miw.climahoy.network.RetrofitCiudadClient;
import es.upm.miw.climahoy.network.RetrofitClimaClient;
import es.upm.miw.climahoy.network.WeatherAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MiW";

    private GeoAPIService geoAPIService;
    private WeatherAPIService weatherAPIService;

    private EditText etConsultarClima;
    private ImageButton btnBuscar;
    private TextView tvRespuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        btnBuscar.setOnClickListener(this::obtenerInfoBusqueda);

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
            Toast.makeText(this, "Abrir ajustes", Toast.LENGTH_SHORT).show();
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

    public void obtenerInfoBusqueda(View view) {
        String name = etConsultarClima.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Introduce una ciudad o municipio", Toast.LENGTH_SHORT).show();
            return;
        }

        geoAPIService.getCityByName(name, 1, "es").enqueue(new Callback<CiudadList>() {
            @Override
            public void onResponse(Call<CiudadList> call, Response<CiudadList> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    List<Ciudad> ciudades = response.body().getResults();
                    if (ciudades.isEmpty()) {
                        tvRespuesta.setText("⚠️ No se encontraron resultados.");
                        return;
                    }

                    // Tomamos la primera ciudad encontrada
                    Ciudad ciudad = ciudades.get(0);
                    double lat = ciudad.getLatitude();
                    double lon = ciudad.getLongitude();

                    // Ahora llamamos a la segunda API con esas coordenadas
                    weatherAPIService.getCurrentWeather(lat, lon, true).enqueue(new Callback<Clima>() {
                        @Override
                        public void onResponse(Call<Clima> call, Response<Clima> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Clima clima = response.body();
                                ClimaActual actual = clima.getClimaActual();
                                Log.d(LOG_TAG, "Response: " + new Gson().toJson(response.body()));


                                String info = "🌆 " + ciudad.getName() + " (" + ciudad.getCountry() + ")\n"
                                        + "🌡 Temperatura: " + actual.getTemperature() + " °C\n"
                                        + "💨 Viento: " + actual.getWindspeed() + " km/h\n"
                                        + "🧭 Dirección: " + actual.getWinddirection() + "°\n"
                                        + "🕒 Hora: " + actual.getTime() + "\n"
                                        + "🗺 Zona horaria: " + clima.getTimezone();

                                tvRespuesta.setText(info);
                            } else {
                                tvRespuesta.setText("⚠️ No se pudo obtener el clima actual.");
                            }
                        }

                        @Override
                        public void onFailure(Call<Clima> call, Throwable t) {
                            tvRespuesta.setText("❌ Error al obtener el clima: " + t.getMessage());
                        }
                    });

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

}