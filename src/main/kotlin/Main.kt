import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState


//import com.kevinnzou.sample.MainWebView

@Composable
fun App() {
//    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("НВГУ ФИТМ | ИТ Музей", color = Color.White) },
//                backgroundColor = Color(0xff0f9d58))
                backgroundColor = Color(0xff1e63b2))
            },
            content = { MyContent() }
        )
    }
}

@Composable //todo сделать обычными объектами: текстом и картинками
fun MyContent(){
//https://github.com/oleksandrbalan/textflow
    var text by remember { mutableStateOf("Hello, World!") }

    Button(onClick = {
            text = "Hello, Desktop!"
    }) {
            Text(text)
    }
}

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Fullscreen
    )

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        alwaysOnTop = true,
        state = state
    ) {
        App()
    }
}


//@Composable
//fun Button(text: String = "", action: (() -> Unit)? = null) {
//    Button(
//        modifier = Modifier.size(270.dp, 40.dp),
//        onClick = { action?.invoke() }
//    ) {
//        Text(text)
//    }
//}
