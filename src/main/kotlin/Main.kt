import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

//import com.kevinnzou.sample.MainWebView

@Composable
fun App() {
//    var text by remember { mutableStateOf("Hello, World!") }
    val curPath = System.getProperty("user.dir")
    println("user dir = $curPath")
    val fitimLogo = "fitim.png"
    val fitimLogoWhite = "fitim_white.png"
    val nvsuLogoWhite = "NVSU_white.png"
    val backgroundImage = "items/background.jpg"

    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
//        Scaffold(
//            topBar = {
//                TopAppBar(title = {
        Column(Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
            ) {
                Image(
//нужен import androidx.compose.foundation.Image
                    painter = painterResource(nvsuLogoWhite), //указываем источник изображения
                    contentDescription = "", //можно вставить описание изображения
                    contentScale = ContentScale.Fit, //параметры масштабирования изображения
                )
                Text("НВГУ ФИТМ | ИТ Музей", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Image(
//нужен import androidx.compose.foundation.Image
                    painter = painterResource(fitimLogoWhite), //указываем источник изображения
                    contentDescription = "", //можно вставить описание изображения
                    contentScale = ContentScale.Fit, //параметры масштабирования изображения
                )
            }
            MyContent()
        }
//                },
////                backgroundColor = Color(0xff0f9d58))
//                backgroundColor = Color(0xff1e63b2))
//            },
//            content = { MyContent() }
//        )
    }
}

@Composable //todo сделать обычными объектами: текстом и картинками
fun MyContent(){
//https://github.com/oleksandrbalan/textflow
//    TextFlow(
//        text = "Text Flow allows to display a text which wraps around an image (or any other Composable)",
//        modifier = Modifier.width(220.dp),
//        obstacleAlignment = TextFlowObstacleAlignment.TopStart,
//        obstacleContent = {
//            Icon(
//                imageVector = Icons.Default.Done,
//                contentDescription = null,
//                modifier = Modifier.padding(4.dp)
//            )
//        }
//    )
    val annotatedString = buildAnnotatedString {
        append("This is text ")
        appendInlineContent(id = "imageId")
        append(" with a call icon")
    }
    val inlineContentMap = mapOf(
        "imageId" to InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                imageVector = Icons.Default.Call,
                modifier = Modifier.fillMaxSize(),
                contentDescription = ""
            )
        }
    )

    Text(annotatedString, inlineContent = inlineContentMap)
}

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Fullscreen
    )

    Window(
        onCloseRequest = ::exitApplication,
//        undecorated = true, //эти 3 строки нужны для фуллскрина без оконных кнопок
//        alwaysOnTop = true,
//        state = state
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
