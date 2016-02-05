package tarkalabs.com.redditreader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tarkalabs.com.redditreader.models.CustomAdapter;
import tarkalabs.com.redditreader.models.RedditEntry;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://www.reddit.com/hot.json";
    List<RedditEntry> entries=new ArrayList<RedditEntry>();
    ListView lv;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncTask<String, Void, List<RedditEntry>> fetchDataTask = new FetchData().execute(URL);
        customAdapter = new CustomAdapter(this, entries);
        lv= (ListView) findViewById(R.id.listv);
        lv.setAdapter(customAdapter);

    }
    void onRefreshEntries(List<RedditEntry> entries) {
        customAdapter.setRedditlist(entries);
        customAdapter.notifyDataSetChanged();
    }

    public class FetchData extends AsyncTask<String,Void,List<RedditEntry>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<RedditEntry> redditEntries) {
            super.onPostExecute(redditEntries);
            onRefreshEntries(redditEntries);
        }

        @Override
        protected List<RedditEntry> doInBackground(String... params) {
            String url = params[0];

            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                List<RedditEntry> redditEntries = new ArrayList<>();
                String jsonBody = response.body().string();



                JsonParser jsonParser = new JsonParser();
                JsonObject parse = jsonParser.parse(jsonBody).getAsJsonObject();
                JsonArray children = parse.get("data").getAsJsonObject().getAsJsonArray("children");

                for(int i=0;i<children.size();i++) {
                    GsonBuilder gson = new GsonBuilder();
                    JsonObject data = children.get(i).getAsJsonObject().get("data").getAsJsonObject();
                    RedditEntry redditEntry = gson.create().fromJson(data, RedditEntry.class);
                    redditEntries.add(redditEntry);
                }
                return redditEntries;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
