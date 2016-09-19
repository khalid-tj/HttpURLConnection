package com.example.kp.webservice1;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Techjini on 9/18/2016.
 */
public class StaticUtils {
    public static final String API_URL = "http://tools.techjini.com/assignment.json";


    //
    public static String sConvertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String response;
        try {
            while ((response = reader.readLine()) != null) {
                stringBuilder.append(response + "\n");
            }
        } catch (IOException e) {
            return NetworkRequest.ERROR_IO_EXCEPTION;

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }
        return stringBuilder.toString();
    }
}
