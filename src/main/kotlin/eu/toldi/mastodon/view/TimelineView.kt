package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.main.MainController
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.geometry.Orientation
import tornadofx.*

class TimelineView(val model: TimelineModel) : View() {
    private val controller: MainController by inject()

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
                hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
                skinProperty().onChange {
                    this.lookupAll(".scroll-bar").map { it as ScrollBar }.forEach { bar ->
                        bar.valueProperty().onChange {
                            if(it == 1.0){
                                //Load more toots
                                model.loadMoreToots().forEach {
                                    tootBox += TootView(it)
                                }
                            }
                        }
                    }
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