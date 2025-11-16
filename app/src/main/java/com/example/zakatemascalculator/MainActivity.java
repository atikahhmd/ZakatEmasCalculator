package com.example.zakatemascalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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

    //UI components
    EditText etWeight, etValue;
    RadioButton rbKeep, rbWear;
    Button btnCalculate, btnReset;
    TextView tvTotalValue, tvZakatPayable, tvZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Set the custom toolbar
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

        //Calculate and reset button when clicking
        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnReset.setOnClickListener(v -> resetValue());

        //Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Method to calculate zakat
    private void calculateZakat() {

        //Check empty inputs - if empty display error message
        if (etWeight.getText().toString().isEmpty() || etValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter all inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        //Get user input values
        double weight = Double.parseDouble(etWeight.getText().toString());
        double goldValue = Double.parseDouble(etValue.getText().toString());

        //Determine uruf based on radio button input
        //Keep -> 85g , Wear -> 200g
        int uruf = rbKeep.isChecked() ? 85 : 200;

        //Total value of all gold
        double totalValue = weight * goldValue;

        //Amount of gold that exceeds uruf
        double zakatWeight = weight - uruf;
        if (zakatWeight < 0) zakatWeight = 0; //No negative zakat weight

        //Value of zakatpayable gold
        double zakatPayable = zakatWeight * goldValue;

        //2.5% zakat rate
        double zakat = zakatPayable * 0.025;

        //Display output to two decimal places
        tvTotalValue.setText("RM " + String.format("%.2f", totalValue));
        tvZakatPayable.setText("RM " + String.format("%.2f", zakatPayable));
        tvZakat.setText("RM " + String.format("%.2f", zakat));

    }

    //Method to reset all input and output fields
    private void resetValue() {
        etValue.setText("");
        etWeight.setText("");
        tvTotalValue.setText("");
        tvZakatPayable.setText("");
        tvZakat.setText("");
        Toast.makeText(getApplicationContext(), "Reset successful", Toast.LENGTH_SHORT).show();
    }

    //Load the toolbar menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Handle clicks on toolbar menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Share button to go to Share dialog
        if (item.getItemId() == R.id.menuShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app: https://github.com/atikahhmd/ZakatEmasCalculator.git");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;

        //About button to go to About page
        } else if (item.getItemId() == R.id.menuAbout) {
            startActivity(new Intent(this, com.example.zakatemascalculator.AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


