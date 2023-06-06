package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import db.daos.ClanDao
import db.entities.ClanEntity
import helpers.SingletonHolder

@Database(entities = [ClanEntity::class], version = 1, exportSchema = false)
abstract class CocDatabase : RoomDatabase() {
    abstract fun clanDao(): ClanDao

    companion object : SingletonHolder<CocDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            CocDatabase::class.java,
            "coc_database"
        ).build()
    })
}
