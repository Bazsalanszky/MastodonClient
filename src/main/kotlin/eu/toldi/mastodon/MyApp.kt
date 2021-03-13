package eu.toldi.mastodon

import eu.toldi.mastodon.view.LoginView
import javafx.application.Application
import tornadofx.App

class MyApp : App(LoginView::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}
