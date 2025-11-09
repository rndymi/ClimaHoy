package es.upm.miw.climahoy.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

import es.upm.miw.climahoy.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingActivityFragment())
                .commit();

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class SettingActivityFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(final Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferencias, rootKey);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(prefChangeListener);
        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(prefChangeListener);
            super.onPause();
        }

        private final SharedPreferences.OnSharedPreferenceChangeListener prefChangeListener =
                (sharedPreferences, key) -> {
                    if ("pref_ModoOscuro".equals(key)) {
                        boolean dark = sharedPreferences.getBoolean(key, false);
                        int mode = dark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;

                        if (AppCompatDelegate.getDefaultNightMode() != mode) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                AppCompatDelegate.setDefaultNightMode(mode);
                            }, 150);
                        }
                    }
                };

    }


}
