package eu.toldi.mastodon.view

import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.BrowserHelper
import javafx.scene.Parent
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.awt.Desktop
import java.net.URI
import java.net.URL

class LoginView : View("MastodonKlient: Login") {
    override val root = flowpane {
        vbox{
            paddingAll = 16.0
            text("Instance:"){
                textAlignment = TextAlignment.CENTER
            }
            hbox {
                val text = textfield() {
                    promptText = "Eg: mastodon.social, fosstodon.org"
                }
                button("Login") {
                    val api = ApiHelper(text.cont)
                }
            }
            textflow {
                text("Don't have an account yet?")
                hyperlink("Join an instance!") {
                    setOnAction {
                        BrowserHelper.openLink("https://joinmastodon.org/communities")
                    }
                }
            }
        }
    }
}