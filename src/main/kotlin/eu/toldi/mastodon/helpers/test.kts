import com.google.gson.GsonBuilder
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.Token
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.view.MainModel
import java.lang.reflect.Modifier

val apiHelper = ApiHelper("s.toldi.eu")
val client = Client("GhhxanAWMrz6bzkxqE7WL1-W-KEXKWMetiVpS2Rp5n0","sABAtc4fzhSuEmVY3XJKMskOg715CAFPXzcXaExl6vQ")
val token = Token(
    "7ebwdxWIP2ws9aG5ug5g8qAilfWYzzhBZ4iH9XqjGRs",
    "Bearer",
    "read",
    1615820051
)

val model = MainModel(client,apiHelper.constructLoginAccount(token),apiHelper)

val gson = GsonBuilder()
    .excludeFieldsWithModifiers(Modifier.PRIVATE)
    .create()

println(gson.toJson("Texr"))