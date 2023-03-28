package com.meow.hungergames.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.meow.hungergames.fragments.LoginFragment;
import com.meow.hungergames.fragments.SignUpFragment;

public class LoginAdapter extends FragmentStateAdapter {

    public LoginAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new SignUpFragment();
        }
        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
