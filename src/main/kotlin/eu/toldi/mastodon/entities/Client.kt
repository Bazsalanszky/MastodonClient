package eu.toldi.mastodon.entities

data class Client(val id: Long) {
    lateinit var client_id: String
    lateinit var client_secret: String
    lateinit var name: String
    lateinit var redirect_uri: String
    lateinit var vapid_key: String
    lateinit var website: String
}
