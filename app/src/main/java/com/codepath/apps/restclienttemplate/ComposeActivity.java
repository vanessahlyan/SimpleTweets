package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    EditText etNewTweet;
    Tweet newTweet;
    TextView tvCharCount;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient(this);
        getSupportActionBar().setTitle("Compose Tweet");

        etNewTweet = (EditText) findViewById(R.id.etnewTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        pb = (ProgressBar) findViewById(R.id.pbLoading);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_compose, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_close:
                closeCompose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    private void closeCompose() {
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }


    // implement onKeyUp function
    //Button bPost = (Button) findViewById(R.id.btnCompose);
    //bPost.setOnClickListener

    public void onPostItem(View view) {
        pb.setVisibility(ProgressBar.VISIBLE);
        client.sendTweet(etNewTweet.getText().toString(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Prepare intent to pass back to TimelineActivity
                    newTweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("newTweet", Parcels.wrap(newTweet));
                    setResult(RESULT_OK, data);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    // Activity finished ok, return the data
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("SendTweet", errorResponse.toString());
                pb.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("SendTweet", errorResponse.toString());
                pb.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("SendTweet", responseString);
                pb.setVisibility(ProgressBar.INVISIBLE);
            }
        });


    }
}
