package eu.toldi.mastodon.helpers

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.runBlocking
import java.io.InvalidObjectException

class ApiHelper (host: String){
     val hostURL :String = when {
         host.startsWith("http://") || host.startsWith("https://") -> host
         else -> "https://$host"
     }

    companion object MastodonAPI {
        val base = "/api/v1"
        val accounts = base+"/accounts"
        val accounts_verify_credentials = accounts+"/verify_credentials"
        val timelines = base+"/timelines"
        val pubicTimeline = timelines+"/public"
        val homeTimeline = timelines+"/home"
        val apps = base+"/apps"
        val oauth = "/oauth"
        val authorize = oauth+"/authorize"
        val token = oauth+"/token"
    }
    val client = HttpClient(Apache) {
        install(JsonFeature)
    }

    inline fun <reified T> get(path: String,headers: Map<String,String> = HashMap() ): T? {
        var result :T? = null
        runBlocking {
            val builder : HttpRequestBuilder = HttpRequestBuilder()
            builder.url(hostURL+path)
            for((k,v) in headers){
                builder.headers.append(k,v)
            }
            result = client.get(builder)
        }
        return result;
    }

     fun registerApp(): Client {
         return runBlocking {
             client.submitForm (
                 formParameters = Parameters.build {
                     append("client_name", "MastodonKlient")
                     append("redirect_uris", "urn:ietf:wg:oauth:2.0:oob")
                     append("scopes", "read write follow push")
                     append("website", "https://github.com/Bazsalanszky/MastodonKlient")
                 },
                 url = hostURL+apps
             )
         }
    }

    fun getAccessToken(c: Client,code: String) : LoginAccount.AuthToken{
        return runBlocking {
            client.submitForm (
                formParameters = Parameters.build {
                    append("client_id", c.client_id)
                    append("client_secret",c.client_secret)
                    append("redirect_uri", "urn:ietf:wg:oauth:2.0:oob")
                    append("grant_type", "authorization_code")
                    append("code",code)
                    append("scopes", "read write follow push")
                },
                url = hostURL+ token
            )
        }
    }

    fun constructLoginAccount(token: LoginAccount.AuthToken) :LoginAccount {
        val result: LoginAccount = get(ApiHelper.accounts_verify_credentials, mapOf("Authorization" to "Bearer ${token.access_token}")) ?: throw InvalidObjectException("Failed to create Login Account")
        result.token = token
        return result
    }

}