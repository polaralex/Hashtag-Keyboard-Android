package com.emexezidis.hashtagkeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HashtagPanelFragment extends Fragment{

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

        // Initialize the Array used for the ListView
        ArrayList<String> arrayList = new ArrayList<>();
        populateArrayList(arrayList);

        // Initialize the ListView
        ArrayAdapter adapter = new HashtagAdapter(managingActivity, arrayList);
        ListView listview = (ListView) view.findViewById(R.id.templates_listview);
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(true);

//        final Button hashtagify = (Button) view.findViewById(R.id.hashtagifyButton);
//        hashtagify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mEditText.setText(managingActivity.hashtagifyString(mEditText.getText().toString()));
//            }
//        });

    }

    private void populateArrayList(ArrayList<String> arrayList) {

        for (Integer i=0; i<5; i++) {
            arrayList.add(managingActivity.getSharedPreferenceString("template"+(i.toString())));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //managingActivity.saveSharedPreference("hashtags", mEditText.getText().toString());
    }
}
