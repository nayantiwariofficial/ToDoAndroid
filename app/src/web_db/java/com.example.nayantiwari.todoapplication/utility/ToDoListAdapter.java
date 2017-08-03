package com.example.nayantiwari.todoapplication.utility;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nayantiwari.todoapplication.R;
import com.example.nayantiwari.todoapplication.dto.ToDo;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by nayantiwari on 7/3/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private Context mContext;
    private List<ToDo> todoItems;

    final private ListItemClickListener mOnClickListener;

    public ToDoListAdapter(Context mContext, List<ToDo> todoItems, ListItemClickListener listener) {
        this.mContext = mContext;
        this.todoItems = todoItems;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.todo_list_item, parent, false);
        return new ToDoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoListViewHolder holder, int position) {
//        if (!mCursor.moveToPosition(position))
//            return; // bail if returned null
        ToDo toDo = todoItems.get(position);

        holder.titleTextView.setText(toDo.getTitle());
        holder.initialLetterTextView.setText(String.valueOf(toDo.getTitle().charAt(0)));
        holder.initialLetterTextView.setAllCaps(true);
        String dateTime = toDo.getDate() + "";
//         Update the view holder with the information needed to display
        // Display the date and time
        if (toDo.getDate() == null) {
            holder.titleTextView.setMaxLines(1);
            holder.dateTimeTextView.setText(dateTime);
        } else {
            holder.dateTimeTextView.setVisibility(View.GONE);
            holder.titleTextView.setMaxLines(2);
        }

        holder.itemView.setTag(toDo.getId());
    }

    @Override
    public int getItemCount() {
        return getToDoItems().size();
    }

    public List<ToDo> getToDoItems() {
        if (todoItems == null) {
            todoItems = new ArrayList<>();
        }
        return todoItems;
    }

    public void setTodoItems(List<ToDo> todoItems) {
        this.todoItems = todoItems;
    }

    class ToDoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView dateTimeTextView;
        TextView initialLetterTextView;

        public ToDoListViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_tv);
            dateTimeTextView = (TextView) itemView.findViewById(R.id.date_time_info_tv);
            initialLetterTextView = (TextView) itemView.findViewById(R.id.initial_letter_circle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedItemPosition);
        }


    }
}
