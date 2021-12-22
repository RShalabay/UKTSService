package kz.ukteplo.uktsrepairs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.data.models.User;
import kz.ukteplo.uktsrepairs.data.viewmodels.SigninViewModel;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.FragmentDataListener;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;

public class LoginFragment extends Fragment {
    private SigninViewModel viewModel;
    private FragmentDataListener listener;
    public User user;
    private View currentView;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentView = view;

        ConfigManager configManager = new ConfigManager(getContext());
        if (user != null) {
            EditText etUsername = currentView.findViewById(R.id.login);
            EditText etPassword = currentView.findViewById(R.id.pass);

            etUsername.setText(user.getLogin());
            etPassword.setText(user.getPassword());

            login(user.getLogin(), user.getPassword());
        }

        Button btnlogin = view.findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(v -> signIn());
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        listener = (FragmentDataListener) activity;
    }

    private void signIn() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

        EditText etUsername = currentView.findViewById(R.id.login);
        String username = etUsername.getText().toString();

        EditText etPassword = currentView.findViewById(R.id.pass);
        String password = etPassword.getText().toString();

        login(username, password);
    }

    public void login(String login, String pass) {
        Button btnlogin = currentView.findViewById(R.id.btn_login);
        btnlogin.setEnabled(false);

        if (login != null && pass !=null) {
            viewModel = ViewModelProviders.of(this).get(SigninViewModel.class);
            viewModel.initData(login, pass);
            viewModel.signIn().observe(getViewLifecycleOwner(), authResponse -> {
                if (authResponse.isSuccess()) {

                    ConfigManager configManager = ((UKTSApplication) getActivity().getApplication()).getConfigManager();
                    configManager.setUser(new User(login, pass));
                    configManager.saveUser();
                    configManager.setRole(authResponse.getRole());
                    configManager.saveRole();

                    listener.signIn(true, "");
                } else {
                    showErrorText(currentView, authResponse.getErrorDescription());
                }

            });

        }
        btnlogin.setEnabled(true);
    }

    public void showErrorText(View view, String e) {
        TextView tvError = view.findViewById(R.id.tvErrorLogin);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(e);
    }
}
