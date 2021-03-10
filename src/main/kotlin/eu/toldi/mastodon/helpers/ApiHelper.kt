package eu.toldi.mastodon.helpers

import eu.toldi.mastodon.entities.Toot
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

class ApiHelper (host: String){
     val hostURL = when {
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
}