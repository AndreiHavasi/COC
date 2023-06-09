package db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "townHallLevel") val townHallLevel: Int,
    @ColumnInfo(name = "trophies") val trophies: Int,
    @ColumnInfo(name = "bestTrophies") val bestTrophies: Int,
    @ColumnInfo(name = "expLevel") val expLevel: Int,
    @ColumnInfo(name = "clan") val clan: String?,
    @ColumnInfo(name = "role") val role: String?,
    @ColumnInfo(name = "warStars") val warStars: Int,
)
