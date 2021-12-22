package kz.ukteplo.uktsrepairs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.data.models.User;
import kz.ukteplo.uktsrepairs.data.viewmodels.SignupViewModel;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.FragmentDataListener;

public class RegistrationFragment extends Fragment {
//    private View fragmentView;
    private SignupViewModel viewModel;
    public ConfigManager configManager;
    private FragmentDataListener listener;

    public RegistrationFragment() {
        super(R.layout.fragment_register);
        //configManager = ((UKTSApplication) getActivity().getApplication()).getConfigManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        fragmentView = view;
        Button btnReg = view.findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(v -> signup(view));

        Button btn_signin = view.findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(v -> {
            LoginFragment login = new LoginFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, login, null)
                    .addToBackStack(null)
                    .commit();
            configManager.setRegistred(true);
        });
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        listener = (FragmentDataListener) activity;
    }

    public void signup(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);

        EditText etUsername = view.findViewById(R.id.login);
        String username = etUsername.getText().toString();

        EditText etPassword = view.findViewById(R.id.pass);
        String password = etPassword.getText().toString();

        EditText etPassword2 = view.findViewById(R.id.confrm_pass);
        String password2 = etPassword2.getText().toString();

        EditText etEmail = view.findViewById(R.id.email);
        String email = etEmail.getText().toString();

        Boolean dataIsChecked = checkUserData(username, password, password2, email);

        if (dataIsChecked) {
            TextView tvError = view.findViewById(R.id.tvError);
            if (tvError.getVisibility() == View.VISIBLE) {
                tvError.setText("");
                tvError.setVisibility(View.GONE);
            }

            view.findViewById(R.id.btn_reg).setEnabled(false);
            viewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
            viewModel.initData(username, password, email, "ru");
            viewModel.signUp()
                    .observe(getViewLifecycleOwner(), userResponse -> {
                        if (userResponse.getSuccess()) {
                            User user = new User(username, password, email, "ru");
                            configManager.setUser(user);
                            configManager.saveUser();
                            configManager.setRegistred(true);
                            listener.signUp(true,"", user);
                        } else {
                            showErrorText(view, userResponse.getErrorDescription());
                        }
                    });
            view.findViewById(R.id.btn_reg).setEnabled(true);
        } else {
            showErrorText(view, "Введены некорректные данные для регистрации!");
        }
    }

    public Boolean checkUserData(String u, String p, String p2, String e) {
        if (u.length() != 4) {
            return false;
        }
        if (p.length() < 6 || !p.equals(p2)) {
            return false;
        }
        return e.contains("@") && e.contains(".");
    }

    public void showErrorText(View view, String e) {
        TextView tvError = view.findViewById(R.id.tvError);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(e);
    }
}
