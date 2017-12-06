package com.luk.puda.momey_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Managers.DatabaseHelper;
import Managers.SharedPreferenceManager;
import ModelsForDB.Category;
import ModelsForDB.Record;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferenceManager smp = new SharedPreferenceManager(this);
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tvHeaderName, tvHeaderEmail, tvHeaderPhone;

        //Nastaveni leveho menu - eg jmeno email; phone
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvHeaderName = (TextView) headerView.findViewById(R.id.leftMenuName);
        tvHeaderEmail = (TextView) headerView.findViewById(R.id.tvLeftMenuEmail);
        tvHeaderPhone = (TextView) headerView.findViewById(R.id.tvLeftMenuPhone);
        tvHeaderName.setText(smp.getName());
        tvHeaderEmail.setText(smp.getEmail());
        tvHeaderPhone.setText(smp.getPhone());

        //nastaveni balance
        TextView tvBalance = (TextView)findViewById(R.id.tvBalance);
        if (smp.getBalance() != -1) {
            tvBalance.setText(String.valueOf(smp.getBalance()));
        }

        //test DB
        db = new DatabaseHelper(getApplicationContext());

        Category category_food = new Category("Food");
        Category category_shopping  = new Category("Shopping");
        Category category_home = new Category("Home");
        Category category_traffic = new Category("Traffic");
        Category category_car = new Category("Car");
        Category category_free_time = new Category("Free time");
        Category category_pc = new Category("PC");
        Category category_investment = new Category("Investment");
        Category category_other = new Category("Other");
        Category category_income = new Category("Income");

        long category_food_id = db.createCategory(category_food);
        long category_shopping_id = db.createCategory(category_shopping);
        long category_home_id = db.createCategory(category_home);
        long category_traffic_id = db.createCategory(category_traffic);
        long category_car_id = db.createCategory(category_car);
        long category_free_time_id = db.createCategory(category_free_time);
        long category_pc_id = db.createCategory(category_pc);
        long category_investment_id = db.createCategory(category_investment);
        long category_other_id = db.createCategory(category_other);
        long category_income_id = db.createCategory(category_income);

        Log.d("Category count", "Category count: " + db.getAllCategories().size());

        Date date = new Date();
        date.getTime();
        SimpleDateFormat simpleDate  =  new SimpleDateFormat("dd/MM/yyyy");
        String strDt = simpleDate .format(date);
        //create record
        //Record rc1 = new Record("spending", strDt, 500, smp.getBalance()-500 );

        //long rc1_id = db.createRecord(rc1, category_food_id);

        //Log.e("Record Count", "Record count: " + db.getRecord(0).toString());

        Log.d("get category", "Getting All categories");

        List<Category> allTags = db.getAllCategories();
        for (Category cate : allTags) {
            Log.d("Category Name", cate.getCategory_name());
        }


        Log.d("Record", "Get records under single Tag name");

        //List<Record> tagsWatchList = db.getAllRecordsByCategory(category_food.getCategory_name());
        //for (Record rc : tagsWatchList) {
         //   Log.d("Record Watchlist time", rc.getCreate_at());
        //}

        db.closeDB();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intentRecord = new Intent(this, CreateRecordActivity.class);
                //startActivity(intentRecord);
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onResume() {
        TextView tvHeaderName, tvHeaderEmail, tvHeaderPhone;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvHeaderName = (TextView) headerView.findViewById(R.id.leftMenuName);
        tvHeaderEmail = (TextView) headerView.findViewById(R.id.tvLeftMenuEmail);
        tvHeaderPhone = (TextView) headerView.findViewById(R.id.tvLeftMenuPhone);
        tvHeaderName.setText(smp.getName());
        tvHeaderEmail.setText(smp.getEmail());
        tvHeaderPhone.setText(smp.getPhone());

        TextView tvBalance = (TextView)findViewById(R.id.tvBalance);
        if (smp.getBalance() != -1) {
            tvBalance.setText(String.valueOf(smp.getBalance()));
        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AccountSettings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intentRecord = new Intent(this, CreateRecordActivity.class);
            startActivity(intentRecord);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent2 = new Intent(this, Settings_activity.class);
            startActivity(intent2);

        } else if(id == R.id.nav_help){
            Toast.makeText(getApplicationContext(), "Help will be added later", Toast.LENGTH_LONG);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }
}
