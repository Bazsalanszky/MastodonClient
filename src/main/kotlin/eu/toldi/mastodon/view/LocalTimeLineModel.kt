package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException
import java.util.concurrent.ExecutionException

class LocalTimelineModel(helper: ApiHelper) : TimelineModel(helper) {
    override var toots: List<Toot> = helper.get<List<Toot>>(ApiHelper.pubicTimeline+"?local=true")
        ?: throw InvalidObjectException("Helper cannot be null")

    override fun loadMoreToots(): List<Toot> {
        val newToots = helper.get<List<Toot>>(ApiHelper.pubicTimeline+"?local=true&max_id=${toots.last().id}") ?: throw InvalidObjectException("Failed to load more toots")
        toots = toots.plus(newToots)
        return newToots
    }
}