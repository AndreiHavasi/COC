package com.example.coccheck

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import client.exceptions.CocException
import client.models.clan.Clan
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

        initRecyclerView()
        toggleLoader()
        listenToSearch()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun toggleLoader() {
        if(binding.loader.visibility == View.VISIBLE) binding.loader.visibility = View.GONE
        else binding.loader.visibility = View.VISIBLE
    }

    private fun listenToSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false

                toggleLoader()
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

    private suspend fun getClan(query: String) = withContext(Dispatchers.IO) {
        val clan: Clan? = try {
            main.client.getClan(query)
        }
        catch (e: CocException) {
            e.message?.let { showError(it) }
            null
        }

        clan?.name?.let { Log.d("CLAN NAME", it) }
        withContext(Dispatchers.Main) {
            toggleLoader()
            binding.root.hideKeyboard()
        }
    }

    private suspend fun showError(message: String) {
        Log.d("EXCEPTION MESSAGE", message)
        withContext(Dispatchers.Main) { presentToast(message) }
    }

    private fun presentToast(message: String) {
        val toast: Toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()
    }

    private fun View.hideKeyboard() {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
