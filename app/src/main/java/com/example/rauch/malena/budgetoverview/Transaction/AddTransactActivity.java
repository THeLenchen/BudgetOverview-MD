package com.example.rauch.malena.budgetoverview.Transaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.rauch.malena.budgetoverview.Database.DataSource;
import com.example.rauch.malena.budgetoverview.R;

public class AddTransactActivity extends AppCompatActivity {

    private DataSource mDataSource;
    private RadioButton mSpent;
    private RadioButton mGet;
    private TextView mName;
    private TextView mAmount;
    private int mBooleanSpent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //initialise and open DataSource
        mDataSource = new DataSource(this);
        mDataSource.open();

        //initialise the UI elements
        mSpent = findViewById(R.id.addTransaction_radioButton_give);
        mGet = findViewById(R.id.addTransaction_radioButton_get);
        mAmount = findViewById(R.id.addTransaction_editText_amount);
        mName = findViewById(R.id.addTransaction_editText_name);

        //sets clickListener to save button
        //checks if everything is filled in correctly
        //if yes: saves the data in Database, if no: gives error
        ImageButton save = findViewById(R.id.addTransaction_imageButton_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean goOn = true;
                if (mSpent.isChecked()) {
                    mBooleanSpent = 1;
                } else if ((mGet.isChecked())){
                    mBooleanSpent = 0;
                } else {
                    Snackbar.make(view, "You have to select get or spent", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

                if (goOn == true) {
                    double amount = 0;

                    try {
                        amount = Double.parseDouble(mAmount.getText().toString());
                    } catch (NumberFormatException nfe) {
                        Snackbar.make(view, "Use only numbers an '.' for Amount", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        goOn = false;
                    }

                    if (goOn == true) {
                        String name = mName.getText().toString();
                        mDataSource.createTransaction(name, amount, mBooleanSpent);
                        SharedPreferences sharedPreferences = getSharedPreferences("test", 0);
                        float temp = sharedPreferences.getFloat("budget", 00.00f);
                        double newBudget = temp + mDataSource.getTransactionAmountOfLatestEntry();
                        String budgetString = Double.toString(newBudget);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat("budget", Float.valueOf(budgetString));
                        editor.commit();
                        editor.apply();
                        mDataSource.close();
                        finish();
                    }
                }
            }
        });

        //return if delete button is pressed
        ImageButton delete = findViewById(R.id.addTransaction_imageButton_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.close();
                finish();
            }
        });
    }

}
