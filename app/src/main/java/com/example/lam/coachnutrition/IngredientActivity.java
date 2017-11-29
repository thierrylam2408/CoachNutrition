package com.example.lam.coachnutrition;


import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class IngredientActivity extends AppCompatActivity implements RangementIngredientFragment.OnDialogInteraction {

    private CursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private AccessProvider accessProvider;
    private ModeAffichageIngredient modeAffichage;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("detail", modeAffichage.getDetail());
        outState.putBoolean("name", modeAffichage.getName());
        outState.putBoolean("calorie", modeAffichage.getCalorie());
        outState.putBoolean("croissant", modeAffichage.getCroissant());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Toast.makeText(IngredientActivity.this, "Init", Toast.LENGTH_SHORT).show();
            modeAffichage = new ModeAffichageIngredient(this);
        }else{
            Toast.makeText(IngredientActivity.this, "Recup", Toast.LENGTH_SHORT).show();
            boolean detail = savedInstanceState.getBoolean("detail");
            boolean name = savedInstanceState.getBoolean("name");
            boolean calorie = savedInstanceState.getBoolean("calorie");
            boolean croissant = savedInstanceState.getBoolean("croissant");
            modeAffichage = new ModeAffichageIngredient(this, detail, name, calorie, croissant);
        }
        setContentView(R.layout.activity_ingredient);
        accessProvider = new AccessProvider(this);
        listView = (ListView) findViewById(R.id.ingredients_list);
        refreshAffichage();
    }

    private void refreshAffichage(){
        cursor = accessProvider.query(
                modeAffichage.getColumnsCursor(),
                modeAffichage.getOrderElement(),
                modeAffichage.getOrderOrientation());
        adapter = modeAffichage.getAdapter(cursor);
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
                modeAffichage.changeDetail();
                cursor = accessProvider.query(
                        modeAffichage.getColumnsCursor(),
                        modeAffichage.getOrderElement(),
                        modeAffichage.getOrderOrientation());
                adapter = modeAffichage.getAdapter(cursor);
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

    public void addIngredient(View v) {
        DialogFragment dialogFragment = new NewIngredientDialogFragment();
        dialogFragment.show(getFragmentManager(), "");
    }

    public void onDialogDismiss() {
        refreshAffichage();
    }


    @Override
    public void onDialogInteraction(boolean alphabet, boolean calorie, boolean croissant) {
        modeAffichage.setName(alphabet); modeAffichage.setCalorie(calorie); modeAffichage.setCroissant(croissant);
        refreshAffichage();

    }
}
