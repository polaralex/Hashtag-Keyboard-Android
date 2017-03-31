package com.emexezidis.hashtagkeyboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScreenSlidePageFragment extends Fragment {

    public ScreenSlidePageFragment() {}

    public static ScreenSlidePageFragment newInstance(int page) {

        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle b = new Bundle();
        b.putInt("layout", page);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ViewGroup rootView;
        int layoutSelector = getArguments().getInt("layout");

        if (layoutSelector == 0) {

            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_intro_swipe_style_1, container, false);

        } else if (layoutSelector == 1) {

            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_intro_swipe_style_2, container, false);

        } else {

            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_intro_swipe_style_3, container, false);

            Button enableKeyboard = (Button) rootView.findViewById(R.id.enable_keyboard_button);

            enableKeyboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ScreenSlidePagerActivity)getActivity()).changeImeAndFinish();
                }
            });
        }

        return rootView;
    }
}
