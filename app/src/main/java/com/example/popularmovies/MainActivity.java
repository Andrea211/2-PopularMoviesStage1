package com.example.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private final String POPULAR_QUERY = "popular";

    Poster[] posters;
    ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        // Use LayoutManager to create gridView upon launch
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById (R.id.recycler_view);

        // Using a Grid Layout Manager
        // Number of columns = 3 for design reasons
        final int NUM_OF_COLUMNS = 3;
        mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Upon launch, order posters in popular order
        new FetchDataAsyncTask().execute(POPULAR_QUERY);
    }

    // Create menu to be able to choose sorting options
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Add activities to menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isOnline()) {
            switch (item.getItemId()) {
                case R.id.most_popular:
                    new FetchDataAsyncTask().execute(POPULAR_QUERY);
                    return true;
                case R.id.top_rated:
                    final String TOP_RATED_QUERY = "top_rated";
                    new FetchDataAsyncTask().execute(TOP_RATED_QUERY);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
                }
        } else {
            Toast.makeText(getApplicationContext (), "No internet connection.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    // Create Poster array to store data
    public Poster[] makePostersDataToArray(String postersJsonResults) throws JSONException {

        // JSON keys used for retrieving data
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTER_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String VOTE_COUNT = "vote_count";
        final String ORIGINAL_LANGUAGE = "original_language";

        // Create JSONObject
        JSONObject postersJson = new JSONObject(postersJsonResults);
        // Create JSONArray
        JSONArray resultsArray = postersJson.getJSONArray(RESULTS);

        // Create posters array as long as the resultsArray.length
        posters = new Poster[resultsArray.length()];

        // For every poster, get data
        for (int i = 0; i < resultsArray.length(); i++) {

            // Initialisation
            posters[i] = new Poster();

            JSONObject posterInfo = resultsArray.getJSONObject(i);

            // Set proper data in posters
            posters[i].setOriginalTitle(posterInfo.getString(ORIGINAL_TITLE));
            posters[i].setPosterPath(posterInfo.getString(POSTER_PATH));
            posters[i].setOverview(posterInfo.getString(OVERVIEW));
            posters[i].setVoterAverage(posterInfo.getDouble(VOTER_AVERAGE));
            posters[i].setReleaseDate(posterInfo.getString(RELEASE_DATE));
            posters[i].setVoteCount(posterInfo.getDouble(VOTE_COUNT));
            posters[i].setOriginalLanguage(posterInfo.getString(ORIGINAL_LANGUAGE));
        }
        return posters;
    }

    // Fetch data - asynchronous so it can be performed in background thread
    public class FetchDataAsyncTask extends AsyncTask<String, Void, Poster[]> {
        public FetchDataAsyncTask() {
            super();
        }

        @Override
        protected Poster[] doInBackground(String... params) {

            String posterSearchResults;

            try {
                URL url = QueryUtils.buildUrl(params);
                posterSearchResults = QueryUtils.getResponseFromHttpUrl(url);

                if(posterSearchResults == null) {
                    return null;
                }
            } catch (IOException e) {
                return null;
            }

            try {
                return makePostersDataToArray (posterSearchResults);
            } catch (JSONException e) {
                e.printStackTrace ();
            }
            return null;
        }

        protected void onPostExecute(Poster[] posters) {
            mImageAdapter = new ImageAdapter(getApplicationContext(), posters);
            mRecyclerView.setAdapter(mImageAdapter);
        }
    }

    // See if we have online connection
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
