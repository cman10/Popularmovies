package app.portfolio.popularmovies1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ImageAdapter myCustomArrayAdapter;
    GridView gv;
    String fetchurl;
    String murllist;
    List<String> urllist= new ArrayList<String>();
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  myCustomAdapter = new ArrayAdapter<String>(getActivity(), R.layout.load_movies, R.id.image_load, new ArrayList<String>());
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        gv= (GridView) rootview.findViewById(R.id.grid_view);
//adapter= new ImageAdapter(getActivity(),R.layout.load_movies,url);
//gv.setAdapter(adapter);

        myCustomArrayAdapter = new ImageAdapter(getActivity(),urllist);
        gv.setAdapter(myCustomArrayAdapter);
        return rootview;

    }

public  class fetchposter extends AsyncTask<Object, Object, List<String>> {

    private final String log_tag = fetchposter.class.getSimpleName();
    @Override
    protected List<String> doInBackground(Object... params) {
        String imgurl = "http://image.tmdb.org/t/p/w185";
        HttpURLConnection urlConnection = null;
        String jsonstr = null;

        URL url = null;
        try {
            url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=mykey");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputstream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
            StringBuffer buffer = new StringBuffer();
            if (inputstream == null) {
                return null;
            }

            String line;
            while ((line = br.readLine()) != null) {

                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.
                return null;
            }
            jsonstr = buffer.toString();
            JSONObject obj = new JSONObject(jsonstr);
            JSONArray jarray = obj.getJSONArray("results");


            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                String posterPath = object.getString("poster_path");

fetchurl=imgurl.concat(posterPath);
                urllist.add(fetchurl);
                Log.v(log_tag,"poster_path"+posterPath.toString());
            }
            return urllist;



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        myCustomArrayAdapter.clear();
myCustomArrayAdapter.addAll(urllist);
        super.onPostExecute(strings);
    }
}

}
