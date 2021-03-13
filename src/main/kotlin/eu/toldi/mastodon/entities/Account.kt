package eu.toldi.mastodon.entities

open class Account(val id: String){

    lateinit var acct: String
    lateinit var avatar :String
    lateinit var avatar_static :String
    var bot = false
    lateinit var created_at :String
    var discoverable = false
    lateinit var display_name: String
    lateinit var emojis :List<Emoji>
    lateinit var field: List<metadataField>
    var followers_count = 0
    var following_count = 0
    var group = false
    lateinit var header: String
    lateinit var header_static: String
    var locked = false
    lateinit var note: String
    var statuses_count = 0
    lateinit var url: String
    lateinit var username: String

    data class metadataField(val name:String, val value: String, val verified_at: String)
}
