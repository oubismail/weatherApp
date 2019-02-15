package com.example.myweatherapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myweatherapp.R
import com.example.myweatherapp.models.Forecast
import kotlinx.android.synthetic.main.item_list.view.*

class ForcastesListAdapter(private val myDataset: MutableList<Forecast>, val onCityClickListener: OnCityClickListener) :
    RecyclerView.Adapter<ForcastesListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    fun setData(list: List<Forecast>) {
        myDataset.clear()
        myDataset.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener { onCityClickListener.onCityClick(myDataset[viewHolder.adapterPosition]) }
        viewHolder.cityName.text = myDataset[viewHolder.adapterPosition].name
        viewHolder.cityTemp.text = myDataset[viewHolder.adapterPosition].main?.temp.toString()
        viewHolder.cityState.text = myDataset[viewHolder.adapterPosition].weather?.first()?.main
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val cityName = view.cityName
        val cityTemp = view.cityTemp
        val cityState = view.cityState
    }

    interface OnCityClickListener {
        fun onCityClick(forecast: Forecast)
    }
}