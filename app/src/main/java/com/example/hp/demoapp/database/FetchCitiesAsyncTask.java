package com.example.hp.demoapp.database;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.hp.demoapp.model.CityVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.sale on 25-08-2017.
 */

public class FetchCitiesAsyncTask extends AsyncTask<Void,Void,List<CityVO>>
{
    private DatabaseOperationController mController;
    private OnFetchDataListener         mListener;

    public FetchCitiesAsyncTask(DatabaseOperationController databaseOperationController)
    {
        mController = databaseOperationController;
    }

    @Override
    protected List<CityVO> doInBackground(Void... params)
    {
        Cursor cursor = mController.fetchData();
        if(cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            List<CityVO> cityVOs = new ArrayList<>();
            while(cursor.moveToNext())
            {
                if(!isCancelled())// this condition checks whether asynctask has been cancelled or not due to onDestroy
                {
                    int id          = cursor.getInt(cursor.getColumnIndexOrThrow(CityTableContract.CityTable.COLUMN_ID));
                    String cityName = cursor.getString(cursor.getColumnIndexOrThrow(CityTableContract.CityTable.COLUMN_NAME_CITY));
                    String slug     = cursor.getString(cursor.getColumnIndexOrThrow(CityTableContract.CityTable.COLUMN_SLUG));
                    CityVO cityVO = new CityVO();
                    cityVO.setId(id);
                    cityVO.setName(cityName);
                    cityVO.setSlug(slug);
                    cityVOs.add(cityVO);
                }
                else
                {
                    return null;
                }
            }
            cursor.close();
            return cityVOs;
        }
    }

    @Override
    protected void onPostExecute(List<CityVO> cityVOs)
    {
        super.onPostExecute(cityVOs);
        if(cityVOs == null)
        {
            mListener.onDBFetchFialed();
        }
        else
        {
            mListener.onDBFetchedSuccess(cityVOs);
        }
        mListener   = null;
        mController = null;
    }

    public void registerListener(OnFetchDataListener listener)
    {
        mListener = listener;
    }

    public void unregisterListener()
    {
        mListener = null;
    }
}
