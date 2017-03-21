package com.emexezidis.hashtagkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        managingActivity = ((MainActivity)getActivity());

        super.onViewCreated(view, savedInstanceState);

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
            }
        });

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    managingActivity.saveSharedPreference("hashtags", mEditText.getText().toString());
                } else {
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        managingActivity.saveSharedPreference("hashtags", mEditText.getText().toString());
    }
}
