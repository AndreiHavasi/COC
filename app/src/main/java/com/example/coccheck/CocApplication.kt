package com.example.coccheck

import android.app.Application
import client.Client
import db.CocDatabase
import db.repositories.ClanRepository
import db.repositories.PlayerRepository

class CocApplication : Application() {
    val client by lazy { Client.getInstance(this) }

    private val database by lazy { CocDatabase.getInstance(this) }
    val clanRepository by lazy { ClanRepository(database.clanDao()) }
    val playerRepository by lazy { PlayerRepository(database.playerDao()) }
}
