package eu.toldi.mastodon.helpers

import com.google.gson.Gson
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.Token
import eu.toldi.mastodon.view.MainModel
import java.io.File


class ConfigHelper(client: Client, private val access_token: Token, apiHelper: ApiHelper) {
    private val client_id = client.client_id
    private val client_secret = client.client_secret
    private val instance = apiHelper.hostURL

    fun createMainModel() : MainModel {
        val api = ApiHelper(instance)
        val account = api.constructLoginAccount(access_token)
        val client = api.constructClient(access_token)
        return MainModel(client,account,api)
    }
    fun toFile(filename: String) {
        val gson = Gson()
        File(filename).writeText(gson.toJson(this))
    }
}