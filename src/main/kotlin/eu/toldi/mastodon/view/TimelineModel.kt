package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import tornadofx.ViewModel

abstract class TimelineModel(val helper: ApiHelper) : ViewModel() {
    val toots: MutableList<Toot> = mutableListOf()
    abstract fun loadMoreToots():  List<Toot>
}