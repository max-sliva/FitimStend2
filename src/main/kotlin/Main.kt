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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import java.io.*

//import java.io.File

//import com.kevinnzou.sample.MainWebView

@Composable
fun App(state: MutableState<WindowState>, curPath: String) {
//    val curPath = System.getProperty("user.dir")
    val backgroundImage = "items/background.jpg"
    println("user dir = $curPath")
    var filesSet = remember { mutableStateListOf("$curPath/welcome.txt", "$curPath/$backgroundImage") }
    val itemsDir = "$curPath/items/copy_Rarit"
    val dirsList = listDirsUsingDirectoryStream(itemsDir)
    println("dirsList = $dirsList")
    //var setOfItems =
//    var itemsMap = mutableMapOf<String, String>()
    var itemsMap2 = mutableMapOf<String, Set<String>>() //мап для хранения названия экспоната и набора из его описания и картинки
    dirsList.forEach {
        val filesList = listFilesUsingDirectoryStream("$itemsDir/$it")
        val txtFile = filesList.find { it.contains(".txt") }
        val imgFile = filesList.find { it != txtFile }
        val txtFilePath = "$itemsDir/$it/$txtFile"
        val imgFilePath = "$itemsDir/$it/$imgFile"
//        val filesSet = setOf(txtFilePath, imgFilePath)
        val file = File(txtFilePath)
        val ioStream = BufferedReader(FileReader(file))
        val firstStringInFile = ioStream.readLine()
        val s = if (firstStringInFile=="") ioStream.readLine() else firstStringInFile //если первая строка пустая
//        println("for $it: $filesList caption = $s")
//        itemsMap[s] = "$itemsDir/$it"
        itemsMap2[s] = setOf(txtFilePath, imgFilePath)
    }
    itemsMap2.forEach {
        println("${it.key} : ${it.value}")
    }
    val fitimLogo = "fitim.png"
//    val fitimLogoWhite = "fitim_white.png"
    val facultyLogoWhite = "faculty_white.png"
    val nvsuLogoWhite = "NVSU_white.png"
    var fontSize = remember { mutableStateOf(26.sp) }
//    MaterialTheme() {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
//        Scaffold(
//            topBar = {
//                TopAppBar(title = {
        Column(Modifier.fillMaxSize()) {
            UpperBar(nvsuLogoWhite, facultyLogoWhite)
            DropdownDemo(itemsMap2.keys.toList(), fontSize) {
                println("selected = $it")
                val tempList = itemsMap2[it]?.toList()
                filesSet.clear()
                filesSet.add(tempList!!.first())
                filesSet.add(tempList.last())
            }
            MyContent(filesSet, fontSize, state)
        }
//                },
////                backgroundColor = Color(0xff0f9d58))
//                backgroundColor = Color(0xff1e63b2))
//            },
//            content = { MyContent() }
//        )
//    }
}

@Composable
private fun UpperBar(nvsuLogoWhite: String, facultyLogoWhite: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
//

            .fillMaxWidth()
            .height(100.dp)
//        .padding(20.dp)

            .background(Color.DarkGray)
//            .background(Color(0xff1e63b2))

    ) {
        val nvsuFile = File(nvsuLogoWhite)

        val nvsuBitmap: ImageBitmap = remember(nvsuFile) {
            loadImageBitmap(nvsuFile.inputStream())
        }
        val facultyFile = File(facultyLogoWhite)
        val facultyBitmap: ImageBitmap = remember(facultyFile) {
            loadImageBitmap(facultyFile.inputStream())
        }
        Image(

            painter = BitmapPainter(image = nvsuBitmap),
//            painter = painterResource(nvsuLogoWhite), //указываем источник изображения
            contentDescription = "", //можно вставить описание изображения
            contentScale = ContentScale.Fit, //параметры масштабирования изображения
            modifier = Modifier.padding(8.dp)
        )
        var text = File("captionText.txt").readText()
        Text(text, color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
//        Text("НВГУ ФИТМ | ИТ Музей", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Image(
            painter = BitmapPainter(image = facultyBitmap), //указываем источник изображения
//            painter = painterResource(facultyLogoWhite), //указываем источник изображения
            contentDescription = "", //можно вставить описание изображения
            contentScale = ContentScale.Fit, //параметры масштабирования изображения
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun MyContent(filesSet: SnapshotStateList<String>, fontSize: MutableState<TextUnit>, state: MutableState<WindowState>) {
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
    var text = File("$textFile").readText().replace("\t", "   ")
//    println(text)
//    val file = File("$curPath/items/copy_Rarit/alfa/Внешний_вид_кинокамеры.jpg")
    val file = File("$imageFile")
    val imageBitmap: ImageBitmap = remember(file) {
        loadImageBitmap(file.inputStream())
    }
    var isOpen by remember { mutableStateOf(true) }
    var secondWindowShow by remember { mutableStateOf(false) }
    println()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color(0xff1e63b2)))
            .padding(30.dp)
    ) {
        val stateVertical = rememberScrollState(0)
//        Box(
//            modifier = Modifier
//                .weight(5f)
//                .verticalScroll(stateVertical)
//        ) {
            val textSize:TextUnit = fontSize.value
            Text(
                text, fontSize = textSize,
                modifier = Modifier
                    .verticalScroll(stateVertical)
                    .weight(5f),
                textAlign = TextAlign.Justify,
//                maxLines = 100
            )
//        }
        VerticalScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
        Image(
            modifier = Modifier
                .weight(2f)
                .clickable(
                    onClick = {
                        println("image clicked")
                        secondWindowShow = true
                    }
                ),
            painter = BitmapPainter(image = imageBitmap),
            contentDescription = null
        )
        if (secondWindowShow) {
//            DialogWindow(
//                onCloseRequest = { isAskingToClose = false },
//                title = "Close the document without saving?",
//            ) {
//                Button(
//                    onClick = { isOpen = false }
//                ) {
//                    Text("Yes")
//                }
//            }
            Window( //второе окно для увеличения изображения
                onCloseRequest = {
                    secondWindowShow = false
                    state.value = WindowState(WindowPlacement.Fullscreen)
                },
                undecorated = true, //эти 3 строки нужны для фуллскрина без оконных кнопок
                alwaysOnTop = true,
                state = WindowState(WindowPlacement.Fullscreen),
                title = " Window Example"
            ){
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    painter = BitmapPainter(image = imageBitmap),
                    contentDescription = null
                )
                Button(
                    onClick = {
                        secondWindowShow = false
                        state.value = WindowState(WindowPlacement.Fullscreen)
                    }
                ){
                    Text("Закрыть")
                }
            }
//            state.value = WindowState(WindowPlacement.Fullscreen)
        }
