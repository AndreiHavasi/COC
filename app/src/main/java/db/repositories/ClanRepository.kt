package db.repositories

import androidx.annotation.WorkerThread
import db.daos.ClanDao
import db.entities.ClanEntity
import kotlinx.coroutines.flow.Flow

class ClanRepository(private val clanDao: ClanDao) {
    val clans: Flow<List<ClanEntity>> = clanDao.getClans()

    @WorkerThread
    suspend fun insert(clanEntity: ClanEntity) {
        clanDao.insert(clanEntity)
    }

    @WorkerThread
    suspend fun delete(clanTag: String) {
        clanDao.delete(clanTag)
    }
}
