package db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import db.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player_table ORDER BY name ASC")
    fun getPlayers(): Flow<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playerEntity: PlayerEntity)

    @Query("DELETE FROM player_table WHERE tag = :playerTag")
    suspend fun delete(playerTag: String)
}
