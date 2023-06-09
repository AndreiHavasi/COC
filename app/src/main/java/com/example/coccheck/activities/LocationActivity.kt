package com.example.coccheck.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import client.Client
import client.models.clan.ClanRanking
import com.example.coccheck.CocApplication
import com.example.coccheck.adapters.ClanRankingAdapter
import com.example.coccheck.databinding.LocationActivityBinding
import com.example.coccheck.isConnectedToInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: LocationActivityBinding
    private lateinit var clanRankings: ArrayList<ClanRanking>
    val client: Client by lazy {(application as CocApplication).client }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LocationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        getClanRankings()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun getClanRankings() {
        val locationId = intent.getStringExtra("locationId")

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if(!isConnectedToInternet(applicationContext)) return@withContext

                val res = client.getClanRankingsByLocation(locationId!!, 10)
                clanRankings = ArrayList(res.items)

                withContext(Dispatchers.Main) {
                    hideLoader()
                    initAdapter()
                }
            }
        }
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE
    }

    private fun initAdapter() {
        val adapter = ClanRankingAdapter(clanRankings)
        binding.recyclerView.adapter = adapter
    }
}
