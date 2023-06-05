package com.example.coccheck.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import client.models.location.Location
import com.example.coccheck.R

class LocationAdapter(private val locations: ArrayList<Location>): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private lateinit var listener: OnClickListener

    class LocationViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.locationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = locations[position]

        holder.name.text = item.name
        holder.itemView.setOnClickListener{ listener.onItemClick(item) }
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    interface OnClickListener {
        fun onItemClick(item: Location)
    }

    override fun getItemCount(): Int = locations.size
}
