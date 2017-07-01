package com.harpreetdev.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harpreetdev.demoapp.adapters.RecyclerViewAdapter;
import com.harpreetdev.demoapp.model.ListItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    String response;
    int position;
    ImageView imageView;
    TextView trackname,artistName,artistID, trackID,
            collectionID,collectionPrice,releaseDate,trackLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        trackname  = (TextView) findViewById(R.id.tv_track_name);
        artistName = (TextView) findViewById(R.id.tv_artist_name);
        artistID   = (TextView) findViewById(R.id.tv_artist_id);
        trackID    = (TextView) findViewById(R.id.tv_track_id);
        collectionID    = (TextView) findViewById(R.id.tv_collection_id);
        collectionPrice = (TextView) findViewById(R.id.tv_collection_price);
        releaseDate     = (TextView) findViewById(R.id.tv_release_date);
        trackLink  =  (TextView) findViewById(R.id.tv_track_link);
        imageView  = (ImageView) findViewById(R.id.imageview_main);


        response = Data.RESPONSE;
        Intent i = getIntent();
        position = i.getIntExtra("pos",0);

        showData();



    }

    private void showData() {


        try {
            JSONObject jsonobj  = new JSONObject(response);
            JSONArray jsonarray = jsonobj.getJSONArray("results");
                JSONObject obj  = jsonarray.getJSONObject(position);

            trackname.setText(obj.getString("trackName"));
            artistName.setText(obj.getString("artistName"));
            artistID.setText(obj.getString("artistId"));
            collectionID.setText(obj.getString("collectionId"));
            trackID.setText(obj.getString("trackId"));
            collectionPrice.setText("$" + obj.getString("collectionPrice"));
            releaseDate.setText(obj.getString("releaseDate"));
            trackLink.setText(obj.getString("trackViewUrl"));
            Picasso.with(this).load(obj.getString("artworkUrl100")).into(imageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
