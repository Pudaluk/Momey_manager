package com.luk.puda.momey_manager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import javax.xml.datatype.Duration;

import Managers.SharedPreferenceManager;

/**
 * Created by Lukas on 12.11.2017.
 */

public class ProfileActivity extends Activity {

    SharedPreferenceManager smp = new SharedPreferenceManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        final EditText editName = (EditText)findViewById(R.id.editName);
        final EditText editEmail = (EditText)findViewById(R.id.editEmail);
        final EditText editPhone = (EditText)findViewById(R.id.editPhone);
        Button save = (Button)findViewById(R.id.btnSaveProfile);

        if (smp.getName() != "") {
            editName.setText(smp.getName());
        }

        if(smp.getEmail() != ""){
            editEmail.setText(smp.getEmail());
        }


        if (smp.getPhone() != ""){
            editPhone.setText(smp.getPhone());
        }





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                smp.saveName(editName.getText().toString());
                smp.saveEmail(editEmail.getText().toString());
                smp.savePhone(editPhone.getText().toString());

            }
        });
    }
}
