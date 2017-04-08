package com.example.csaenz.nba_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by csaenz on 4/7/2017.
 */

public class Eastern_Conference_Fragment extends Fragment {

    public Eastern_Conference_Fragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getContext().getString(R.string.eastern_fragment_title) + " Fragment");
        return rootView;
    }
}
