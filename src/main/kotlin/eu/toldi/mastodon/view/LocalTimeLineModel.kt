package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class LocalTimelineModel(helper: ApiHelper) : TimelineModel(helper) {
    override val toots: List<Toot> = helper.get<List<Toot>>(ApiHelper.pubicTimeline+"?local=true")
        ?: throw InvalidObjectException("Helper cannot be null")
}