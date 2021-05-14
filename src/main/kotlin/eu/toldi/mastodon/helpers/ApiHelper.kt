package eu.toldi.mastodon.helpers

import com.google.gson.annotations.Expose
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.entities.Token
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class ApiHelper (host: String){
    @Expose
     val hostURL :String = when {
         host.startsWith("http://") || host.startsWith("https://") -> host
         else -> "https://$host"
     }
    @PublishedApi
    internal val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature)
    }


    companion object MastodonAPI {
        val base = "/api/v1"
        val accounts = base+"/accounts"
        val accounts_verify_credentials = accounts+"/verify_credentials"
        val timelines = base+"/timelines"
        val pubicTimeline = timelines+"/public"
        val homeTimeline = timelines+"/home"
        val apps = base+"/apps"
        val apps_verify_credentials = apps+"/verify_credentials"
        val oauth = "/oauth"
        val authorize = oauth+"/authorize"
        val token = oauth+"/token"
    }


     inline fun <reified T: Any> get(path: String, headers: Map<String,String> = HashMap() ): T {
        var result :T? = null
        runBlocking {
            val builder = HttpRequestBuilder()
            builder.url(hostURL+path)
            for((k,v) in headers){
                builder.headers.append(k,v)
            }
            result = client.get(builder)
        }
        return result ?: throw IllegalArgumentException("")
    }


     inline fun <reified T: Any> authedGet(path: String, token:String): T {
        return get<T>(path,mapOf("Authorization" to "Bearer $token"))
    }

     fun registerApp(): Client {
         return runBlocking {
             client.submitForm<Client> (
                 formParameters = Parameters.build {
                     append("client_name", "Mastodon Client")
                     append("redirect_uris", "urn:ietf:wg:oauth:2.0:oob")
                     append("scopes", "read write follow push")
                     append("website", "https://github.com/Bazsalanszky/MastodonKlient")
                 },
                 url = hostURL+apps
             )
         }
    }

    fun getAccessToken(c: Client,code: String) : Token {
        return runBlocking {
            client.submitForm<Token> (
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

    fun constructLoginAccount(token: Token): LoginAccount {
        return LoginAccount(
            token,
            get(accounts_verify_credentials, mapOf("Authorization" to "Bearer ${token.access_token}"))
        ).also {
            println("Logged in as ${it.account.acct}")
        }
    }

    fun constructClient(token: Token): Client {
        return get(apps_verify_credentials, mapOf("Authorization" to "Bearer ${token.access_token}"))
    }

}