package client

import client.exceptions.AuthException
import client.exceptions.BadRequestException
import client.exceptions.CocException
import client.exceptions.MaintenanceException
import client.exceptions.NotFoundException
import client.exceptions.RateLimitException
import client.exceptions.ServerException
import kotlinx.serialization.json.Json
import okhttp3.Response
import java.io.IOException

object Utils {
    const val URL = "https://api.clashofclans.com/"
    const val API_VERSION = "v1"
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        prettyPrint = true
    }

    @Throws(CocException::class)
    fun checkResponse(res: Response): Response {
        if (res.isSuccessful) return res
        when (res.code) {
            400 -> throw BadRequestException()
            403 -> throw AuthException()
            404 -> throw NotFoundException()
            429 -> throw RateLimitException()
            503 -> throw MaintenanceException()
            else -> throw ServerException()
        }
    }

    @Throws(IOException::class)
    inline fun <reified T> deserialize(res: Response): T {
        return json.decodeFromString(res.body?.string() ?: "")
    }

    fun formatTag(tag: String): String {
        return if (tag.startsWith("#")) tag.replace("#", "%23") else "%23$tag"
    }
}
