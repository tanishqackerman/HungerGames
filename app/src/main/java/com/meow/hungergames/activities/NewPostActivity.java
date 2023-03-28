package com.meow.hungergames.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.meow.hungergames.R;
import com.meow.hungergames.dao.PostDao;
import com.meow.hungergames.dao.UserDao;
import com.meow.hungergames.databinding.ActivityMainBinding;
import com.meow.hungergames.databinding.ActivityNewPostBinding;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NewPostActivity extends AppCompatActivity implements RequestCallBack {

    private ActivityNewPostBinding binding;
    private int nolIngredient = 1;
    private int nolStep = 1;
    private Uri imgUri = null;
    private PostDao postDao;
    private UserDao userDao;
    private boolean isVeg = false;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialise();
        initialiseListeners();
    }

    public void initialise() {
        postDao = new PostDao(NewPostActivity.this, this);
        userDao = new UserDao(NewPostActivity.this, this);
    }

    public void initialiseListeners() {
        binding.addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText step = (EditText) binding.ingredientLl.getChildAt(binding.ingredientLl.getChildCount() - 2);
                if (step.getText().toString().isEmpty()) Toast.makeText(NewPostActivity.this, "Enter Previous Ingredient First", Toast.LENGTH_SHORT).show();
                else addLine(binding.ingredientLl, nolIngredient, "Enter Ingredient");
            }
        });

        binding.addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText step = (EditText) binding.stepLl.getChildAt(binding.stepLl.getChildCount() - 2);
                if (step.getText().toString().isEmpty()) Toast.makeText(NewPostActivity.this, "Enter Previous Step First", Toast.LENGTH_SHORT).show();
                else addLine(binding.stepLl, nolStep, "Enter Step");
            }
        });

        binding.uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.recipeNameEditText.getText().toString().isEmpty()) Toast.makeText(NewPostActivity.this, "Recipe name cannot be empty", Toast.LENGTH_SHORT).show();
                else if (binding.ingredientEditText.getText().toString().isEmpty()) Toast.makeText(NewPostActivity.this, "Ingredients cannot be empty", Toast.LENGTH_SHORT).show();
                else if (binding.stepEditText.getText().toString().isEmpty()) Toast.makeText(NewPostActivity.this, "Steps cannot be empty", Toast.LENGTH_SHORT).show();
                else if (imgUri == null) Toast.makeText(NewPostActivity.this, "Image cannot be empty", Toast.LENGTH_SHORT).show();
                else {
                    userDao.getUser(FirebaseAuth.getInstance().getUid());
                }
            }
        });

        binding.isVeg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVeg = isChecked;
            }
        });

        binding.recipeImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(NewPostActivity.this)
                        .start();
            }
        });
    }

    public void addLine(LinearLayout ll, int no, String check) {
        EditText et = new EditText(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 40, 0, 0);
        et.setBackgroundResource(R.drawable.ingredient_bg);
        et.setPadding(40, 20, 20, 20);
        et.setLayoutParams(p);
        et.setHint(check);
        et.setId(no + 1);
        ll.addView(et, ll.getChildCount() - 1);
        no++;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imgUri = data.getData();
        binding.recipeImg.setVisibility(View.VISIBLE);
        binding.recipeImg.setImageURI(imgUri);
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_USER:
                if (status) {
                    User user = (User) object;
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    String hehe = dateFormat.format(date);
                    List<String> ing = new ArrayList<>();
                    for(int i = 0; i < binding.ingredientLl.getChildCount(); i++) if (binding.ingredientLl.getChildAt(i) instanceof EditText) ing.add(((EditText) binding.ingredientLl.getChildAt(i)).getText().toString());
                    List<String> st = new ArrayList<>();
                    for(int i = 0; i < binding.stepLl.getChildCount(); i++) if (binding.stepLl.getChildAt(i) instanceof EditText) st.add(((EditText) binding.stepLl.getChildAt(i)).getText().toString());
                    RecipePost recipePost = new RecipePost(
                            UUID.randomUUID().toString().replaceAll("-", ""),
                            FirebaseAuth.getInstance().getUid(),
                            user.getUserName(),
                            user.getUserImage(),
                            imgUri.toString(),
                            binding.recipeNameEditText.getText().toString(),
                            0,
                            0,
                            ing,
                            st,
                            hehe,
                            isVeg
                    );
                    uploadImage(recipePost);
                }
        }
    }

    public void uploadImage(RecipePost recipePost) {
        storageReference = FirebaseStorage.getInstance().getReference("posts/" + recipePost.getPostId());
        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        imgUri = uri.getResult();
                        recipePost.setImgUrl(imgUri.toString());
                        postDao.savePost(recipePost.getJSONObject());
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}