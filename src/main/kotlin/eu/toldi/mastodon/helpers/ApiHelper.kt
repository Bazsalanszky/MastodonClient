package eu.toldi.mastodon.helpers

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import eu.toldi.mastodon.entities.Client
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.runBlocking

class ApiHelper (host: String){
     val hostURL :String = when {
         host.startsWith("http://") || host.startsWith("https://") -> host
         else -> "https://$host"
     }

    companion object MastodonAPI {
        val base = "/api/v1"
        val accounts = base+"/accounts"
        val timelines = base+"/timelines"
        val pubicTimeline = timelines+"/public"
        val apps = base+"/apps"
    }
    val client = HttpClient(Apache) {
        install(JsonFeature)
    }

    inline fun <reified T> get(path: String): T? {
        var result :T? = null
        runBlocking {
            result = client.get(hostURL + path)
        }
        return result;
    }

     fun registerApp(): Client?{
         var result: Client? = null
         runBlocking {
             val response = client.submitForm<Client>(
                 formParameters = Parameters.build {
                     append("client_name", "MastodonKlient")
                     append("redirect_uris", "urn:ietf:wg:oauth:2.0:oob")
                     append("scopes", "read write follow push")
                     append("website", "https://github.com/Bazsalanszky/MastodonKlient")
                 },
                 url = hostURL+apps
             )
             result = response;
         }
         return result
    }
}