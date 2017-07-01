package com.harpreetdev.demoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harpreetdev.demoapp.DetailsActivity;
import com.harpreetdev.demoapp.R;
import com.harpreetdev.demoapp.model.ListItem;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Harpreet on 7/1/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    List<ListItem> listitems;

    public RecyclerViewAdapter(List<ListItem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item,parent,false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final ListItem lm = listitems.get(position);
        holder.tvTrackName.setText(lm.getTrackName());
        holder.tvPrice.setText("$"+ lm.getPrice());
        int duration =  Integer.parseInt(lm.getDuration());
        int seconds = (duration/1000)%60;
        long minutes = ((duration-seconds)/1000)/60;



        holder.tvDuration.setText(minutes + ":" + seconds);
        holder.tvSonytype.setText(lm.getGenre());
        String trimArtist=lm.getArtistName();
        trimArtist=trimArtist.substring( 0 , Math.min(trimArtist.length(), 10));
        holder.tvArtist.setText(trimArtist+"...");
        Picasso.with(context).load(lm.getArtWorkURl()).into(holder.imageViewArtWork);
        holder.linearLayoutCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("pos",position);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  tvTrackName,tvArtist,tvSonytype,tvDuration,tvPrice;
        public LinearLayout linearLayoutCell;
        public ImageView imageViewArtWork;


        public ViewHolder(View itemView) {
            super(itemView);
           tvTrackName = (TextView) itemView.findViewById(R.id.textview_trackname);
            tvArtist   = (TextView) itemView.findViewById(R.id.textview_artistname);
            tvSonytype = (TextView) itemView.findViewById(R.id.textview_genre);
            tvDuration = (TextView) itemView.findViewById(R.id.textview_duration);
            tvPrice    = (TextView) itemView.findViewById(R.id.textview_price);
            imageViewArtWork= (ImageView) itemView.findViewById(R.id.imageview_artwork);
            linearLayoutCell=(LinearLayout) itemView.findViewById(R.id.linearlayout_listitem);

        }
    }
}
