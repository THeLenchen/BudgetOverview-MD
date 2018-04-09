package com.example.rauch.malena.budgetoverview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rauch.malena.budgetoverview.Database.DataSource;
import com.example.rauch.malena.budgetoverview.Depts.AddDeptActivity;
import com.example.rauch.malena.budgetoverview.Depts.DeptAdapter;

public class Tab1_depts extends Fragment implements ClickListener {

    private DeptAdapter mDeptAdapter;
    private RecyclerView mDeptRecyclerView;
    //Constants used when calling the update activity
    public static final String DEPT_POSITION = "Position";
    //Constants used for SharedPreference
    private static final String FILENAME = "test";
    private static final String VAL_KEY_BUDGET = "budget";
    private static final String VAL_KEY_GIVE_TO = "giveTo";
    private static final String VAL_KEY_GET_FROM = "getFrom";
    //Database related local variables
    private Cursor mCursor;
    private DataSource mDataSource;
    private View mRootView;
    private TextView mGet;
    private TextView mGive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.tap1_depts, container, false);

        //initialise RecyclerView, set Layout, set Adapter and define Dept layout
        mDeptRecyclerView = mRootView.findViewById(R.id.tab1_RecyclerView);
        mDeptRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mDeptAdapter = new DeptAdapter(this.getContext(), mCursor);
        mDeptRecyclerView.setAdapter(mDeptAdapter);

        //initialise DataSource for database operations
        mDataSource = new DataSource(mRootView.getContext());
        mDataSource.open();

        mGet = mRootView.findViewById(R.id.tab1_textView_get);
        mGive = mRootView.findViewById(R.id.tab1_textView_give);

        //initialise the floatingbutton
        FloatingActionButton button = mRootView.findViewById(R.id.tap1_floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddDeptActivity.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        DataSource mDataSource = new DataSource(mRootView.getContext());
                        mDataSource.open();
                        SharedPreferences sharedPreferences = mRootView.getContext().getSharedPreferences("test", 0);
                        float temp = sharedPreferences.getFloat("budget", 00.00f);
                        double newBudget = temp + mDataSource.deleteDept(position);
                        String budgetString = Double.toString(newBudget);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat("budget", Float.valueOf(budgetString));
                        editor.commit();
                        editor.apply();
                        mDeptAdapter.notifyItemRemoved(position);
                    }
                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mDeptRecyclerView);

        updateUI();

        return mRootView;
    }

    //Updates the UI
    private void updateUI() {
        mCursor = mDataSource.getAllDepts();
        if (mDeptAdapter == null) {
            mDeptAdapter = new DeptAdapter(this.getContext(), mCursor);
            mDeptRecyclerView.setAdapter(mDeptAdapter);
        } else {
            mDeptAdapter.swapCursor(mCursor);
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        float get = sharedPreferences.getFloat(VAL_KEY_GET_FROM, 00.00f);
        if (get <= 00.00f) {
            editor.putFloat(VAL_KEY_GET_FROM, (float) mDataSource.getAmountOfMoneyToGiveOrToGet(true));
            editor.commit();
            editor.apply();
            get = sharedPreferences.getFloat(VAL_KEY_GET_FROM, 00.00f);
        }
        mGet.setText(String.valueOf(get) + " €");

        float give = sharedPreferences.getFloat(VAL_KEY_GIVE_TO, 00.00f);
        if (give <= 00.00f) {
            editor.putFloat(VAL_KEY_GIVE_TO, (float) mDataSource.getAmountOfMoneyToGiveOrToGet(false));
            editor.commit();
            editor.apply();
            give = sharedPreferences.getFloat(VAL_KEY_GIVE_TO, 00.00f);
        }
        mGive.setText(String.valueOf(give) + " €");

    }

    //update UI and open DataSource when returning from an activity
    @Override
    public void onResume() {
        super.onResume();
        mDataSource.open();
        updateUI();
    }

    @Override
    public void reminderonClick(long id) {
    }

    @Override
    public void reminderonLongClick(long id) {
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }

    @Override
    public void transactionOnLongClick(long id) {
    }

    @Override
    public void transactionOnClick(long id) {
    }

}
