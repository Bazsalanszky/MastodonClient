package eu.toldi.mastodon

import javafx.scene.text.FontWeight
import tornadofx.*


class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val toot by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        toot {
            borderWidth += box(5.px)
            borderRadius += box(10.px)
            backgroundColor += c("282c37")
            borderColor += box(c("282c37"))
            backgroundRadius += box(5.px)
        }
    }
}
