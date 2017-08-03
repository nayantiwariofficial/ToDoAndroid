package com.example.nayantiwari.todoapplication.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by nayantiwari on 8/4/17.
 */

public class ServiceGenerator {

    private final static String BASE_URL = "http://192.168.0.168:8090/rest/";

    private static OkHttpClient.Builder httpClient = null;
    private static Retrofit.Builder builder = null;

    static {
        httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES); // connect timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        builder = new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create(objectMapper));
    }

    public static ToDoService createService() {
        Retrofit retrofit = builder.build();
        return retrofit.create(ToDoService.class);
    }

}
