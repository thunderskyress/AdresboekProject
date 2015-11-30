package com.example.adresboek;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener{
    Button  btnAdd,btnRefresh;
    TextView plaats_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.AddItem);
        btnAdd.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.AddItem)){
            Intent intent = new Intent(this,AdresDetail.class);
            intent.putExtra("plaats_id",0);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlaatsRepo repo = new PlaatsRepo(this);
        ArrayList<HashMap<String,String>> plaatsList = repo.getPlaatsList();
        if (plaatsList.size() !=0){
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    plaats_id = (TextView)  view.findViewById(R.id.Adres_ID);
                    String plaatsID = plaats_id.getText().toString();
                    Intent objintent = new Intent(getApplicationContext(),AdresDetail.class);
                    objintent.putExtra("plaats_id",Integer.parseInt(plaatsID));
                    startActivity(objintent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,plaatsList,R.layout.view_adres_entry, new String[]{"id" , "name"}, new int[]{R.id.Adres_ID,R.id.Adres_Name});
            setListAdapter(adapter);
        } else {
            Toast.makeText(this,"Geen plaats gevonden",Toast.LENGTH_SHORT).show();
        }
    }
}
