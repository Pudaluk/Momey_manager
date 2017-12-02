package Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luk.puda.momey_manager.R;

import Managers.SharedPreferenceManager;
import Utils.Validation;


/**
 * Created by Lukas on 02.12.2017.
 */

public class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.preference_profile);

        final Preference name = (Preference) findPreference("nameKey");
        name.setSummary(SharedPreferenceManager.from(getActivity()).getName());
        name.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(R.string.profile_name);

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View mView = inflater.inflate(R.layout.dialog_prefs_profile, null);
                alertDialog.setView(mView);


                final EditText nameField = (EditText) mView.findViewById(R.id.profile_prefs_field);
                nameField.setText(SharedPreferenceManager.from(getActivity()).getName());
                nameField.setInputType(InputType.TYPE_CLASS_TEXT);

                alertDialog.setPositiveButton(getString(R.string.dialog_button_save), null);
                alertDialog.setNegativeButton(getString(R.string.dialog_button_cancel), null);

                final AlertDialog alert = alertDialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button positive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferenceManager.from(getActivity()).saveName(nameField.getText().toString());
                                nameField.setVisibility(View.GONE);
                                alert.dismiss();
                                name.setSummary(nameField.getText().toString());
                                Toast.makeText(getActivity(), R.string.profile_phone_changed + " " + nameField.getText().toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        Button negative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();
                return true;
            }
        });


        final Preference phone = (Preference) findPreference("phoneKey");
        phone.setSummary(SharedPreferenceManager.from(getActivity()).getPhone());
        phone.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(R.string.profile_phone);

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View mView = inflater.inflate(R.layout.dialog_prefs_profile, null);
                alertDialog.setView(mView);


                final EditText phoneField = (EditText) mView.findViewById(R.id.profile_prefs_field);
                phoneField.setText(SharedPreferenceManager.from(getActivity()).getPhone());
                phoneField.setInputType(InputType.TYPE_CLASS_PHONE);
                final TextView phoneError = (TextView) mView.findViewById(R.id.profile_prefs_error);
                phoneError.setText(R.string.profile_phone_error);

                alertDialog.setPositiveButton(getString(R.string.dialog_button_save), null);
                alertDialog.setNegativeButton(getString(R.string.dialog_button_cancel), null);

                final AlertDialog alert = alertDialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button positive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Validation.isValidPhoneNumber(phoneField.getText().toString())) {
                                    SharedPreferenceManager.from(getActivity()).savePhone(phoneField.getText().toString());
                                    phoneError.setVisibility(View.GONE);
                                    alert.dismiss();
                                    phone.setSummary(phoneField.getText().toString());
                                    Toast.makeText(getActivity(), R.string.profile_phone_changed + " " + phoneField.getText().toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    phoneError.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        Button negative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();
                return true;
            }
        });

        final Preference email = (Preference) findPreference("emailKey");
        email.setSummary(SharedPreferenceManager.from(getActivity()).getEmail());
        email.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(R.string.profile_email);

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View mView = inflater.inflate(R.layout.dialog_prefs_profile, null);
                alertDialog.setView(mView);


                final EditText emailField = (EditText) mView.findViewById(R.id.profile_prefs_field);
                emailField.setText(SharedPreferenceManager.from(getActivity()).getEmail());
                emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                final TextView emailError = (TextView) mView.findViewById(R.id.profile_prefs_error);
                emailError.setText(R.string.profile_email_error);

                alertDialog.setPositiveButton(getString(R.string.dialog_button_save), null);
                alertDialog.setNegativeButton(getString(R.string.dialog_button_cancel), null);

                final AlertDialog alert = alertDialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button positive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Validation.isValidEmailAddress(emailField.getText().toString())) {
                                    SharedPreferenceManager.from(getActivity()).saveEmail(emailField.getText().toString());
                                    emailError.setVisibility(View.GONE);
                                    alert.dismiss();
                                    email.setSummary(emailField.getText().toString());
                                    Toast.makeText(getActivity(), R.string.profile_email_changed + " " + emailField.getText().toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    emailError.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        Button negative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                        negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(SharedPreferenceManager.Phone)) {
            Preference pref = findPreference(key);
            if (pref instanceof EditTextPreference) {
                EditTextPreference editTextPreference = (EditTextPreference) pref;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}