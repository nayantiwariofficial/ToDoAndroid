package com.example.nayantiwari.todoapplication.utility;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private final static String BASE_URL = "http://192.168.0.168:8090/rest/todo";

    private static final String LOG_TAG = "Network Utils";

    public static URL buildUrl(long id) {

        Uri builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath(String.valueOf(id))
                .build();
        Log.i(LOG_TAG, "buildUrl: " + builtUri);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrl() {
        Uri builtUri;
        builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .build();
        Log.i(LOG_TAG, "buildUrl: " + builtUri);


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

}
