package com.meow.hungergames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meow.hungergames.R;
import com.meow.hungergames.adapters.ExploreAdapter;
import com.meow.hungergames.dao.PostDao;
import com.meow.hungergames.databinding.FragmentExploreBinding;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements RequestCallBack {

    private FragmentExploreBinding binding;
    private PostDao postDao;
    private ExploreAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);

        postDao = new PostDao(getContext(), this);


        ArrayList<String> rvs = new ArrayList<>();
        rvs.add("Hottest Recipes");
        rvs.add("Latest Recipes");
        rvs.add("Vegetarian Recipes");
        rvs.add("Non-Vegetarian Recipes");

        postDao.getTypePosts("hottest");
        postDao.getTypePosts("latest");
        postDao.getTypePosts("veg");
        postDao.getTypePosts("nonVeg");

        adapter = new ExploreAdapter(getActivity(), rvs);
        binding.expRv.setAdapter(adapter);
        binding.expRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_HOTTEST_POSTS:
                if (status) {
                    ArrayList<RecipePost> hottest = (ArrayList<RecipePost>) object;
                    adapter.changeHottest(hottest);
                }
                break;
            case Constants.GET_LATEST_POSTS:
                if (status) {
                    ArrayList<RecipePost> latest = (ArrayList<RecipePost>) object;
                    adapter.changeLatest(latest);
                }
                break;
            case Constants.GET_VEG_POSTS:
                if (status) {
                    ArrayList<RecipePost> veg = (ArrayList<RecipePost>) object;
                    adapter.changeVeg(veg);
                }
                break;
            case Constants.GET_NON_VEG_POSTS:
                if (status) {
                    ArrayList<RecipePost> nonVeg = (ArrayList<RecipePost>) object;
                    adapter.changeNonVeg(nonVeg);
                }
                break;
        }
    }
}