package com.example.rauch.malena.budgetoverview;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rauch.malena.budgetoverview.Database.DataSource;
import com.example.rauch.malena.budgetoverview.Transaction.AddTransactActivity;
import com.example.rauch.malena.budgetoverview.Transaction.TransactionAdapter;


public class Tab3_transact extends Fragment implements ClickListener {


    //locale Variables
    private TransactionAdapter mTransactionAdapter;
    private RecyclerView mTransactionRecyclerView;
    // Database related to Database
    private Cursor mCursor;
    private DataSource mDataSource;
    //Constants used when calling the update activity
    public static final String TRANSACTION_POSITION = "Position";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.tap3_transakt, container, false);

        //initialise DataSource for database operations
        mDataSource = new DataSource(rootView.getContext());
        mDataSource.open();

        //initialise RecyclerView, set Layout, set Adapter and define Transaction layout
        mTransactionRecyclerView = rootView.findViewById(R.id.tab3_RecyclerView_Transakt);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mTransactionAdapter = new TransactionAdapter(mCursor, this.getContext());
        mTransactionRecyclerView.setAdapter(mTransactionAdapter);


        //initialise the floatingbutton
        FloatingActionButton button = rootView.findViewById(R.id.transaction_floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the AddTransactionActivity by taping the button
                Intent intent = new Intent(getContext(), AddTransactActivity.class);
                startActivity(intent);
            }
        });

        //Item Touch to delete an Entry
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
                        return;
                    }
                };

        //attache the ItemTouchHelper to the Recyclerview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mTransactionRecyclerView);

        updateUI();

        return rootView;
    }

    //update UI and open DataSource when returning from an activity
    @Override
    public void onResume() {
        super.onResume();
        mDataSource.open();
        updateUI();
    }

    //closes the dataSource and the cursor if the App is paused
    @Override
    public void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        mDataSource.close();
    }


    //Updates the UI
    private void updateUI() {
        mCursor = mDataSource.getAllTransactions();
        if (mTransactionAdapter == null) {
            mTransactionAdapter = new TransactionAdapter(mCursor, this.getContext());
            mTransactionRecyclerView.setAdapter(mTransactionAdapter);
        } else {
            mTransactionAdapter.swapCursor(mCursor);
        }

        TextView budget = rootView.findViewById(R.id.tab3_textView_budget);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("test", 0);
        float budgetString = sharedPreferences.getFloat("budget", 00.00f);
        budget.setText(String.valueOf(budgetString)+" €");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void reminderonClick(long id) {
        //Intent intent = new Intent(this, UpdateActivity.class);
        //intent.putExtra(TRANSACTION_POSITION, id);
        //startActivity (intent);
    }

    @Override
    public void reminderonLongClick(long id) {    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Object> loader, Object data) {    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Object> loader) {    }

    @Override
    public void transactionOnLongClick(long id) {    }

    @Override
    public void transactionOnClick(long id) {    }
}
