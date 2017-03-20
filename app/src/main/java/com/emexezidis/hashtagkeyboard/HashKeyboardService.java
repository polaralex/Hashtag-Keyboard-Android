package com.emexezidis.hashtagkeyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class HashKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    public final static int insertHashtagButton = 257;
    public final static int editHashtagButton = 258;

    private String hashtags;
    private InputConnection ic;

    @Override
    public View onCreateInputView() {
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
            case Keyboard.KEYCODE_SHIFT:
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case editHashtagButton:
                Intent intent = new Intent(this, MainActivity.class);
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
        SharedPreferences sharedPref = getSharedPreferences("hashkeyboard", 0);
        hashtags = sharedPref.getString("hashtags", "empty");
        return (hashtags);
    }

    private int checkForLengthToDelete() {

        String text = ic.getTextBeforeCursor(100, 0).toString();

        if(text.length() != 0) {
            Log.e("DEBUG", "NO INPUT");

            int textLength = text.length();
            int lastOccurenceOfHashtag = text.lastIndexOf('#');

            return textLength - lastOccurenceOfHashtag;
        }

        return 0;
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
