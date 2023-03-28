package com.meow.hungergames.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.activities.MainActivity;
import com.meow.hungergames.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        checkBox();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        return binding.getRoot();
    }

    private void checkBox() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("email", "");
        if (check.equals("true")) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().overridePendingTransition(0, 0);
            getActivity().finish();
        }
    }

    private void userLogin() {
        String email, pass;
        email = binding.emailInput.getText().toString();
        pass = binding.passInput.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.email.setError("Email cannot be empty");
            binding.email.requestFocus();
        }
        if (TextUtils.isEmpty(pass)) {
            binding.pass.setError("Password cannot be empty");
            binding.pass.requestFocus();
        } else {
            binding.loading.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", "true");
                    editor.apply();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().finish();
                    binding.loading.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    binding.loading.setVisibility(View.GONE);
                }
            });
        }
    }

}