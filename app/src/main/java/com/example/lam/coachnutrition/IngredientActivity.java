package com.example.lam.coachnutrition;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class IngredientActivity extends AppCompatActivity {

    private CursorAdapter adapter;
    private ListView listView;
    private AccessProvider accessProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        accessProvider = new AccessProvider(this);
        listView = (ListView) findViewById(R.id.ingredients_list);
        String[] columns = {AccessProvider.columnId, AccessProvider.columnName, AccessProvider.columnCalorie};
        adapter = AdapterProvider.getTwoItemAdapter(this, accessProvider.query(columns));
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.research:
                Intent intent = new Intent(this, SearchIngredientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectCremerie(View v) {
        adapter.swapCursor(null);
    }

    public void addIngredient(View v) {
        DialogFragment dialogFragment = new NewIngredientDialogFragment();
        dialogFragment.show(getFragmentManager(), "");
    }

    public void onDialogDismiss() {
        String[] columns = {AccessProvider.columnId, AccessProvider.columnName, AccessProvider.columnCalorie};
        adapter.swapCursor(accessProvider.query(columns));
    }

}
