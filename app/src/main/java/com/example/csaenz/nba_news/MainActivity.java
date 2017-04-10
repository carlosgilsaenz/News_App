package com.example.csaenz.nba_news;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.csaenz.nba_news.QueryUtils.isConnected;

public class MainActivity extends AppCompatActivity {

    private static final int REPEAT_COUNT = 0;

    private static final int ANIMATION_DURATION = 1000;

    private FragmentAdapter mFragmentAdapter;

    public static RotateAnimation mRotateAnimation;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @OnClick(R.id.fab)
    public void ButtonClick() {

        if (isConnected(this)) {

            mFab.startAnimation(mRotateAnimation);

            mViewPager.getAdapter().notifyDataSetChanged();

        } else {
            Toast.makeText(this, R.string.no_internet_string, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRotateAnimation = setupAnimation();

        //  Set up the Action bar
        setSupportActionBar(mToolbar);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mFragmentAdapter);

        // Set up tabs for each fragment
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public RotateAnimation setupAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                        0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(REPEAT_COUNT);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(ANIMATION_DURATION);
        return rotateAnimation;
    }
}
