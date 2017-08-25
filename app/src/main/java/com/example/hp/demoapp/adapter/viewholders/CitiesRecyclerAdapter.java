package com.example.hp.demoapp.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.demoapp.R;
import com.example.hp.demoapp.model.CityVO;

import java.util.ArrayList;

/**
 * Created by kunal.sale on 24-08-2017.
 */

public class CitiesRecyclerAdapter extends RecyclerView.Adapter<CitiesViewHolder>
{
    private ArrayList<CityVO>         mCityVOsList;
    private OnRecyclerClickedListener mListener;

    public CitiesRecyclerAdapter(ArrayList<CityVO> cityVOsList)
    {
        mCityVOsList = cityVOsList;
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_row_layout , parent, false);
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder holder, int position)
    {
        holder.onBindHolder(mCityVOsList.get(position) , mListener);
    }

    @Override
    public int getItemCount()
    {
        return mCityVOsList.size();
    }

    public void registerListener(OnRecyclerClickedListener listener)
    {
        mListener = listener;
    }

    public void unregisterListener()
    {
        mListener = null;
    }
}
