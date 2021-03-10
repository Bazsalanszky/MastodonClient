package eu.toldi.mastodon

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.ApiHelper
import eu.toldi.mastodon.main.MainView
import eu.toldi.mastodon.view.LoginView
import eu.toldi.mastodon.view.PublicTimelineView
import javafx.application.Application
import tornadofx.App

class MyApp : App(LoginView::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}
