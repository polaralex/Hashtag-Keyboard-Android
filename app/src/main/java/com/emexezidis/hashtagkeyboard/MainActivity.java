package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private static Boolean activityVisible;
    private static MainActivity activityReference;

    SharedPreferences sharedPref;

    String defaultHashtagList = "#hello #there #you #can #edit #these #hashtags";
    String processedText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityReference = this;
        sharedPref = this.getSharedPreferences("hashkeyboard", 0);

        handleIntent();
        checkIfFirstRun();
        showFragment();
    }

    // Used when the Activity is not already visible:
    private void handleIntent() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.getBoolean("changeImeAndClose", false)) {
                System.out.println("Change IME and close");
                changeIme();
                finish();
            }
        }
    }

    private void showFragment() {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, new PopupFragment()).commit();
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

    private void checkIfFirstRun() {

        if (getSharedPreferenceInt("firstRun") == 0) {
            saveSharedPreference("hashtags", defaultHashtagList);
            saveSharedPreferenceInt("firstRun", 1);
        }
    }

    protected String getSharedPreferenceString(String tag) {

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

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    protected void saveSharedPreference(String tag, String text) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, text);
        editor.apply();
    }

    public void changeIme() {

        InputMethodManager imeManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imeManager != null) {
            imeManager.showInputMethodPicker();
        }
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public static MainActivity getActivityInstance() {
        return activityReference;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.activityPaused();
    }
}
