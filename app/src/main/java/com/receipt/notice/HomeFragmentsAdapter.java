package com.receipt.notice;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * @author youtui
 */
public class HomeFragmentsAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> fragmentsList = new ArrayList<>();
        public HomeFragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
                fragmentsList.add(new HelloFragment ());
                fragmentsList.add(new LogListFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
                return fragmentsList.get(position);
        }

        @Override
        public int getItemCount() {
                return fragmentsList.size();
        }
}
