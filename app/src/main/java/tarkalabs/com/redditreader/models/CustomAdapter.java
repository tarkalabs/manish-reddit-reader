package tarkalabs.com.redditreader.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tarkalabs.com.redditreader.R;

public class CustomAdapter extends BaseAdapter {

    RedditEntry redditEntry=new RedditEntry();

    public List<RedditEntry> getRedditlist() {
        return redditlist;
    }

    public void setRedditlist(List<RedditEntry> redditlist) {
        this.redditlist = redditlist;
    }

    List<RedditEntry> redditlist ;
    Context context;

    public CustomAdapter(Context context,List<RedditEntry> list){
        this.redditlist = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return redditlist.size();
    }

    @Override
    public Object getItem(int position) {
        return redditlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView comments = (TextView) view.findViewById(R.id.comment);
        TextView score = (TextView) view.findViewById(R.id.score);

        RedditEntry redditEntry = redditlist.get(position);
        title.setText(redditEntry.title);

        comments.setText(redditEntry.numComments.toString());

        score.setText(redditEntry.score.toString());
        return view;
    }
}
