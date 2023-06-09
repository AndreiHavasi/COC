package db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clan_table")
data class ClanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "locationName") val locationName: String?,
    @ColumnInfo(name = "members") val members: Int,
    @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "warsWon") val warsWon: Int,
    @ColumnInfo(name = "warFrequency") val warFrequency: String,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "requiredTrophies") val requiredTrophies: Int,
)
