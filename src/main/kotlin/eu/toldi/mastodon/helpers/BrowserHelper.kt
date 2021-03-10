package eu.toldi.mastodon.helpers

import java.awt.Desktop
import java.net.URI

object BrowserHelper {
    fun openLink(URL: String) {
        val desktop = Desktop.getDesktop();
        if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)){
            desktop.browse(URI(URL));
        }else{
            val runtime = Runtime.getRuntime();
            runtime.exec("xdg-open " + URL);
        }
    }
}