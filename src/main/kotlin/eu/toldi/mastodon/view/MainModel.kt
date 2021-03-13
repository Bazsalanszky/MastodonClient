package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.helpers.ApiHelper

class MainModel(val client: Client, val account: LoginAccount,val apiHelper: ApiHelper) {
}