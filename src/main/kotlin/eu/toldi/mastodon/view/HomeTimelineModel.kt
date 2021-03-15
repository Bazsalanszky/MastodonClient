package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class HomeTimelineModel(helper: ApiHelper,val account: LoginAccount) :TimelineModel(helper) {
    override var toots: List<Toot> = helper.get<List<Toot>>(ApiHelper.homeTimeline+"?Authorization=${account.token.access_token}")
        ?: throw InvalidObjectException("Helper cannot be null")

    override fun loadMoreToots(): List<Toot> {
        val newToots = helper.get<List<Toot>>(ApiHelper.homeTimeline+"?Authorization=${account.token.access_token}&max_id=${toots.last().id}") ?: throw InvalidObjectException("Failed to load more toots")
        toots = toots.plus(newToots)
        return newToots
    }
}