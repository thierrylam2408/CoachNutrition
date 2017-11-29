package com.example.lam.coachnutrition;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class SearchIngredientActivity extends AppCompatActivity {

    private CursorAdapter adapter;
    private ListView listView;
    private AccessProvider accessProvider;
    private ModeAffichageIngredient modeAffichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            modeAffichage = new ModeAffichageIngredient(this);
        }else{
            boolean detail = savedInstanceState.getBoolean("detail");
            boolean name = savedInstanceState.getBoolean("name");
            boolean calorie = savedInstanceState.getBoolean("calorie");
            boolean croissant = savedInstanceState.getBoolean("croissant");
            modeAffichage = new ModeAffichageIngredient(this, detail, name, calorie, croissant);
        }
        setContentView(R.layout.activity_research_ingredient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.ingredients_list);
        accessProvider = new AccessProvider(this);
        adapter = AdapterProvider.getOneItemAdapter(this, null);
        listView.setAdapter(adapter);
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
                            AccessProvider.columnName+" LIKE '%"+query+"%'",
                            modeAffichage.getOrderElement(),
                            modeAffichage.getOrderOrientation());
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
