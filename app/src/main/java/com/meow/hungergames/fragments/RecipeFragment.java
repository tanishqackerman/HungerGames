package com.meow.hungergames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.hungergames.R;
import com.meow.hungergames.databinding.FragmentRecipeBinding;
import com.meow.hungergames.databinding.FragmentSearchBinding;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}