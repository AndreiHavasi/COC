package client.models.clan

import client.models.common.BadgeUrls
import client.models.common.Label
import client.models.common.Language
import client.models.location.Location
import client.models.warleague.WarLeague
import kotlinx.serialization.Serializable

@Serializable
data class Clan(
    val warLeague: WarLeague? = null,
    val memberList: List<ClanMember>,
    val requiredVersusTrophies: Int = 0,
    val requiredTownhallLevel: Int = 0,
    val requiredTrophies: Int = 0,
    val clanVersusPoints: Int = 0,
    val tag: String,
    val isWarLogPublic: Boolean = false,
    val warFrequency: String,
    val clanLevel: Int = 0,
    val warWinStreak: Int = 0,
    val warWins: Int = 0,
    val warTies: Int = 0,
    val warLosses: Int = 0,
    val clanPoints: Int = 0,
    val chatLanguage: Language? = null,
    val labels: List<Label>,
    val name: String,
    val location: Location? = null,
    val type: String? = null,
    val members: Int = 0,
    val description: String? = null,
    val badgeUrls: BadgeUrls? = null
)
