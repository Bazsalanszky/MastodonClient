package eu.toldi.mastodon

import com.google.gson.GsonBuilder
import eu.toldi.mastodon.helpers.ConfigHelper
import eu.toldi.mastodon.view.LoginView
import eu.toldi.mastodon.view.MainView
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus
import java.io.File

class LoginApp : App(LoginView::class, Styles::class){
    val loginController: LoginController by inject()
    init {
        reloadStylesheetsOnFocus()
    }


    override fun start(stage: Stage) {
        super.start(stage)
        loginController.init()
    }
}

fun main(args: Array<String>) {

    Application.launch(LoginApp::class.java, *args)
}
