package com.example.lam.coachnutrition;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class FoodActivity extends AppCompatActivity
        implements RangementFoodFragment.OnDialogInteraction {

    private CursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private AccessProvider accessProvider;
    private DisplayFood modeAffichage;
    private boolean edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean detail = pref.getBoolean("detail", DisplayFood.DEFAULT_DETAIL);
        boolean name = pref.getBoolean("name", DisplayFood.DEFAULT_NAME);
        boolean calorie = pref.getBoolean("calorie", DisplayFood.DEFAULT_CALORIE);
        boolean croissant = pref.getBoolean("croissant", DisplayFood.DEFAULT_CROISSANT);
        modeAffichage = new DisplayFood(this, detail, name, calorie, croissant);
        edit = getIntent().getBooleanExtra("edit", true);
        setContentView(R.layout.activity_ingredient);
        accessProvider = new AccessProvider(this);
        listView = (ListView) findViewById(R.id.ingredients_list);
        if(edit){
            listView.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                    Cursor cursor = (Cursor) adapter.getItem(i);
                    final String nom = cursor.getString(BaseInformation.FOOD_CODE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
                    builder.setMessage("Veux tu supprimer " + nom + " ? ")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    accessProvider.deleteFood(nom);
                                    refreshAffichage();
                                }
                            })
                            .setNegativeButton("Non", null)
                            .show();
                }
            });
        }
        else{

        }
        refreshAffichage();
    }

    private void refreshAffichage() {
        cursor = accessProvider.query(
                modeAffichage.getColumnsCursor(),
                modeAffichage.getOrderElement(),
                modeAffichage.getOrderOrientation(),
                BaseInformation.CONTENT_URI_FOOD);
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
                Intent intent = new Intent(this, SearchFoodActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                return true;
            case R.id.pannel:
                modeAffichage.changeDetail();
                this.getSharedPreferences("pref", MODE_PRIVATE).edit().
                        putBoolean("detail", modeAffichage.getDetail());
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor prefEdit = pref.edit();
                prefEdit.putBoolean("detail", modeAffichage.getDetail());
                prefEdit.apply();
                cursor = accessProvider.query(
                        modeAffichage.getColumnsCursor(),
                        modeAffichage.getOrderElement(),
                        modeAffichage.getOrderOrientation(),
                        BaseInformation.CONTENT_URI_FOOD);
                adapter = modeAffichage.getAdapter(cursor);
                listView.setAdapter(adapter);
                return true;
            case R.id.option:
                DialogFragment dialog = new RangementFoodFragment().newInstance();
                dialog.show(getFragmentManager(), "mon dialog");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectCremerie(View v) {
        Cursor cursor = accessProvider.query(
            modeAffichage.getColumnsCursor(),
            BaseInformation.FoodEntry.COLUMN_CATEGORY + " = 0",
            modeAffichage.getOrderElement(),
            modeAffichage.getOrderOrientation(),
            BaseInformation.CONTENT_URI_FOOD
        );
        adapter.swapCursor(cursor);
    }

    public void addIngredient(View v) {
        DialogFragment dialogFragment = new NewFoodDialogFragment();
        dialogFragment.show(getFragmentManager(), "");
    }

    public void onDialogDismiss() {
        refreshAffichage();
    }


    @Override
    public void onDialogInteraction(boolean alphabet, boolean calorie, boolean croissant) {
        modeAffichage.setName(alphabet);
        modeAffichage.setCalorie(calorie);
        modeAffichage.setCroissant(croissant);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = pref.edit();
        prefEdit.putBoolean("name", modeAffichage.getName());
        prefEdit.putBoolean("calorie", modeAffichage.getCalorie());
        prefEdit.putBoolean("croissant", modeAffichage.getCroissant());
        prefEdit.apply();
        refreshAffichage();

    }
}