package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    EditText etNewTweet;
    Tweet newTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient(this);
        getSupportActionBar().setTitle("Compose Tweet");

        etNewTweet = (EditText) findViewById(R.id.newTweet);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onPostItem(View v){

        // pb.setVisibility(ProgressBar.VISIBLE)
        client.sendTweet(etNewTweet.getText().toString(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Prepare intent to pass back to TimelineActivity
                    newTweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("new_tweet", Parcels.wrap(newTweet));
                    setResult(RESULT_OK, data);
                    //pb.setVisibility(ProgressBar.INVISIBLE)
                    // Activity finished ok, return the data
                    finish();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


    }
}
