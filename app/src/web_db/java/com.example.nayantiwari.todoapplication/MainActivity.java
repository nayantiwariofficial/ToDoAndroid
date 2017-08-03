package com.example.nayantiwari.todoapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nayantiwari.todoapplication.dto.ToDo;
import com.example.nayantiwari.todoapplication.utility.NetworkUtils;
import com.example.nayantiwari.todoapplication.utility.ToDoListAdapter;
import com.example.nayantiwari.todoapplication.utility.ToDoLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<ToDo>>, ToDoListAdapter.ListItemClickListener {

    private RecyclerView recyclerView;
    private ToDoListAdapter toDoListAdapter;
    private List<ToDo> data;
    private Loader<List<ToDo>> listLoader;
    private RequestType requestType = RequestType.GET;

    public static final String TODO_TITLE_KEY1 = "TODO_TITLE_KEY";
    public static final String TODO_DATE_TIME1 = "TODO_DATE_TIME";
    public static final String TODO_ITEM_ID1 = "TODO_ITEM_ID1";
    public static final String TODO_IS_CHECKED = "TODO_IS_CHECKED";

    private static final String TAG = "MainActivity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) this.findViewById(R.id.all_todo_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoListAdapter = new ToDoListAdapter(this, new ArrayList<ToDo>(), this);

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
//                final int fromPosition = viewHolder.getAdapterPosition();
//                final int toPosition = target.getAdapterPosition();

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                int adapterPosition = viewHolder.getAdapterPosition();
                toDoListAdapter.getToDoItems().remove(adapterPosition);
                toDoListAdapter.notifyItemRemoved(adapterPosition);
//                toDoListAdapter.notifyDataSetChanged();
            }

        }).attachToRecyclerView(recyclerView);

        listLoader = getSupportLoaderManager().initLoader(0, savedInstanceState, this);
        listLoader.forceLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, ShowToDoActivity.class);
        ToDo toDo = data.get(clickedItemIndex);
        requestType = RequestType.GET;
        String toDoUrl = NetworkUtils.buildUrl(toDo.getId()).toString();
        ToDoLoader toDoLoader = new ToDoLoader(this, toDoUrl, requestType);
        toDoLoader.loadInBackground();
        intent.putExtra(TODO_TITLE_KEY1, toDo.getTitle());
        intent.putExtra(TODO_ITEM_ID1, toDo.getId());
        intent.putExtra(TODO_DATE_TIME1, String.valueOf(toDo.getDate()));
        intent.putExtra(TODO_IS_CHECKED, toDo.getIsChecked());
        startActivity(intent);
    }

    private void removeGuest(long id) {
        String toDoUrl = NetworkUtils.buildUrl(id).toString();
        requestType = RequestType.DELETE;
        ToDoLoader toDoLoader = new ToDoLoader(this, toDoUrl, requestType);
        toDoLoader.forceLoad();
        Log.i(TAG, "removeGuest: " + toDoUrl);
//        listLoader.forceLoad();
    }

    public String getAllToDo() {
        String toDoUrl = NetworkUtils.buildUrl().toString();
        System.out.println(toDoUrl);

        return toDoUrl;
    }

    @Override
    public Loader<List<ToDo>> onCreateLoader(int id, Bundle args) {
        String toDoUrl = getAllToDo();
        requestType = RequestType.GET;
        Log.i("MainActivity", "onCreateLoader: " + toDoUrl);
        return new ToDoLoader(this, toDoUrl, requestType);
    }

    @Override
    public void onLoadFinished(Loader<List<ToDo>> loader, List<ToDo> data) {
        ToDoListAdapter toDoListAdapter = (ToDoListAdapter) recyclerView.getAdapter();
        if (isNetworkAvailable()) {
            if (data != null) {
                Log.i("MainActivity", "onLoadFinished: Loaded " + data.size() + " items.");
            } else {
                Log.i(TAG, "onLoadFinished: No Data");
            }
            this.data = data;
            toDoListAdapter.setTodoItems(this.data);
            toDoListAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ToDo>> loader) {

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
