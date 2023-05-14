package com.example.coccheck

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coccheck.databinding.FragmentClansBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Clans : Fragment() {
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentClansBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        main = (activity as MainActivity)
        binding = FragmentClansBinding.inflate(inflater, container, false)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        this.listenToSearch()

        return binding.root
    }

    private fun listenToSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false

                if (query.length != 8) {
                    presentToast()
                    return false;
                }

                lifecycleScope.launch {
                    getClan(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun presentToast() {
        val toast: Toast = Toast.makeText(context, "You need to enter 8 letters!", Toast.LENGTH_LONG)
        toast.show()
    }

    private suspend fun getClan(query: String) = withContext(Dispatchers.IO) {
        val clan = main.client.getClan(query)
        clan.name?.let { Log.d("CLAN NAME", it) }
    }
}
