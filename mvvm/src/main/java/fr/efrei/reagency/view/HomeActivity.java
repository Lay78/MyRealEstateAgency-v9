package fr.efrei.reagency.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.efrei.reagency.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnWelcome;
    private boolean supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnWelcome = findViewById(R.id.btnWelcome);
        btnWelcome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UsersActivity.class));
            }
        });
    }
}