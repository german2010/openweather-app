package weather.gpolitov.com.weatherapp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.favorite_item.view.*
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.Favorite


class FavoriteAdapter(private val clickListener: (Favorite) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var items: MutableList<Favorite> = ArrayList()
    private var hasConnected = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position], clickListener)
    }

    fun updateList(favoriteList: List<Favorite>?, hasConnected: Boolean) {
        this.hasConnected = hasConnected
        if (favoriteList != null) {
            items.clear()
            items.addAll(favoriteList)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite, clickListener: (Favorite) -> Unit) {
            itemView.name_text_view.text = favorite.name
            itemView.temp_text_view.text = favorite.temp
            if (!hasConnected) {
                itemView.date_text_view.visibility = View.VISIBLE
                itemView.date_text_view.text = favorite.date
            }

            itemView.setOnClickListener { clickListener(favorite) }
        }
    }
}