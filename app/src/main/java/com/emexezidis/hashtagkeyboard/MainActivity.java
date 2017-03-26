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

    private static final String TAG = "Change IME Tag";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            if (getIntent().getStringExtra("changeIme").equals("true")) {
                backToPreviousIme();
            }
        } else {

            getSupportActionBar().hide();
            firstRunSetup();
            showFragment();
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

        System.out.println("1st Regex: ");
        for (String string : stringArray) {
            System.out.println(string);
        }

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

    private void firstRunSetup() {

        if (getSharedPreferenceInt("firstRun") == 0) {
            saveSharedPreference("hashtags", "template0");
            saveSharedPreferenceInt("numberOfTemplates", 1);
            saveSharedPreferenceInt("firstRun", 1);
        }
    }

    private void addTemplate(String content) {
        Integer currentNumberOfTemplates = getSharedPreferenceInt("numberOfTemplates");
        saveSharedPreference("template" + ((Integer)(currentNumberOfTemplates+1)).toString(), content);
    }

    protected String getSharedPreferenceString(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        String savedValue = sharedPref.getString(tag, "empty");

        if (savedValue.equals("empty")) {
            return ("");
        } else {
            return savedValue;
        }
    }

    protected int getSharedPreferenceInt(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        return (sharedPref.getInt(tag, 0));
    }

    protected void saveSharedPreferenceInt(String tag, int value) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    protected void saveSharedPreference(String tag, String text) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
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
