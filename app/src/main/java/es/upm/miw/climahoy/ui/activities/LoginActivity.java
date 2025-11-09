package es.upm.miw.climahoy.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import es.upm.miw.climahoy.R;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            startSignInFlow();
        }
    }

    private void startSignInFlow() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_launcher_foreground)
                .setTheme(R.style.Theme_ClimaHoy)
                .setTosAndPrivacyPolicyUrls(
                        "https://firebase.google.com/terms/",
                        "https://firebase.google.com/policies/privacy"
                )
                .setIsSmartLockEnabled(false)
                .setAlwaysShowSignInMethodScreen(true)
                .setLockOrientation(true)
                .build();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (resultCode == RESULT_OK && user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                if (response == null) finish();
            }
        }
    }

}