//        Image(
//            modifier = Modifier.weight(1f),
//            painter = painterResource("items/copy_Rarit/alfa/Внешний_вид_кинокамеры.jpg"), //указываем источник изображения
//            contentDescription = "", //можно вставить описание изображения
////                contentScale = ContentScale.FillWidth, //параметры масштабирования изображения
//        )
    }
}

fun main() = application {
//    configureArduinoConnect()
    val curPath = System.getProperty("user.dir")
    var windowMode = File("mode.txt").readText() //для переключения режима с заголовком / без  для деплоя и отладки
    println("windowMode  = $windowMode")
    var stateMuseumWindow: MutableState<WindowState> = remember { mutableStateOf(WindowState(WindowPlacement.Fullscreen))}
    var choice = remember { mutableStateOf(0) }
    var stateFirstWindow: MutableState<WindowState> = remember { mutableStateOf(WindowState(WindowPlacement.Fullscreen))}
    var loadingWindowIsVisible = remember{ mutableStateOf(true)}

    Window( //стартовое окно
        onCloseRequest = ::exitApplication,
        //эти 3 строки нужны для фуллскрина без оконных кнопок
        undecorated = false, //сделать true, чтобы без рамок было
        alwaysOnTop = false,
        visible = loadingWindowIsVisible.value,
        state = WindowState(WindowPlacement.Floating)
//        state = WindowState(WindowPlacement.Fullscreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    choice.value = 1
                    println("choice = $choice")
                }
            ){
                Text("Настройки")
            }
            Button(
                onClick = {
                    choice.value = 2
                }
            ){
                Text("Музей")
            }
        }
    }
//    listFilesUsingJavaIO("/")
    var settingsWindowVisible = mutableStateOf(false)

    if (choice.value == 1){  //для окна с настройками
        println("Settings")
        settingsWindowVisible.value = true
        loadingWindowIsVisible.value = false
        SettingsWindow(settingsWindowVisible, choice, loadingWindowIsVisible, curPath)
    }

    if (choice.value == 2) //для основного окна с музеем
        Window(
        onCloseRequest = ::exitApplication,
        //эти 3 строки нужны для фуллскрина без оконных кнопок
        undecorated = windowMode.contains("0"), //если в файле mode.txt первое число 0, то без оконных кнопок, иначе они будут, для отладки
        alwaysOnTop = true,
        state = stateMuseumWindow.value
//        state = WindowState(WindowPlacement.Fullscreen)
        ) {
            App(stateMuseumWindow, curPath)
        }

//    secondWindow(::exitApplication)
}

@Composable
fun DropdownDemo(
    itemsInitial: List<String>,
    fontSize: MutableState<TextUnit>,
    onUpdate: (x: String) -> Unit
) { //комбобокс для выбора компорта для подключения к Arduino
    var expanded by remember { mutableStateOf(false) }
//    val items = listOf("com1", "com2", "com3")
//    val disabledValue = "B"
    var items = remember { mutableStateListOf<String>() }
    itemsInitial.forEach {
        if (!items.contains(it)) items.add(it)
    }
    var selectedIndex by remember { mutableStateOf(-1) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)
        .height(IntrinsicSize.Min)
        .fillMaxWidth()
//        .border(BorderStroke(2.dp, Color.Green))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
        ){
            Text( //заголовок комбобокса
                if (selectedIndex < 0) "Выберите экспонат: ▼" //если еще ничего не выбрано
                else items[selectedIndex] + " ▼", //если выбрано
                fontSize = 20.sp,
                modifier = Modifier.clickable(onClick = { //при нажатии на текст раскрываем комбобокс
                    expanded = true
                })
            )

            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
//                .padding(all = 50.dp)
            ){
                Text("Шрифт: ")
                FontSizeButton("+"){
                    var size = fontSize.value.value
                    size++
                    fontSize.value = size.sp
                }
                FontSizeButton("-"){
                    var size = fontSize.value.value
                    size--
                    fontSize.value = size.sp
                }
            }
        }

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
                    Text(text = s)
                }
            }
        }
    }
}
@Composable //ф-ия для создания кнопок изменения размера шрифта
private fun FontSizeButton(s: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
//                        .height(20.dp)
            .border(BorderStroke(2.dp, Color.White))
            .heightIn(max = 30.dp),
        contentPadding = PaddingValues(all = 0.dp),
        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(Color(0xff1e63b2))
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
    )
    {
        Text( s, color = Color.White)
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
