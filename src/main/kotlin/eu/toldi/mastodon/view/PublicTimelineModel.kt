package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class PublicTimelineModel(helper: ApiHelper) : TimelineModel(helper) {


    override fun loadMoreToots(): List<Toot> {
        val addition = if(toots.size > 0){
            "?max_id=${toots.last().id}"
        }else ""
        val newToots = helper.get<List<Toot>>(ApiHelper.pubicTimeline+addition)
        toots.addAll(newToots)
        return newToots
    }
}