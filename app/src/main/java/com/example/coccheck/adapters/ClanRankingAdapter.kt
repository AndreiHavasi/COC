package com.example.coccheck.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import client.models.clan.ClanRanking
import com.example.coccheck.R

class ClanRankingAdapter(private val clanRanking: ArrayList<ClanRanking>): RecyclerView.Adapter<ClanRankingAdapter.ClanRankingViewHolder>() {
    class ClanRankingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val tag: TextView = view.findViewById(R.id.tag)
        val clanLevel: TextView = view.findViewById(R.id.clanLevel)
        val members: TextView = view.findViewById(R.id.members)
        val clanPoints: TextView = view.findViewById(R.id.clanPoints)
        val rank: TextView = view.findViewById(R.id.rank)
        val previousRank: TextView = view.findViewById(R.id.previousRank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClanRankingViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.clan_ranking_item, parent, false)
        return ClanRankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClanRankingViewHolder, position: Int) {
        val item = clanRanking[position]

        holder.name.text = item.name
        holder.tag.text = item.tag
        holder.clanLevel.text = item.clanLevel.toString()
        holder.members.text = item.members.toString()
        holder.clanPoints.text = item.clanPoints.toString()
        holder.rank.text = item.rank.toString()
        holder.previousRank.text = item.previousRank.toString()
    }

    override fun getItemCount(): Int = clanRanking.size
}
