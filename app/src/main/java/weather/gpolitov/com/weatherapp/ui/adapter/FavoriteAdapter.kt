package weather.gpolitov.com.weatherapp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.Favorite


class FavoriteAdapter(private val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var items: MutableList<Favorite> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = items[position]

        holder.name.text = favorite.name
        holder.temp.text = favorite.temp
    }

    fun updateList(favoriteList: List<Favorite>?) {
        if (favoriteList != null) {
            items.clear()
            items.addAll(favoriteList)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView!!.findViewById(R.id.name_text_view)
        var temp: TextView = itemView!!.findViewById(R.id.temp_text_view)
    }
}