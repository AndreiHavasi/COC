package com.example.coccheck.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import client.exceptions.CocException
import client.models.clan.Clan
import com.example.coccheck.R
import com.example.coccheck.activities.MainActivity
import com.example.coccheck.capitalize
import com.example.coccheck.databinding.FragmentClansBinding
import com.example.coccheck.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Clans : Fragment() {
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentClansBinding
    private var clan: Clan? = null

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
        hideClan()
        hideLoader()
        hideErrorToast()
    }

    private fun hideClan() {
        binding.clanView.clan.visibility = View.GONE
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
        clan = try {
            val res = main.client.getClan(query)
            showClan(res)
            res
        }
        catch (e: CocException) {
            showError(e.message)
            null
        }
    }

    private suspend fun showError(message: String?) = withContext(Dispatchers.Main) {
        hideKeyboardAndLoader()
        binding.clanView.clan.visibility = View.GONE

        message?.let {
            Log.d("ERROR MESSAGE", message)
            presentToast(message)
        }
    }

    private fun hideKeyboardAndLoader() {
        binding.root.hideKeyboard()
        hideLoader()
    }

    private suspend fun showClan(clan: Clan) = withContext(Dispatchers.Main) {
        hideKeyboardAndLoader()

        binding.clanView.name.text = clan.name
        binding.clanView.tag.text = clan.tag
        binding.clanView.location.text = clan.location?.name ?: resources.getString(R.string.null_field)
        binding.clanView.members.text = clan.members.toString()
        binding.clanView.points.text = clan.clanPoints.toString()
        binding.clanView.warsWon.text = clan.warWins.toString()
        binding.clanView.warFrequency.text = clan.warFrequency.capitalize()
        binding.clanView.type.text = clan.type?.capitalize() ?: resources.getString(R.string.null_field)
        binding.clanView.requiredTrophies.text = clan.requiredTrophies.toString()
        binding.clanView.clan.visibility = View.VISIBLE

        clan.name?.let { Log.d("CLAN NAME", it) }
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
}
