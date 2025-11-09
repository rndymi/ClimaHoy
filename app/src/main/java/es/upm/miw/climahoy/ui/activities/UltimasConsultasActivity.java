package es.upm.miw.climahoy.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.climahoy.R;
import es.upm.miw.climahoy.ui.adapters.ClimaConsultasListAdapter;
import es.upm.miw.climahoy.ui.viewmodel.ClimaConsultasViewModel;

public class UltimasConsultasActivity extends AppCompatActivity {

    private ClimaConsultasViewModel climaConsultasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimasconsultas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewConsultas);
        final ClimaConsultasListAdapter adapter = new ClimaConsultasListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        climaConsultasViewModel = new ViewModelProvider(this).get(ClimaConsultasViewModel.class);

        climaConsultasViewModel.getUltimasConsultas().observe(this, adapter::setConsultas);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
