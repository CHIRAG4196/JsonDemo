package com.example.chirag.jsondemo;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chirag.jsondemo.model.Geoname;

import java.util.ArrayList;

/**
 * Created by chirag on 27-Apr-17.
 */

public class GeoAdapter extends RecyclerView.Adapter<GeoAdapter.UserViewHolder>{

    private Context context;
    private ArrayList<Geoname> geonameArrayList;
    public GeoAdapter(Context context, ArrayList<Geoname> userArrayList){
        this.context = context;
        this.geonameArrayList = userArrayList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Geoname geoname = geonameArrayList.get(position);
        holder.mTvFirstName.setText(geoname.getName());
        holder.mTvLastName.setText(geoname.getFclName());

    }

    @Override
    public int getItemCount() {
        return geonameArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvFirstName,mTvLastName;
        public UserViewHolder(View itemView) {
            super(itemView);
            mTvFirstName = (AppCompatTextView) itemView.findViewById(R.id.tv_first_name);
            mTvLastName = (AppCompatTextView) itemView.findViewById(R.id.tv_last_name);
        }
    }
}
