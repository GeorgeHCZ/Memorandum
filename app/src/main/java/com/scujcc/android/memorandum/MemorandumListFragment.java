package com.scujcc.android.memorandum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by George on 2017/12/2.
 */

public class MemorandumListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_memorandum_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.memorandum_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_event_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_event:
                Memorandum memorandum = new Memorandum();
                MemorandumLab.get(getActivity()).addMemorandum(memorandum);
                Intent intent = MemorandumPagerActivity.newIntent(getActivity(), memorandum.getID());
                startActivity(intent);
                return  true;
            case  R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){
        MemorandumLab memorandumLab = MemorandumLab.get(getActivity());
        int memorandumCount = memorandumLab.getMemorandums().size();
        String subtitle = getString(R.string.subtitle_format, memorandumCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        MemorandumLab memorandumLab = MemorandumLab.get(getActivity());
        List<Memorandum> memorandums = memorandumLab.getMemorandums();

        if (mAdapter == null){
            mAdapter = new CrimeAdapter(memorandums);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Memorandum mMemorandum;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_memorandum_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_memorandum_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_memorandum_solved_check_box);
        }

        public void bindCrime(Memorandum memorandum) {
            mMemorandum = memorandum;
            mTitleTextView.setText(mMemorandum.getTitle());
            mDateTextView.setText(mMemorandum.getDate().toString());
            mSolvedCheckBox.setChecked(mMemorandum.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MemorandumPagerActivity.newIntent(getActivity(), mMemorandum.getID());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Memorandum> mMemorandums;

        public CrimeAdapter(List<Memorandum> memorandums) {
            mMemorandums = memorandums;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_memorandum, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Memorandum memorandum = mMemorandums.get(position);
            holder.bindCrime(memorandum);
        }

        @Override
        public int getItemCount() {
            return mMemorandums.size();
        }
    }
}
