package com.example.coccheck

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import client.exceptions.CocException
import client.models.clan.Clan
import com.example.coccheck.databinding.FragmentClansBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

        initUI()
        listenToSearch()

        return binding.root
    }

    private fun initUI() {
        hideLoader()
        hideErrorToast()
        initRecyclerView()
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideErrorToast() {
        binding.toastView.errorToast.visibility = View.GONE
    }

    private fun initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun listenToSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false

                showLoader()
                Log.d("QUERY", query)
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
            withContext(Dispatchers.Main) {
                hideKeyboardAndLoader()
                e.message?.let { showError(it) }
            }
            null
        }
        hideKeyboardAndLoader()
        clan?.name?.let { Log.d("CLAN NAME", it) }
    }

    private fun hideKeyboardAndLoader() {
        binding.root.hideKeyboard()
        hideLoader()
    }

    private suspend fun showError(message: String) {
        Log.d("ERROR MESSAGE", message)
        presentToast(message)
    }

    private suspend fun presentToast(message: String) {
        val toastMessage: String = when(message) {
            "400" -> resources.getString(R.string.bad_request)
            "403" -> resources.getString(R.string.unauthorized_request)
            "404" -> resources.getString(
                R.string.resource_not_found,
                resources.getString(R.string.clan)
            )
            "429" -> resources.getString(R.string.too_many_requests)
            "503" -> resources.getString(R.string.server_maintenance)
            else -> resources.getString(R.string.server_error)
        }
        binding.toastView.errorToastText.text = toastMessage
        binding.toastView.errorToast.visibility = View.VISIBLE

        delay(3000)
        fadeErrorToast()
    }

    private fun fadeErrorToast() {
        val transition = Fade()
        transition.duration = 500
        transition.addTarget(binding.toastView.errorToast)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        hideErrorToast()
    }

    private fun View.hideKeyboard() {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
