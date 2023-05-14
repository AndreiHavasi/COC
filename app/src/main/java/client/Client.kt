package client

import client.Utils.checkResponse
import client.Utils.deserialize
import client.Utils.formatTag
import client.exceptions.CocException
import client.models.clan.Clan
import client.models.clan.ClanList
import client.models.location.Location
import client.models.location.LocationList
import client.models.player.Player
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Client(private val token: String) {
    private val http: OkHttpClient = OkHttpClient()

    private fun getBaseRequest(suffix: String): Request.Builder {
        return Request.Builder()
            .header("Authorization", "Bearer $token")
            .url(Utils.URL + Utils.API_VERSION + suffix)
    }

    private fun get(url: String): Response {
        val res = http.newCall(getBaseRequest(url).build()).execute()
        return checkResponse(res)
    }

    @Throws(IOException::class, CocException::class)
    fun getPlayer(tag: String): Player {
        val t = formatTag(tag)
        val res = get("/players/$t")
        return deserialize(res)
    }

    @Throws(IOException::class, CocException::class)
    fun getLocations(): List<Location> {
        val res = get("/locations")
        return deserialize<LocationList>(res).items
    }

    @Throws(IOException::class, CocException::class)
    fun getLocation(locationId: String): Location {
        val res = get("/locations/$locationId")
        return deserialize(res)
    }

    @Throws(IOException::class, CocException::class)
    fun getClans(name: String): List<Clan> {
        val res = get("/clans")
        return deserialize<ClanList>(res).items
    }

    @Throws(IOException::class, CocException::class)
    fun getClan(clanTag: String): Clan {
        val tag = formatTag(clanTag)
        val res = get("/clans/$tag")
        return deserialize(res)
    }
}
