package com.example.coccheck

import android.app.Application
import db.CocDatabase
import db.repositories.ClanRepository

class CocApplication : Application() {
    private val database by lazy { CocDatabase.getDatabase(this) }
    val clanRepository by lazy { ClanRepository(database.clanDao()) }
}