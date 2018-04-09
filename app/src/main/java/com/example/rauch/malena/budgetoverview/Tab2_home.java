package com.example.rauch.malena.budgetoverview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**Fragments support different defices, so your applikation acts diffrently on a mobilephone then on a tablet
 * Phone: start new activity -> new screen
 * Tablet: start new activity -> use full size, show activity next to the fragment
 */
public class Tab2_home  extends Fragment{

    public static final String POSITION = "Position";
    Button mEdit;
    TextView mBudget;
    private static final String FILENAME = "test";
    private static final String VAL_KEY = "budget";
    ViewGroup mContainer;
    private SharedPreferences mSharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tap2_home, container, false);

        mContainer = container;
        mBudget = rootView.findViewById(R.id.home_textView_budget);


        //with the help of SharedPreferences variables can be saved in key -> value form
        //this is used if you only need to save a view datas without the use of a structured database
        //SharedPreference mSharedPref is initialised
        mSharedPref = container.getContext().getSharedPreferences(FILENAME, 0);

        updateUI();

        //Edit-Button
        //start new activity when clicking on the Button
        mEdit = rootView.findViewById(R.id.home_button_edit);
        mEdit.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
                Intent intent = new Intent(container.getContext(), EditBudgetActivity.class);
                startActivity(intent);
           }
        });

        return rootView;
    }

    //update UI and open DataSource when returning from an activity
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    //updates the Interface -> loadas the budget into the TextView
    public void updateUI(){
        mBudget.setText(String.valueOf(mSharedPref.getFloat(VAL_KEY, 00.00f)));
    }


}
