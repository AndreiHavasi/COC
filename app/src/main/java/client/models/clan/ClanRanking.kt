package client.models.clan

import client.models.common.BadgeUrls
import client.models.location.Location
import kotlinx.serialization.Serializable

@Serializable
data class ClanRanking(
    val tag: String? = null,
    val name: String? = null,
    val location: Location? = null,
    val badgeUrls: BadgeUrls? = null,
    val clanLevel: Int = 0,
    val members: Int = 0,
    val clanPoints: Int = 0,
    val rank: Int = 0,
    val previousRank: Int = 0,
)
