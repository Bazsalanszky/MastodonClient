package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class HomeTimelineModel(helper: ApiHelper,val account: LoginAccount) :TimelineModel(helper) {
    override var toots: MutableList<Toot> = helper.authedGet<MutableList<Toot>>(ApiHelper.homeTimeline,account.token.access_token)
        ?: throw InvalidObjectException("Helper cannot be null")

    override fun loadMoreToots(): List<Toot> {
        val newToots = helper.authedGet<List<Toot>>(ApiHelper.homeTimeline+"?max_id=${toots.last().id}",account.token.access_token) ?: throw InvalidObjectException("Failed to load more toots")
        toots.addAll(newToots);
        return newToots
    }
}