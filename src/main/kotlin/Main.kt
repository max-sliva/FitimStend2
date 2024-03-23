import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.*

//import java.io.File

//import com.kevinnzou.sample.MainWebView

@Composable
fun App() {
    val curPath = System.getProperty("user.dir")
    println("user dir = $curPath")
    var filesSet = remember { mutableStateListOf("$curPath/welcome.txt", "$curPath/НВГУ_лого с корпусом.png") }
    val itemsDir = "$curPath/items/copy_Rarit"
    val dirsList = listDirsUsingDirectoryStream(itemsDir)
    println("dirsList = $dirsList")
    //var setOfItems =
//    var itemsMap = mutableMapOf<String, String>()
    var itemsMap2 = mutableMapOf<String, Set<String>>()
    dirsList.forEach {
        val filesList = listFilesUsingDirectoryStream("$itemsDir/$it")
        val txtFile = filesList.find { it.contains(".txt") }
        val imgFile = filesList.find { it!=txtFile }
        val txtFilePath = "$itemsDir/$it/$txtFile"
        val imgFilePath = "$itemsDir/$it/$imgFile"
//        val filesSet = setOf(txtFilePath, imgFilePath)
        val file = File(txtFilePath)
        val ioStream = BufferedReader(FileReader(file))
        ioStream.readLine()
        val s = ioStream.readLine()
//        println("for $it: $filesList caption = $s")
//        itemsMap[s] = "$itemsDir/$it"
        itemsMap2[s] = setOf(txtFilePath, imgFilePath)
    }
    itemsMap2.forEach{
        println("${it.key} : ${it.value}")
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
            DropdownDemo(itemsMap2.keys.toList()){
                println("selected = $it")
                val tempList = itemsMap2[it]?.toList()
                filesSet.clear()
                filesSet.add(tempList!!.first())
                filesSet.add(tempList.last())
            }
            MyContent(filesSet)
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

@Composable
fun MyContent(filesSet: SnapshotStateList<String>) {
    val curPath = System.getProperty("user.dir")
    println("user dir = $curPath")
    println("filesSet = ${filesSet.toList()}")
    val textFile = filesSet.find {
        it.contains(".txt")
    }
    val imageFile = filesSet.find {
        !it.contains(".txt")
    }
//    var text = File("$curPath/items/copy_Rarit/alfa/Кинокамера_Киев-16_Альфа_Полуавтомат.txt").readText().replace("\t","   ")
    var text = File("$textFile").readText().replace("\t","   ")
//    println(text)
//    val file = File("$curPath/items/copy_Rarit/alfa/Внешний_вид_кинокамеры.jpg")
    val file = File("$imageFile")
    val imageBitmap: ImageBitmap = remember(file) {
        loadImageBitmap(file.inputStream())
    }
    println()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color.Blue))
            .padding(20.dp)
    ) {
        Text(
            text, fontSize = 20.sp,
            modifier = Modifier.weight(3f)
        )
        Image( //todo сделать увеличение картинки на весь экран по щелчку
            modifier = Modifier.weight(1f),
            painter = BitmapPainter(image = imageBitmap),
            contentDescription = null
        )
//        Image(
//            modifier = Modifier.weight(1f),
//            painter = painterResource("items/copy_Rarit/alfa/Внешний_вид_кинокамеры.jpg"), //указываем источник изображения
//            contentDescription = "", //можно вставить описание изображения
////                contentScale = ContentScale.FillWidth, //параметры масштабирования изображения
//        )
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

@Composable
fun DropdownDemo(itemsInitial:  List<String>, onUpdate: (x: String) -> Unit) { //комбобокс для выбора компорта для подключения к Arduino
    var expanded by remember { mutableStateOf(false) }
//    val items = listOf("com1", "com2", "com3")
//    val disabledValue = "B"
    var items = remember { mutableStateListOf<String>() }
    itemsInitial.forEach {
        if (!items.contains(it))items.add(it)
    }
    var selectedIndex by remember { mutableStateOf(-1) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Text( //заголовок комбобокса
            if (selectedIndex<0) "Выберите экспонат: ▼" //если еще ничего не выбрано
            else items[selectedIndex]+" ▼", //если выбрано
            modifier = Modifier.clickable(onClick = { //при нажатии на текст раскрываем комбобокс
//                val tempPortList = SerialPortList.getPortNames().toList() //получаем активные порты
//                println("SerialPortList = $tempPortList")
//                tempPortList.forEach {//добавляем новые порты к списку
//                    if (!items.contains(it))items.add(it)
//                }
//                items.forEach{//убираем отключенные порты
//                    if (!tempPortList.contains(it)) {
////                        println("$it not in SerialPortList")
//                        items.remove(it)
//                    }
//                }
                expanded = true
            })
        )
        DropdownMenu( //сам выпадающий список для комбобокса
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            items.forEachIndexed { index, s -> //заполняем элементы выпадающего списка
                DropdownMenuItem(
                    onClick = { //обработка нажатия на порт
                        selectedIndex = index
                        expanded = false
                        onUpdate(s)
//                        println("selected = $s")
                    }
                ) {
//                    val disabledText = if (s == disabledValue) {
//                        " (Disabled)"
//                    } else {
//                        ""
//                    }
                    Text(text = s )
                }
            }
        }
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
