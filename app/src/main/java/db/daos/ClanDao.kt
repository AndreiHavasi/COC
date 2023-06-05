package db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import db.entities.ClanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClanDao {
    @Query("SELECT * FROM clan_table ORDER BY name ASC")
    fun getClans(): Flow<List<ClanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clanEntity: ClanEntity)

    @Query("DELETE FROM clan_table WHERE name = :name")
    suspend fun delete(name: String)
}
