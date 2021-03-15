package eu.toldi.mastodon.entities

data class Token(val access_token: String, val token_type: String, val scope : String, val created_at: Int)