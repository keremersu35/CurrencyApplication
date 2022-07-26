package com.example.currencyapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Currency;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.currencyapplication.Model.Product;
import com.example.currencyapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Context context;
    ArrayList<Product> arrayList;
    FloatingActionButton supportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    bottomNavigationView = findViewById(R.id.bottom_navigation);
    context = getApplicationContext();
    supportButton = findViewById(R.id.supportButton);

    supportButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
            startActivity(intent);
        }
    });

    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){

                case R.id.nav_currency:
                    fragment = new CurrencyFragment();
                    break;

                case R.id.nav_gold:
                    fragment = new GoldFragment();
                    break;

                case R.id.nav_cart:
                    fragment = new CartFragment(context);
                    break;

                case R.id.nav_past:
                    fragment = new PastOrdersFragment(context);
                    break;
            }
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }

            return true;
        }
    });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CurrencyFragment()).commit();
        }
}

