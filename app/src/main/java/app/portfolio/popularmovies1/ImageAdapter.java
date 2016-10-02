package app.portfolio.popularmovies1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Asus on 9/25/2016.
 */

public class ImageAdapter extends ArrayAdapter<String> {

    private Context context;
    private LayoutInflater inflater;
    public ImageAdapter(Activity context,List<String> urllist) {
        super(context,0,urllist);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView=inflater.inflate(R.layout.list,parent,false);
        }
        Picasso
                .with(context)
                .load()


        return convertView;

    }
}
