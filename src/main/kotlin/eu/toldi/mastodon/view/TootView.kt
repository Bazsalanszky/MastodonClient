package eu.toldi.mastodon.view

import eu.toldi.mastodon.Styles
import eu.toldi.mastodon.entities.Toot
import eu.toldi.mastodon.helpers.BrowserHelper
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextFlow
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.InputSource
import tornadofx.*
import java.io.StringReader
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory


class TootView(private val t: Toot) : View("My View") {
    private lateinit var textFlow: TextFlow
    override val root = borderpane {
        paddingAll = 10.0

        addClass(Styles.toot)
        top = hbox {
            setPrefSize(32.0, 32.0)
            imageview(t.account.avatar) {
                fitHeightProperty().bind(parent.prefHeight(32.0).toProperty())
                fitWidthProperty().bind(parent.prefWidth(32.0).toProperty())
            }
            vbox {
                paddingAll = 5
                text(t.account.display_name) {
                    fill = Color.WHITE
                }
                text(t.account.acct) {
                    fill = Color.WHITE
                }
            }
        }
        center {
            stackpane {
                stackpaneConstraints {
                    prefWidth = 800.0
                    vgrow = Priority.ALWAYS
                }
                textFlow = textflow {
                    prefWidth = Region.USE_COMPUTED_SIZE
                }
            }
        }
    }
    private val brPattern = Pattern.compile("(<br>|<br[ ]?/>)")
    init {
        parseContent()
    }

    private fun parseContent() {
        val factory = DocumentBuilderFactory.newDefaultInstance()
        val builder = factory.newDocumentBuilder()
        val html = "<!DOCTYPE test [\n" +
                "  <!ENTITY nbsp \"&#160;\">\n" +
                "]><html>${t.content}</html>".replace(brPattern.toRegex(),"\n")
        var doc: Document = builder.parse(InputSource(StringReader(html)))

        val nodes = doc.getElementsByTagName("html").item(0).childNodes
        for (i in 0..nodes.length) {
            val node = nodes.item(i) ?: continue
            parseNode(node)
        }
    }

    private fun parseNode(node: Node) {
        when {
            node.childNodes.length > 1 && node.nodeName != "a" -> {
                for (i in 0..node.childNodes.length) {
                    val n = node.childNodes.item(i) ?: continue
                    parseNode(n)
                }
            }
            node.nodeName == "p" -> {
                if(node.childNodes.length > 0)
                    parseNode(node.childNodes.item(0))
                textFlow += text("\n\n")
            }

            node.nodeName == "a" -> {
                textFlow += hyperlink(node.textContent) {
                    setOnAction {
                        BrowserHelper.openLink(node.attributes.getNamedItem("href").nodeValue)
                    }
                    isWrapText = true

                }
            }

            node.nodeName == "span" -> {

                textFlow += text(node.textContent) {
                    wrappingWidth = 590.0
                    wrappingWidthProperty().set(590.0)
                    fill = Color.WHITE
                }
            }
            node.nodeName == "br" -> {
                textFlow += text("\n")
            }
            else -> {
                textFlow += text(node.textContent) {
                    wrappingWidth = 590.0
                    wrappingWidthProperty().set(590.0)
                    fill = Color.WHITE
                }
            }
        }
    }
}
