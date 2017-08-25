package com.example.hp.demoapp.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.demoapp.R;
import com.example.hp.demoapp.model.CityVO;

/**
 * Created by kunal.sale on 24-08-2017.
 */

class CitiesViewHolder extends RecyclerView.ViewHolder
{
    private TextView mTxtCityName;

    CitiesViewHolder(View itemView)
    {
        super(itemView);
        mTxtCityName = (TextView)itemView.findViewById(R.id.txtCityName);
    }

    void onBindHolder(final CityVO cityVO , final OnRecyclerClickedListener listener)
    {
        mTxtCityName.setText(cityVO.getName());
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClicked(getLayoutPosition());
            }
        });
    }
}
