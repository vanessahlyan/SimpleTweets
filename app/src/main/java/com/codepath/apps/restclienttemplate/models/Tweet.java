package com.codepath.apps.restclienttemplate.models;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.util.Locale;

@Parcel
public class Tweet {
    // list out the attributes
    public String body;
    public long uid; // data base ID for the tweet
    public String createdAt;
    public String timeStamp;

    public Tweet() {}

    public User user;

    // deserialize the JSON
    @TargetApi(Build.VERSION_CODES.N)
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));



        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(tweet.createdAt).getTime();
            tweet.timeStamp = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return tweet;
    }


}
