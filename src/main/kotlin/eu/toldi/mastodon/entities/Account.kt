package eu.toldi.mastodon.entities

import kotlin.properties.Delegates

open class Account(val id: String){
    lateinit var username: String
    lateinit var display_name: String
    lateinit var acct: String
    lateinit var avatar :String
    var bot by Delegates.notNull<Boolean>()
}
