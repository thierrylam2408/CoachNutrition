package com.example.lam.coachnutrition;


import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref=getSharedPreferences("pref", MODE_PRIVATE);
        boolean detail = pref.getBoolean("detail", ModeAffichageIngredient.DETAIL_DEFAULT);
        boolean name = pref.getBoolean("name", ModeAffichageIngredient.NAME_DEFAULT);
        boolean calorie = pref.getBoolean("calorie", ModeAffichageIngredient.CALORIE_DEFAULT);
        boolean croissant = pref.getBoolean("croissant", ModeAffichageIngredient.CROISSANT_DEFAULT);
        modeAffichage = new ModeAffichageIngredient(this, detail, name, calorie, croissant);
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
                this.getSharedPreferences("pref", MODE_PRIVATE).edit().putBoolean("detail", modeAffichage.getDetail());
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor prefEdit = pref.edit();
                prefEdit.putBoolean("detail", modeAffichage.getDetail());
                prefEdit.apply();
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
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = pref.edit();
        prefEdit.putBoolean("name", modeAffichage.getName());
        prefEdit.putBoolean("calorie", modeAffichage.getCalorie());
        prefEdit.putBoolean("croissant", modeAffichage.getCroissant());
        prefEdit.apply();
        refreshAffichage();

    }
}
