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
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

open class Client(private val token: String) {
    private val http: OkHttpClient = OkHttpClient()

    companion object {
        @Volatile
        private var INSTANCE: Client ?= null

        @Suppress("DEPRECATION")
        fun getClient(context: Context): Client {
            return INSTANCE ?: synchronized(this) {
                val applicationInfo: ApplicationInfo = context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                val apiKey = applicationInfo.metaData["API_KEY"] as String
                val instance = Client(apiKey)

                INSTANCE = instance
                instance
            }
        }
    }

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
