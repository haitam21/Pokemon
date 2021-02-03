package com.example.tp_pokemon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class detail_pokemon extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    TextView textView_name;
    Button button;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);
        imageView=(ImageView) findViewById(R.id.imagePok);
        textView=(TextView) findViewById(R.id.idPok);
        textView_name=(TextView) findViewById(R.id.pokName);
        button=(Button) findViewById(R.id.btnBack);
        viewPager=findViewById(R.id.view_pager_det);
        tabLayout=findViewById(R.id.tab_det);
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")){
            int number=getIntent().getIntExtra("id",0);
            String name=getIntent().getStringExtra("name");
            Glide.with(this).load("https://cdn.traction.one/pokedex/pokemon/"+number+".png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            textView.setText("#"+number);
            textView_name.setText(name);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add("Details");
        arrayList.add("Evolutions");

        prepareViewPager(viewPager,arrayList);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_description_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_evolution);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        detail_pokemon.MainAdapter adapter= new detail_pokemon.MainAdapter(getSupportFragmentManager());
        PokemonDetailFragment fragment=new PokemonDetailFragment();
        for(int i=0; i<arrayList.size(); i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));
            bundle.putInt("id",getIntent().getIntExtra("id",0));
            bundle.putString("name",getIntent().getStringExtra("name"));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment,arrayList.get(i));
            fragment=new PokemonDetailFragment();
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