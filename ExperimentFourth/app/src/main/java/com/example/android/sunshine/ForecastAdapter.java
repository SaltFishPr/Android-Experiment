/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.support.v7.widget.RecyclerView}.
 */
class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    /*
     * Below, we've defined an interface to handle clicks on items within this Adapter. In the
     * constructor of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface. We store that instance in this variable to call the onClick method whenever
     * an item is clicked in the list.
     */
    final private ForecastAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ForecastAdapterOnClickHandler {
        void onClick(long date);
    }

    private Cursor mCursor;

    /**
     * Creates a ForecastAdapter.
     *
     * @param context      Used to talk to the UI and app resources
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public ForecastAdapter(@NonNull Context context, ForecastAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (like ours does) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.today_item, viewGroup, false);
            view.setFocusable(true);
            return new TodayViewHolder(view);
        } else {
            View view = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.forecast_list_item, viewGroup, false);
            view.setFocusable(true);
            return new ForecastAdapterViewHolder(view);
        }


    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the
     *                   contents of the item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        mCursor.moveToPosition(position);

        long dateInMillis = mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
        /* Get human readable string using our utility method */
        String dateString = SunshineDateUtils.getFriendlyDateString(mContext, dateInMillis, false);
        /* Use the weatherId to obtain the proper description */
        int weatherId = mCursor.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(mContext, weatherId);
        /* Read high temperature from the cursor (in degrees celsius) */
        double highInCelsius = mCursor.getDouble(MainActivity.INDEX_WEATHER_MAX_TEMP);
        /* Read low temperature from the cursor (in degrees celsius) */
        double lowInCelsius = mCursor.getDouble(MainActivity.INDEX_WEATHER_MIN_TEMP);

        String highAndLowTemperature =
                SunshineWeatherUtils.formatHighLows(mContext, highInCelsius, lowInCelsius);

        String highString = SunshineWeatherUtils.formatTemperature(mContext, highInCelsius);
        String lowString = SunshineWeatherUtils.formatTemperature(mContext, lowInCelsius);

        Drawable drawable;
        switch (description) {
            case "Drizzle":
            case "Light Rain":
            case "Moderate Rain":
                drawable = mContext.getResources().getDrawable(R.drawable.art_light_rain);
                break;
            case "Heavy Rain":
            case "Extreme Rain":
            case "Intense Rain":
            case "Freezing Rain":
            case "Light Shower":
            case "Ragged Shower":
                drawable = mContext.getResources().getDrawable(R.drawable.art_rain);
                break;
            case "Light Snow":
            case "Snow":
            case "Heavy Snow":
            case "Sleet":
            case "Shower Sleet":
            case "Rain and Snow":
            case "Shower Snow":
                drawable = mContext.getResources().getDrawable(R.drawable.art_snow);
                break;
            case "Mist":
            case "Smoke":
            case "Haze":
            case "Sand, Dust":
            case "Fog":
                drawable = mContext.getResources().getDrawable(R.drawable.art_fog);
                break;
            case "Clear":
            case "Mostly Clear":
                drawable = mContext.getResources().getDrawable(R.drawable.art_clear);
                break;
            case "Scattered Clouds":
            case "Broken Clouds":
                drawable = mContext.getResources().getDrawable(R.drawable.art_light_clouds);
                break;
            case "Overcast Clouds":
                drawable = mContext.getResources().getDrawable(R.drawable.art_clouds);
                break;
            default:
                drawable = mContext.getResources().getDrawable(R.drawable.ic_unknown_24px);
                break;
        }
        if (getItemViewType(position) == 0) {
            ((TodayViewHolder) viewHolder).weatherImage.setImageDrawable(drawable);
            ((TodayViewHolder) viewHolder).mDate.setText(dateString.trim());
            ((TodayViewHolder) viewHolder).mWeather.setText(description.trim());
            ((TodayViewHolder) viewHolder).mHighTemperature.setText(highString.trim());
            ((TodayViewHolder) viewHolder).mLowTemperature.setText(lowString.trim());

        } else {
            Log.i("dateString:", dateString);
            ((ForecastAdapterViewHolder) viewHolder).weatherImage.setImageDrawable(drawable);
            ((ForecastAdapterViewHolder) viewHolder).mDate.setText(dateString.trim());
            ((ForecastAdapterViewHolder) viewHolder).mWeather.setText(description.trim());
            ((ForecastAdapterViewHolder) viewHolder).mTemperature.setText(highAndLowTemperature.trim());
        }
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Swaps the cursor used by the ForecastAdapter for its weather data. This method is called by
     * MainActivity after a load has finished, as well as when the Loader responsible for loading
     * the weather data is reset. When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newCursor the new cursor to use as ForecastAdapter's data source
     */
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView weatherImage;
        final TextView mDate;
        final TextView mWeather;
        final TextView mTemperature;

        ForecastAdapterViewHolder(View view) {
            super(view);

            weatherImage = view.findViewById(R.id.image_weather);
            mDate = view.findViewById(R.id.tv_date);
            mWeather = view.findViewById(R.id.tv_weather);
            mTemperature = view.findViewById(R.id.tv_temperature);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            long dateInMillis = mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
            mClickHandler.onClick(dateInMillis);
        }
    }

    class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView weatherImage;
        final TextView mDate;
        final TextView mWeather;
        final TextView mHighTemperature;
        final TextView mLowTemperature;

        TodayViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherImage = itemView.findViewById(R.id.image_weather_today);
            mDate = itemView.findViewById(R.id.date_today);
            mWeather = itemView.findViewById(R.id.weather_description_today);
            mHighTemperature = itemView.findViewById(R.id.high_temperature_today);
            mLowTemperature = itemView.findViewById(R.id.low_temperature_today);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            long dateInMillis = mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
            mClickHandler.onClick(dateInMillis);
        }
    }
}