package com.luk.puda.momey_manager

import Fragments.PrefsFragment
import Fragments.SettingsFragmentKt
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import com.luk.puda.momey_manager.R
import com.luk.puda.momey_manager.R.id.toolbar

import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by Lukas on 08.12.2017.
 */

class SettingsActivitykt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // toolbar
        toolbar

        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        // Instead of findViewById<TextView>(R.id.textView)
        textView.setText("Hello, world!")

        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragmentKt()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }
}