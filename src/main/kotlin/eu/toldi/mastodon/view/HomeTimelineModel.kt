package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class HomeTimelineModel(helper: ApiHelper,val account: LoginAccount) :TimelineModel(helper) {

    override fun loadMoreToots(): List<Toot> {
        val addition = if(toots.size > 0){
            "?max_id=${toots.last().id}"
        }else ""
        val newToots = helper.authedGet<List<Toot>>(ApiHelper.homeTimeline+addition,account.token.access_token)
        toots.addAll(newToots)
        return newToots
    }
}