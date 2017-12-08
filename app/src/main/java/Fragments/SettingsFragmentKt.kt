package Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.luk.puda.momey_manager.BuildConfig
import com.luk.puda.momey_manager.R
import org.jetbrains.anko.*

/**
 * Created by Lukas on 08.12.2017.
 */
class SettingsFragmentKt : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_settings)

        val intro = findPreference("intro") as Preference
        intro.setOnPreferenceClickListener {

            true
        }

        val changelog = findPreference("changelog") as Preference
        changelog.setOnPreferenceClickListener {
            alert {
                title = "Changelog"
                positiveButton("Close") {  }
                customView {
                    verticalLayout {
                        textView("0.0.5 First version of app")
                        textView("SQL db working")
                        padding = dip(16)
                    }
                }
            }.show()
            true
        }

        val about_app = findPreference("about") as Preference
        about_app.setOnPreferenceClickListener {

            alert {
                title = "About"
                positiveButton("Close") {  }
                customView {
                    verticalLayout {
                        imageView(R.mipmap.ic_launcher)
                        textView(R.string.app_name)
                        textView(" Version 0.0.5")
                        textView("Copyright 2017, Pudaluk")
                        padding = dip(16)
                    }
                }
            }.show()
            true
        }

    }
}