package client

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import client.Utils.checkResponse
import client.Utils.deserialize
import client.Utils.formatTag
import client.exceptions.CocException
import client.models.clan.Clan
import client.models.location.Location
import client.models.location.LocationClanRankings
import client.models.location.LocationList
import client.models.player.Player
import helpers.SingletonHolder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

@Suppress("DEPRECATION")
class Client private constructor(context: Context) {
    private val http: OkHttpClient = OkHttpClient()
    private val token: String

    init {
        val applicationInfo: ApplicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        token = applicationInfo.metaData["API_KEY"] as String
    }

    companion object : SingletonHolder<Client, Context>(::Client)

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
    fun getClanRankingsByLocation(locationId: String, limit: Int): LocationClanRankings {
        val res = get("/locations/$locationId/rankings/clans?limit=$limit")
        return deserialize(res)
    }

    @Throws(IOException::class, CocException::class)
    fun getClan(clanTag: String): Clan {
        val tag = formatTag(clanTag)
        val res = get("/clans/$tag")
        return deserialize(res)
    }
}
