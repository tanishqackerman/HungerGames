package com.meow.hungergames.fragments;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.R;
import com.meow.hungergames.activities.LoginActivity;
import com.meow.hungergames.activities.MainActivity;
import com.meow.hungergames.activities.NewPostActivity;
import com.meow.hungergames.activities.RecipeActivity;
import com.meow.hungergames.adapters.ProfileAdapter;
import com.meow.hungergames.adapters.SearchAdapter;
import com.meow.hungergames.dao.PostDao;
import com.meow.hungergames.dao.UserDao;
import com.meow.hungergames.databinding.FragmentLoginBinding;
import com.meow.hungergames.databinding.FragmentProfileBinding;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements RequestCallBack {

    private FragmentProfileBinding binding;
    private ProfileAdapter adapter;
    private static final String SHARED_PREFS = "sharedPrefs";
    private PostDao postDao;
    private UserDao userDao;
    private ArrayList<RecipePost> userPosts;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initialise();
        initialiseListeners();

        postDao.getTypePosts(FirebaseAuth.getInstance().getUid());
        userDao.getUser(FirebaseAuth.getInstance().getUid());
        return binding.getRoot();
    }

    public void initialise() {
        userPosts = new ArrayList<>();
        user = new User();
        postDao = new PostDao(getContext(), this);
        userDao = new UserDao(getContext(), this);
    }

    public void initialiseListeners() {

        adapter = new ProfileAdapter(getContext(), userPosts, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int check) {
                switch (check) {
                    case Constants.NEW_POST_CLICKED:
                        startActivity(new Intent(getActivity(), NewPostActivity.class));
                        break;
                    case Constants.LOGOUT_CLICKED:
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", "false");
                        editor.apply();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        break;
                    case Constants.EDIT_PROFILE_CLICKED:
                        EditProfileFragment editProfileFragment = new EditProfileFragment();
                        editProfileFragment.show(getActivity().getSupportFragmentManager(), editProfileFragment.getTag());
                        break;
                    case Constants.USER_RECIPE_LONG_CLICK:
                        PopupMenu popupMenu = new PopupMenu(new ContextThemeWrapper(getContext(), R.style.PopupMenu), view);
                        popupMenu.getMenuInflater().inflate(R.menu.delete_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() ==  R.id.delete_post) {
                                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject = userPosts.get(position).getJSONObject();
                                    postDao.deletePost(jsonObject, userPosts.get(position).getPostId());
                                    userPosts.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                        break;
                    case Constants.RECIPE_ITEM_CLICKED:
                        startActivity(new Intent(getActivity(), RecipeActivity.class).putParcelableArrayListExtra(Constants.RECIPE_MODEL_KEY, userPosts).putExtra("position", position));
                        break;
                }
            }
        }, user);
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_USER:
                if (status) {
                    User user = (User) object;
                    adapter.changeUser(user);
                }
                break;
            case Constants.GET_USER_POST:
                if (status) {
                    ArrayList<RecipePost> posts = (ArrayList<RecipePost>) object;
                    userPosts.addAll(posts);
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    binding.profileRv.setLayoutManager(manager);
                    binding.profileRv.setAdapter(adapter);
                }
        }
    }
}