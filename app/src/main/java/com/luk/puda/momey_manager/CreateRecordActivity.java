package com.luk.puda.momey_manager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Managers.DatabaseHelper;
import Managers.SharedPreferenceManager;
import ModelsForDB.Category;
import ModelsForDB.Record;

/**
 * Created by Lukas on 03.12.2017.
 */

public class CreateRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> type = new ArrayList<String>();
    //String[] type;// = { "Income", "Outcome" };
    String[] categorySpinner;
    String selectedCategory = "";
    DatabaseHelper db;
    SharedPreferenceManager sharedPref;

    LinearLayout typeIn_ll,typeOut_ll;
    //string to save in DB
    String selectedType = "Income";
    String selectedDate;
    TextView pick_date;
    EditText amount_of_rc_et;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        db = new DatabaseHelper(getApplicationContext());
        sharedPref = new SharedPreferenceManager(getApplicationContext());

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(selectedType);
        }

        typeIn_ll = (LinearLayout) findViewById(R.id.typeIn_ll);
        typeOut_ll = (LinearLayout) findViewById(R.id.typeOut_ll);

        if (selectedType.equals("Income")){
            typeIn_ll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            typeOut_ll.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            typeOut_ll.setBackgroundColor(getResources().getColor(R.color.Expense));
            typeIn_ll.setBackgroundColor(getResources().getColor(R.color.white));
        }

        typeIn_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "Income";
                typeIn_ll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                typeOut_ll.setBackgroundColor(getResources().getColor(R.color.white));
                getSupportActionBar().setTitle(selectedType);
            }
        });

        typeOut_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "Outcome";
                typeOut_ll.setBackgroundColor(getResources().getColor(R.color.Expense));
                typeIn_ll.setBackgroundColor(getResources().getColor(R.color.white));
                getSupportActionBar().setTitle(selectedType);
            }
        });

        //DATE PICK
        Date date = new Date();
        date.getTime();
        SimpleDateFormat simpleDate  =  new SimpleDateFormat("dd.MM.yyyy");
        selectedDate = simpleDate.format(date);

        pick_date = (TextView)findViewById(R.id.pick_date);
        pick_date.setText(selectedDate);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker1);

        pick_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { showDialog(DATE_DIALOG_ID); }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();


        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        selectedDate = ""+day+"."+month+"."+year+"";

        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show();

        List<Category> allTags = db.getAllCategories();
        for (Category cate : allTags) {
            Log.d("Category Name", cate.getCategory_name());
    //        for (int i = 0; i < allTags.size(); i ++){
              type.add(cate.getCategory_name());
           // }
        }



        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        amount_of_rc_et = (EditText)findViewById(R.id.amount_of_rc_et);

        Button btn = (Button) findViewById(R.id.btn_save_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount_of_rc_et != null || amount_of_rc_et.getText().toString().equals("")){
                    if (selectedType.equals("Income")){
                        Record rc1 = new Record(selectedType, selectedDate, Integer.parseInt(amount_of_rc_et.getText().toString()), sharedPref.getBalance()+Integer.parseInt(amount_of_rc_et.getText().toString()), selectedCategory );
                        sharedPref.saveBalance(sharedPref.getBalance()+Integer.parseInt(amount_of_rc_et.getText().toString()));
                        Toast.makeText(CreateRecordActivity.this, " < INCOME >", Toast.LENGTH_SHORT).show();
                    } else {
                        Record rc1 = new Record(selectedType, selectedDate, Integer.parseInt(amount_of_rc_et.getText().toString()), sharedPref.getBalance() - Integer.parseInt(amount_of_rc_et.getText().toString()), selectedCategory);
                        //long rc1_id = db.createRecord(rc1, 0);
                        sharedPref.saveBalance(sharedPref.getBalance() - Integer.parseInt(amount_of_rc_et.getText().toString()));
                        Toast.makeText(CreateRecordActivity.this, " < OUTCOME >", Toast.LENGTH_SHORT).show();
                    }
                    //db.createRecord(rc1, 0);

                }else
                    Toast.makeText(CreateRecordActivity.this, "ENTER AMOUNT!!", Toast.LENGTH_SHORT).show();

                //long rc1_id = db.createRecord(rc1, category_food_id);
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
        selectedCategory = type.get(position);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    //update month day year
    private void updateDisplay() {
        int mmMonth = mMonth+1;
        selectedDate = mDay+"."+mmMonth+"."+mYear;
        pick_date.setText(selectedDate);
    }

    // the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

}
