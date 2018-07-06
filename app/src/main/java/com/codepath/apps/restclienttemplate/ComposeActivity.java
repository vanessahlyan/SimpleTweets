package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    Tweet newTweet;

    @BindView(R.id.etnewTweet) EditText etNewTweet;
    @BindView(R.id.tvCharCount) TextView tvCharCount;
    @BindView(R.id.pbLoading) ProgressBar pb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient(this);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Compose Tweet");


        Tweet originalTweet;
        originalTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("currentTweet"));
        if (originalTweet != null) {
            etNewTweet.setText("@" + originalTweet.getUser().screenName);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return super.onCreateOptionsMenu(menu);
    }


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


    private void closeCompose() {
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }


    // implement onKeyUp function

    public void onPostStatusUpdate(View view) {
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








