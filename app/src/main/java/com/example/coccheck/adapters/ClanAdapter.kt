package com.example.coccheck.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coccheck.R
import db.entities.ClanEntity

class ClanAdapter(private val clans: ArrayList<ClanEntity>): RecyclerView.Adapter<ClanAdapter.ClanViewHolder>() {
    class ClanViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val tag: TextView = view.findViewById(R.id.tag)
        val location: TextView = view.findViewById(R.id.location)
        val members: TextView = view.findViewById(R.id.members)
        val points: TextView = view.findViewById(R.id.points)
        val warsWon: TextView = view.findViewById(R.id.warsWon)
        val warFrequency: TextView = view.findViewById(R.id.warFrequency)
        val type: TextView = view.findViewById(R.id.type)
        val requiredTrophies: TextView = view.findViewById(R.id.requiredTrophies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClanViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.clan, parent, false)
        return ClanViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClanViewHolder, position: Int) {
        val item = clans[position]

        holder.name.text = item.name
        holder.tag.text = item.tag
        holder.location.text = item.locationName
        holder.members.text = item.members.toString()
        holder.points.text = item.points.toString()
        holder.warsWon.text = item.warsWon.toString()
        holder.warFrequency.text = item.warFrequency
        holder.type.text = item.type
        holder.requiredTrophies.text = item.requiredTrophies.toString()
    }

    override fun getItemCount(): Int = clans.size
}
