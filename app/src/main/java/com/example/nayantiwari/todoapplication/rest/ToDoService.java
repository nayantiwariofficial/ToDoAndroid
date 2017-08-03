package com.example.nayantiwari.todoapplication.rest;

import com.example.nayantiwari.todoapplication.dto.ToDo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nayantiwari on 8/4/17.
 */

public interface ToDoService {

    @GET
    public List<ToDo> getAll();

    @GET("{id}")
    public ToDo getToDo(@Path("id") long id);

    @POST
    public ToDo createToDo(@Body ToDo toDo);

    @PUT("{id}")
    public ToDo updateToDo(@Path("id") long id, @Body ToDo toDo);

    @DELETE("{id}")
    public ToDo deleteToDo(@Path("id") long id);

}