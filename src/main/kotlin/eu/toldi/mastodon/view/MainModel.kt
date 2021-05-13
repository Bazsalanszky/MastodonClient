package eu.toldi.mastodon.view

import com.google.gson.annotations.Expose
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.helpers.ApiHelper

class MainModel(
    @Expose
    val client: Client,
    @Expose
    val account: LoginAccount,
    @Expose val apiHelper: ApiHelper
    )