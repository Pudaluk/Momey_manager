package com.luk.puda.momey_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Managers.DatabaseHelper;
import ModelsForDB.Category;

/**
 * Created by Lukas on 03.12.2017.
 */

public class CreateRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> type = new ArrayList<String>();
    //String[] type;// = { "Income", "Outcome" };
    String[] categorySpinner;
    String selectedType = "";
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        db = new DatabaseHelper(getApplicationContext());

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        List<Category> allTags = db.getAllCategories();
        for (Category cate : allTags) {
            Log.d("Category Name", cate.getCategory_name());
    //        for (int i = 0; i < allTags.size(); i ++){
              type.add(cate.getCategory_name());
           // }
        }

        Date date = new Date();
        date.getTime();
        SimpleDateFormat simpleDate  =  new SimpleDateFormat("dd.MM.yyyy");
        String strDt = simpleDate .format(date);


        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Button btn = (Button) findViewById(R.id.btn_save_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        selectedType = type.get(position);
        Toast.makeText(getApplicationContext(),type.get(position) ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
