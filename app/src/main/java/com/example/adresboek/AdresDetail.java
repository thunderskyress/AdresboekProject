package com.example.adresboek;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdresDetail extends ActionBarActivity implements android.view.View.OnClickListener {

    Button btnSave, btnDelete, btnClose,btnMap;
    EditText editTextName, editTextAdres;
    private int _Plaats_Id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adres_detail);

        btnSave = (Button) findViewById(R.id.SaveBtn);
        btnClose = (Button) findViewById(R.id.CloseBtn);
        btnDelete = (Button) findViewById(R.id.DeleteBtn);
        btnMap  = (Button) findViewById(R.id.MapBtn);

        editTextName = (EditText) findViewById(R.id.TagEditText);
        editTextAdres = (EditText) findViewById(R.id.AdresEditText);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnMap.setOnClickListener(this);

        _Plaats_Id = 0;
        Intent intent = getIntent();
        _Plaats_Id = intent.getIntExtra("plaats_id",0);
        PlaatsRepo repo = new PlaatsRepo(this);
        Plaats plaats = new Plaats();
        plaats = repo.getPlaatsByID(_Plaats_Id);

        editTextName.setText(plaats.Plaats_Name);
        editTextAdres.setText(plaats.Plaats_Adres);

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.SaveBtn)){
            PlaatsRepo repo = new PlaatsRepo(this);
            Plaats plaats = new Plaats();
            plaats.Plaats_Name = editTextName.getText().toString();
            plaats.Plaats_Adres = editTextAdres.getText().toString();
            plaats.Plaats_ID = _Plaats_Id;

            if (_Plaats_Id ==0){
                _Plaats_Id = repo.insert(plaats);
                Toast.makeText(this, "Nieuwe plaats ingevoerd",Toast.LENGTH_SHORT).show();
            } else{
                repo.update(plaats);
                Toast.makeText(this, "Plaats update",Toast.LENGTH_SHORT).show();
            }
            finish();
        } else if (v == findViewById(R.id.DeleteBtn)){
            PlaatsRepo repo = new PlaatsRepo(this);
            repo.delete(_Plaats_Id);
            Toast.makeText(this,"Plaats deleted",Toast.LENGTH_SHORT).show();
            finish();
        } else if (v == findViewById(R.id.CloseBtn)){
            finish();
        } else if (v == findViewById(R.id.MapBtn)){
            String tagname,location;
            tagname = editTextName.getText().toString();
            location = editTextAdres.getText().toString();

            Intent  intent = new Intent(this,MapsActivity.class);
            intent.putExtra("name",tagname);
            intent.putExtra("location",location);
            startActivity(intent);
        }
    }


}
