package com.example.csaenz.nba_news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by csaenz on 4/9/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Story> mStories;

    public RecycleViewAdapter(Context context, ArrayList<Story> stories) {
        mContext = context;
        mStories = stories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate custom Layout
        View storyView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(storyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get correct Restaurant from ArrayList
        Story story = mStories.get(position);

        // Set Title TextView
        TextView title = holder.mTitle;
        title.setText(story.getTitle());

        //  Set Section TextView
        TextView section = holder.mSection;
        section.setText(story.getSection());

        //  Set Date TextView
        TextView date = holder.mDate;
        date.setText(story.getDate());

        //  Set custom onclick listener with custom method
        String url = story.getUrl();
        holder.mCardView.setOnClickListener(new CardViewClicked(url));
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    /**
     * Custom Helper Methods
     */
    public void clear() {
        int size = mStories.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mStories.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public class CardViewClicked implements View.OnClickListener {

        String mUrl;

        public CardViewClicked(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mUrl));
            mContext.startActivity(intent);
        }
    }

    /**
     * Custom ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_text_view)
        TextView mTitle;
        @BindView(R.id.section_text_view)
        TextView mSection;
        @BindView(R.id.date_text_view)
        TextView mDate;
        @BindView(R.id.card_view)
        CardView mCardView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
