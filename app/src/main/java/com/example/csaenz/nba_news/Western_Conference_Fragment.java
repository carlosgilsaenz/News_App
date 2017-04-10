package com.example.csaenz.nba_news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.csaenz.nba_news.QueryUtils.isConnected;

/**
 * Created by csaenz on 4/7/2017.
 */

public class Western_Conference_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Story>> {

    private static final String URL_STRING = "https://content.guardianapis.com/search?q=nba%20AND%20western%20conference&api-key=";

    private static final String DEV_KEY = "87cb8304-e614-430c-915f-7f088bdde4ad";

    public static final int WEST_CONF_LOADER_ID = 0;

    private RecycleViewAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecycleView;

    @BindView(R.id.section_label)
    TextView mSectionLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        //  Verify network connection and runs LoaderManager if true
        //  Sends Toast message otherwise
        if (isConnected(getActivity())) {
            getLoaderManager().initLoader(WEST_CONF_LOADER_ID, null, this);
        } else {
            Toast.makeText(getContext(), R.string.no_internet_string, Toast.LENGTH_SHORT).show();
            mSectionLabel.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Story>> onCreateLoader(int id, Bundle args) {

        return new AsyncLoader(getContext(), URL_STRING, DEV_KEY);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Story>> loader, ArrayList<Story> data) {
        mAdapter = new RecycleViewAdapter(getContext(), data);
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.notifyDataSetChanged();

        mSectionLabel.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Story>> loader) {
        mAdapter.clear();
    }
}