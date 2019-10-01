package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ListView numOccurrencesListView = (ListView) findViewById(R.id.stats_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_styleless_listviewitem, R.id.textView, getNumOfOccurences());
        numOccurrencesListView.setAdapter(arrayAdapter);
    }

    private String[] getNumOfOccurences() {
        String[] numOfOccurrencesArray = new String[20];
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);

        for (int diceValue = 1; diceValue <= 20; diceValue++) {
            String keyForCurrentRoll = getString(R.string.num_of_occurrences_base)+diceValue;
            int defaultValue = getResources().getInteger(R.integer.default_value);
            int numOccurrences = sharedPreferences.getInt(keyForCurrentRoll, defaultValue);
            String timeOrTimes = numOccurrences == 1 ? " time.": " times.";
            numOfOccurrencesArray[diceValue-1] = diceValue+" has occurred "+numOccurrences+timeOrTimes;
        }

        return numOfOccurrencesArray;
    }
}
