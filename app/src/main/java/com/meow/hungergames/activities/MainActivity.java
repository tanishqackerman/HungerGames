package com.meow.hungergames.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.meow.hungergames.R;
import com.meow.hungergames.databinding.ActivityMainBinding;
import com.meow.hungergames.fragments.ExploreFragment;
import com.meow.hungergames.fragments.ProfileFragment;
import com.meow.hungergames.fragments.SavedFragment;
import com.meow.hungergames.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int[] tabIcons = {
            R.drawable.restaurant,
            R.drawable.findicon,
            R.drawable.cheficon,
            R.drawable.recipe
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        binding.tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        binding.tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        binding.tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        binding.tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExploreFragment());
        adapter.addFrag(new SearchFragment());
        adapter.addFrag(new SavedFragment());
        adapter.addFrag(new ProfileFragment());
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}