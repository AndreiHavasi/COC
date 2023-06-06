package com.example.coccheck.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import client.Client
import com.example.coccheck.CocApplication
import com.example.coccheck.fragments.Clans
import com.example.coccheck.fragments.Locations
import com.example.coccheck.fragments.Players
import com.example.coccheck.R
import com.example.coccheck.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val client: Client by lazy {(application as CocApplication).client }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Players())

        listenToNavigationClick()
    }

    private fun listenToNavigationClick() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.players -> replaceFragment(Players())
                R.id.clans -> replaceFragment(Clans())
                R.id.locations -> replaceFragment(Locations())

                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
