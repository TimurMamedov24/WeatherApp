package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tamaramamedova.weatherapp.FinalValues;
import com.example.tamaramamedova.weatherapp.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Adapter for displaying bottom layer to get the weekly weather.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Weather> dailyWeather;

    protected Context context;

    public RecyclerViewAdapter(Context context, List<Weather> dailyWeather) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.dayOfWeek.setText(dailyWeather.get(position).getDayOfWeek());


        String mTemp = Integer.toString(dailyWeather.get(position).getTemperature());
        holder.weatherResult.setText(mTemp+" C");

        holder.weatherResultSmall.setText(mTemp);
        holder.weatherResultSmall.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }


}
