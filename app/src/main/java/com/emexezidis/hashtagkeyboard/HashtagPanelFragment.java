package com.emexezidis.hashtagkeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HashtagPanelFragment extends Fragment{

    private EditText mEditText;
    private MainActivity managingActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        managingActivity = ((MainActivity)getActivity());

        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> arrayList = new ArrayList<>();
        populateArrayList(arrayList);

        ArrayAdapter adapter = new ArrayAdapter<String>(managingActivity, R.layout.listview_layout, arrayList);
        ListView listview = (ListView) view.findViewById(R.id.listview);

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

    private void populateArrayList(ArrayList<String> arrayList) {

        Integer number = managingActivity.getSharedPreferenceInt("numberOfTemplates");

        for (Integer i=0; i<number; i++) {
            arrayList.add(managingActivity.getSharedPreferenceString("template"+i.toString()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        managingActivity.saveSharedPreference("hashtags", mEditText.getText().toString());
    }
}
