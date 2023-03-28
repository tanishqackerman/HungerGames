package com.meow.hungergames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.meow.hungergames.R;
import com.meow.hungergames.databinding.FragmentEditProfileBinding;
import com.meow.hungergames.databinding.FragmentExploreBinding;

public class EditProfileFragment extends BottomSheetDialogFragment {

    private FragmentEditProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}