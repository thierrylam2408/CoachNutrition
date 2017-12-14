package com.example.lam.coachnutrition;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MealActivity extends AppCompatActivity {

    private AccessProvider accessProvider;
    private Cursor cursor;
    private CursorAdapter adapter;
    private ListView listView;
    private int codeMeal;
    private final String[] select = {
            BaseInformation.MealEntry._ID,
            BaseInformation.MealEntry.COLUMN_CODE,
            BaseInformation.MealEntry.COLUMN_NAME,
            BaseInformation.MealEntry.COLUMN_TIMESTAMP,
            BaseInformation.MealEntry.COLUMN_FOOD,
            BaseInformation.MealEntry.COLUMN_WEIGHT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        accessProvider = new AccessProvider(this);
        listView = (ListView) findViewById(R.id.list_ingredients);
        TextView titre = (TextView) findViewById(R.id.titre);
        codeMeal = getIntent().getIntExtra("codeMeal", 0);
        cursor = getMeal();
        titre.setText(cursor.getString(cursor.getColumnIndex(BaseInformation.MealEntry.COLUMN_NAME)));
        cursor = getIngredients();
        adapter = AdapterProvider.getTwoItemAdapterMeal(this, cursor);
        listView.setAdapter(adapter);
    }

    private Cursor getMeal(){
        Cursor c = accessProvider.query(select,
                BaseInformation.MealEntry.COLUMN_CODE + " = '" + codeMeal + "' and " +
                        BaseInformation.MealEntry.COLUMN_NAME + " != 'None'",
                BaseInformation.CONTENT_URI_MEAL);
        c.moveToFirst();
        return c;
    }

    private Cursor getIngredients(){
        Cursor c = accessProvider.query(select,
                BaseInformation.MealEntry.COLUMN_CODE + " = '" + codeMeal + "' and "+
                    BaseInformation.MealEntry.COLUMN_NAME + " = 'None'",
                BaseInformation.CONTENT_URI_MEAL);
        return c;
    }

    public void ajouterIngredient(View v){
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("codeMeal", codeMeal);
        startActivity(intent);
    }

    public void supprimerRepas(View v){

    }
}
