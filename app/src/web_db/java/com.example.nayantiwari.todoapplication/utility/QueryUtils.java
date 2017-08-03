package com.example.nayantiwari.todoapplication.utility;

import android.util.Log;

import com.example.nayantiwari.todoapplication.RequestType;
import com.example.nayantiwari.todoapplication.dto.ToDo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = "QueryUtils";

    public static List<ToDo> fetchToDoData(String requestUrl) {
        return fetchToDoData(requestUrl, RequestType.GET);
    }

    public static List<ToDo> fetchToDoData(String requestUrl, RequestType requestType) {

        URL url = createUrl(requestUrl);

        String jsonResponse;
        List<ToDo> allTodos = null;
        try {
            if (requestType == null) {
                requestType = RequestType.GET;
            }
            jsonResponse = makeHttpRequest(url, requestType.toString());
            Log.i(LOG_TAG, "fetchToDoData: " + jsonResponse);
            allTodos = RetrieveJson.getAllTodos();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "fetchToDoData: going to extractFeatureFromJson");

        return allTodos;
    }

    public static List<ToDo> fetchToDoData(String requestUrl, RequestType requestType, ToDo toDo) {

        URL url = createUrl(requestUrl);

        String jsonResponse;
        List<ToDo> allTodos = null;
        try {
            if (requestType == null) {
                requestType = RequestType.POST;
            }
            jsonResponse = makeHttpRequest(url, requestType.toString(), toDo);
            Log.i(LOG_TAG, "fetchToDoData: " + jsonResponse);
            allTodos = RetrieveJson.getAllTodos();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "fetchToDoData: going to extractFeatureFromJson");

        return allTodos;
    }

    private static String makeHttpRequest(URL url, String requestType, ToDo toDo) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        Log.i(LOG_TAG, "makeHttpRequest: " + url);

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(requestType);
            if (requestType.equals("POST") || requestType.equals("DELETE") || requestType.equals("PUT")) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writer().writeValue(urlConnection.getOutputStream(), toDo);

            }
//            urlConnection.getHeaderFields().put("Content-Type", Collections.singletonList("application/json"));
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.i(LOG_TAG, "makeHttpRequest: if condition satisfied");
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String makeHttpRequest(URL url, String requestType) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        Log.i(LOG_TAG, "makeHttpRequest: " + url);

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(requestType);
            if (requestType.equals("POST") || requestType.equals("DELETE")) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
            }
//            urlConnection.getHeaderFields().put("Content-Type", Collections.singletonList("application/json"));
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.i(LOG_TAG, "makeHttpRequest: if condition satisfied");
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building URL ", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
