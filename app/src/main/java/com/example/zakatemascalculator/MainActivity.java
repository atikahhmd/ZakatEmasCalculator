package com.example.zakatemascalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    RadioButton rbKeep, rbWear;
    Button btnCalculate, btnReset;
    TextView tvTotalValue, tvZakatPayable, tvZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.zakat_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize components - assigned to object
        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);
        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvZakatPayable = findViewById(R.id.tvZakatPayable);
        tvZakat = findViewById(R.id.tvZakat);

        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnReset.setOnClickListener(v -> resetValue());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void calculateZakat() {
        if (etWeight.getText().toString().isEmpty() || etValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter all inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(etWeight.getText().toString());
        double goldValue = Double.parseDouble(etValue.getText().toString());

        int uruf = rbKeep.isChecked() ? 85 : 200;

        double totalValue = weight * goldValue;
        double zakatWeight = weight - uruf;
        if (zakatWeight < 0) zakatWeight = 0;

        double zakatPayable = zakatWeight * goldValue;
        double zakat = zakatPayable * 0.025;

        tvTotalValue.setText("RM" + totalValue);
        tvZakatPayable.setText("RM" + zakatPayable);
        tvZakat.setText("RM" + zakat);
    }

    private void resetValue() {
        etValue.setText("");
        etWeight.setText("");
        tvTotalValue.setText("");
        tvZakatPayable.setText("");
        tvZakat.setText("");
        Toast.makeText(getApplicationContext(), "Reset successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app: https://github.com/yourusername/zakatapp");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (item.getItemId() == R.id.menuAbout) {

            startActivity(new Intent(this, com.example.zakatemascalculator.AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


