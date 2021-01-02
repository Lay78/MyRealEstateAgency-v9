package fr.efrei.reagency.view;

import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import fr.efrei.reagency.R;
import fr.efrei.reagency.adapter.UsersAdapter;
import fr.efrei.reagency.bo.User;
import fr.efrei.reagency.preferences.AppPreferences;
import fr.efrei.reagency.viewmodel.UsersActivityViewModel;
import fr.efrei.reagency.viewmodel.UsersActivityViewModel.OrderState;

import static fr.efrei.reagency.adapter.UsersAdapter.UserViewHolder.curSelectedUser;
import static fr.efrei.reagency.adapter.UsersAdapter.UserViewHolder.viewUserSelected;

final public class UsersActivity
        extends AppCompatActivity
//        implements OnClickListener
{

    //The tag used into this screen for the logs
    public static final String TAG = UsersActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private UsersActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        viewUserSelected = false;
        getSupportActionBar().setTitle(getResources().getString(R.string.user_list));

        //We configure the click on the fab
        findViewById(R.id.fabAddUser).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(UsersActivity.this, AddUserActivity.class));
            }
        });

        findViewById(R.id.fabViewUser).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (viewUserSelected) {
                    final Intent intent = new Intent(UsersActivity.this, UserDetailActivity.class);
                    intent.putExtra(UserDetailActivity.USER_EXTRA, curSelectedUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(UsersActivity.this, R.string.user_not_selected, Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.fabEditUser).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (viewUserSelected) {
                    startActivity(new Intent(UsersActivity.this, EditUserActivity.class));
                } else {
                    Toast.makeText(UsersActivity.this, R.string.user_not_selected, Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.fabProperties).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String userLogin = AppPreferences.getUserLogin(getApplication());
                if (userLogin != null/*!userLogin.equals("")*/) {
                    startActivity(new Intent(UsersActivity.this, PropertiesActivity.class));
                } else {
                    startActivity(new Intent(UsersActivity.this, ProfileActivity.class));
                }
            }
        });
        viewModel = new ViewModelProvider(this).get(UsersActivityViewModel.class);

        observeUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewUserSelected = false;
        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        viewModel.loadUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //We add the "menu_user" to the current activity
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //We handle the click on a menu item
        if (item.getItemId() == R.id.sortUserAsc) {
            viewModel.updateOrder(OrderState.SortAsc);
        } else if (item.getItemId() == R.id.sortUserDes) {
            viewModel.updateOrder(OrderState.SortDes);
        } else if (item.getItemId() == R.id.noSortUser) {
            viewModel.updateOrder(OrderState.NoSort);
        } else if (item.getItemId() == R.id.profile) {
            final Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_user_quit) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    private void observeUsers() {
        viewModel.users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                final UsersAdapter usersAdapter = new UsersAdapter(users);
                recyclerView.setAdapter(usersAdapter);
            }
        });
    }

}