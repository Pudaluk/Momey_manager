package Managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Lukas on 12.11.2017.
 */



public class SharedPreferenceManager {

    public static final String MyPrefs = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";

    public static final String Balance = "balanceKey";
    public static final String NameOfAcc = "nameOfAccKey";


    private Context context;

    public SharedPreferenceManager(Context context){
        this.context = context;
    }

    public static SharedPreferenceManager from(Context context){
        return new SharedPreferenceManager(context);
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

    public void saveBalance(Integer value) {

        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Balance, value);
        editor.commit();

    }

    public void saveNameOfAcc(String value) {

        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NameOfAcc, value);
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

    public int getBalance() {
        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedPref.contains(Balance)) {
            return (sharedPref.getInt(Balance, -1));
        }
        return (sharedPref.getInt(Balance, -1));
    }

    public String getNameOfAcc() {
        SharedPreferences sharedPref = context.getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (sharedPref.contains(NameOfAcc)) {
            return (sharedPref.getString(NameOfAcc, ""));
        }
        return (sharedPref.getString(NameOfAcc, ""));
    }


}
