package eu.toldi.mastodon.view

import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.BrowserHelper
import javafx.scene.text.TextFlow
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.InputSource
import tornadofx.*
import java.io.StringReader
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory


class TootView(val t: Toot) : View("My View") {
    lateinit var textFlow: TextFlow
    override val root = borderpane {
        top = hbox {
            setPrefSize(64.0, 64.0)
            imageview(t.account.avatar) {
                fitHeightProperty().bind(parent.prefHeight(64.0).toProperty())
                fitWidthProperty().bind(parent.prefWidth(64.0).toProperty())
            }
            vbox {
                paddingAll = 5
                text(t.account.display_name)
                text(t.account.acct)
            }
        }
        center {
            stackpane {
                textFlow = textflow {

                }
            }
        }
    }
    val br_pattern = Pattern.compile("(<br>|<br[ ]?/>)")
    init {
        parseContent()
    }

    fun parseContent() {
        val factory = DocumentBuilderFactory.newDefaultInstance()
        val builder = factory.newDocumentBuilder()
        val html = "<html>${t.content}</html>"

        var doc: Document = builder.parse(InputSource(StringReader(html.replace(br_pattern.toRegex(),"\n"))))

        val nodes = doc.getElementsByTagName("html").item(0).childNodes
        for (i in 0..nodes.length) {
            val node = nodes.item(i) ?: continue
            parseNode(node)
        }
    }

    fun parseNode(node: Node) {
        when {
            node.childNodes.length > 1 && node.nodeName != "a" -> {
                for (i in 0..node.childNodes.length) {
                    val n = node.childNodes.item(i) ?: continue
                    parseNode(n)
                }
            }
            node.nodeName == "p" -> {
                parseNode(node.childNodes.item(0))
                textFlow += text("\n\n")
            }

            node.nodeName == "a" -> {
                textFlow += hyperlink(node.textContent) {
                    setOnAction {
                        BrowserHelper.openLink(node.attributes.getNamedItem("href").nodeValue)
                    }
                }
            }

            node.nodeName == "span" -> {
                textFlow += text(node.textContent)
            }
            node.nodeName == "br" -> {
                textFlow += text("\n")
            }
            else -> {
                textFlow += text(node.textContent)
            }
        }
    }
}
