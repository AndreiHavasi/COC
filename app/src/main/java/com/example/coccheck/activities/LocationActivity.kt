package com.example.coccheck.activities

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import client.Client
import client.models.clan.ClanRanking
import com.example.coccheck.adapters.ClanRankingAdapter
import com.example.coccheck.databinding.LocationActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: LocationActivityBinding
    private lateinit var clanRankings: ArrayList<ClanRanking>
    lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LocationActivityBinding.inflate(layoutInflater)

        setClient()
        setContentView(binding.root)

        initRecyclerView()
        getClanRankings()
    }

    @Suppress("DEPRECATION")
    private fun setClient() {
        val applicationInfo: ApplicationInfo = application.packageManager
            .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        val apiKey = applicationInfo.metaData["API_KEY"] as String
        client = Client(apiKey)
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun getClanRankings() {
        val locationId = intent.getStringExtra("locationId")

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
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
