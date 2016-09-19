package com.example.kp.webservice1;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Techjini on 9/18/2016.
 */
public class NetworkRequest {
    public static final String ERROR_HTTP_CLIENT_TIMEOUT = "408";
    public static final String ERROR_IO_EXCEPTION = "http_io_exception_error";
    public static final String ERROR_UNKNOWN = "http_unknown_error";

    //
    public String callServer() {
        String result = null;
        int resCode = -1;
        try {
            //Declaring a URL Connection
            URL url = new URL(StaticUtils.API_URL);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            //Opening InputStream to connection
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                //Log.e("resCode",""+resCode);
                InputStream in = httpConn.getInputStream();
                // Downloading and decoding the string response using builder
                result = StaticUtils.sConvertStreamToString(in);
                return result;
            }
            else if(resCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                result = ERROR_HTTP_CLIENT_TIMEOUT;
                return result;
            } else {
                result = ERROR_UNKNOWN;
                return result;
            }

        } catch (MalformedURLException e) {
            result = ERROR_UNKNOWN;
            return result;

        } catch (IOException e) {
            result = ERROR_IO_EXCEPTION;
            return result;
        }
    }
}
