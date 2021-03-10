package eu.toldi.mastodon.view

import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.BrowserHelper
import javafx.scene.text.TextAlignment
import tornadofx.*

class LoginView : View("MastodonKlient: Login") {
    override val root = flowpane {
        vbox{
            paddingAll = 16.0
            text("Instance:"){
                textAlignment = TextAlignment.CENTER
            }
            hbox {
                val instanceField = textfield() {
                    promptText = "Eg: mastodon.social, fosstodon.org"
                }
                button("Login") {
                    setOnAction {
                        val api = ApiHelper(instanceField.text)
                        val c = api.registerApp()
                        if(c == null)
                            throw IllegalArgumentException("Registration Failed!")
                    }


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