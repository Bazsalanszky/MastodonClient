package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class PublicTimelineModel(helper: ApiHelper) : TimelineModel(helper) {
    override var toots: MutableList<Toot> = helper.get<MutableList<Toot>>(ApiHelper.pubicTimeline)
        ?: throw InvalidObjectException("Helper cannot be null")

    override fun loadMoreToots(): List<Toot> {
        val newToots = helper.get<List<Toot>>(ApiHelper.pubicTimeline+"?max_id=${toots.last().id}") ?: throw InvalidObjectException("Failed to load more toots")
        toots.addAll(newToots)
        return newToots
    }
}