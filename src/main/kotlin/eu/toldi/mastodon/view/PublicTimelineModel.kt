package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class PublicTimelineModel(helper: ApiHelper) : TimelineModel(helper) {
    override val toots: List<Toot> = helper.get<List<Toot>>(ApiHelper.pubicTimeline)
        ?: throw InvalidObjectException("Helper cannot be null")
}