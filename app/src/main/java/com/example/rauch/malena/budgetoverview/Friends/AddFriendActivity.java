package com.example.rauch.malena.budgetoverview.Friends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rauch.malena.budgetoverview.Database.DataSource;
import com.example.rauch.malena.budgetoverview.R;

public class AddFriendActivity extends AppCompatActivity {

    private DataSource mDataSource;
    private TextView mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        //initialise and open DataSource
        mDataSource = new DataSource(this);
        mDataSource.open();

        mName = findViewById(R.id.addFriend_editText_name);

        ImageButton save = findViewById(R.id.addFriend_imageButton_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.createFriend(mName.getText().toString());

                mDataSource.close();
                finish();
            }
        });
        ImageButton delete = findViewById(R.id.addFriend_imageButton_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.close();
                finish();
            }
        });
    }
}
