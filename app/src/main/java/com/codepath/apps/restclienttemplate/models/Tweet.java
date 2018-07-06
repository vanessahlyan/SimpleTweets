package com.codepath.apps.restclienttemplate.models;

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
    /*
    public JSONObject entitiesObject;
    public JSONArray mediaArray;
    public JSONObject mediaObject;
    */

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

        /*
        tweet.entitiesObject = jsonObject.getJSONObject("entities");

        if (tweet.entitiesObject != null && tweet.entitiesObject.has("media")) {
            tweet.mediaArray = tweet.getEntitiesObject().getJSONArray("media");

            if (tweet.mediaArray != null && tweet.mediaArray.size() > 0) {
                tweet.mediaObject = tweet.mediaArray.getJSONObject(0);
            }
        }
        */

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

    /*
    public JSONObject getEntitiesObject() {
        return entitiesObject;
    }

    public JSONArray getMediaArray() {
        return mediaArray;
    }
    */
}