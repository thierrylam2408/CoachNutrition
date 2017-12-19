package com.example.coachnutrition;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
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
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        refreshDate();
        refreshListMeal();

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                Cursor cursor = (Cursor) adapter.getItem(i);
                int code = cursor.getInt(cursor.getColumnIndex(BaseInformation.MealEntry.COLUMN_CODE));
                Intent intent = new Intent(getApplication(), MealActivity.class);
                intent.putExtra("codeMeal", code);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListMeal();
    }

    private void refreshListMeal() {
        cursor = accessProvider.getMealByDay(cal);
        adapter = AdapterProvider.getTwoItemAdapterListMeal(this, cursor);
        listView.setAdapter(adapter);
    }

    private void refreshDate() {
        if (dayFromToday == 0) {
            previous.setEnabled(false);
            previous.setImageResource(R.drawable.ic_dnd_on_24dp);
        } else {
            previous.setEnabled(true);
            previous.setImageResource(R.drawable.ic_chevron_left_24dp);
        }
        if (dayFromToday == 7) {
            next.setEnabled(false);
            next.setImageResource(R.drawable.ic_dnd_on_24dp);
        } else {
            next.setEnabled(true);
            next.setImageResource(R.drawable.ic_chevron_right_24dp);
        }
        date.setText(String.format("%1$tA %1$tb %1$td %1$tY", cal));
        refreshListMeal();
    }

    public void ajouterRepas(View v) {
        DialogFragment dialog = new CreateMealFragment().newInstance();
        dialog.show(getFragmentManager(), "mon dialog");
    }

    public void goPrevious(View v) {
        dayFromToday--;
        cal.add(Calendar.DAY_OF_YEAR, -1);
        refreshDate();
    }

    public void goNext(View v) {
        dayFromToday++;
        cal.add(Calendar.DAY_OF_YEAR, 1);
        refreshDate();
    }

    @Override
    public void onFragmentInteraction(String nom, int hours, int mins) {
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, mins);
        Timestamp tp = new Timestamp(cal.getTimeInMillis());
        Cursor c = accessProvider.query(
                new String[]{"MAX(" + BaseInformation.MealEntry.COLUMN_CODE + ")"},
                BaseInformation.CONTENT_URI_MEAL);
        c.moveToFirst();
        int code = c.getInt(0) + 1;
        Meal meal = new Meal(code, nom, tp);
        accessProvider.insertMeal(meal);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        refreshListMeal();
    }
}
