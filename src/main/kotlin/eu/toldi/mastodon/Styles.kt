package eu.toldi.mastodon

import javafx.scene.text.FontWeight
import tornadofx.*


class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val toot by cssclass()
        val background by cssclass()
        val scrollable by cssclass()
        val tootBg = c("#282c37")
        val defaultBg = c("#191b22")
        val buttonColor = c("#2b90d9")
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
            backgroundColor = multi(tootBg)
            borderColor += box(tootBg)
            backgroundRadius += box(5.px)
        }
        background {
            backgroundColor = multi(defaultBg)
        }
        button {
            backgroundColor = multi(buttonColor)
            textFill = c("#ffffff")
        }
        s(textInput, label) {
            textFill = c("#ffffff")
        }
        s(textInput, button) {
            textFill = c("#000000")
        }

        scrollable {
            borderRadius = multi(box(10.px))
            borderWidth = multi(box(5.px))
            borderColor = multi(box(defaultBg))
            backgroundColor = multi(defaultBg)
        }

         track {
            borderColor = multi(box(defaultBg))
        }

        thumb {
            borderRadius = multi(box(10.px))
            borderWidth = multi(box(5.px))
            borderColor = multi(box(tootBg))
        }
        scrollPane {
            borderRadius = multi(box(10.px))
            borderWidth = multi(box(5.px))
            borderColor = multi(box(defaultBg))
            backgroundColor = multi(defaultBg)
        }

    }
}
