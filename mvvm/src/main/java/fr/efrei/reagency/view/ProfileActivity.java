package fr.efrei.reagency.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.efrei.reagency.R;
import fr.efrei.reagency.adapter.UsersAdapter;
import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.viewmodel.ProfileActivityViewModel;

final public class ProfileActivity
        extends AppCompatActivity
        implements OnClickListener, AdapterView.OnItemSelectedListener {

    //The tag used into this screen for the logs
    public static final String TAG = ProfileActivity.class.getSimpleName();

    private TextView currentAgent;
    private TextView loginEditText;
    private Spinner spinnerAgentList;

    private ProfileActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            setContentView(R.layout.activity_profile);
        } else {
            setContentView(R.layout.activity_profile_loolipop);
        }

        //We retrieve the EditText References
        spinnerAgentList = findViewById(R.id.spinnerAgentList);
        currentAgent = findViewById(R.id.currentAgent);
        loginEditText = findViewById(R.id.nameLogin);

        //We add the listener on the "save" button
        findViewById(R.id.btnLogin).setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(ProfileActivityViewModel.class);

        viewModel.loadUsers();

        spinnerAgentList.setOnItemSelectedListener(this);
        observeUsers();
        observeName();
    }

    private void observeName() {
        viewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                loginEditText.setText(name);
                currentAgent.setText(name);
            }
        });
    }

    @Override
    public void onClick(View v) {
        saveLogin();
    }

    private void saveLogin() {
        viewModel.saveLogin(loginEditText.getText().toString());

        startActivity(new Intent(ProfileActivity.this, PropertiesActivity.class));
        //we return to the previous screen
        // DO NOT DELETE THIS LINE BELOW
        // onBackPressed();
    }

    private void observeUsers() {
        viewModel.users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                final UsersAdapter usersAdapter = new UsersAdapter(users);
                //recyclerView.setAdapter(usersAdapter);
                String[] list = new String[usersAdapter.getItemCount()];
                List<User> u = users;
                //List<User> u = usersAdapter.users;
                for (int i = 0; i < usersAdapter.getItemCount(); i++) {
                    list[i] = u.get(i).name;
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAgentList.setAdapter(dataAdapter);
                String nameLogin = loginEditText.getText().toString();
                int i = dataAdapter.getPosition(nameLogin);
                if (i == -1) i = 0;
                spinnerAgentList.setSelection(i);
            }
        });
    }

    @Override
    public void onItemSelected(@NotNull AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerAgentList:
                String nameSelected = spinnerAgentList.getSelectedItem().toString();
                loginEditText.setText(nameSelected);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}