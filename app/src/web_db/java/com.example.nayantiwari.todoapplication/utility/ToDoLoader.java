package com.example.nayantiwari.todoapplication.utility;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.nayantiwari.todoapplication.RequestType;
import com.example.nayantiwari.todoapplication.dto.ToDo;

import java.util.List;

public class ToDoLoader extends AsyncTaskLoader<List<ToDo>> {

    private String mUrl;
    private RequestType requestType;
    private ToDo toDo;

    public ToDoLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
        this.requestType = RequestType.GET;
    }

    public ToDoLoader(Context context, String url, RequestType requestType) {
        super(context);
        this.mUrl = url;
        this.requestType = requestType;
    }

    public ToDoLoader(Context context, String mUrl, RequestType requestType, ToDo toDo) {
        super(context);
        this.mUrl = mUrl;
        this.requestType = requestType;
        this.toDo = toDo;
    }

    @Override
    public List<ToDo> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i("ToDo Loader", "loadInBackground: Made it till here");
        if (requestType == RequestType.POST || requestType == RequestType.PUT)
            return QueryUtils.fetchToDoData(mUrl, requestType, this.toDo);
        else
            return QueryUtils.fetchToDoData(mUrl, requestType);
    }


}