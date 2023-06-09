package db.repositories

import androidx.annotation.WorkerThread
import db.daos.PlayerDao
import db.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

class PlayerRepository(private val playerDao: PlayerDao) {
    val players: Flow<List<PlayerEntity>> = playerDao.getPlayers()

    @WorkerThread
    suspend fun insert(playerEntity: PlayerEntity) {
        playerDao.insert(playerEntity)
    }

    @WorkerThread
    suspend fun delete(playerTag: String) {
        playerDao.delete(playerTag)
    }
}
