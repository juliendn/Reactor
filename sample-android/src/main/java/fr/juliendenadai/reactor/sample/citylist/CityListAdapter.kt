package fr.juliendenadai.reactor.sample.citylist

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.juliendenadai.reactor.sample.R
import fr.juliendenadai.reactor.sample.pojo.MeteoForCity

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.CityListViewHolder>() {

    private val differ = AsyncListDiffer<MeteoForCity>(this, object : DiffUtil.ItemCallback<MeteoForCity>() {

        override fun areItemsTheSame(oldItem: MeteoForCity?, newItem: MeteoForCity?): Boolean =
                oldItem === newItem

        override fun areContentsTheSame(oldItem: MeteoForCity?, newItem: MeteoForCity?): Boolean =
                oldItem == newItem
    })

    fun updateDatas(newDatas: List<MeteoForCity>) {
        differ.submitList(newDatas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CityListViewHolder(layoutInflater.inflate(R.layout.citylist_row, parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        holder.city.text = getData(position).city
        holder.temp.text = getData(position).temperature.toString()
    }

    private fun getData(position: Int): MeteoForCity = differ.currentList[position]

    inner class CityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView by lazy { itemView.findViewById<TextView>(R.id.citylist_row_text) }
        val temp: TextView by lazy { itemView.findViewById<TextView>(R.id.citylist_row_temp) }
    }

}
