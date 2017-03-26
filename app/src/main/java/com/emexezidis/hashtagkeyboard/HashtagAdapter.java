package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class HashtagAdapter extends ArrayAdapter<String> {

    Context mContext;

    public HashtagAdapter(Context context, ArrayList<String> strings) {
        super(context, 0, strings);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        String hashtags = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }

        // Lookup view for data population
        final TextView title = (TextView) convertView.findViewById(R.id.listview_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Edit Hashtags"); //Set Alert dialog title here

                // Set an EditText view to get user input
                final EditText input = new EditText(mContext);
                input.setText(title.getText().toString());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        String srt = input.getEditableText().toString();
                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);


        // Populate the data into the template view using the data object
        title.setText(hashtags);

        // Return the completed view to render on screen
        return convertView;
    }

}