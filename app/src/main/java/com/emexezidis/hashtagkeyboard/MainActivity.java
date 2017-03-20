package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String defaultHashtagList = "#test #default #hashtags";
    EditText mEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstRunSetup();

        mEditText = new EditText(this);
        mEditText = (EditText) findViewById(R.id.saved_hashtags);
        mEditText.setText(getSharedPreferenceString("hashtags"));

        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedPreference("hashtags", mEditText.getText().toString());
            }
        });

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    saveSharedPreference("hashtags", mEditText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditText.setText(getSharedPreferenceString("hashtags"));
    }

    private void firstRunSetup() {
        if (getSharedPreferenceInt("firstRun") == 0) {
            saveSharedPreference("hashtags", defaultHashtagList);
        }
    }

    private void saveSharedPreference(String tag, String text) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, text);
        editor.apply();
    }

    private String getSharedPreferenceString(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        String savedValue = sharedPref.getString(tag, "empty");

        if (savedValue.equals("empty")){
            return("");
        } else {
            return savedValue;
        }
    }

    private int getSharedPreferenceInt(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        return (sharedPref.getInt(tag, 0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPreference("hashtags", mEditText.getText().toString());
    }
}