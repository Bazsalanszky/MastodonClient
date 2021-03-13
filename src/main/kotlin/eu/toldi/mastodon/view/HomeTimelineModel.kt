package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import java.io.InvalidObjectException

class HomeTimelineModel(helper: ApiHelper,val account: LoginAccount) :TimelineModel(helper) {
    override val toots: List<Toot> = helper.get<List<Toot>>(ApiHelper.pubicTimeline+"?Authorization=${account.token.access_token}")
        ?: throw InvalidObjectException("Helper cannot be null")
}