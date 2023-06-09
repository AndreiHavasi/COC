package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import db.daos.ClanDao
import db.daos.PlayerDao
import db.entities.ClanEntity
import db.entities.PlayerEntity
import helpers.SingletonHolder

@Database(entities = [ClanEntity::class, PlayerEntity::class], version = 2, exportSchema = false)
abstract class CocDatabase : RoomDatabase() {
    abstract fun clanDao(): ClanDao

    abstract fun playerDao(): PlayerDao

    companion object : SingletonHolder<CocDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            CocDatabase::class.java,
            "coc_database"
        ).fallbackToDestructiveMigration().build()
    })
}
