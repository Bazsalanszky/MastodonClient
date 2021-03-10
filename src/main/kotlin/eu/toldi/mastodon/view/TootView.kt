package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import javafx.application.Platform
import javafx.scene.layout.BorderPane
import javafx.scene.text.Text
import tornadofx.*

class TootView(val t:Toot) : View("My View") {
    override val root = borderpane {
        top = hbox {
            setPrefSize(64.0, 64.0)
            imageview(t.account.avatar) {
                fitHeightProperty().bind(parent.prefHeight(64.0).toProperty())
                fitWidthProperty().bind(parent.prefWidth(64.0).toProperty())
            }
            vbox {
                paddingAll = 5
                text(t.account.display_name)
                text(t.account.acct)
            }
        }
        center {
            stackpane {
                val w = webview{
                    engine.loadContent(t.content)
                    engine.loadWorker.stateProperty().onChange {
                        val wh = engine.executeScript("document.documentElement.scrollHeight").toString().toDouble()
                        prefHeight = wh
                    }
                }
                widthProperty().onChange {
                    w.prefWidth = it
                    val wh = w.engine.executeScript("document.documentElement.scrollHeight").toString().toDouble()
                    w.prefHeight = wh
                }
            }
        }

    }
}
