package eu.toldi.mastodon.entities

data class Toot(val id: Long) {
    lateinit var uri: String
    lateinit var content: String
    lateinit var account: Account
}
