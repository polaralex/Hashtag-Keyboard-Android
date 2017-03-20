package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Change IME Tag";
    String defaultHashtagList = "#hello #there #you #can #edit #these #hashtags";
    EditText mEditText;
    String processedText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstRunSetup();

        mEditText = new EditText(this);
        mEditText = (EditText) findViewById(R.id.saved_hashtags);
        mEditText.setText(getSharedPreferenceString("hashtags"));

        backToPreviousIme();

        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedPreference("hashtags", mEditText.getText().toString());
            }
        });

        final Button hashtagify = (Button) findViewById(R.id.hashtagifyButton);
        hashtagify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashtagifyString();
            }
        });

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    saveSharedPreference("hashtags", mEditText.getText().toString());
                } else {
                }
            }
        });
    }

    private void hashtagifyString() {

        processedText = "";

        String[] stringArray = mEditText.getText().toString().split("\\s+");

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

        mEditText.setText(processedText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditText.setText(getSharedPreferenceString("hashtags"));
    }

    private void firstRunSetup() {
        if (getSharedPreferenceInt("firstRun") == 0) {
            saveSharedPreference("hashtags", defaultHashtagList);
            saveSharedPreferenceInt("firstRun", 1);
        }
    }

    private String getSharedPreferenceString(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        String savedValue = sharedPref.getString(tag, "empty");

        if (savedValue.equals("empty")) {
            return ("");
        } else {
            return savedValue;
        }
    }

    private int getSharedPreferenceInt(String tag) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        return (sharedPref.getInt(tag, 0));
    }

    private void saveSharedPreferenceInt(String tag, int value) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    private void saveSharedPreference(String tag, String text) {

        SharedPreferences sharedPref = this.getSharedPreferences("hashkeyboard", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, text);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPreference("hashtags", mEditText.getText().toString());
    }

    private void backToPreviousIme() {
        try {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            final IBinder token = this.getWindow().getAttributes().token;
            imm.switchToLastInputMethod(token);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Log.e(TAG,"cannot set the previous input method:");
            t.printStackTrace();

            //TODO: Make this exception handling better
            InputMethodManager imeManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imeManager != null) {
                imeManager.showInputMethodPicker();
            }
        }
    }
}