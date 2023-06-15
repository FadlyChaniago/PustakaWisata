package com.example.perpusdesa.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpusdesa.R;
import com.example.perpusdesa.adapter.HomeListAdapter;
import com.example.perpusdesa.databinding.ActivityMainBinding;
import com.example.perpusdesa.model.PepusModel;
import com.example.perpusdesa.viewmodel.PerpusListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeListAdapter.ItemClickListener {

    private List<PepusModel> pepusModelList;
    private HomeListAdapter adapter;
    private PerpusListViewModel viewModel;
    private SearchView searchView;
    private ActivityMainBinding binding;

    private ListView listView;
    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    boolean filterHidden = true;
    private String selectedFilter = "all";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        hideFilter();

        sharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        pepusModelList = new ArrayList<>();

        searchView = findViewById(R.id.searchView);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.blue));
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag() == null) {
                    // Klik pertama
                    Animation animScaleUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_up);
                    searchEditText.startAnimation(animScaleUp);
                    view.setTag(true);
                } else {
                    // Klik kedua
                    Animation animScaleDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_down);
                    searchEditText.startAnimation(animScaleDown);
                    view.setTag(null);
                }
            }
        });
        bottomNavigationView = findViewById(R.id.nav);

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                return true;
            } else if (item.getItemId() == R.id.bookmark) {
                startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new HomeListAdapter(this, pepusModelList, this);
        recyclerView.setAdapter(adapter);


        viewModel = ViewModelProviders.of(this).get(PerpusListViewModel.class);
        viewModel.getPerpusListObserver().observe(this, new Observer<List<PepusModel>>() {
            @Override
            public void onChanged(List<PepusModel> pepusModels) {
                if(pepusModels != null) {
                    pepusModelList = pepusModels;
                    adapter.setPerpusList(pepusModels);
                }
            }
        });
        viewModel.makeApiCall();
    }

    private void initWidgets() {
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.category1);
        filterView2 = (LinearLayout) findViewById(R.id.category2);;
    }

    private void filterList(String text) {
        selectedFilter = text;
        List<PepusModel> filteredList = new ArrayList<>();
//        if (pepusModelList != null) { // Periksa apakah pepusModelList tidak null
            for (PepusModel pepusModel : pepusModelList) {
                if (pepusModel.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(pepusModel);
                }
            }
//        }

//        if (filteredList.isEmpty()) {
//            Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show();
//        } else {
            adapter.setFilteredList(filteredList);
//        }
    }
    public void allFilterTapped(View view) {
        selectedFilter = "all";

        adapter.setFilteredList(pepusModelList);
    }

    public void KulinerFilterTapped(View view)
    {
        filterList("kuliner");
    }
    public void HomeFilterTapped(View view)
    {
        filterList("homestay");
    }
    public void MinumanFilterTapped(View view)
    {
        filterList("minuman");
    }
    public void MakananFilterTapped(View view)
    {
        filterList("makanan");
    }
    public void KerajinanFilterTapped(View view)
    {
        filterList("kerajinan");
    }

    public void showFilterTapped(View view)
    {
        if(filterHidden == true)
        {
            filterHidden = false;
            showFilter();
        }
        else
        {
            filterHidden = true;
            hideFilter();
        }
    }

    private void hideFilter()
    {
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter()
    {
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }


    @Override
    public void onPerpusClick(PepusModel book) {
        Toast.makeText(this, "Name Book is : " +book.getTitle(), Toast.LENGTH_SHORT).show();
    }
}