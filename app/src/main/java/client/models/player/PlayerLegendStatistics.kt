package client.models.player

import client.models.league.LegendSeasonResult
import kotlinx.serialization.Serializable

@Serializable
data class PlayerLegendStatistics(
    val currentSeason: LegendSeasonResult? = null,
    val previousVersusSeason: LegendSeasonResult? = null,
    val bestVersusSeason: LegendSeasonResult? = null,
    val legendTrophies: Int = 0,
    val previousSeason: LegendSeasonResult? = null,
    val bestSeason: LegendSeasonResult? = null
)
