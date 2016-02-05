package tarkalabs.com.redditreader.models;


import com.google.gson.annotations.SerializedName;

public class RedditEntry {
    public String url;
    @SerializedName("num_comments")
    public Integer numComments;
    public Integer score;
    public String title;
}
