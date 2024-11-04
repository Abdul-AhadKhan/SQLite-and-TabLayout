package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutAdapter extends FragmentStateAdapter {

    public TabLayoutAdapter(FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NewProductsFragment();
            case 1:
                return new ScheduledProductsFragment();
            default:
                return new DeliveredProductsFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
