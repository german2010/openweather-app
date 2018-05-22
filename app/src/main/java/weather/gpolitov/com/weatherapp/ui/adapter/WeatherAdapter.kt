package weather.gpolitov.com.weatherapp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.WeatherList

class WeatherAdapter(private val context: Context) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var items: MutableList<WeatherList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = items[position]

        holder.date.text = weather.dateTime
        holder.description.text = weather.weather[0].description
        holder.temperature.text = weather.main.temp.toString()
    }

    fun updateList(weatherList: List<WeatherList>?) {
        if (weatherList != null) {
            items.clear()
            items.addAll(weatherList)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView!!.findViewById(R.id.date_text_view)
        var description: TextView = itemView!!.findViewById(R.id.description_text_view)
        var temperature: TextView = itemView!!.findViewById(R.id.temp_text_view)
    }
}