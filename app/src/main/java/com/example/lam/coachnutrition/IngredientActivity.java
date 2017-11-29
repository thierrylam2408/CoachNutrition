package com.example.lam.coachnutrition;


import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class IngredientActivity extends AppCompatActivity implements RangementIngredientFragment.OnDialogInteraction {

    private CursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private AccessProvider accessProvider;
    private boolean detail;
    private boolean name;
    private boolean calorie;
    private boolean croissant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        accessProvider = new AccessProvider(this);
        listView = (ListView) findViewById(R.id.ingredients_list);
        initBooleanAffichage();
        refreshAffichage();
    }

    private void initBooleanAffichage(){
        detail = false;
        name = true;
        calorie = false;
        croissant = true;
    }

    private void refreshAffichage(){
        cursor = accessProvider.query(getColumnsCursor(), getOrderElement(), getOrderOrientation());
        adapter = getAdapter(cursor);
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
            case R.id.pannel:
                detail = !detail;
                cursor = accessProvider.query(getColumnsCursor(), getOrderElement(), getOrderOrientation());
                adapter = getAdapter(cursor);
                listView.setAdapter(adapter);
                return true;
            case R.id.option:
                DialogFragment dialog = new RangementIngredientFragment().newInstance();
                dialog.show(getFragmentManager(), "mon dialog");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectCremerie(View v) {
        adapter.swapCursor(null);
    }

    private CursorAdapter getAdapter(Cursor query){
        if(detail)
            return AdapterProvider.getTwoItemAdapter(this, query);
        else return AdapterProvider.getOneItemAdapter(this, query);
    }

    private String[] getColumnsCursor(){
        String[] notDetailedColumns = {AccessProvider.columnId, AccessProvider.columnName};
        String[] detailedColumns = {AccessProvider.columnId, AccessProvider.columnName, AccessProvider.columnCalorie};
        if(!detail)
            return notDetailedColumns;
        else return detailedColumns;
    }

    private String getOrderOrientation(){
        if(croissant)
            return "ASC";
        else return "DESC";
    }

    private String getOrderElement(){
        if(name)
            return AccessProvider.columnName;
        else return AccessProvider.columnCalorie;
    }

    public void addIngredient(View v) {
        DialogFragment dialogFragment = new NewIngredientDialogFragment();
        dialogFragment.show(getFragmentManager(), "");
    }

    public void onDialogDismiss() {
        String[] columns = {AccessProvider.columnId, AccessProvider.columnName, AccessProvider.columnCalorie};
        adapter.swapCursor(accessProvider.query(columns));
    }

    @Override
    public void onDialogInteraction(boolean alphabet, boolean calorie, boolean croissant) {
        this.name = alphabet; this.calorie = calorie; this.croissant = croissant;
        refreshAffichage();

    }
}
