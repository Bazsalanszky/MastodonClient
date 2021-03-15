package eu.toldi.mastodon.entities

import com.google.gson.annotations.Expose


data class Client(@Expose val client_id: String,@Expose val client_secret: String) {
    @Expose
    lateinit var id: String
    @Expose
    lateinit var name: String
    @Expose
    lateinit var redirect_uri: String
    @Expose
    lateinit var vapid_key: String
    @Expose
    lateinit var website: String
}
