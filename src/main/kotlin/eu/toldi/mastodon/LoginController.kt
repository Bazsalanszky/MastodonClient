package eu.toldi.mastodon

import com.google.gson.GsonBuilder
import eu.toldi.mastodon.entities.Client
import eu.toldi.mastodon.entities.LoginAccount
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.helpers.ConfigHelper
import eu.toldi.mastodon.view.LoginView
import eu.toldi.mastodon.view.MainModel
import eu.toldi.mastodon.view.MainView
import javafx.scene.control.Alert
import tornadofx.Controller
import tornadofx.alert
import java.io.File

class LoginController : Controller() {

    private val loginView :LoginView by inject()
    private val mainView :MainView by inject()

    fun init() {
        with(config) {
            if (loginWithFile())
                showMainView()
            else
                showLoginView()
        }
    }

    fun loginWithCode(api: ApiHelper, client: Client, code:String) {
        val token = api.getAccessToken(client, code)
        val account: LoginAccount
        val config = ConfigHelper(client,token,api)
        config.toFile("auth.json")
        try {
            account = api.constructLoginAccount(token)
            mainView.init(MainModel(client, account, api))
            showMainView()
        } catch (e: Exception) {
            alert(
                Alert.AlertType.ERROR,
                "Auth Error",
                "Error during logging you in:${e.message} ${e.stackTrace}"
            )
            e.printStackTrace()
            return
        }
    }

    private fun showMainView() {
        loginView.replaceWith(mainView, sizeToScene = true, centerOnScreen = true)
    }

    private fun showLoginView() {
        mainView.replaceWith(loginView, sizeToScene = true, centerOnScreen = true)
    }

    private fun loginWithFile() :Boolean {
        try{
            val gson = GsonBuilder().create()
            val file = File("auth.json")
            if(file.exists()) {
                val conf = gson.fromJson(file.readText(), ConfigHelper::class.java)
                mainView.init(conf.createMainModel())
                return true
            }
        }catch(e: Exception) {
            // Ez nem egy fontos hiba
        }
        return false
    }
}
