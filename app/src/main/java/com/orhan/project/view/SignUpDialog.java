package com.orhan.project.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.orhan.project.R;
import com.orhan.project.databinding.FragmentSignUpDialogBinding;
import com.orhan.project.model.Input;
import com.orhan.project.model.User;
import com.orhan.project.util.Converter;
import com.orhan.project.util.UserRepository;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;

public class SignUpDialog extends AppCompatDialogFragment {

    private FragmentSignUpDialogBinding binding;
    private AlertDialog dialog;
    private ArrayList<TextInputLayout> uiComponents;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.fragment_sign_up_dialog, null, false);
        binding.setFragment(this);

        createDynamicLayout();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new AlertDialog.Builder(getActivity())
                .setView(binding.getRoot())
                .create();
        return dialog;
    }

    private void createDynamicLayout() {
        ArrayList<Input> list = Converter.getJsonDataAsArray(getContext());
        uiComponents = new ArrayList<>();

        for (Input i: list) {
            View child = getLayoutInflater().inflate(R.layout.row_textbox, null);
            TextInputLayout textInputLayout = child.findViewById(R.id.text_field);
            textInputLayout.setHint(i.getTitle());

            String textType = textInputLayout.getHint().toString();
            if (textType.contains("Password")) {
                textInputLayout.getEditText().setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (textType.contains("Email")) {
                textInputLayout.getEditText().setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }

            binding.container.addView(child);

            uiComponents.add(textInputLayout);
        }

    }

    public void close(View view){
        dialog.dismiss();
    }

    public void signUp(View view) {

        String email = "";
        String password = "";
        String job = "";
        String age = "";

        if (uiComponents != null) {
            for (TextInputLayout textInputLayout: uiComponents) {
                String fieldTitle = textInputLayout.getEditText().getHint().toString();
                String field = textInputLayout.getEditText().getText().toString();
                if (field.equals("")) {
                    Toast.makeText(getActivity(), "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (fieldTitle.contains("Email")) {
                        email = field;
                    } else if (fieldTitle.contains("Password")) {
                        password = field;
                    } else if (fieldTitle.contains("Age")) {
                        age = field;
                    } else if (fieldTitle.contains("Job")) {
                        job = field;
                    }
                }
            }

            // save db
            User user = new User(email, password, job, age);
            UserRepository userRepository = new UserRepository(getActivity());
            userRepository.insert(user);

            dialog.dismiss();
        }
    }


}
