package com.example.pertemuan5_recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.pertemuan5_recycleview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

// Recycle View
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//WorkManager
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    // Shared
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // Recycle View
    RecyclerView recylerView;
    String s1[], s2[],s3[];
    int images[] = {R.drawable.seni1,R.drawable.seni2,R.drawable.seni3,R.drawable.seni4};

    private DrawerLayout dl;
    private ActionBarDrawerToggle abut;

    //WorkManager
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // tugas MyWorker
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork("Notifikasi", ExistingWorkPolicy.REPLACE, request);
            }
        });
        // Shared
        preferences = getSharedPreferences("AndroidHiveLogin", 0);
        editor = preferences.edit();
        // Recycler View
//        setContentView(R.layout.activity_main);
        recylerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.karya);
        s2 = getResources().getStringArray(R.array.deskripsi);
        s3 = getResources().getStringArray(R.array.star);
        SeniAdapter appAdapter = new SeniAdapter(this, s1, s2, s3, images);
        recylerView.setAdapter(appAdapter);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setItemAnimator(new DefaultItemAnimator());

        dl = (DrawerLayout) findViewById(R.id.dl);
        abut = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abut.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abut);
        abut.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent a = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_sql) {
                    Intent a = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(a);
                } else if (id == R.id.nav_restapi) {
                    Intent a = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(a);
                }else if (id == R.id.nav_logout) {
                    editor.clear();
                    editor.commit();
                    Intent a = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(a);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abut.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}