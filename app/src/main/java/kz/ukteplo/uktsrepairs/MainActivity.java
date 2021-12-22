package kz.ukteplo.uktsrepairs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import kz.ukteplo.uktsrepairs.data.models.User;
import kz.ukteplo.uktsrepairs.data.viewmodels.DistrictViewModel;
import kz.ukteplo.uktsrepairs.data.viewmodels.RouteViewModel;
import kz.ukteplo.uktsrepairs.fragments.LoginFragment;
import kz.ukteplo.uktsrepairs.fragments.RegistrationFragment;
import kz.ukteplo.uktsrepairs.fragments.RepairFragment;
import kz.ukteplo.uktsrepairs.fragments.RepairsListFragment;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.FragmentDataListener;
import kz.ukteplo.uktsrepairs.utils.OnDialogClickListener;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;

public class MainActivity extends AppCompatActivity implements FragmentDataListener {
    private ConfigManager configManager;
    public RepairsListFragment repairsListFragment = new RepairsListFragment();
    private Intent intent;
    private Menu mainMenu;
    private OnDialogClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //getSupportActionBar().hide();

        TextView loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        configManager = ((UKTSApplication) getApplication()).getConfigManager();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase token", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    /* Get new FCM registration token */
                    String token = task.getResult();
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d("firebase token", msg);
                    /* Пишем токен в настройки */
                    RetrofitManager retrofitManager = RetrofitManager.getInstance(configManager);
                    retrofitManager.setToken(token);
                    loading.setVisibility(View.GONE);
                    showAuthScreen();

                });
    }

    private void showAuthScreen() {
        if (configManager.isRegistred()) {
            LoginFragment login = new LoginFragment();
            login.user = configManager.getUser();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, login, null)
                    //.addToBackStack(null)
                    .commit();
        } else {
            RegistrationFragment registration = new RegistrationFragment();
            registration.configManager = configManager;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, registration, null)
                    //.addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mainMenu = menu;
        /*if (configManager.getRole() != null) {
            if (configManager.getRole().equals("3")) {
                getMenuInflater().inflate(R.menu.main, menu);
            }
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (configManager.getRole() != null) {
            if (configManager.getRole().equals("3")) {
                RepairFragment fragment = new RepairFragment();
                listener = (OnDialogClickListener) fragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, fragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onRestart() {
        super.onRestart();

        /*if (!configManager.isLogined()) {
            showAuthScreen();
        }*/
    }

    @Override
    public void signUp(boolean result, String errorMsg, User user) {
        LoginFragment login = new LoginFragment();
        if (result) {
            login.user = user;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, login, null)
                    //.addToBackStack(null)
                    .commit();
            /*if (user != null) {
                login.login(user.getLogin(), user.getPassword());
            }*/
        }
    }

    @Override
    public void selectRepair(String id) {
        RepairFragment fragment = new RepairFragment(id);
        listener = (OnDialogClickListener) fragment;
        fragment.repairsListFragment = repairsListFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                //.remove(repairsListFragment)
                .addToBackStack(null)
                .commit();
        //repairsListFragment = null;
    }

    @Override
    public void selectRepair(String id, String isApproved, String actionType) {
        RepairFragment fragment = new RepairFragment(id);
        listener = (OnDialogClickListener) fragment;
        fragment.repairsListFragment = repairsListFragment;
        if (!isApproved.isEmpty()) fragment.isApproved = isApproved;
        if (!actionType.isEmpty()) fragment.actionType = actionType;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void alert(Boolean result) {
        listener.alert(result);
    }

    @Override
    public void signIn(boolean result, String errorMsg) {
        if (result) {
            if (configManager.getRole().equals("3")) {
                getMenuInflater().inflate(R.menu.main, mainMenu);
            }
            DistrictViewModel viewModel = ViewModelProviders.of(this).get(DistrictViewModel.class);
            viewModel.downloadDistricts();
            RouteViewModel viewModel1 = ViewModelProviders.of(this).get(RouteViewModel.class);
            viewModel1.downloadRoutes();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, repairsListFragment, null)
                    .commit();
            getSupportActionBar().show();

            intent = getIntent();
            String id = intent.getStringExtra("repairId");
            String actionType = intent.getStringExtra("actionType");
            if (id != null) {
                selectRepair(id, "", actionType);
            }
        } else Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}