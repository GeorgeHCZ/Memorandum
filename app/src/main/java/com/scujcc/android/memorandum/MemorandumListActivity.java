package com.scujcc.android.memorandum;


import android.support.v4.app.Fragment;

/**
 * Created by George on 2017/12/2.
 */

public class MemorandumListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new MemorandumListFragment();
    }
}
