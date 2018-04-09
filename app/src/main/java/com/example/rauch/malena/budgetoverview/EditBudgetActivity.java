package com.example.rauch.malena.budgetoverview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditBudgetActivity extends AppCompatActivity {

    private EditText mNewBudget;
    private static final String FILENAME = "test";
    private static final String VAL_KEY = "budget";
    private SharedPreferences mSharedPref;

    //Constructor
    public EditBudgetActivity() {   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

        //initialise the 
        TextView old = findViewById(R.id.edit_textView_budget);
        mNewBudget = findViewById(R.id.edit_editBudget);

        mSharedPref = getSharedPreferences(FILENAME, 0);
        float temp = mSharedPref.getFloat(VAL_KEY, 00.00f);
        old.setText(String.valueOf(temp));


        ImageButton save = findViewById(R.id.imageButton_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saves the new entered budget into the SharedPreference
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putFloat(VAL_KEY, Float.valueOf(mNewBudget.getText().toString()));
                editor.commit();
                editor.apply();
                finish();
            }
        });
        ImageButton delete = findViewById(R.id.imageButton_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
}
