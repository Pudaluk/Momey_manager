package com.luk.puda.momey_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import Fragments.PrefsFragment;
import Managers.SharedPreferenceManager;

/**
 * Created by Lukas on 02.12.2017.
 */

public class AccountSettings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account_settings);

        final SharedPreferenceManager sharedPref = new SharedPreferenceManager(this);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final EditText name = (EditText)findViewById(R.id.et_name_of_acc);
        final EditText balance = (EditText)findViewById(R.id.et_balance_of_acc);
        Button save = (Button)findViewById(R.id.btn_acc_save);

        if (sharedPref.getNameOfAcc() != "") {
            name.setText(sharedPref.getNameOfAcc());
        }

        if (sharedPref.getBalance() != -1) {
            balance.setText(String.valueOf(sharedPref.getBalance()));
        }

        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sharedPref.saveNameOfAcc(name.getText().toString());
               sharedPref.saveBalance(Integer.parseInt(balance.getText().toString()));
               finish();
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
}