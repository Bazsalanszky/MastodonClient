package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import tornadofx.ViewModel

class PublicTimeLineModel() : ViewModel() {
    val helper = ApiHelper("s.toldi.eu")
    var toots = helper.get<List<Toot>>(ApiHelper.pubicTimeline)
}