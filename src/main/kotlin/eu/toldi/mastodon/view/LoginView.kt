package eu.toldi.mastodon.view

import eu.toldi.mastodon.LoginController
import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.BrowserHelper
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import javafx.scene.control.TextInputDialog
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import tornadofx.*


class LoginView : View("Mastodon Client : Login") {
    private val loginController: LoginController by inject()
    private lateinit var instanceField : TextField
    override val root = flowpane {
        addClass(Styles.background)
        vbox {

            paddingAll = 16.0
            form {
                fieldset("Login") {
                    field("Instance") {
                        instanceField = textfield {
                            promptText = "Eg: mastodon.social, fosstodon.org"
                        }
                    }
                }
                button("Login") {
                    setOnAction {
                        val api = ApiHelper(instanceField.text)
                        val client: Client
                        try {
                            client = api.registerApp()
                        } catch (e: Exception) {
                            alert(
                                Alert.AlertType.ERROR,
                                "Client Error",
                                "Error during client registration:${e.message}"
                            )
                            return@setOnAction
                        }
                        BrowserHelper.openLink("${api.hostURL}${ApiHelper.authorize}?client_id=${client.client_id}&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code")
                        val dialog = TextInputDialog("")
                        dialog.title = "Authentication token"
                        dialog.headerText = "Please login, then enter your authentication token"
                        dialog.contentText = "Authentication token:"
                        val result = dialog.showAndWait()
                        if (result.isPresent) {
                            loginController.loginWithCode(api, client, result.get())
                        }
                    }

                }
            }

            textflow {
                text("Don't have an account yet?") {
                    fill = Color.WHITE
                }
                hyperlink("Join an instance!") {
                    setOnAction {
                        BrowserHelper.openLink("https://joinmastodon.org/communities")
                    }
                }

            }
        }
    }

    init {

    }

    override fun onBeforeShow() {
        super.onBeforeShow()
    }
}