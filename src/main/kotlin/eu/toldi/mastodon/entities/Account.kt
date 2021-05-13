package eu.toldi.mastodon.entities

import com.google.gson.annotations.Expose

data class Account(val id: String,
                   @Expose
    val acct: String,
                   val avatar :String,
                   val avatar_static :String,
                   val bot: Boolean = false,
                   val created_at :String,
                   val discoverable: Boolean = false,
                   val display_name: String,
                   val emojis :List<Emoji>,
                   val field: List<metadataField>,
                   val followers_count: Int = 0,
                   var following_count: Int = 0,
                   val group: Boolean = false,
                   val header: String,
                   val header_static: String,
                   var locked: Boolean = false,
                   val note: String,
                   val statuses_count: Int = 0,
                   val url: String,
                   val username: String) {

    data class metadataField(val name:String, val value: String, val verified_at: String)
}
