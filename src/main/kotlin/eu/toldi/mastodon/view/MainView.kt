package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.helpers.ApiHelper
import javafx.scene.layout.VBox
import tornadofx.*

class MainView(val model: MainModel) : View("MasotodonKlinet") {
    lateinit var h_box : VBox
    lateinit var l_box : VBox
    lateinit var f_box : VBox
    override val root = drawer {
        item("Home") {
            h_box = vbox {
                label("Home Timeline") {
                    addClass(Styles.heading)
                }
            }
        }
        item("Local"){
            l_box = vbox {
                label("Local Timeline") {
                    addClass(Styles.heading)
                }
            }

        }
        item("Federated",expanded = true) {
             f_box = vbox {
                label("Federated Timeline") {
                    addClass(Styles.heading)
                }
            }
        }
    }

    init {
//        h_box += TimelineView(HomeTimelineModel(model.apiHelper,model.account))
        f_box += TimelineView(PublicTimelineModel(model.apiHelper))
        l_box += TimelineView(LocalTimelineModel(model.apiHelper))
    }
}