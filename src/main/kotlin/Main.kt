import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Component
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel
import androidx.compose.foundation.layout.size
//import com.kevinnzou.sample.MainWebView

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
        Scaffold(
            topBar = { TopAppBar(title = { Text("НВГУ | ИТ Музей", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
            content = { MyContent() }
        )
    }
}

fun actionButton(
    text: String,
    action: () -> Unit
): JButton {
    val button = JButton(text)
    button.alignmentX = Component.CENTER_ALIGNMENT
    button.addActionListener { action() }
    return button
}
@Composable
fun MyContent(){
    var text by remember { mutableStateOf("Hello, World!") }
    // Declare a string that contains a url
//    val mUrl = "floppy5.html"

    val counter = remember { mutableStateOf(0) }

    val inc: () -> Unit = { counter.value++ }
    val dec: () -> Unit = { counter.value-- }
    SwingPanel(
        background = Color.White,
        modifier = Modifier.size(270.dp, 90.dp),
        factory = {
            JPanel().apply {
                //todo add JEditorPane to load html files
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(actionButton("1. Swing Button: decrement", dec))
                add(actionButton("2. Swing Button: decrement", dec))
                add(actionButton("3. Swing Button: decrement", dec))
            }
        }
    )
    // Adding a WebView inside AndroidView
    // with layout as full screen
//    AndroidView(factory = {
//        WebView(it).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            webViewClient = WebViewClient()
//            loadUrl(mUrl)
//        }
//    }, update = {
//        it.loadUrl(mUrl)
//    })
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
