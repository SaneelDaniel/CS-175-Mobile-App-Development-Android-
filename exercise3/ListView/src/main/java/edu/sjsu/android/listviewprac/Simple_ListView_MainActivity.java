package edu.sjsu.android.listviewprac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simple_ListView_MainActivity extends AppCompatActivity {

    List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();
        ListView lv = (ListView) findViewById(R.id.listView);
        SimpleAdapter simpleAdpt = new SimpleAdapter(this, planetsList,
                android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
        lv.setAdapter(simpleAdpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                TextView clickedView = (TextView) view;
                Toast.makeText(Simple_ListView_MainActivity.this, "Item with id ["+id+"]- Position ["+position+"] - Planet ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList(){
        planetsList.add(createPlanet("planet", "mercury"));
        planetsList.add(createPlanet("planet", "Venus"));
        planetsList.add(createPlanet("planet", "Earth"));
        planetsList.add(createPlanet("planet", "Mars"));
        planetsList.add(createPlanet("planet", "Jupiter"));
        planetsList.add(createPlanet("planet", "Saturn"));
        planetsList.add(createPlanet("planet", "Uranus"));
        planetsList.add(createPlanet("planet", "Neptune"));
    }

    private HashMap<String, String> createPlanet(String planet, String mercury) {

        HashMap<String, String> planetT = new HashMap<String, String>();
        planetT.put(planet, mercury);
        return  planetT;
    }
}