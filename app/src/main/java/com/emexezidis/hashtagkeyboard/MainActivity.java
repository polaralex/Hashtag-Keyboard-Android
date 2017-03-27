package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    String defaultHashtagList = "#hello #there #you #can #edit #these #hashtags";
    String processedText;
    SharedPreferences sharedPref;

    private static final String TAG = "Change IME Tag";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        sharedPref = getSharedPreferences("hashkeyboard", Context.MODE_PRIVATE);

        if (bundle != null) {

            if (bundle.getString("changeIme").equals("true")) {
                backToPreviousIme();
                finish();
            }

        } else {

            getSupportActionBar().hide();
            firstRunSetup();
            showFragment();
        }
    }

    private void firstRunSetup() {

        if (getSharedPreferenceInt("firstRun") == 0) {

            // Loading the default values into the templates:
            saveSharedPreference("template0", defaultHashtagList);

            // Empty Strings for not-yet-used templates:
            for(Integer i=1; i<5; i++) {
                editTemplate(i, "#empty");
            }

            saveSharedPreference("selectedTemplate", 1);
            saveSharedPreference("firstRun", 1);
        }
    }

    private void showFragment() {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, new HashtagPanelFragment()).commit();
    }

    protected String hashtagifyString(String input) {

        processedText = "";

        String[] stringArray = input.split("\\s+");

        for (String string : stringArray) {
            if (string.contains("#")) {
                processedText += (string + " ");
            } else {
                processedText += ("#" + string + " ");
            }
        }

        return (processedText);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void editTemplate(Integer template, String content) {
        saveSharedPreference("template" + template.toString(), content);
    }

    protected String getSharedPreferenceString(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        String savedValue = sharedPref.getString(tag, "empty");

        return savedValue;
    }

    protected int getSharedPreferenceInt(String tag) {

        return (sharedPref.getInt(tag, 0));
    }

    protected void saveSharedPreference(String tag, int value) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    protected void saveSharedPreference(String tag, String text) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, text);
        editor.apply();
    }

    private void backToPreviousIme() {

        try {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            final IBinder token = this.getWindow().getAttributes().token;
            imm.switchToLastInputMethod(token);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Log.e(TAG, "cannot set the previous input method:");
            t.printStackTrace();

            //TODO: Make this exception handling better
            InputMethodManager imeManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imeManager != null) {
                imeManager.showInputMethodPicker();
            }

            finish();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}