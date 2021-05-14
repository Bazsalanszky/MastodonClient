package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.main.MainController
import javafx.geometry.Insets
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*

class TimelineView(val model: TimelineModel) : View() {
    private val controller: MainController by inject()

    private lateinit var tootBox: VBox
    override val root = borderpane {
        style = "-fx-background-color: #191b22;"

        center {
            scrollpane {
                isFitToWidth = true
                isFitToHeight = true
                vbox {
                    style = "-fx-background-color: #191b22;"
                    tootBox = vbox {
                        vboxConstraints {
                            margin = Insets(10.0,10.0,10.0,10.0)
                            vGrow = Priority.ALWAYS
                            spacing = 5.0
                            alignment = Pos.CENTER
                        }
                    }
                    hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                    vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
                    vbox {
                        progressindicator {

                        }
                        alignment = Pos.CENTER
                    }
                }
                style = "-fx-border-radius: 10;" +
                        "-fx-border-width:5;" +
                        "-fx-border-color: #191b22;" +
                        "-fx-background-radius: 10;" +
                        "-fx-background-color: #191b22;"
                skinProperty().onChange {
                    this.lookupAll(".scroll-bar").map { it as ScrollBar }.forEach { bar ->
                        bar.valueProperty().onChange {
                            if (it == 1.0) {
                                GlobalScope.launch(Dispatchers.IO) {
                                    //Load more toots
                                    model.loadMoreToots().forEach {
                                        withContext(Dispatchers.Main) {
                                            tootBox += TootView(it)
                                        }

                                    }
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