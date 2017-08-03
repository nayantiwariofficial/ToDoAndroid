package com.example.nayantiwari.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.example.nayantiwari.todoapplication.MainActivity.TODO_DATE_TIME1;
import static com.example.nayantiwari.todoapplication.MainActivity.TODO_ITEM_ID1;
import static com.example.nayantiwari.todoapplication.MainActivity.TODO_TITLE_KEY1;

//import com.example.nayantiwari.todoapplication.web_db.R;

public class ShowToDoActivity extends AppCompatActivity {

    private static final String TAG = "ShowToDoActivity";

    FloatingActionButton floatingActionButton;
    public static final String TODO_TITLE_KEY = "TODO_TITLE_KEY";
    public static final String TODO_ITEM_ID = "TODO_ITEM_ID";
    public static final String TODO_DATE_TIME = "TODO_DATE_TIME";
    public static final String TODO_MODE = "TODO_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_do);

        TextView titleTextView = (TextView) findViewById(R.id.todo_tv_info);
        TextView dateTimeTextView = (TextView) findViewById(R.id.date_time_show_tv);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.edit_todo);
        final Intent intent = getIntent();
        if (intent.hasExtra(TODO_TITLE_KEY1)) {
            titleTextView.setText(intent.getStringExtra(TODO_TITLE_KEY1));
        }

        if (intent.hasExtra(TODO_DATE_TIME1)) {
            dateTimeTextView.setText(intent.getStringExtra(TODO_DATE_TIME1));
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ShowToDoActivity.this, AddNewToDoActivity.class);
                intent1.putExtra(TODO_TITLE_KEY, intent.getStringExtra(TODO_TITLE_KEY1));
                String stringExtra = intent.getStringExtra(TODO_DATE_TIME1);
                long todoId = intent.getLongExtra(TODO_ITEM_ID1, 0);
                Log.i(TAG, "onClick: todoId: " + todoId);
                Log.i(TAG, "onClick: " + stringExtra);
                intent1.putExtra(TODO_ITEM_ID, todoId);
                intent1.putExtra(TODO_DATE_TIME, stringExtra);
                intent1.putExtra(TODO_MODE, "edit");
                startActivity(intent1);
            }
        });
    }

}
