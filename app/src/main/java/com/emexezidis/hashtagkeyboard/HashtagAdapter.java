package com.emexezidis.hashtagkeyboard;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if this is the currently selected template:
        final Integer selectedTemplate = ((MainActivity)mContext).getSharedPreferenceInt("selectedTemplate");

        // Get the data item for this position:
        String hashtags = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view:
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }

        // Lookup view for data population:
        final TextView title = (TextView) convertView.findViewById(R.id.listview_title);

        // Set onClick Listener for Edit Dialog:
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == (selectedTemplate-1)) {
                    showAlert(title);
                } else {
                    // Select this template
                    ((MainActivity)mContext).saveSharedPreference("selectedTemplate", position+1);
                }
            }
        });

        // Populate the data into the template view using the data object:
        title.setText(hashtags);

        // Toggle element color:
        if (position == selectedTemplate-1) {
            convertView.setBackgroundColor(Color.RED);
        }

        // Toggle element visibility:
        if (hashtags.equals("empty")) {
            convertView.setVisibility(View.GONE);
        }

        // Return the completed view to render on screen:
        return convertView;
    }

    private void showAlert(TextView title) {

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Edit Hashtags"); //Set Alert dialog title here

        // Set an EditText view to get user input:
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
}