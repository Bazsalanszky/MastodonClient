package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.helpers.ApiHelper
import javafx.collections.MapChangeListener
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MainView : View("MasotodonKlinet") {
    lateinit var model: MainModel
    lateinit var h_box: VBox
    lateinit var l_box: VBox
    lateinit var f_box: VBox
    override val root = drawer {
        style = "-fx-background-color: #191b22;"
        item("Home") {
            vbox {
                vbox {
                    label("Home Timeline") {
                        font = Font.font(20.0)
                        textFill = Color.WHITE
                    }
                    alignment = Pos.CENTER
                }
                h_box = vbox {

                }
            }
        }
        item("Local") {
            l_box = vbox {
                vbox {
                    label("Local Timeline") {
                        font = Font.font(20.0)
                        textFill = Color.WHITE
                    }
                    alignment = Pos.CENTER
                }
            }

        }
        item("Federated", expanded = true) {
            f_box = vbox {
                vbox {
                    label("Federated Timeline") {
                        font = Font.font(20.0)
                        textFill = Color.WHITE
                    }
                    alignment = Pos.CENTER
                }
            }
        }
    }

    fun init(mainModel: MainModel) {
        model = mainModel
        h_box += TimelineView(HomeTimelineModel(model.apiHelper, model.account))
        f_box += TimelineView(PublicTimelineModel(model.apiHelper))
        l_box += TimelineView(LocalTimelineModel(model.apiHelper))

    }


}