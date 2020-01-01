package com.orhan.project.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orhan.project.R;
import com.orhan.project.databinding.ActivityMainBinding;
import com.orhan.project.model.User;
import com.orhan.project.util.OnDataPass;
import com.orhan.project.util.UserRepository;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity implements OnDataPass {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
    }


    public void confirmLogin(View view) {

        // login
        UserRepository repository = new UserRepository(getApplicationContext());
        repository.getUserList(this);
    }

    @Override
    public void OnTaskComplete(ArrayList<User> users) {

        if (!validateEmail() | !validatePassword()) {
            return;
        }

        String email = binding.textFieldEmail.getEditText().getText().toString();
        String password = binding.textFieldPassword.getEditText().getText().toString();

        for (User user : users) {
            if (user.getUsername().equals(email) && user.getPassword().equals(password)) {
                Intent intent = new Intent(MainActivity.this, SuccessfulActivity.class);
                startActivity(intent);
                return;
            }
        }

        Toast.makeText(getApplicationContext(), "Kullanıcı adı ya da şifresi yanlış", Toast.LENGTH_SHORT).show();
    }

    public void confirmSignUp(View view) {
        // sign up fragment
        SignUpDialog dialog = new SignUpDialog();
        dialog.show(getSupportFragmentManager(), "sign up");
    }

    private boolean validateEmail() {

        String email = binding.textFieldEmail.getEditText().getText().toString();

        if (email.isEmpty()) {
            binding.textFieldEmail.setError("Field cannot be empty!");
            return false;
        }

        binding.textFieldEmail.setError(null);
        return true;
    }

    private boolean validatePassword() {

        String password = binding.textFieldPassword.getEditText().getText().toString();

        if (password.isEmpty()) {
            binding.textFieldPassword.setError("Field cannot be empty!");
            return false;
        }

        binding.textFieldPassword.setError(null);
        return true;
    }


}
