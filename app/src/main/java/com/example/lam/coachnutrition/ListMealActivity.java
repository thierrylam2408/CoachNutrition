package com.example.lam.coachnutrition;

import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListMealActivity extends AppCompatActivity {

    private CursorAdapter adapter;
    private Cursor cursor;
    private ListView listView;
    private TextView date;
    private AccessProvider accessProvider;
    private Calendar day;
    private int actualDay;
    private ImageButton previous;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        actualDay = 0;
        listView = (ListView) findViewById(R.id.listMenu);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        date = (TextView) findViewById(R.id.day);
        accessProvider = new AccessProvider(this);
        day = Calendar.getInstance();
        refreshDate();
    }

    private void refreshDate(){
        if(actualDay == 0){
            previous.setEnabled(false);
            previous.setImageResource(R.drawable.ic_dnd_on_24dp);
        }
        else{
            previous.setEnabled(true);
            previous.setImageResource(R.drawable.ic_chevron_left_24dp);
        }
        if(actualDay == 7){
            next.setEnabled(false);
            next.setImageResource(R.drawable.ic_dnd_on_24dp);
        }
        else{
            next.setEnabled(true);
            next.setImageResource(R.drawable.ic_chevron_right_24dp);
        }
        date.setText(String.format("%1$tA %1$tb %1$td %1$tY", day));
    }

    public void goPrevious(View v){
        actualDay--;
        day.add(Calendar.DAY_OF_YEAR, -1);
        refreshDate();
    }

    public void goNext(View v){
        actualDay++;
        day.add(Calendar.DAY_OF_YEAR, 1);
        refreshDate();
    }
}
