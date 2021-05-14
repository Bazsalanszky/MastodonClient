package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import javafx.geometry.Insets
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*

class TimelineView(private val model: TimelineModel) : View() {

    private lateinit var tootBox: VBox
    override val root = borderpane {
        style = "-fx-background-color: #191b22;"

        center {
            scrollpane {
                minHeight = 800.0
                minWidth = 800.0
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
                addClass(Styles.scrollable)
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
        GlobalScope.launch(Dispatchers.IO) {
            // Initial toot load
            model.loadMoreToots()
            withContext(Dispatchers.Main){
                model.toots.forEach {
                    tootBox += TootView(it)
                }
            }
        }
    }
}