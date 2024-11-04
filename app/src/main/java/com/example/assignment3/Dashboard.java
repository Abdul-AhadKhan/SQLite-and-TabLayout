package com.example.assignment3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Dashboard extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabLayoutAdapter tabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayoutAdapter = new TabLayoutAdapter(this);
        viewPager2.setAdapter(tabLayoutAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setText("NEW");
                        tab.setIcon(R.drawable.new_product_icon);
                        break;
                    case 1:
                        tab.setText("SCHEDULED");
                        tab.setIcon(R.drawable.scheduled_icon);
                        break;
                    default:
                        tab.setText("DELIVERED");
                        tab.setIcon(R.drawable.delivered_icon);
                }
            }
        });
        tabLayoutMediator.attach();
    }
}