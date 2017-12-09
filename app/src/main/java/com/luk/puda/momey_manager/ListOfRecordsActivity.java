package com.luk.puda.momey_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.List;

import Adapters.RecordAdapter;
import Fragments.PrefsFragment;
import Managers.DatabaseHelper;
import ModelsForDB.Record;

/**
 * Created by Lukas on 08.12.2017.
 */



public class ListOfRecordsActivity  extends AppCompatActivity {

    DatabaseHelper db;

    RecordAdapter recordAdapter = null;
    ListView listView = null;
    List<Record> records = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_of_records_activity);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Money overview");
        }


        db = new DatabaseHelper(getApplicationContext());

        records = db.getAllRecords();
        recordAdapter = new RecordAdapter(this,R.layout.record_detail,records);

        listView = (ListView) findViewById(R.id.list_of_records);
        listView.setAdapter(recordAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
