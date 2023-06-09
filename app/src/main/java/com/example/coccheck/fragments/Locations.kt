package com.example.coccheck.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import client.models.location.Location
import com.example.coccheck.activities.LocationActivity
import com.example.coccheck.activities.MainActivity
import com.example.coccheck.adapters.LocationAdapter
import com.example.coccheck.databinding.FragmentLocationsBinding
import com.example.coccheck.isConnectedToInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Locations : Fragment() {
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentLocationsBinding
    private lateinit var locations: ArrayList<Location>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        main = (activity as MainActivity)
        binding = FragmentLocationsBinding.inflate(inflater, container, false)

        initRecyclerView()
        getLocations()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(main)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun getLocations() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if(!isConnectedToInternet(requireContext().applicationContext)) return@withContext

                val res = main.client.getLocations().filterNot { item -> item.name == "" }
                locations = ArrayList(res)

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
        val adapter = LocationAdapter(locations)
        adapter.setOnClickListener(object : LocationAdapter.OnClickListener {
            override fun onItemClick(item: Location) {
                val intent = Intent(main, LocationActivity::class.java)
                intent.putExtra("locationId", item.id.toString())
                startActivity(intent)
            }
        })

        binding.recyclerView.adapter = adapter
    }
}
