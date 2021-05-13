package eu.toldi.mastodon.view

import com.google.gson.GsonBuilder
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.BrowserHelper
import eu.toldi.mastodon.helpers.ConfigHelper
import io.ktor.client.features.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.TextInputDialog
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import tornadofx.*
import java.io.File
import java.lang.reflect.Modifier
import java.util.regex.Pattern


class LoginView : View("Mastodon Client") {

    override val root = flowpane {
        vbox {
            paddingAll = 16.0
            text("Instance:") {
                textAlignment = TextAlignment.CENTER
            }
            hbox {
                form {
                    val instanceField = textfield() {
                        promptText = "Eg: mastodon.social, fosstodon.org"
                    }
                    button("Login") {
                        setOnAction {
                            val api = ApiHelper(instanceField.text)
                            val c: Client
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
                                val token = api.getAccessToken(c, code)
                                val account: LoginAccount
                                val config = ConfigHelper(c,token,api)
                                config.toFile("auth.json")
                                try {
                                    account = api.constructLoginAccount(token)
                                    val mainView = MainView(MainModel(c, account, api))
                                    replaceWith(mainView)
                                } catch (e: Exception) {
                                    alert(
                                        Alert.AlertType.ERROR,
                                        "Auth Error",
                                        "Error during logging you in:${e.message} ${e.stackTrace}"
                                    )
                                    e.printStackTrace()
                                    return@setOnAction
                                }
                            }
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

    init {

    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        try{
            val gson = GsonBuilder().create()
            val conf = gson.fromJson<ConfigHelper>(File("auth.json").readText(),ConfigHelper::class.java)
            val stage = primaryStage;
            val scene = Scene(MainView(conf.createMainModel()).root)
            stage.scene = scene
            stage.show()
        }catch(e: Exception) {
            e.printStackTrace()
        }
    }
}