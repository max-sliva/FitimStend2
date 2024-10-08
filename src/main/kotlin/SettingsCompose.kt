import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.io.*
import javax.swing.JFileChooser

//import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsWindow(
    isVisible: MutableState<Boolean>,
    choice: MutableState<Int>,
    loadingWindowIsVisible: MutableState<Boolean>,
    curPath: String
) {
    var itemsInStend = mutableStateOf(getItemsInStend(curPath))
    // var btnRemoveIsEnabled = mutableStateOf(false)
    var stendsAddedNum = mutableStateOf(0)
    var compAddedNum = mutableStateOf(0)
    var stendList = remember {mutableStateListOf<StendBoxModel>()}
    var stendBordersList = remember { mutableStateListOf<BorderStroke>() }
    var itemsBordersMap = remember { mutableStateMapOf<String, BorderStroke>() }
    var directoryName = mutableStateOf("")
    var itemsMap2 = remember {mutableMapOf<String, Set<String>>() }//мап для хранения названия экспоната и набора из его описания и картинки
    val barForStendVisibility = remember { mutableStateOf(false) }
    val rowValue = remember { //объект для работы с текстом, для TextField
        mutableStateOf("") //его начальное значение
    }
//    var selectedItem = remember {mutableMapOf<String, Set<String>>() }
    var selectedItem =  mutableStateOf(Pair("", ""))
    Window(
        onCloseRequest = {
            //isVisible.value = false
            choice.value = 0
            loadingWindowIsVisible.value = true
        },
        visible = isVisible.value,
        undecorated = false,
        alwaysOnTop = false,
        state = WindowState(WindowPlacement.Maximized)
//        state = WindowState(WindowPlacement.Fullscreen)
    ) {
//        val windowSize = LocalWindowInfo.current.containerSize

//        val windowHeight = remember { mutableStateOf(window.height) }
//        val windowState = rememberWindowState(size = DpSize.Unspecified)
//        println("window height = ${windowState.size.height}")
        val dialogState = remember { mutableStateOf(false) }
//        DialogWindow(onCloseRequest = { dialogState.value = false }, visible = dialogState.value,
//            content = {
//                Text("dialog content")
//            }
//        )
        val stateVertical = rememberScrollState(0)
        Window( //окно с экспонатами
//            state = WindowState(WindowPlacement.Maximized),
            resizable = true,
            onCloseRequest = {dialogState.value = false },
            visible = dialogState.value,
//                    onCloseRequest: () -> Unit,
//        state: WindowState = ...,
//        visible: Boolean = ...,
//        title: String = ...,
//        icon: Painter? = ...,
//        undecorated: Boolean = ...,
//        transparent: Boolean = ...,
//        resizable: Boolean = ...,
//        enabled: Boolean = ...,
//        focusable: Boolean = ...,
        alwaysOnTop = true,
//        onPreviewKeyEvent: (KeyEvent) -> Boolean = ...,
//        onKeyEvent: (KeyEvent) -> Boolean = ...,
//        content: @Composable() (FrameWindowScope.() -> Unit)
        ) {
            ShowFlowRow(itemsMap2, itemsBordersMap, selectedItem)
//            VerticalScrollbar(
//                modifier = Modifier.align(Alignment.CenterEnd)
//                    .fillMaxHeight(),
//                adapter = rememberScrollbarAdapter(stateVertical)
//            )
//                    Text("dialog content")
        }
        Column(Modifier.fillMaxSize()){
            Row(//верхний ряд с управляющими элементами
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
//                    .background(Color.Black)
                  //  .padding(5.dp)

            ) {
                ControlBar(stendsAddedNum, compAddedNum, stendList, stendBordersList)
                BarForStend(barForStendVisibility,rowValue)
//                println("window height = $")
            }
            Row(// ряд с кнопкой загрузки экспонатов
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                        .padding(20.dp)
                    .background(Color(0xff1e63b2))
//                    .background(Color.Black)
//                    .padding(5.dp)

            ) {
                Button(
                    onClick = {
                        println("loading folder with items")
                        val fileChooser = JFileChooser(System.getProperty("user.dir")).apply {
                            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                            dialogTitle = "Выберите папку с экспонатами..."
                            approveButtonText = "Выбрать"
                            approveButtonToolTipText = "Выбрать папку в качестве папки с экспонатами"
                        }
                        fileChooser.showOpenDialog(window /* OR null */)
                        directoryName.value = fileChooser.selectedFile.path
                        println("folder = ${directoryName.value}")
                        val itemsList = listDirsUsingDirectoryStream(directoryName.value)
                        val itemsDir = directoryName.value
                        println("items list = $itemsList")
//                        var itemsMap2 = mutableMapOf<String, Set<String>>() //мап для хранения названия экспоната и набора из его описания и картинки
                        itemsList.forEach {
                            val filesList = listFilesUsingDirectoryStream("$itemsDir/$it")
                            val txtFile = filesList.find { it.contains(".txt") }
                            val imgFile = filesList.find { it != txtFile }
                            val txtFilePath = "$itemsDir/$it/$txtFile"
                            val imgFilePath = "$itemsDir/$it/$imgFile"
                            val file = File(txtFilePath)
                            val ioStream = BufferedReader(FileReader(file))
                            val firstStringInFile = ioStream.readLine()
                            val s = if (firstStringInFile=="") ioStream.readLine() else firstStringInFile //если первая строка пустая
                            itemsMap2[s] = setOf(firstStringInFile, imgFilePath)
                            itemsBordersMap[s] = BorderStroke(4.dp, Color(0xff1e63b2))

                        }
                        println("Список экспонатов:")
                        itemsMap2.forEach {
                            println("${it.key} : ${it.value}")
                        }
                    }
                ){
                    Text("Папка с экспонатами...")
                }
                Text(text = directoryName.value, color = Color.White)
            }

            LazyRow ( //центральный ряд с содержимым
                horizontalArrangement = Arrangement.spacedBy(5.dp,Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxSize()
//                    .ho
                    .border(BorderStroke(2.dp, Color(0xff1e63b2)))
//                    .border(BorderStroke(2.dp, Color(0xff00ff00)))
                    .padding(30.dp)
            ) {
                items(stendList) { model ->
                    StendBox(model = model, stendBordersList, dialogState, barForStendVisibility, rowValue, itemsInStend, selectedItem)
                }
            }
        }

    }
//    DialogWindow(visible = isVisible.value, onCloseRequest = { isVisible.value = false }) {
//        Text("dialog content")
//    }
}

private fun getItemsInStend(curPath: String): ItemsInStend? { //ф-ия для считывания текущего состояния стенда из файла
    val myFile = File("$curPath/itemsInStend.dat")
    val fin = FileInputStream(myFile)
    var oin: ObjectInputStream? = null
//        var myHash2 = HashMap<String, String>()
    var itemsInStend: ItemsInStend? = null
    try {
        println("itemsInStend.dat size = ${fin.readAllBytes().size}")
        oin = ObjectInputStream(fin)
        itemsInStend = oin.readObject() as ItemsInStend
    } catch (e: EOFException){
        println("itemsInStend.dat is empty")
    }

    println("itemsInStend from file = $itemsInStend")
    oin?.close()
    fin.close()
    return itemsInStend
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShowFlowRow(
    itemsMap2: MutableMap<String, Set<String>>,
    itemsBordersMap: MutableMap<String, BorderStroke>,
    selectedItem: MutableState<Pair<String, String>>,
    ) {
    val stateVertical = rememberScrollState(0)
    var itemsBordersList = remember { mutableStateListOf<BorderStroke>() }
    FlowRow(
        modifier = Modifier
            .verticalScroll(stateVertical),
//                    .weight(5f),,
//        horizontalArrangement = Arrangement.Start,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
//        verticalArrangement = Arrangement.Top,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = { // rows
            //example https://composables.com/foundation-layout/flowrow
            itemsMap2.forEach {
                Column( //TODO передавать данные экспоната в стенд, а здесь делать неактивным
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .border(itemsBordersMap[it.key]!!)
                        .height(200.dp)
                        .clickable{
                            println("items box left clicked, it = ${it.key}")
                            selectedItem.value = Pair(it.key, it.value.last())
                            itemsBordersMap[it.key] = BorderStroke(4.dp, Color.Green)
                            itemsBordersMap.forEach{it2->
                                if (it2.key!=it.key) itemsBordersMap[it2.key] = BorderStroke(4.dp, Color(0xff1e63b2))
                            }
                        }
                ) {
                    makeItem(it)
                }
            }
//            VerticalScrollbar(
//                modifier = Modifier
//                    .fillMaxHeight()
//                   // .padding(start = 20.dp)
//                   ,
//                adapter = rememberScrollbarAdapter(stateVertical)
//            )
        }
    )
}



@Composable //панель для установки кол-ва рядов на стенде
private fun BarForStend(barForStendVisibility: MutableState<Boolean>, rowValue: MutableState<String>) {
    if (barForStendVisibility.value) {
        Text(text = "Ряды: ", color = Color.White,)
        TextField(
            value = rowValue.value,
            onValueChange = { newValue ->
                rowValue.value = newValue
            },
        )
    }
}

@Composable
private fun ControlBar(
    stendsAddedNum: MutableState<Int>,
    compAddedNum: MutableState<Int>,
    stendList: MutableList<StendBoxModel>,
    bordersList: SnapshotStateList<BorderStroke>,
) {
    Button(
        onClick = {
            //btnRemoveIsEnabled.value = true
            stendList.add(StendBoxModel("stend", 4, stendsAddedNum.value))
            stendsAddedNum.value++
            bordersList.add(BorderStroke(2.dp, Color.Black))
//            println("stendList = $stendList")
        },
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Добавить стенд ")
    }
    Button(
        onClick = {
            //btnRemoveIsEnabled.value = false
            stendsAddedNum.value--
            stendList.reverse()
            for (element in stendList) {
                if (element.type == "stend") {
                    stendList.remove(element)
                    break
                }
            }
            stendList.reverse()
        },
        enabled = stendsAddedNum.value > 0,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Убрать стенд")
    }
    Button(
        onClick = {
            compAddedNum.value++
            stendList.add(StendBoxModel("comp", 0))
        },
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Добавить комп")
    }
    Button(
        onClick = {
            compAddedNum.value--
            stendList.reverse()
            for (element in stendList) {
                if (element.type == "comp") {
                    stendList.remove(element)
                    break
                }
            }
            stendList.reverse()
        },
        enabled = compAddedNum.value > 0,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Text("Убрать комп")
    }
}