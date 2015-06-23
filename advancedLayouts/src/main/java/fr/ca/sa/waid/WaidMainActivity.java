package fr.ca.sa.waid;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonymobile.smartextension.advancedlayouts.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class WaidMainActivity extends ListActivity {


    private TextView textView;
    private Chronometer chrono;
    private String currentActivity;
    private long     startedTime = 0;
    private Map<String, Long> timers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timers = new HashMap<String,Long>();

        setContentView(R.layout.list_activity);

        // Get ListView object from xml
        textView = (TextView) findViewById(R.id.text);
        chrono = (Chronometer) findViewById(R.id.chronometer);


        BufferedReader in = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.listeprojets)));
        String[] values = new String[200];
        try {
            String line;
            int i=0;
            while ((line = in.readLine()) != null && i<200) {
                values[i]=line;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        //ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.project_line_layout, R.id.projectLabel, values);

        CustomAdapter adapter=new CustomAdapter(this,values);

        // Assign adapter to ListView
        setListAdapter(adapter);
    }



    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list,view,position,id);
        // Récupération de la valeur cliquée
        String  itemValue    = (String)getListView().getItemAtPosition(position);
        textView.setText(itemValue);
        startChrono(itemValue);
        // Affiche les infos de l'activité sélectionnée
        Toast.makeText(getApplicationContext(), "Activité sélectionnée : " + position + "  Valeur : " + itemValue, Toast.LENGTH_LONG).show();

    }


    private void startChrono(String activityId) {
        // Récupération de l'heure courante
        long time = SystemClock.elapsedRealtime();
        // On sauvegarde le timer courant
        if (currentActivity != null) {
            long elapsed = time - chrono.getBase();
            timers.put(currentActivity, elapsed);
        }

        // Récupération de l'ancien chrono si existant
        Long lastTime = timers.get(activityId);
        if (lastTime == null) {
            lastTime = 0l;
        }
        currentActivity = activityId;

        chrono.setBase(time - lastTime);
        chrono.stop();
        chrono.start();
    }


}

