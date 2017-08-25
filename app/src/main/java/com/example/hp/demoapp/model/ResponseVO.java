package com.example.hp.demoapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.sale on 24-08-2017.
 */

public class ResponseVO
{
    @SerializedName("objects")
    @Expose
    private ArrayList<CityVO> citiesList = null;

    public ArrayList<CityVO> getCities() {
        return citiesList;
    }

    public void setObjects(ArrayList<CityVO> citiesList) {
        this.citiesList = citiesList;
    }
}
