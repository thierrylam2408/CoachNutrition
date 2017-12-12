package com.example.lam.coachnutrition;

import android.app.DialogFragment;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class ListMealActivity extends AppCompatActivity
        implements CreateMealFragment.OnFragmentInteractionListener {

    private CursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private TextView date;
    private AccessProvider accessProvider;
    private Calendar cal;
    private int dayFromToday;
    private ImageButton previous;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meal);
        dayFromToday = 0;
        listView = (ListView) findViewById(R.id.listMenu);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        date = (TextView) findViewById(R.id.day);
        accessProvider = new AccessProvider(this);
        cal = Calendar.getInstance();
        refreshDate();
        refreshListMeal();
    }

    private void refreshListMeal(){
        String[] columns = {BaseInformation.MealEntry._ID, BaseInformation.MealEntry.COLUMN_NAME};
        cursor = accessProvider.query(
                columns,
                BaseInformation.MealEntry.COLUMN_TIMESTAMP + " < '" + String.format("%1$tA %1$tb %1$td %1$tY", cal) + "'",
                BaseInformation.MealEntry.COLUMN_TIMESTAMP,
                "ASC",
                BaseInformation.CONTENT_URI_MEAL);
        adapter = AdapterProvider.getTwoItemAdapterMeal(this, cursor);
        listView.setAdapter(adapter);
    }

    private void refreshDate(){
        if(dayFromToday == 0){
            previous.setEnabled(false);
            previous.setImageResource(R.drawable.ic_dnd_on_24dp);
        }
        else{
            previous.setEnabled(true);
            previous.setImageResource(R.drawable.ic_chevron_left_24dp);
        }
        if(dayFromToday == 7){
            next.setEnabled(false);
            next.setImageResource(R.drawable.ic_dnd_on_24dp);
        }
        else{
            next.setEnabled(true);
            next.setImageResource(R.drawable.ic_chevron_right_24dp);
        }
        date.setText(String.format("%1$tA %1$tb %1$td %1$tY", cal));
    }

    public void ajouterRepas(View v){
        DialogFragment dialog = new CreateMealFragment().newInstance();
        dialog.show(getFragmentManager(), "mon dialog");
    }

    public void goPrevious(View v){
        dayFromToday--;
        cal.add(Calendar.DAY_OF_YEAR, -1);
        refreshDate();
    }

    public void goNext(View v){
        dayFromToday++;
        cal.add(Calendar.DAY_OF_YEAR, 1);
        refreshDate();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
