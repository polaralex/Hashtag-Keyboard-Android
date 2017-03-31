package com.emexezidis.hashtagkeyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class HashKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    public final static int insertHashtagButton = 257;
    public final static int editHashtagButton = 258;

    private String hashtags;
    private InputConnection ic;

    Intent intent;

    @Override
    public View onCreateInputView() {

        checkIfFirstRun();

        KeyboardView kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        Keyboard keyboard = new Keyboard(this, R.xml.hashtag_keys);

        kv.setKeyboard(keyboard);
        kv.setPreviewEnabled(false);
        kv.setOnKeyboardActionListener(this);

        hashtags = "";

        return kv;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        ic = getCurrentInputConnection();

        switch (primaryCode) {

            case Keyboard.KEYCODE_DELETE:

                int lengthToDelete = checkForLengthToDelete();
                ic.deleteSurroundingText(lengthToDelete, 0);
                break;

            case Keyboard.KEYCODE_DONE:

                // Show Change IME screen:
                intent = new Intent(this, MainActivity.class);

                if (MainActivity.isActivityVisible()) {
                    MainActivity.getActivityInstance().changeIme(false);
                } else {
                    System.out.println("Going to send a Stay Open intent");
                    intent.putExtra("changeImeAndClose", true);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case editHashtagButton:

                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case insertHashtagButton:

                hashtags = getCurrentHashtags();
                ic.commitText(hashtags, 1);
                break;

            default:
        }
    }

    private String getCurrentHashtags() {

        SharedPreferences sharedPref = getSharedPreferences(MainActivity.HASHTAG_KEYBOARD_PREF_NAME, 0);
        hashtags = sharedPref.getString("hashtags", "#click #edit #button");
        return (hashtags);
    }

    private int checkForLengthToDelete() {

        String text = ic.getTextBeforeCursor(100, 0).toString();

        if (text.length() != 0) {

            int textLength = text.length();
            int lastOccurenceOfHashtag = text.lastIndexOf('#');

            return textLength - lastOccurenceOfHashtag;
        }

        return 0;
    }

    private void checkIfFirstRun() {

        SharedPreferences sharedPref = getSharedPreferences(MainActivity.HASHTAG_KEYBOARD_PREF_NAME, 0);

        if (sharedPref.getInt("firstRun", 0) == 0) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }
}