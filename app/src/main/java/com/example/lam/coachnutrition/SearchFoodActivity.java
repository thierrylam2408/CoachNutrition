package com.example.lam.coachnutrition;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class SearchFoodActivity extends AppCompatActivity {

    private CursorAdapter adapter;
    private ListView listView;
    private AccessProvider accessProvider;
    private DisplayFood modeAffichage;
    private boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref=getSharedPreferences("pref", MODE_PRIVATE);
        boolean detail = pref.getBoolean("detail", DisplayFood.DEFAULT_DETAIL);
        boolean name = pref.getBoolean("name", DisplayFood.DEFAULT_NAME);
        boolean calorie = pref.getBoolean("calorie", DisplayFood.DEFAULT_CALORIE);
        boolean croissant = pref.getBoolean("croissant", DisplayFood.DEFAULT_CROISSANT);
        setContentView(R.layout.activity_research_ingredient);
        modeAffichage = new DisplayFood(this, detail, name, calorie, croissant, null);

        adapter = modeAffichage.getAdapter(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.ingredients_list);
        listView.setAdapter(adapter);
        accessProvider = new AccessProvider(this);
        edit = getIntent().getBooleanExtra("edit", true);
        if(edit){
            listView.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                    Cursor cursor = (Cursor) adapter.getItem(i);
                    final String nom = cursor.getString(BaseInformation.FOOD_CODE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchFoodActivity.this);
                    builder.setMessage("Veux tu supprimer " + nom + " ? ")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    accessProvider.deleteFood(nom);
                                    finish();
                                }
                            })
                            .setNegativeButton("Non", null)
                            .show();
                }
            });
        }
        else{

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setIconifiedByDefault(false);
            searchView.requestFocus();
            searchView.onActionViewExpanded();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    recherche(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    recherche(s);
                    return false;
                }
                public void recherche(String query){
                    Cursor cursor = accessProvider.query(
                            modeAffichage.getColumnsCursor(),
                            BaseInformation.FoodEntry.COLUMN_NAME +" LIKE '%"+query+"%'",
                            modeAffichage.getOrderElement(),
                            modeAffichage.getOrderOrientation(),
                            BaseInformation.CONTENT_URI_FOOD);
                    adapter.swapCursor(cursor);
                }
            });
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}