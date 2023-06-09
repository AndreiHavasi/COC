package com.example.coccheck

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.view.View
import android.view.inputmethod.InputMethodManager


fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }

fun View.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun isConnectedToInternet(context: Context): Boolean {
    return try {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.getNetworkCapabilities(cm.activeNetwork)!!.hasCapability(NET_CAPABILITY_INTERNET)
    } catch (e: Exception) {
        false
    }
}
