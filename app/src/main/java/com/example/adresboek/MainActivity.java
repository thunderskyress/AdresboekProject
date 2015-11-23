package com.example.adresboek;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener{
    Button  btnAdd;
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
}
