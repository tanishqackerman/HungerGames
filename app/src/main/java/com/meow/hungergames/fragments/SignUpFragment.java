package com.meow.hungergames.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.meow.hungergames.R;
import com.meow.hungergames.activities.MainActivity;
import com.meow.hungergames.activities.NewPostActivity;
import com.meow.hungergames.dao.UserDao;
import com.meow.hungergames.databinding.FragmentLoginBinding;
import com.meow.hungergames.databinding.FragmentSignUpBinding;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.User;
import com.meow.hungergames.volley.RequestCallBack;

import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class SignUpFragment extends Fragment implements RequestCallBack {

    private FragmentSignUpBinding binding;
    private FirebaseAuth mAuth;
    private static final String SHARED_PREFS = "sharedPrefs";
    private UserDao userDao;
    private StorageReference storageReference;
    private Uri imgUri = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        checkBox();

        mAuth = FirebaseAuth.getInstance();
        userDao = new UserDao(getContext(), this);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });

        binding.chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(requireActivity())
                        .crop(1f, 1f)
                        .start();
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

    private void userSignUp() {
        String email, pass, confpass;
        email = binding.emailInput.getText().toString();
        pass = binding.passInput.getText().toString();
        confpass = binding.confPassInput.getText().toString();
        if (TextUtils.isEmpty(binding.nameInput.getText().toString())) {
            binding.nameInput.setError("Name cannot be empty");
            binding.nameInput.requestFocus();
        } else if (TextUtils.isEmpty(binding.bioInput.getText().toString())) {
            binding.bioInput.setError("Bio cannot be empty");
            binding.bioInput.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            binding.emailInput.setError("Email cannot be empty");
            binding.emailInput.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            binding.passInput.setError("Password cannot be empty");
            binding.passInput.requestFocus();
        } else if (TextUtils.isEmpty(confpass)) {
            binding.confPassInput.setError("Password cannot be empty");
            binding.confPassInput.requestFocus();
        } else if (imgUri == null) Toast.makeText(getContext(), "Image cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", "true");
                    editor.apply();
                    uploadImage();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgUri = data.getData();
        Toast.makeText(getContext(), imgUri.toString(), Toast.LENGTH_SHORT).show();
    }

    public void uploadImage() {
        storageReference = FirebaseStorage.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid());
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        imgUri = uri.getResult();
                        User user = new User(FirebaseAuth.getInstance().getUid(), binding.nameInput.getText().toString(), imgUri.toString(), binding.bioInput.getText().toString(), new Date().toString());
                        JSONObject jsonObject = user.getJSONObject();
                        userDao.saveUser(jsonObject);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {

    }
}