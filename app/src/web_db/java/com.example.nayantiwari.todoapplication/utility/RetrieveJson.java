package com.example.nayantiwari.todoapplication.utility;

import com.example.nayantiwari.todoapplication.dto.ToDo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nayantiwari on 8/1/17.
 */

public class RetrieveJson {

    public static List<ToDo> getAllTodos() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = NetworkUtils.buildUrl();
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            output.append(line).append("\n");
        }
        List<ToDo> toDos = objectMapper.readValue(output.toString(), new TypeReference<List<ToDo>>() {});
        return toDos;
    }

    private void printJson() throws Exception {
//        System.out.println(getJson());
    }

    private void setValuesToData() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2017);

        ToDo sample = new ToDo(1, "title", 0, cal.getTime());

//        List<String> strings = Arrays.asList(keys);
//        String[] strings1 = strings.toArray(new String[strings.size()]);

        ObjectMapper objectMapper = new ObjectMapper();

        //Json to JAVA
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, sample);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
