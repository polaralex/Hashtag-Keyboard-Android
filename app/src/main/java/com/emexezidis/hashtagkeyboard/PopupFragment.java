package com.emexezidis.hashtagkeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PopupFragment extends Fragment{

    private EditText mEditText;
    private MainActivity managingActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Basic Initialization
        // (I use a reference to the calling activity to use its
        // public methods):
        managingActivity = ((MainActivity)getActivity());
        mEditText = (EditText) view.findViewById(R.id.saved_hashtags);
        mEditText.setText(managingActivity.getSharedPreferenceString("hashtags"));

        final Button hashtagify = (Button) view.findViewById(R.id.hashtagifyButton);
        hashtagify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText(managingActivity.hashtagifyString(mEditText.getText().toString()));
            }
        });

        Button save = (Button) view.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managingActivity.saveSharedPreference("hashtags", mEditText.getText().toString());
                showSnackbar("Hashtag template saved.");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void showSnackbar(String text) {
        Snackbar snackbar = Snackbar
                .make(managingActivity.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    protected void clearText() {
        mEditText.setText("");
    }
}
