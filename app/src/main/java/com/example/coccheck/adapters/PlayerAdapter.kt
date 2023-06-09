package com.example.coccheck.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coccheck.R
import db.entities.PlayerEntity

class PlayerAdapter(private val players: ArrayList<PlayerEntity>): RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val tag: TextView = view.findViewById(R.id.tag)
        val townhall: TextView = view.findViewById(R.id.townhall)
        val trophies: TextView = view.findViewById(R.id.trophies)
        val bestTrophies: TextView = view.findViewById(R.id.bestTrophies)
        val experienceLevel: TextView = view.findViewById(R.id.experienceLevel)
        val clan: TextView = view.findViewById(R.id.clan)
        val role: TextView = view.findViewById(R.id.role)
        val warStars: TextView = view.findViewById(R.id.warStars)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = players[position]

        holder.name.text = item.name
        holder.tag.text = item.tag
        holder.townhall.text = item.townHallLevel.toString()
        holder.trophies.text = item.trophies.toString()
        holder.bestTrophies.text = item.bestTrophies.toString()
        holder.experienceLevel.text = item.expLevel.toString()
        holder.clan.text = item.clan
        holder.role.text = item.role
        holder.warStars.text = item.warStars.toString()
    }

    override fun getItemCount(): Int = players.size
}
