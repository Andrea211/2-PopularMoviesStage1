package com.example.popularmovies;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class QueryUtils {

    // Data to create url, including my private key
    private final static String MOVIE_DATABASE_URL = "http://api.themoviedb.org/3/movie";
    private final static String API_KEY = "enter your key";

    // Build Url necessary to retrieve data from the internet
    public static URL buildUrl(String[] query)  {
        Uri builtUri = Uri.parse(MOVIE_DATABASE_URL).buildUpon()
                .appendPath(query[0])
                .appendQueryParameter("api_key", API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
