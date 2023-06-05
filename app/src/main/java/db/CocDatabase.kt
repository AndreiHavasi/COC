package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import db.daos.ClanDao
import db.entities.ClanEntity
import kotlin.concurrent.Volatile

@Database(entities = [ClanEntity::class], version = 1, exportSchema = false)
abstract class CocDatabase : RoomDatabase() {
    abstract fun clanDao(): ClanDao

    companion object {
        @Volatile
        private var INSTANCE: CocDatabase ?= null

        fun getDatabase(context: Context): CocDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocDatabase::class.java,
                    "coc_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
