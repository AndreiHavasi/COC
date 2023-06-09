package com.example.coccheck.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coccheck.activities.MainActivity
import com.example.coccheck.adapters.ClanAdapter
import com.example.coccheck.adapters.PlayerAdapter
import com.example.coccheck.databinding.FragmentFavoritesBinding
import db.entities.ClanEntity
import db.entities.PlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Favorites : Fragment() {
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoriteClans: List<ClanEntity>
    private lateinit var favoritePlayers:  List<PlayerEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        main = (activity as MainActivity)
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        initRecyclerViews()
        getFavoriteClans()
        getFavoritePlayers()

        return binding.root
    }

    private fun initRecyclerViews() {
        binding.clanRecyclerView.layoutManager = LinearLayoutManager(main)
        binding.clanRecyclerView.setHasFixedSize(true)

        binding.playerRecyclerView.layoutManager = LinearLayoutManager(main)
        binding.playerRecyclerView.setHasFixedSize(true)
    }

    private fun getFavoriteClans() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO)  {
                main.clanRepository.clans.collect {
                    favoriteClans = it
                    initClanAdapter()
                }
            }
        }
    }

    private suspend fun initClanAdapter() = withContext(Dispatchers.Main) {
        val adapter = ClanAdapter(ArrayList(favoriteClans))
        binding.clanRecyclerView.adapter = adapter
    }

    private fun getFavoritePlayers() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                main.playerRepository.players.collect {
                    favoritePlayers = it
                    initPlayerAdapter()
                }
            }
        }
    }

    private suspend fun initPlayerAdapter() = withContext(Dispatchers.Main) {
        val adapter = PlayerAdapter(ArrayList(favoritePlayers))
        binding.playerRecyclerView.adapter = adapter
    }
}
