package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    // list out the attributes
    public String body;
    public long uid; // data base ID for the tweet
    public String createdAt;
    public String timeStamp;
    public String mediaLink;

    public Tweet() {}

    public User user;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        JSONObject entitiesObject;
        JSONArray mediaArray;
        JSONObject mediaObject;



        entitiesObject = jsonObject.getJSONObject("entities");

        if (entitiesObject != null && entitiesObject.has("media")) {
            mediaArray = entitiesObject.getJSONArray("media");

            if (mediaArray != null) {
                mediaObject = mediaArray.getJSONObject(0);
                tweet.mediaLink = mediaObject.getString("media_url");
                System.out.println("hello");
            }
        }


        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public User getUser() {
        return user;
    }




}