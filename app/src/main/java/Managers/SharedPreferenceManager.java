package Managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lukas on 12.11.2017.
 */



public class SharedPreferenceManager {

    public static final String MyPrefs = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";


    private Context context;

    public SharedPreferenceManager(Context context){
        this.context = context;
    }

    public void saveName(String value) {

        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Name, value);
        editor.commit();

    }

    public void savePhone(String value) {

        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Phone, value);
        editor.commit();

    }

    public void saveEmail(String value) {

        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Email, value);
        editor.commit();

    }

    public String getName() {
        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedPref.contains(Name)) {
            return (sharedPref.getString(Name, ""));
        }
        return (sharedPref.getString(Name, ""));
    }

    public String getPhone() {
        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedPref.contains(Phone)) {
            return (sharedPref.getString(Phone, ""));
        }
        return (sharedPref.getString(Phone, ""));
    }

    public String getEmail() {
        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedPref.contains(Email)) {
            return (sharedPref.getString(Email, ""));
        }
        return (sharedPref.getString(Email, ""));
    }


}
