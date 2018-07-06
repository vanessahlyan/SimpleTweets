package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User currentUser;

    //@BindView(R.id.ivProfileImage) ImageView profileImage;
    @BindView(R.id.tvName) TextView name;
    @BindView(R.id.tvUsername) TextView username;
    @BindView(R.id.tvFollowingCount) TextView followingCount;
    @BindView(R.id.tvFollowerCount) TextView followerCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


        currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("User"));
        getSupportActionBar().setTitle("@" + currentUser.getScreenName());
        name.setText(currentUser.getName());
        username.setText(currentUser.getScreenName());
        followingCount.setText(Integer.toString(currentUser.getFollowingCount()) + " Following");
        followerCount.setText(Integer.toString(currentUser.getFollowersCount()) + " Followers");




    }
}
