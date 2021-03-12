package eu.toldi.mastodon.entities


class LoginAccount(id: String,val token: AuthToken) : Account(id) {
    class AuthToken(val access_token: String,val token_type: String,val scope : String,val created_at: Int)
}