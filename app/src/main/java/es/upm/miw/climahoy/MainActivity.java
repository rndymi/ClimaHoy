package es.upm.miw.climahoy;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserInfo;
    private Button btnLogout;

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
    }

    private void cerrarSesion() {
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(task -> {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    });
                });

    }

}