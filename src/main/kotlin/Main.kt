import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File

//import java.io.File

//import com.kevinnzou.sample.MainWebView

@Composable
fun App() {
//    var text by remember { mutableStateOf("Hello, World!") }
    val curPath = System.getProperty("user.dir")
    println("user dir = $curPath")
    val itemsDir = "$curPath/items/copy_Rarit"
    val dirsList = listDirsUsingDirectoryStream(itemsDir)
    println("dirsList = $dirsList")
    //var setOfItems =
    dirsList.forEach {
        val filesList = listFilesUsingDirectoryStream("$itemsDir/$it")
        println("for $it: $filesList")
    }
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
            UpperBar(nvsuLogoWhite, fitimLogoWhite)
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

@Composable
private fun UpperBar(nvsuLogoWhite: String, fitimLogoWhite: String) {
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
            painter = painterResource(nvsuLogoWhite), //указываем источник изображения
            contentDescription = "", //можно вставить описание изображения
            contentScale = ContentScale.Fit, //параметры масштабирования изображения
        )
        Text("НВГУ ФИТМ | ИТ Музей", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Image(
            painter = painterResource(fitimLogoWhite), //указываем источник изображения
            contentDescription = "", //можно вставить описание изображения
            contentScale = ContentScale.Fit, //параметры масштабирования изображения
        )
    }
}

@Composable //todo сделать обычными объектами: текстом и картинками
fun MyContent() {
    val curPath = System.getProperty("user.dir")
    println("user dir = $curPath")
    var text = File("$curPath/items/copy_Rarit/alfa/Кинокамера_Киев-16_Альфа_Полуавтомат.txt").readText().replace("\t","   ")
//    println(text)
    println()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color.Blue))
    ) {
        Text(
            text, fontSize = 20.sp,
            modifier = Modifier.weight(3f)
        )
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource("items/copy_Rarit/alfa/Внешний_вид_кинокамеры.jpg"), //указываем источник изображения
            contentDescription = "", //можно вставить описание изображения
//                contentScale = ContentScale.FillWidth, //параметры масштабирования изображения
        )
    }
}

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Fullscreen
    )
    listFilesUsingJavaIO("/")
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
