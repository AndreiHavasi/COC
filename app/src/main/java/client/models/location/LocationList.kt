package client.models.location

import kotlinx.serialization.Serializable

@Serializable
data class LocationList(val items: List<Location>)
