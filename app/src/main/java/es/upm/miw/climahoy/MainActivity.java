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

import org.jspecify.annotations.NonNull;

import java.util.List;

import javax.xml.transform.Result;

import es.upm.miw.climahoy.models.Ciudad;
import es.upm.miw.climahoy.models.CiudadList;
import es.upm.miw.climahoy.network.GeoAPIService;
import es.upm.miw.climahoy.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MiW";
    private EditText etConsultarClima;
    private ImageButton btnBuscar;
    private TextView tvRespuesta;
    private GeoAPIService geoAPIService;

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

        // Inicializamos Retrofit
        geoAPIService = RetrofitClient.getInstance().create(GeoAPIService.class);

        // Botón buscar
        btnBuscar.setOnClickListener(this::obtenerInfoBusqueda);

        /*
        tvUserInfo = findViewById(R.id.tvUserInfo);
        btnLogout = findViewById(R.id.btnLogout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userText = "Usuario: " +
                    (user.getDisplayName() != null ? user.getDisplayName() : "Sin nombre") +
                    "\nEmail: " + user.getEmail();
            tvUserInfo.setText(userText);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        btnLogout.setOnClickListener(v -> cerrarSesion());
        */

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

        geoAPIService.getCityByName(name, 10, "es").enqueue(new retrofit2.Callback<CiudadList>() {
    @Override
    public void onResponse(retrofit2.Call<CiudadList> call, retrofit2.Response<CiudadList> response) {
        if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
            List<Ciudad> ciudades = response.body().getResults();
            if (ciudades.isEmpty()) {
                tvRespuesta.setText("⚠️ No se encontraron resultados.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Ciudad c : ciudades) {
                sb.append("🏙️ ").append(c.getName())
                  .append(" (").append(c.getCountry()).append(")")
                  .append("\nLatitud: ").append(c.getLatitude())
                  .append("\nLongitud: ").append(c.getLongitude())
                  .append("\nElevación: ").append(c.getElevation())
                  .append("\nPoblación: ").append(c.getPopulation() != null ? c.getPopulation() : "N/A")
                  .append("\nZona horaria: ").append(c.getTimezone())
                  .append("\n\n");
            }
            tvRespuesta.setText(sb.toString());
        } else {
            tvRespuesta.setText("⚠️ No se encontraron resultados o la respuesta fue vacía.");
        }
    }

    @Override
    public void onFailure(retrofit2.Call<CiudadList> call, Throwable t) {
        tvRespuesta.setText("❌ Error de conexión: " + t.getMessage());
    }
});
    }

}