package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.BrowserHelper
import io.ktor.client.features.*
import io.ktor.client.request.forms.*
import javafx.scene.control.Alert
import javafx.scene.control.TextInputDialog
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
                        val c : Client
                        try {
                            c = api.registerApp()
                        } catch (e: Exception) {
                            alert(
                                Alert.AlertType.ERROR,
                                "Client Error",
                                "Error during client registration:${e.message}"
                            )
                            return@setOnAction
                        }
                        BrowserHelper.openLink("${api.hostURL}${ApiHelper.authorize}?client_id=${c.client_id}&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code")
                        val dialog = TextInputDialog("")
                        dialog.title = "Authentication token"
                        dialog.headerText = "Please login, then enter your authentication token"
                        dialog.contentText = "Authentication token:"
                        val result = dialog.showAndWait()
                        if (result.isPresent) {
                            val code = result.get()
                            println(code)
                            val token = api.getAccessToken(c,code)
                            println("${token.access_token} ${token.token_type} ${token.scope} ${token.created_at}")
                        }
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