package eu.toldi.mastodon.main

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.view.PublicTimeLineModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Label
import javafx.util.converter.NumberStringConverter
import tornadofx.*

class MainView : View("Mastodon") {

    private val controller: MainController by inject()

    private val model = PublicTimeLineModel()

    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }
        label("Public Timeline")

        var cucc = vbox {
            if(model.toots != null)  {
                for( toot in model.toots!!){
                    vbox {
                        label(toot.account.display_name)
                        vbox {
                            text(toot.content)
                        }
                    }
                }
            }
        }
    }
}


