package com.meow.hungergames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.R;
import com.meow.hungergames.adapters.SavedAdapter;
import com.meow.hungergames.dao.SavedDao;
import com.meow.hungergames.databinding.FragmentSavedBinding;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.Saved;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;

import java.util.ArrayList;

public class SavedFragment extends Fragment implements RequestCallBack, TempRequestCallBack {

    private FragmentSavedBinding binding;
    private SavedAdapter adapter;
    private SavedDao savedDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater, container, false);

        savedDao = new SavedDao(getActivity(), this, this);
        savedDao.getSaved(FirebaseAuth.getInstance().getUid());
        return binding.getRoot();
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_SAVED:
                if (status) {
                    ArrayList<Saved> saveds = new ArrayList<>((ArrayList<Saved>) object);
                    adapter = new SavedAdapter(getContext(), saveds, new SavedAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int check) {

                        }
                    });
                    binding.savedRv.setAdapter(adapter);
                    binding.savedRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                }
                break;
        }
    }

    @Override
    public void onRequestTempSuccessful(Object object, int check, boolean status, int position, String message) {

    }
}