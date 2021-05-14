package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MainView : View("Masotodon Client") {
    private lateinit var model: MainModel
    private lateinit var hBox: VBox
    private lateinit var lBox: VBox
    private lateinit var fBox: VBox
    override val root = drawer {
        addClass(Styles.background)
        item("Home") {
            vbox {
                vbox {
                    label("Home Timeline") {
                        font = Font.font(20.0)
                        textFill = Color.WHITE
                    }
                    alignment = Pos.CENTER
                }
                hBox = vbox {

                }
            }
        }
        item("Local") {
            lBox = vbox {
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
            fBox = vbox {
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
        hBox += TimelineView(HomeTimelineModel(model.apiHelper, model.account))
        fBox += TimelineView(PublicTimelineModel(model.apiHelper))
        lBox += TimelineView(LocalTimelineModel(model.apiHelper))

    }


}