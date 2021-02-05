package com.example.tp_pokemon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.tp_pokemon.Model.PokemonResponse;
import com.example.tp_pokemon.Retrofit.PokedexAPI;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_gen);

        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add("gen1");
        arrayList.add("gen2");
        arrayList.add("gen3");
        arrayList.add("gen4");
        arrayList.add("gen5");
        arrayList.add("gen6");
        arrayList.add("gen7");
        arrayList.add("gen8");

        prepareViewPager(viewPager,arrayList);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter= new MainAdapter(getSupportFragmentManager());
        MainFragment fragment=new MainFragment();
        for(int i=0; i<arrayList.size(); i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));
            bundle.putInt("gene",i+1);
            bundle.putStringArrayList("names",getIntent().getStringArrayListExtra("names"));
            bundle.putStringArrayList("urls",getIntent().getStringArrayListExtra("urls"));
            bundle.putIntegerArrayList("ids",getIntent().getIntegerArrayListExtra("ids"));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment,arrayList.get(i));
            fragment=new MainFragment();
        }
        viewPager.setAdapter(adapter);

    }

    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentList= new ArrayList<>();

        public void addFragment(Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
}