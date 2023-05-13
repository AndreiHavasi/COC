package client.models.league

import client.models.common.IconUrls
import kotlinx.serialization.Serializable

@Serializable
data class League(val name: String? = null, val id: Int = 0, val iconUrls: IconUrls? = null)
