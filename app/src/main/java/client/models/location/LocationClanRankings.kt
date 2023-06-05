package client.models.location

import client.models.clan.ClanRanking
import kotlinx.serialization.Serializable

@Serializable
data class LocationClanRankings(
    val items: List<ClanRanking>,
)
