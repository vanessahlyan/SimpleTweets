package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class TweetDetailActivity extends AppCompatActivity {

    Tweet currentTweet;
    TwitterClient client;

    @BindView(R.id.tvBody) TextView body;
    @BindView(R.id.tvUsername) TextView username;
    @BindView(R.id.tvName) TextView name;
    @BindView(R.id.tvCreatedAt) TextView createdAt;
    @BindView(R.id.ivProfileImage) ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.bind(this);

        currentTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("currentTweet"));
        body.setText(currentTweet.getBody());
        username.setText("@" + currentTweet.getUser().screenName);
        name.setText(currentTweet.getUser().name);
        createdAt.setText(currentTweet.getCreatedAt());

        Glide.with(this).load(currentTweet.user.profileImageUrl).into(profileImage);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfileActivity();

            }
        });

    }

    public void launchProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("User", Parcels.wrap(currentTweet.getUser()));
        //i.putExtra("screen_name", currentTweet.getUser().getScreenName());
        startActivity(i);
    }

    public void onStartRetweet(View view) {
        Intent startRetweet = new Intent(this, ComposeActivity.class);
        startRetweet.putExtra("currentTweet", Parcels.wrap(currentTweet));
        startActivity(startRetweet);
    }

    @OnClick(R.id.btnFavoriteSelector)
    public void onFavoriteClick(View v) {
        if(v.isActivated()) {
            onUnfavorite();
        } else {
            onFavorite();
        }

        v.setActivated(!v.isActivated());
    }

    public void onFavorite() {
        // no need intent
        client.favorite(currentTweet.getUid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(TweetDetailActivity.this, "You liked this tweet", Toast.LENGTH_LONG).show();
                // Activity finished ok, return the data

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("SendTweet", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("SendTweet", errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("SendTweet", responseString);

            }


        });

    }


    public void onUnfavorite() {
        // no need intent
        client.unfavorite(currentTweet.getUid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(TweetDetailActivity.this, "You unliked this tweet", Toast.LENGTH_LONG).show();
                // Activity finished ok, return the data

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("SendTweet", errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("SendTweet", errorResponse.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("SendTweet", responseString);

            }


        });

    }




}





