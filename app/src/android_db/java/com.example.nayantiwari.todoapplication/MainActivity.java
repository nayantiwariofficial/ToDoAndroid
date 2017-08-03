package com.example.nayantiwari.todoapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.nayantiwari.todoapplication.data.ToDoListContract;
import com.example.nayantiwari.todoapplication.data.ToDoListDbHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private ToDoListAdapter toDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.all_todo_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ToDoListDbHelper dbHelper = new ToDoListDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllToDo();
        toDoListAdapter = new ToDoListAdapter(cursor, this);

        recyclerView.setAdapter(toDoListAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewToDoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                final int fromPosition = viewHolder.getAdapterPosition();
//                final int toPosition = target.getAdapterPosition();

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                toDoListAdapter.swapCursor(getAllToDo());
            }

        }).attachToRecyclerView(recyclerView);

    }

    private boolean removeGuest(long id) {

        return mDb.delete(ToDoListContract.ToDoListEntry.TABLE_NAME,
                ToDoListContract.ToDoListEntry._ID + "=" + id,
                null) > 0;

    }

    public Cursor getAllToDo() {
        return mDb.query(ToDoListContract.ToDoListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}