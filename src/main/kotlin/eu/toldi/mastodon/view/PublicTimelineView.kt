package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.main.MainController
import javafx.scene.layout.VBox
import tornadofx.*

class PublicTimelineView : View("Public timeline") {
    private val controller: MainController by inject()

    private val model = PublicTimeLineModel()
    private lateinit var tootBox: VBox
    override val root = borderpane {
        top {
            label("Public Timeline") {
                addClass(Styles.heading)
            }
        }
        center {
            scrollpane {
                tootBox = vbox {

                }
            }
        }
    }

    init {
        model.toots?.forEach {
            tootBox += TootView(it)
        }
    }
}