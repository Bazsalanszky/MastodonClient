package eu.toldi.mastodon.entities

import com.google.gson.annotations.Expose


data class Client(
    @Expose
    val client_id: String,
    @Expose
    val client_secret: String,
    @Expose
    val id: String,
    @Expose
    val name: String,
    @Expose
    val redirect_uri: String,
    @Expose
    val vapid_key: String,
    @Expose
    val website: String
)
