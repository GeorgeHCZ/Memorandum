package com.scujcc.android.memorandum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by George on 2017/12/2.
 */

public class MemorandumPagerActivity extends AppCompatActivity{
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent,crime_id";

    private ViewPager mViewPager;
    private List<Memorandum> mMemorandums;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, MemorandumPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstancestate){
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_memorandum_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_memorandum_pager_view_pager);

        mMemorandums = MemorandumLab.get(this).getMemorandums();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Memorandum crime = mMemorandums.get(position);
                return MemorandumFragment.newInstance(crime.getID());
            }

            @Override
            public int getCount() {
                return mMemorandums.size();
            }
        });

        for(int i=0;i<mMemorandums.size();i++){
            if(mMemorandums.get(i).getID().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
